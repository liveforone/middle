# Middle
* 예약 중계 서비스입니다.
* **"MSA에서 경계나누기"**
* **"추천 알고리즘"**
* **"코드로 푼 동시성 문제"**
* 위의 3가지 키워드를 집중적으로 고민하고 다룬 프로젝트 입니다.

# 0. 목차
1. [프로젝트 소개](#1-프로젝트-소개)


# 1. 프로젝트 소개
## 소개
* 병원/미용실/극장 등 그 어떤 곳이던, 
* 예약이 필요한 모든 곳에서 사용가능한 "범용 예약 중계 서비스" 입니다.
* 도메인 모델 패턴을 적용하였고, 마이크로 서비스 아키텍처를 적용하였습니다.
* 해당 프로젝트는 3가지의 주요 주제를 집중적으로 고민하고 다루었습니다.
## 주요 주제 및 고민
#### 1. MSA에서 경계나누기
* MSA에서 어떻게 경계를 나누고 어떤 기준으로 서비스를 분리했는지 고민하였습니다.
#### 2. 추천 알고리즘
* 가게를 추천하는 가게 추천 시스템(가게 광고)이 해당 서비스의 주 수익모델입니다. 
* 추천 알고리즘의 다양한 사례를 살펴보고, 
* 인프라가 많이 갖추어지지 않은 작은 기업에서도 적용할 수 있는 추천 알고리즘에 대해 고민해보았습니다.
#### 3. 동시성 문제 처리
* 예약에서 동시성 문제는 논리적 에러/장애를 야기하는 중요한 문제입니다.
* 동시성 문제를 DB단이 아닌 프레임워크 단에서 코드로 해결한 방법을 보실 수 있습니다.
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

## 서비스
유저 서비스
상점 서비스
추천 서비스
시간표 서비스(timetable 상점에서 등록한 예약과, 그 예약들의 예약가능자 수를 저장하는 두개의 테이블을 저장함)
예약 서비스(사용자가 예약하는 서비스, 예약 기록 저장)
리뷰 서비스(good/bad로 평점 매김)

## 예약서비스 동시성 문제
같은 시간에 가능한 예약이 여러개일 수가 있다.
예시로 한 병원에 4시반에 의사 3명이 동시에 진찰을 볼 수 있다면 그 시간에 3명의 환자를 예약받을 수 있을것이다.
그러나 하나만 예약이 가능할때 거의 동시에 예약을 한다면 어떻게 처리해야할까?
이를 통해 예약이 예약 가능 수를 초과한다면..?
거의 동일한 시간에 발생하는 예약에 대해 예약 가능수를 초과하지 않도록 하는 방법에 대해 생각해보아야한다.
1. 예약이 insert 쿼리로 나갈경우
데이터가 생성되는 방식으로 예약을 할 때에는
가게 주인이, 예약자 리스트에서 두번째 혹은 초과 예약자를 예약 취소시키고, 초과 되었다고 연락함.
2. 동기로 처리
해당 서비스 메소드를 동기로 처리하면된다.
async 어노테이션 달지 말아라.
완료 순서가 보장되지 않으니,, 고민은 좀 해봐야겠다만.. 동기로 처리하는 쪽으로 마음 굳힘
3. 결론 : 자바는 음수를 제한하지 않는다. Wrapper를 이용해야함. 그리고 음수를 제한하다고 동시성에서 자유로워지진 않는다.
기본적으로 validator를 이용해 dto프로젝션으로 예약자수 컬럼만 가져오고 0이라는 상수를 이용해 비교연산자로 예약 가능여부를 체크한다.
또한 서비스 로직에서 synchronized를 이용해 동시성을 제어한다.
4. 링크 
[단건 프로젝션](https://icarus8050.tistory.com/m/5)
[싱크로나이즈드](https://thalals.tistory.com/370)
[어노테이션과 싱크로나이즈드 같이 사용](https://kdhyo98.tistory.com/59)

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

## 쿼리 참조 링크
https://parksay-dev.tistory.com/m/48
https://m.blog.naver.com/birdparang/221574304831
마지막 값

## 수익 모델
이전 프로젝트에서는 수익모델을 직접 구현해보지 않고 구상만 해보았으나,
이번 프로젝트에서는 새롭게 플랫폼에 맞는 수익모델을 구상해보고
구현까지 해보았다.(비즈니스 밸류 창출)

## 반복되는 일 찾기
* 부하를 줄이기 위해서는 같은 일이 계속 반복되는 것이 무엇인지를 찾아야한다.
* 예를들어 insert가 가 많이 발생하는데, insert 하는 테이블의 컬럼값중 단 하나만 다르고, 나머지가 똑같다면?
* 이런 경우가 바로 반복되는 일이다. 
* 이런일을 어떻게 처리해 부하를 분산시키고, 성능을 향상할지를 고민해보아야한다.
* 위의 예시에서는 배치로 처리할 수 있다.

## 동기 비동기 적절사용
이커머스처럼 상품 조회(쿼리)가 많고 주문(커맨드)가 적은 형태가 아니라
예약(커맨드)이 많고, 예약조회(쿼리)가 더 적기 때문에 카프카 커넥트를 이용해 커맨드에 대한 처리를 비동기로 처리해 서버에 부담을 줄인다.
사실상 DB와 서버가 연결되어있긴 하지만 조회용도(조회는 반드시 동기로 처리해야하기때문에 조회를 DB와 서버를 끊는 다고 해결할 순 없다.)이지, 커맨드는 모두 비동기로 처리한다.
또한 이번 어플리케이션에서 조회는 상품처럼 많은 양을 가져오진 않기때문에(기본이 검색을 통해 필요한 데이터만 가져오기 때문) 부하가 심하진 않다.

## 비동기
void 함수같은 경우 async로 선언해서 task를 날려버리고 무시한다.
repo 함수같은 경우 selete 쿼리가 아닌 이상 async로 선언해서 날려버리고 무시한다.
=> task 속도가 향상, 사용자에게 정상적으로 api가 처리됬다는 메세지를 빨리 보여줄수 있어서 순환효과를 가져온다.

## 배치 - 예약에서 들어감
스케줄러를 이용해 새벽시간에 벌크연산으로 처리하도록 하면된다.(insert, update, delete)
배치는 반드시 로그 남긴다.
[스케줄러](https://itworldyo.tistory.com/40)


## 링크
### 비동기와 쓰레드 풀
[async 간단 설명](https://bepoz-study-diary.tistory.com/m/399)
[스프링 쓰레드 풀](https://velog.io/@tritny6516/Spring-Thread-Pool)
[비동기 쓰레드 풀1](https://antkdi.github.io/posts/post-java-springboot-async/)
[비동기 쓰레드 풀2](https://sabarada.tistory.com/m/215)

## 도메인 모델 패턴
도메인 모델 패턴을 사용할 경우 서비스로직이 작아진다.
또한 도메인 모델 패턴을 사용했다고 명시한다(문서에).
도메인 모델 패턴에서 필수적인 요소는 조회다.
id = 1이라는 order를 취소하려면
Orders order = findOneById(id);
으로 조회를 해놓고
Orders 엔티티에 작성된 cancel()이라는 함수를 
order.cancel();으로 이용해 상태 등을 변경하는 비즈니스 로직 처리를 해주면된다.
엔티티의 멤버 함수는 this를 자주 이용하게 될것이다.
[도메인 모델](https://applepick.tistory.com/153)

## 예외는 전역에서 처리
예외는 컨트롤러에서 끝내고 서비스에선 앞에서 처리되었으니깐..
으로 일관하지 말고 서비스에서도 처리해야한다.
exceptionhandler는 모든 메소드에 다 붙여야하나 controller advice는 그러지 않아도 알아서 컨트롤러를 통해 발생된 모든 예외를 다 처리해준다. 서비스로직에서 예외가 발생해도 그대로 예외를 컨트롤러로 넘겨주면된다.
validator == true => throw new Exception();
@ControllerAdvice는 Controller로 들어온 요청에 대해 에러를 핸들하게 되는데, Controller에서 Service를 호출하였을 때, 해당 Service에서 Exception을 발행하였다면, 해당 Service의 에러도 같이 처리하게 됩니다. 예외 클래스를 분리해서 등록하여 사용해 보세요.
[예외처리](https://kwonyeeun.tistory.com/64)
[커스텀 예외처리](https://velog.io/@hwsa1004/Spring-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%ACCustom-Exception-%EB%A7%8C%EB%93%A4%EA%B8%B0)
[커스텀 예외처리2](https://www.daddyprogrammer.org/post/446/spring-boot2-5-exception-handling-controlleradvice/)
[커스텀 예외처리3](https://thalals.tistory.com/272)

## 영향지도 - 스타일가이드에 작성
* 영향지도를 만들어서 경계를 나누어라
* 누가 누구에게 영향을 끼치고, 누가 누구에게 영향을 받는지 영향지도를 그려보면, 쉽게 이벤트/도메인 별 경계를 나누기 쉬워진다.
* 이 영향지도를 바탕으로 서비스를 나누고,
* 서비스간 통신 설계를 꾸려나가면된다.
### 예시
* 상점예약 서비스와 예약서비스 두개의 분리를 고민하며 만들어낸 방법이다.
* 3개의 테이블이 필요할것으로 예상된다.
* 첫째는 예약을 기록하는 테이블, 
* 둘째는 상점에서 등록한 예약을 저장하는 테이블로 이 테이블의 예약자 수는 가변적이다.
* 셋째는 상점에서 등록한 예약자 수를 가지고 있는 테이블로 예약이 완료되면 상점에서 등록한 예약테이블의 예약자수는 줄어들지만, 이 테이블은 원래의 예약자 수를 가지고 있어
* 하루가 끝날때 배치 처리를 이용해 상점에서 등록한 예약 테이블의 예약자 수를 원상태로 복구하는 역할을 한다.
* 이 테이블의 영향지도를 그려보면, 예약을 기록하는 테이블은 상점에서 등록한 테이블에 영향을 주고, 예약을 기록하는 테이블은 원래 예약자수를 저장하는 테이블과 아무런 영향을 주고받지 않는다.
* 또한 상점에서 등록한 예약 테이블은 원래 예약자수를 저장하는 테이블과 영향을 주고 받는다.
* 따라서 상점에서 등록한 예약테이블과 원래 예약자수를 저장하는 테이블은 하나의 서비스로 구성해도 문제가 생기지 않으며,
* 예약을 기록하는 테이블도 따로 서비스로 분리해도 문제가 생기지 않게 된다.
* 따라서 3개의 테이블을 바탕으로 2개의 서비스로 분리가 가능하며, 이를 통해 지속적인 쿼리요청에 대비할 수 있는 구조가 만들어진다.
* 또한 이렇게 영향지도를 그려서 쉽게 경계를 분리해, 설계단에서 미리 대응을 할 수 있게된다.

## UUID 필요성 - 고민한점
* [uuid링크](https://wakestand.tistory.com/361)
* email과 같이 외부에 종속되는 요소를 식별자로 사용하지 않고 uuid를 만든점 기술, 더하여 uuid의 중복을 완전히 걱정하기 싫어서 문자열도 덧댐, 이메일변경시 다바뀜(식별불가능, 주민번호의 사례를 인용)

## 간단 흐름도
* 예약 -> 가게 예약 예약자수 마이너스, 나의 예약에서 예약확인 가능
* 리뷰 -> 리뷰 등록시 좋아요/싫어요 선택, 가게에 좋아요 싫어요 카운트됨

## 기술
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
11. 필요하면 캐시 사용, 캐시 사용시 yml 로그레벨 설정하기

## 프로젝트시 확인
1. async 와 Authorization 폴더 복붙
2. QClass 생성전 gernerated gitignore에 등록
3. gateway 서비스에 등록
4. 프로듀서 필요시 gson 등록
5. 1대1인경우 fk에 unique 걸기
6. 빈 객체의 id를 가지고 null체크, id가 없을 경우에는 대표 컬럼
7. feign 사용시 log level 설정
8. 서킷브레이커에 null 체크후 null인경우 new로 빈객체 리턴

## 할것
추천 서비스 api 테스트
10개 정도 상점 만들고 알고리즘 테스트

## 카프카 테스트 할 것
* 광고 서비스 만들고 상점 페이지로 조회테스트 해서 정상적으로 추천 해주는지 확인
* 리뷰 서비스 제작 후 상점 좋아요/싫어요 정상 작동 테스트
* 시간표 서비스 제작후 유저 탈퇴시 시간표 삭제 테스트

## 리팩토링
* 코드 요구사항에 '기술' 내용을 정리하고, 스타일 가이드 내용 제목만 해서 필요한것 간단히 작성
* 향후 사용된 기술 바탕으로 기존 프로젝트 리팩토링