## 예약 서비스
* 예약 서비스는 예약을 담당하는 서비스로 타임테이블 서비스와 연관이 깊습니다.
* 타임테이블 서비스와 최대한 분리시켰지만, 서로 연계되는 특성이 남아 있습니다.
* 예약은 많은 양의 validation으로 동시성문제를 여러번 검증해나고 있습니다.
* 동시성문제는 사실 타임테이블 서비스와 예약서비스 모두의 문제입니다.
* 이러한 문제를 풀려는 노력이 담겨있습니다.
* 전체 문서의 고민점에 가시면 동시성문제를 해결한 방법이 기술되어 있습니다.

## 상세 요구사항
* 상세 예약 페이지는 username 검증을 통해 접근을 제한한다.
* 회원들은 자신들이 예약한 예약 리스트를 볼 수 있습니다.
* 모든 페이징은 15개로 제한합니다.
* 상점 주인 또한 상점에 속한 예약리스트를 볼 수 있습니다.
* 예약시 타임테이블 서비스와 요청을 주고 받음으로 동시성을 제어합니다.
* 타임테이블 서비스로 부터 돌아오는 boolean 응답에서 상대 서비스가 장애시 false를 리턴합니다.
* 예약 취소시 date로 날짜를 검증하고 타임테이블 서비스에 요청을 보내 시간을 한 번 더 검증합니다.

## API 설계
```
[GET] /reservation/detail/{id} : 예약 상세조회
[GET] /reservation/me : 나의 예약 리스트
[GET] /reservation/shop/{shopId} : 상점 예약 리스트
[POST] /reserve/{timetableId} : 예약
[PUT] /cancel/{id} : 예약 취소
```

## 서비스간 통신
### 타임테이블 id로 상점 id 리턴
* feign client
```
[POST] /get/shop-id/{id}
```
### 예약시 타임테이블 잔여 예약자 수 검증 및 마이너스
* feign client
```
[POST] /reserve/timetable/{id}
```
### 예약 취소시 타임테이블 시간 검증 및 잔여 예약자수 복구
* feign client
```
/cancel/timetable/{id}
```

## 날짜 검증
* 현재 날짜와 예약 날짜는 반드시 같아야한다.
* 서비스 정책상 그날 바로 예약을 잡고 일을 진행하는 것이기 때문에, 
* 반드시 현재 날짜와 예약 날짜가 동일해야한다.
* 아래는 코드이다.
```
LocalDate savedDate = LocalDate.of(2022, 1, 1); // 저장된 LocalDate 값
LocalDate currentDate = LocalDate.now(); // 현재 날짜

if (!currentDate.equals(savedDate)) { // 두 날짜가 같은지 확인
    //에러발생
}
```