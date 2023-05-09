## 카운트 쿼리 최적화
* 상점을 추천해주는 추천 서비스에서 기본적인 전략에 의거해
* 데이터베이스에 저장된 추천들중 잔여노출횟수가 1이상인 데이터들을 count한다.
* 당연하게도 count 쿼리는 데이터가 많아지면 많아질수록 느려진다.

## 첫번째 해결책 : 실패
* 첫번째로 생각해본 해결책은 id만 count하면? 이라는 생각이 들었다.
* 즉 count(컬럼)으로 처리하면 성능이 더 좋지 않을까?라는 생각이다.
* 왜냐하면 데이터를 selece하여 조회할때에 프로젝션같은 기능을 쓰는 이유가 무엇인가?
* 가져오는 컬럼을 필요한 것들만 가져옴으로써 성능을 높이기 위함이다.
* 그러나 count에서는 이것이 먹히지 않았다.
* 그냥 count와 count(컬럼)을 테스트해보니 데이터가 쌓이면 쌓일수록 count(컬럼)이 더 느렸다.
* 이유는 그냥 count()는 데이터를 읽지않고 훑고 가는 느낌이었다면,
* count(컬럼)은 데이터를 읽는다. 이러한 이유로 첫번째 해결책은 사용하지 않았다.

## 두번째 해결책 : 실패
* 두번째 해결책은 추천 알고리즘 문서에도 나와있듯 캐쉬를 쓰는것이었다.
* 캐쉬는 생각보다 무거웠다. 특히나 외부 캐쉬는 많이무거웠다.
* redis같은 외부캐쉬의 도입은 사실당 db를 하나 더 놓는 느낌이었다.
* 따라서 사용하지 않았다.

## 세번째 해결책 : 성공
* id에 인덱스를 또 만드는 방법이 있다.
* 이는 [링크](https://m.blog.naver.com/birdparang/221574304831)에서 잘 기술되어있다.
* 필자는 링크를 참조할때 많은 생각이 들었다.
* 이미 pk에는 인덱스가 걸려있고, pk를 기반으로 조회하는 인덱스는 제일 빠른데 왜 또 중복하여 만들라는것인지 말이다.
* 이는 mysql이라는 데이터베이스의 특징으로 알 수 있다.
* 다른 db와 달리 mysql(mariadb)의 innodb 엔진의 pk는 다른 db의 구조와 조금 다르다.
* 즉 쉽게말해 pk가 count()쿼리, 집계함수를 이용하기에 부적절한 구조이다.
* 따라서 'pk가 제일빨라!'라고 pk에 의존적으로 생각할것이 아니라,
* 인덱스를 만들어서 해당 인덱스를 타게 함으로써 count쿼리의 성능을 높일 수 있다.