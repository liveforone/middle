## 예약취소시 현재시간을 어떻게 검증해야할까?
* 예약취소는 이번 프로젝트에서 정책상 반드시 1시간 이상 전에 요청해야한다.
* 예약 취소는 예약 서비스에서 이루어지고,
* 만약 예약 취소가 가능하다면, 타임테이블 서비스의 잔여 예약 가능자수를 원상복구해야한다.
* 그렇다면 어떻게 현재시간을 검증해서 예약취소의 가능/불가능을 판가름 할까?

## 솔루션
* 기본적으로 예약 가능 시간(hour)과 예약 가능 분(minute)이 저장되어있다.
* 예약 가능시간 - 현재 시
* 예약 가능분 - 현재 분
* 시에는 계산이 용이하도록 100을 곱해준다. 이에 따라 정책시간인 1시간은 100이되고, 시의 연산에도 100을 곱한다.
* 분은 그대로 사용하며, (예약가능시 - 현재시) + (예약가능분 - 현재분) 의 연산을 한다.
* 위의 연산한 결과값이 정책시 * 100 한 값이상일 경우 update 쿼리를 처리하고,
* 이상이 아닐경우 update처리하지 않는다.

## 간단하게 표현
```
계산된 시 = (예약가능시 - 현재시) * 100
계산된 분 = 예약가능분 - 현재분

계산된 시 + 계산된 분 > 정책시 * 100

위의 연산이 update쿼리의 where절에 들어감.
```

## 쿼리 dsl로 표현
```
private final long LIMIT_TIME = 100;

public static BooleanExpression plusRemainingCondition(Long id) {
    LocalTime currentTime = LocalTime.now();

    long nowHour = currentTime.getHour();
    long nowMinute = currentTime.getMinute();

    NumberExpression<Long> calculatedTime = timetable.reservationHour
            .add(-nowHour)
            .multiply(HUNDRED)
            .add(timetable.reservationMinute.add(-nowMinute));

    return timetable.id.eq(id)
            .and(calculatedTime.goe(LIMIT_TIME));
}
```