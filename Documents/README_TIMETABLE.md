## 타임테이블 서비스 소개
* 타임테이블 서비스는 가게에서 예약 가능한 주문을 담고 있는 서비스 입니다.
* 예약 가능자 수를 체크해 마이너스 혹은 플러스를 하여 예약 서비스에 적절한 bool 값을 리턴하는 역할도 합니다.
* 예약을 하기전 첫번째 관문역할을 합니다.
* 각 타임테이블 별로 예약 가능한 수를 조정하고, 저장하고 있기도 합니다.

## 요구사항
* 상점 삭제시(회원탈퇴 전이) 타임테이블은 전체 벌크 삭제처리한다.
* 상점은 상점이 등록되어있는 OWNER 회원만 등록가능하다.
* 등록 제한은 존재하지 않는다.
* 페이징시 15(limit = 15)개씩 페이징한다.
* 시간 수정이 가능하며, 시/분을 둘다 동시에 변경하는 것이 가능하고,
* 시만, 혹은 분만 변경도 가능하다. 모두 하나의 api에서 처리한다.
* 기본 예약가능자수를 저장하여 매일 밤 12시 정각마다, 스케줄러를 이용해 배치처리하여 남은 예약 가능자 수를 기본 예약 가능자 수로 복구한다.
* 기본적인 예약은 기한이 하루이기 때문이다.
* 타임테이블에서 남은 예약 가능자 수를 마이너스하는 로직은 '예약'이 발생할때 제일 먼저 일어나는 이벤트이다.
* 타임테이블에서 마이너스를 하고, 변경된 row가 0이하라면 false를 리턴한다.
* 이를 통해서 예약의 가능/불가능을 예약 서비스로 전달한다.

## API 설계
```
[GET] : /timetables/{shopId} : 상점에 속한 타임테이블 페이징(15개)
[GET] : /timetable/detail/{id} : 타임테이블 상세조회
[POST] : /timetable/create/{shopId} : 타임테이블 등록
[PUT] : /timetable/update-time/{id} : 시간 업데이트(시/분 하나만도 가능)
[DELETE] : /timetable/delete/{id} : 타임테이블 삭제
```

## Json Body 
```
[타임테이블 생성]
{
  "reservationHour": 11,
  "reservationMinute": 10,
  "basicRemaining": 10
}

[시간 업데이트]
{
  "reservationHour": 12,
  "reservationMinute": 45
}
```

## 서비스간 통신
### 예약 가능자수 마이너스 처리 후 bool 값 리턴
* 예약 서비스에 provide controller 제공
* 예약시 예약가능자수 마이너스 처리후,
* 변경된 데이터가 존재한다면 true를, 아니면 false를 리턴하여 동시성을 잡아낸다.
```
[POST] : /reserve/timetable
```
### 상점 삭제시 타임테이블 삭제
```
topic : remove-timetable
request : shopId
```
### 상점 검증(유저네임 요청, 상점 id 리턴) 
* 페인 클라이언트로 사용한다.
```
url : /provide/shop/{username}
```

## 예약시간 기본 설정
* 기본적으로 시/분을 자유롭게 설정이 가능하고, 
* 분을 기입하지 않으면 0으로 처리되어 들어간다.(=> 정각으로 처리)

## 예약시 가능자 수 마이너스 매커니즘
* 해당 id에 맞는 데이터 중(한개임), 예약 가능자수가 0 초과인 경우의 데이터만 변경한다.
* 그리고 영향받은 row의 수를 가져온다.
* 영향받은 row의 수가 0 초과라면 마이너스가 정상적으로 완료되었다는 것이고,
* 아니라면 정상적으로 처리되지 않은것이다.
* 이를 boolean으로 예약 서비스에게 넘겨준다.
* 기본적으로 where절의 조건을 이용해 동시성문제를 차단하고, 
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
              timetable.remaining.add(TimetableRepoUtil.MINUS_ONE)
        )
        .where(TimetableRepoUtil.minusRemainingCondition(id))
        .execute();

  return affectedRows > TimetableRepoUtil.ZERO_VALUE;
}

[where 조건절]
public static BooleanExpression minusRemainingCondition(Long id) {
    return timetable.id.eq(id)
        .and(timetable.remaining.gt(ZERO_VALUE));
}
```

## 배치 처리
* 매일 저녁 12시 정각이 되면 하루동안 변경되었던 예약 가능자 수를,
* 기본 예약 가능자 수로 복구시킨다.
* 기본적으로 배치는 스케줄러를 이용한다.
* 모든 데이터에 대해 remaining(남은 예약 가능자 수)를
* basicRemaining(기본 예약 가능자수) 으로 변경하면된다.
### 기준 컬럼
* 기본 예약 가능자수 컬럼은 복구해야하는 기준이 된다.
* 이 경우 이 값은 절대로 변경되어서는 안된다.
* 따라서 updatable = false로 설정하여서 변경불가능 하도록 한다.
* 이러한 매커니즘의 단점은, 기본 예약 가능자수를 수정하려면
* 타임테이블을 삭제하고, 새롭게 다시 만들어야한다는 것이다.
* 그러나, 이러한 예약 서비스를 사용하는 비즈니스를 생각해볼때, 이러한 예약 가능자수는
* 현실세계에서, 즉 호텔의 방의 수나 미용실의 직원 수 혹은 의자 수 등 잘 바뀌지 않는 것들이다.
* 따라서 이러한 변경이 존재할경우 삭제하고 만들라고 권하는 것은 그렇게 큰 불편함을 야기하지 않는다.