## 타임테이블 서비스 소개
* 타임테이블 서비스는 가게에서 예약 가능한 주문을 담고 있는 서비스 입니다.
* 예약 가능자 수를 체크해 마이너스 혹은 플러스를 하여 예약 서비스에 적절한 bool 값을 리턴하는 역할도 합니다.
* 예약을 하기전 첫번째 관문역할을 합니다.
* 각 타임테이블 별로 예약 가능한 수를 조정하고, 저장하고 있기도 합니다.

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

## 예약 가능자 수 마이너스 매커니즘
* 해당 id에 맞는 데이터 중(한개임), 예약 가능자수가 0 초과인 경우의 데이터만 변경한다.
* 그리고 영향받은 row의 수를 가져온다.
* 영향받은 row의 수가 0 초과라면 마이너스가 정상적으로 완료되었다는 것이고,
* 아니라면 정상적으로 처리되지 않은것이다.
* 이를 boolean으로 컨트롤러에게 넘겨준다.
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

## 예약시간 기본 설정
기본적으로 시간 설정이 가능하고, 분을 기입안하면 0으로 처리되어 들어간다.
분을 기입하면 분이 들어간다.
분을 기입하지 않는것에 대해 validation은 하지 않는다.

## 동시성 제어
동시성을 제어하려면
예약을 할때 예약이 먼저 일어나면 안되고(서비스가 분리되어 있으니깐.)
타임테이블에서 커맨드를 처리하고 완료하여 리턴하면 나머지 예약 시스템이 동작한다.

## 서비스 분리
단일 책임 원칙을 위해서 서비스 끼리는 종속적이면 안된다.
그러나 기존 설계는 너무 서비스끼리 종속적이다.
이를 분리하면
예약 서비스에서 타임테이블로 쿼리를 보내서 예약 가능자수를 마이너스한다.
이때 update쿼리가 불가능하여 에러를 내면 bool 값으로 false를 보내고,
성공한다면 true를 보낸다.
예약 서비스에서는 이를 validator로 체크하여 최종결과를 사용자에게 리턴한다.

## 서비스간 통신
provide controller로 예약 가능자수 마이너스 처리 후 bool 값 리턴
```
/reserve/timetable
```

상점 삭제시 타임테이블 삭제
```
topic : remove-timetable
request : shopId
```

상점 검증(유저네임 요청, 상점 id 리턴) 페인 클라이언트
```
url : /provide/shop/{username}
```

배치처리시 굳이 새로운 테이블을 만들필요가 없다.
orm(jpa)에서 제공하는 좋은 기능인 updateable = false가 있기 때문이다.
이를 통해 해당 시간에 제공가능한 예약가능자수를 저장하고 묶어서 변경이 불가능하도록 한후,
remaining, 즉 남은 예약 가능자수를 이용해 예약을 처리하고,
배치를 이용해 remaing 값은 기본 예약 가능자수로 변경처리하면된다.
단점은 딱 하나 있는데, 바로 기본 예약자수를 변경할때이다.
그러나, 숙박업소, 영화관, 미용실등은 해당 시간에 예약자 수가 변동될 일은 거의 없으며, 변동을 원하면 해당 시간을 삭제하고 새로만들면 된다.

## 배치 - 후에 문서화
스케줄러를 이용해 새벽시간에 벌크연산으로 처리하도록 하면된다.(insert, update, delete)
배치는 반드시 로그 남긴다.
[스케줄러](https://itworldyo.tistory.com/40)

## 시간표 + 예약가능 수 구조
3개의 테이블이 필요함.
예약(주문처럼 예약 레코드? 예약한 예약) 테이블, 
가게 주인이 올린 예약, 하루 전체에 대한 예약
각 예약별 가능한 예약자 수
가게 주인이 올린 예약은 주인이 삭제하지 않는이상, 삭제되지 않음.
가게 주인이 예약을 등록하면 가능한 예약자 수 테이블에 예약자 수가 들어감(업데이트를 하게되면 이 테이블도 업데이트)
예약을 하면 가게 주인이 올린 예약에서 마이너스됨, 이때 예약 가능 수 테이블은 건들면 안됨
예약 가능 수 테이블은 가게 주인이 예약자 가능수를 변경할때 변경되는 것임.
하루가 지나 0시 1분이 되는 순간, 예약 가능 수 테이블에서 값을 가져와 배치(스케줄러)로 가게 주인이 올린 예약 수를 원복 시킴. 플러스나 마이너스 연산자가 아니라 아예 변경시키는 set연산자로 변경시킴
```
UPDATE TB1 A
  JOIN TB2 B
   SET A.AGE = B.AGE
 WHERE A.id = B.aId(fk);
```
[다른 테이블 참조해 전체 업데이트](https://lifelife7777.tistory.com/99)