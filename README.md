# Middle
* 예약 중계 서비스입니다.
* **"MSA에서 경계나누기"**
* **"추천 알고리즘"**
* **"DB 동시성 문제 해결"**
* 위의 3가지 키워드를 집중적으로 고민하고 다룬 프로젝트 입니다.

# 0. 목차
1. [프로젝트 소개](#1-프로젝트-소개)
2. [프로젝트 주요 고민 및 해결](#2-프로젝트-주요-고민-및-해결)
3. [요구사항](#3-요구사항)
4. [서비스별 설계 문서](#4-서비스별-설계-문서)
5. [프로젝트 설계 문서](#5-프로젝트-설계-문서)

# 1. 프로젝트 소개
## 소개
* 병원/미용실/극장 등 그 어떤 곳이던, 
* 예약이 필요한 모든 곳에서 사용가능한 "범용 예약 중계 서비스" 입니다.
* 도메인 모델 패턴을 적용하였고, 마이크로 서비스 아키텍처를 적용하였습니다.
* 계층 별 주요 로직에는 모두 선언형으로 작성하여 복잡도를 낮추고 응집도를 극대화 시켰습니다.
* [스타일 가이드](https://github.com/liveforone/study/tree/main/%5B%EB%82%98%EB%A7%8C%EC%9D%98%20%EC%8A%A4%ED%83%80%EC%9D%BC%20%EA%B0%80%EC%9D%B4%EB%93%9C%5D)를 지키며 개발하였습니다.
* 해당 프로젝트는 3가지의 아래의 주요 주제를 집중적으로 고민하고 다루었습니다.
## 주요 주제 및 고민
#### 1. MSA에서 경계나누기
* MSA에서 어떻게 경계를 나누고 어떤 기준으로 서비스를 분리했는지 고민하였습니다.
* [MSA에서 경계나누기](https://github.com/liveforone/middle/blob/master/Documents/DIVIDE_BOUNDARIES_MSA.md)
#### 2. 추천 알고리즘
* 가게를 추천하는 가게 추천 시스템(가게 광고)이 해당 서비스의 주 수익모델입니다. 
* 추천 알고리즘의 다양한 사례를 살펴보고, 
* 인프라가 많이 갖추어지지 않은 작은 기업에서도 적용할 수 있는 추천 알고리즘에 대해 고민해보았습니다.
#### 3. 동시성 문제 처리
* 예약을 처리하면서 잔여 예약가능수를 마이너스 하는 과정에서 동시성문제가 발생했습니다.
* 이 문제는 논리적 에러/장애를 야기하는 중요한 문제입니다.
* 성능을 떨어뜨리지 않으면서 동시성문제를 처리하는 것을 고민해 보았습니다.
* 동시성 문제 해결은 타임테이블 서비스에서 아주 잘 확인할 수 있습니다.(예약 서비스보다 타임테이블 서비스에서 더 잘 드러남)
* [동시성 문제 해결](https://github.com/liveforone/middle/blob/master/Documents/SOLVE_CONCURRENCY_PROBLEM.md)
## 기술 스택
* Framework : Spring Boot 3.0.5~ & Spring Cloud
* Lang : Java17
* Data : Spring Data Jpa & Query Dsl & MySql
* Security : Spring Security & Jwt
* Service Communication : Apache Kafka(Async), Open Feign Client(Sync)
* Container : Docker & Docker-compose
* Test : Junit5
* Util : Apache Commons Lang3, LomBok
* Batch : Spring scheduler + ORM (배치 프레임워크 이용 없이 순수 ORM과 스케줄러로 구현)

# 2. 프로젝트 주요 고민 및 해결
* [MSA에서 경계나누기](https://github.com/liveforone/middle/blob/master/Documents/DIVIDE_BOUNDARIES_MSA.md)
* [추천 알고리즘]()
* [음수가 되면 안되는 컬럼에서 동시성 문제 해결](https://github.com/liveforone/middle/blob/master/Documents/SOLVE_CONCURRENCY_PROBLEM.md)
* [배치 처리는 어떤 메커니즘으로 만들어야할까](https://github.com/liveforone/middle/blob/master/Documents/BATCH_PROCESSING.md)
* [조회 쿼리와 페이징 성능 최적화]()
* [count 쿼리 성능 최적화]()
* [repository 유틸 클래스에서 Q클래스 중복]()
* [UUID 필요성]()
* [예약취소시 현재시간을 어떻게 검증해야할까?](https://github.com/liveforone/middle/blob/master/Documents/SOLVE_CANCEL_RESERVATION_TIME_VALIDATION_PROBLEM.md)

# 3. 요구사항


# 4. 서비스별 설계 문서
* [유저 서비스](https://github.com/liveforone/middle/blob/master/Documents/README_USER.md)
* [상점 서비스](https://github.com/liveforone/middle/blob/master/Documents/README_SHOP.md)
* [추천 서비스](https://github.com/liveforone/middle/blob/master/Documents/README_RECOMMEND.md)
* [타임테이블 서비스](https://github.com/liveforone/middle/blob/master/Documents/README_TIMETABLE.md)
* [예약 서비스](https://github.com/liveforone/middle/blob/master/Documents/README_RESERVATION.md)
* [리뷰 서비스]()

# 5. 프로젝트 설계 문서
* [도메인 모델 패턴]()

## 서비스
리뷰 서비스(good/bad로 평점 매김)


## 수익 모델
이전 프로젝트에서는 수익모델을 직접 구현해보지 않고 구상만 해보았으나,
이번 프로젝트에서는 새롭게 플랫폼에 맞는 수익모델을 구상해보고
구현까지 해보았다.(비즈니스 밸류 창출)



## UUID 필요성 - 고민한점
* [uuid링크](https://wakestand.tistory.com/361)
* email과 같이 외부에 종속되는 요소를 식별자로 사용하지 않고 uuid를 만든점 기술, 더하여 uuid의 중복을 완전히 걱정하기 싫어서 문자열도 덧댐, 이메일변경시 다바뀜(식별불가능, 주민번호의 사례를 인용)

## 도메인 모델 패턴 적용 이유 - 설계
돈이 오가는 송금 서비스의 경우 도메인 모델을 사용하면 그 효과가 극대화된다.
직접 만들어보니 도메인 모델을 사용하지 않아서 복잡한 비즈니스 로직때문에 어플리케이션 레이어(service 계층)이 아주 더러워지고, 이해하기 난해했는데, 이것을 적용하면 아주 제격이겠다는 생각이 들었다.
이러한 이유로 향후 프로젝트들에 도메인 모델패턴을 적용하고 있다.

## 기술 - 고민점에 적기
1. 비동기 -> 업데이트, insert, delete
command의 경우 비동기로, 쿼리는 동기로
조건은 void, 즉 리턴값이 없어야함. 커맨드, 즉 명령에 관한 작업은 모두 비동기로 처리(명령만 내리고 바로 할일을 하게하기위해)
비동기는 잘 사용해야함. 상품 같은 경우 상품이 등록되고 그 상품의 id를 파일의 itemId에 저장하는 연관관계를 맺고 있음.
즉 순서가 중요. 그러나 이러한 비동기는 순서를 없애버림.
따라서 파일이 정상적으로 저장되지 않음.
이러한 것을 잘 파악하고 사용해야함.
업데이트 같은 경우 대다수의 경우에서는 문제가 없으나 생성 같은 경우는 잘 염두하고 사용해야함.
삭제시에도 주의 아이템처럼 순서 중요한경우에는 생성, 업데이트, 삭제 모두 주의하라
결론 : 메서드 호출 순서, void 확인, create의 경우 비동기 호출 금지
테스트할때에는 async를 주석처리하고 테스트
2. 배치 => 배치로 업데이트할땐 더티체킹이 아니라 배치 쿼리 메소드 만들어서 벌크로 연산한다. 더티체킹하면 난리난다...
3. 매퍼적극 활용(매퍼를 제외한 곳에서 setter사용 금지) => 이를 통해 선언형 스타일 적극 활용
mapper의 권한을 더욱 강하게 위임한다.
매퍼를 제외한 곳에서 dto의 setter 사용을 금지하여 선언형 프로그래밍 스타일을 극대화한다.
4. controller advice로 커스텀 예외 처리
5. 도메인 모델 패턴 => 더티체킹을 극대화로 활용하게됨 => 리파지토리는 오로지 조회로직만 남게됨, 또한 업데이트 쿼리들은 모두 사라짐 => cqrs처럼 리파지토리는 오로지 조회에만 집중할 수 있음. => 더티체킹을 기반으로 코드를 작성해보면, 깔끔하게 유지보수 가능한 코드가 나오는지 알 수 있습니다. 그리고 수 많은 update 쿼리가 없어도 되지요. => 엔티티를 항상 조회해야 하는 성능 부담도 사실 PK 기반의 데이터 단건 조회이기 때문에 전체 애플리케이션으로 보면 성능에 거의 영향을 미치지 않습니다.
도메인 모델은 더하고 빼는 연산에 유의하여야한다. 
this.object += 값
this.object -= 값
또한 order시에 location이 있다면, orderLocation으로 등등 주문에 관한 세부 정보가 커진다면 그것을 따로 모델로 빼서 이벤트 처리를 하도록 한다.
6. 더티체킹 적극 활용(save를 제외하고는 모두 save메소드 호출할 필요없음) -> Put mapping만 사용
7. 페이징이나, 리스트로 가져올때에는 가져올 데이터가 정해져있다. 
전체 데이터를 가져오는 경우가 아니라면 dto projection을 이용해 가져온다. 프로젝션에서 페치조인을 쓰면안된다.(조인을 할거면 그냥 조인만)
주로 대량의 데이터를 조회할때 성능 이슈가 발생하는데, 필요한 만큼의 데이터를 이때는 가져올 수 있기 때문이다. 
단건 조회의 경우 사용자에게 리턴하는 것 이외에 데이터 validation 체킹등에도 활용되므로 어떤 값을 사용할지 몰라서 다 가져오는 것이 좋다. 
다만 validator도 컴포넌트로 등록하여 리포지토리를 직접 사용하고 있다.
따라서 서비스에서 컨트롤러로 전달하는 dto는 반드시 response이며, 이 response에는 전달하고자 하는 값만 사용해도 된다.
단건을 이용해 밸리데이션 같은 곳에 활용하는 경우에는 직접 참조할것이기 때문이다.
즉 혹시나 이런 로직이 존재한다면 떼어놓고 리파지토리에 직접 접근하도록 변경하고, response dto는 오로지 전달하고자 하는 값만 들어있도록 한다.
고민한점에 작성한다.
8. 인덱스 환경을 항상 고려한다. 특히 여러개의 데이터(리스트 등)를 가져오는 환경에서는 인덱스를 반드시 태우도록 하며, 특히 검색의 경우에는 상당히 많은 양의 데이터가 리턴될 확률이 높다. 이 경우 like쿼리 그중에서도 시작어로 검색(keyword%) 연산자로 검색쿼리처리한다. 이는 쿼리 dsl에서 statrsWith()메소드를 사용하면 된다.
또한 이를 반드시 코드 요구사항에 명시한다.
9. validation시 단건 조회를 한다. 이때 validation을 위한 쿼리를 따로 만들어라. 주로 id만 조회하는 쿼리(한개 컬럼 조회)를 이용하면 성능에도 큰 영향을 미치 않을 뿐더러 null check하기도 아주 좋다.
10. repo 단에서도 dto 프로젝션이나 booleanexpression으로 페이징처리할때 유틸클래스 만들어서 처리하기
11. Object.equeals 같은 것으로 판별하는 경우(주로 문자열)에는 밸리데이터로 옮겨서 밸리데이터를 사용하도록 하라. => 밸리데이터에서만 검증하고, 그 이외에 검증 로직을 또 넣지 말기
12. 동적쿼리 처럼 함수를 집약적으로 설계한다. 같은일을 하는 함수를 호출부에 따라 분리하지 말고 추상화 시켜서 하나로 만들어서 함수 내부에서 분류해서 작동하도록 한다.
13. 상수를 담을 그릇은 final class로 한다.
14. 밸리데이터는 선언형으로 하고, 선언형으로하기위해 컨트로럴 어드바이스와 커스텀 예외를 등록한다. dto validation 시 주의 점은 @Email 어노테이션 다음에 꼭 @NotBlank도 달아주어야 한다는 것이다. -> 컨트롤러를 선언형으로 함
15. 상수는 static import 형태로 사용한다. 그러나 static import는 여러 곳에서 사용되는 상수에는 사용하면 안되고, 오로지 한 곳에서, 누가봐도 딱 그 상수클래스가 사용된 경우에 사용하자. 또한 private 생성자를 만들어서 생성을 막는다. 이때 noargs(access = private)이 아닌 직접 만들어서 사용한다.
16. 리파지토리 유틸클래스에서 큐클래스 중복되는것을 static 블록과 final로 선언하여 동적으로 생성된 것 까지 참조 가능하도록 설정한다.
17. 상수 클래스들의 네이밍은 뒤에 constant붙이지 않고 그냥 명시한다.디렉터리 이름이 constant여서 상관없다.(도메인 상수클래스처럼 정말 상수라는것 이외에 이름이 없는 경우가 아니면 모두 적용한다.)
18. last id에 default value 반드시 넣기
19. provide controller는 하나만 만든다. 특정 서비스에 종속되게 네이밍하지 않는다.

## 프로젝트시 확인
1. async 와 Authorization 폴더 복붙
2. QClass 생성전 gernerated gitignore에 등록
3. gateway 서비스에 등록
4. 프로듀서 필요시 gson 등록
5. 1대1인경우 fk에 unique 걸기
6. 빈 객체의 id를 가지고 null체크, id가 없을 경우에는 대표 컬럼
7. feign 사용시 log level 설정
8. 서킷브레이커에 null 체크후 null인경우 new로 빈객체 리턴
9. 상수 클래스들의 네이밍은 뒤에 constant붙이지 않고 그냥 명시한다.디렉터리 이름이 constant여서 상관없다.

## 페이징
자유검색 기회, 동적쿼리로 하나의 메서드로 다양한 검색을 지원
검색 조건 순서로 인덱스 만들어야함
도시 -> 도로명 -> 이름
동적쿼리시 null이면 where에서 무시함
request param은 문자일 경우 required=false로
숫자일 경우 default value = "0"으로 설정하면 null 허용이 된다.
페이징시에 default value(숫자)와 null(문자) 허용

## count 쿼리 성능 최적화
지금은 데이터가 적어서 실감이 안나지만 향후 데이터가 커지면 매번 count쿼리시 자원소모가 너무 심해질것이다.
따라서 테이블을 하나 만들어서 단 하나의 데이터를 저장한다.
그것은 count쿼리로 날린 전체 테이블의 수이고, 이를 저장해 하루 혹은 1시간 마다 배치로 다시 전체 테이블의 수를 업데이트하는(광고를 등록하는 수가 하루에 많지 않을 것이기 때문에 매번 count 쿼리를 날리는게 상당히 부담이 된다.) 방식을 사용하면 count 쿼리를 매번 날릴때와 달리 성능이 어마어마하게 최적화 될것이다.
애초에 데이터를 읽는다는 것이 속도가 더 느리다.
즉 count(컬럼)이 count(*)보다 느리다.
또한 최고 id를 가져오는 것을 사용 불가능
https://parksay-dev.tistory.com/m/48

## 모듈화시 Q클래스 중복 - 고민점
Qclass를 이중으로 참조하는것을 막기위해
클래스 내부에서 private static final QClass class;로 선언하고
static {
        shop = QShop.shop;
    }
을 이용해서 static 블럭으로 클래스 변수를 초기화시켜 사용한다.
이렇게 생성한 큐클래스는 동적으로 생성된 큐클래스를 참조하는 것도 가능해져서 할 수 있는것이 늘어난다.
후에 util클래스를 리파지토리임플에서 static import처리까지해주면 아주 깔끔한 코드가 탄생한다.

## 할것
* api네이밍 리팩터링(직관적, rest 스타일)
* 전체 api를 오로지 데이터 전달만 하는 api로 변경하고, 그 전에 화면 정의서 먼저 만들기

## 문서화
* count쿼리 성능 최적화 문서 만들고 추천 서비스 리드미에 링크 추가
* 페이징 문서 만들어서 no offset이랑, 페이지 사이즈 고정 명시 + 스타일 가이드에도 작성

## 테스트 할 것
* 리뷰 서비스 제작 후 상점 좋아요/싫어요 정상 작동 테스트
* 회원탈퇴시(=상점 삭제시) 리뷰 서비스 삭제