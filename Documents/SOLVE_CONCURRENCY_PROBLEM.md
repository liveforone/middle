## 동시성 문제
* 예약을 처리하면서 동시성문제를 마주하게 되었습니다.
* 기본적인 예약 메커니즘은 다음과 같습니다.
* 예약 서비스의 예약 api를 호출하게되면,
* 예약서비스는 타임테이블 서비스로 예약하려는 시간대의 타임테이블의 잔여 예약가능수를 마이너스하라는 요청을 보냅니다.
* 이 요청을 하면서 동시성 문제가 발생하여 잔여 에약가능수가 0미만이 되어버리는 일이 충분히 발생할 수 있습니다.

## 동시성 제어
* 여러 서비스에서 동시성을 제어하려면 반드시 동기로 작동되어야하며,
* 서비스가 분리되어있기때문에 먼저 요청을 처리하는 서비스부터 순차적으로 요청을 처리하여,
* 마치 게이트웨이처럼 한 서비스, 한 서비스를 거치며 완료했는지, 실패했는지에 대한 결과값을 최종 처리 서비스에 보내주어야합니다.
* 즉 현재 프로젝트에서는 타임테이블 서비스에서 요청을 처리하여 동시성을 제어하고,
* 이에 대한 성공/실패 결과를 예약 서비스에 리턴해주어야한다.

## 예약시 잔여 예약 가능자 수 마이너스 매커니즘과 동시성 처리
### 기본적인 해결책
* 먼저 조회하여 잔여 가능수를 체크하는 방법도 있지만, 이 경우에도 동시성 문제에서 자유로울 수는 없다.
* 조회하였을때에는 잔여수가 남아있어도, update 처리하면서 잔여수가 남아있지 않을 수 있기 때문이다.
```
Timetable timetable = repo.findOneById(id);
if (timetable.remaining > 0) { //조회하여 검증
    repo.minusRemaining(id);  
} 
```
### 필자의 해결책
* update 쿼리에서 해결하였다. 
* update를 처리하면서 조건절에서 동시성을 제어할 수 있는 조건을 거는것이다.
* 일반적인 update쿼리는 id로 값을 찾아서 바로 값을 update해버렸겠지만,
* 필자의 경우에는 id로 값을 찾고, 잔여 예약수 컬럼의 값이 0 초과인 경우에만 update를 처리하게 하였다.
* 쿼리에 표현하면 아래와 같다.
```
UPDATE timetable 
    SET remaining = remaining - 1 
    WHERE id = id and remaining > 0;
```
### 어떻게 업데이트가 성공했는지 파악하는가?
* query dsl 에서 업데이트 쿼리가 날라가면 변경된 데이터 수가 리턴된다.
* 즉 업데이트가 성공적으로 이루어졌다면, 리턴값이 1 이상이 되어야한다.
* 영향받은 row의 수가 0 초과라면 마이너스가 정상적으로 완료되었다는 것이고,
* 아니라면 정상적으로 처리되지 않은것이다.
* 이를 판별하여 boolean으로 예약 서비스에게 넘겨준다.
* 기본적으로 update 쿼리의 where절의 조건을 이용해 동시성문제를 차단하고, 
* 동시성 문제 때문에 예약이라는 긴 이벤트에서 작은단위의 이벤트들이 많이 있는데,
* 마치 게이트웨이 처럼 동시성 문제가 발생하여 예약이 불가능하면,
* 즉각적으로 이러한 정보를 다른 서비스에게 넘겨줌으로써, 
* 동시성문제가 발생하는 이벤트를 효과적으로 처리하였다.
```
[repos]
public boolean minusRemaining(Long id) {
  long affectedRows = queryFactory
        .update(timetable)
        .set(
              timetable.remaining,
              timetable.remaining.add(-1)
        )
        .where(minusRemainingCondition(id))
        .execute();

  return affectedRows > 0;
}

[where 조건절]
private BooleanExpression minusRemainingCondition(Long id) {
    return timetable.id.eq(id) 
        .and(timetable.remaining.gt(0));  //remaining > 0
}
```