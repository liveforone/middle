## 가게 서비스
* 가게 서비스는 가게와 관련된 일들을 처리합니다.
* 다만 확률적으로 가게를 추천해 광고하는 알고리즘 및 시스템은 추천 서비스에서 관할합니다.

## 상세 요구사항
* 가게 등록은 auth가 owner, 즉 가게 주인만 가능하다
* 주소는 상세히 컬럼을 나누어 표기한다. => 검색시 편리성
* 회원 탈퇴시 상점도 삭제한다.
* 상점이 삭제되면, 추천 서비스와 시간표 서비스에 상점에 속한 추천과 시간표(벌크삭제) 삭제 요청을 보낸다.
* 페이징이 들어가는 모든 api에는 추천 서비스를 호출해, 추천하는 상점의 id를 받아 최상단에 끼워 리턴한다.
* 제목으로, 주소(위치)로 검색 가능해야하며, 이들은 인덱스를 태우기위해 like% 쿼리를 이용한다.
* 페이징은 페이징 요구사항 문서를 따르며 동적쿼리를 사용해 조건에 따라 유연하게 대응한다.

## API 설계
```
[GET] /shop/{shopId} : 상점 상세조회
[GET] /my-shop : 나의 상점 조회, auth = OWNER
[GET] /shop/home : 상점 리스트 홈
[GET] /search : 상점 검색. 시/도로명/이름 3가지의 조건을 기호에 맞게 지정 가능하다(일부만, 모두, 아예 없이 검색 가능).
[POST] /create-shop : 상점 등록, auth = OWNER
[PUT] /update/name : 상호명 업데이트
[PUT] /update/tel : 상점 전화번호 업데이트
[PUT] /update/address : 상점 주소 업데이트(모두)
[POST] /shop/username/{username} : provide url, 추천 서비스에 상점 등록시 사용
```

## Json body
```
[상점 등록]
{
  "shopName": "test_name",
  "tel": "01012345678",
  "city": "seoul",
  "street": "changshin daro",
  "detail": "semyoung building 11-5"
}   

[상호 변경]
{
    "shopName" : "updated shopName"
}

[전화번호 변경]
{
    "tel" : "0211334455"
}

[주소 변경]
{
    "city" : "seoul",
    "street" : "sonbukdongro 12-3",
    "detail" : "102동 101호"
}
```

## 서비스간 통신
### 상점 좋아요/싫어요
```
topic : shop-is-good/shop-is-bad
request : shopId
```
### 회원탈퇴시 회원에 속한 상점 삭제
* 회원이 탈퇴할때 회원의 auth가 OWNER일 경우 해당 메세지를 받게 된다.
```
topic : remove-shop-belong-member
request : username
```
### 상점삭제/회원 탈퇴시 추천 삭제
```
topic : remove-recommend
request : shopId
```
### 상점 삭제시 시간표 삭제
* 회원이 탈퇴되며 상점이 삭제된다.
* 이때 시간표 서비스에 해당 상점에 속하는 시간표 삭제 요청을 보낸다.
```
topic : remove-timetable
request : shopId
```
### 추천하는 상점 id 받기
* 페인 클라이언트로 추천 하는 상점의 id를 받아 리스트 페이지 최상단에 넣어 리턴한다.
```
[GET] /draw-shop
```
### username으로 상점 정보 검증 provide api
* username으로 찾아 shopId를 리턴하여, 검증가능하게할 뿐만 아니라,
* 광고 서비스 등록시 shopId 를 자동으로 set할 수 있도록 했다.
```
[POST] /shop/username/{username}
```

## 주소 표기
* city : 도시
* street : 도로명 주소(ex : 세종대로209)
* detail : 상세주소(101동 1001호)
* 이에 대한 설명은 스타일가이드에 적혀있다.
* [스타일가이드](https://github.com/liveforone/study/blob/main/%5B%EB%82%98%EB%A7%8C%EC%9D%98%20%EC%8A%A4%ED%83%80%EC%9D%BC%20%EA%B0%80%EC%9D%B4%EB%93%9C%5D/m.%20%EC%A1%B0%EA%B1%B4%EC%9D%B4%20%EB%B3%B5%EC%9E%A1%ED%95%9C%20%EC%BF%BC%EB%A6%AC%EC%97%90%EC%84%9C%EB%8A%94%20%EC%BB%AC%EB%9F%BC%EC%9D%84%20%EC%9E%91%EA%B2%8C%20%EC%AA%BC%EA%B0%9C%EB%9D%BC.md)

## 광고 추천 상점 반환
* 다중 상점 조회 페이지에는 11개의 상점이 뜬다.
* 기본적으로 페이징은 10개씩인데, 왜 11개일까?
* 최 상단에 페이징과 무관한 추천 상점을 넣었기 때문이다.
* 매 페이징 마다 추천 상점과 일반적인 페이징 리스트를 리턴하는 쿼리를 날린다.
* 추천 상점 id가 null이거나, 조회한 추천 상점이 null이 아닌 경우라면 
* 일반 페이징 맨 처음 인덱스에 추천 상점을 add하여 리턴한다.