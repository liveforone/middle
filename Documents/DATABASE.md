## 데이터 베이스 전반 설계
* ddl은 개발을 할때만 사용하고, 프로젝트 종료시 none으로 두고 직접 생성 변경, 인덱스 쿼리를 날린다.
* 타 서비스를 대용량, 대규모 트래픽으로 조회한다면 Read Only DB를 사용한다.
* 모든 서비스별로 DB가 구축되며, 모두 MySql을 사용한다.
* 도커에서도 똑같이 모두 개별 DB를 구성한다.
* 인덱스는 조회성능을 높이는 중요한 역할을 한다.
* 좋은 인덱스는 아래 링크에 잘 기재해놓았다. 아래를 참고하여 인덱스를 건다.
* [성능을 위한 좋은 인덱스](https://github.com/liveforone/study/blob/main/DB/p.%20%EC%84%B1%EB%8A%A5%EA%B0%9C%EC%84%A0%EC%9D%84%20%EC%9C%84%ED%95%9C%20%EB%8B%A4%EC%96%91%ED%95%9C%20%EC%9D%B8%EB%8D%B1%EC%8A%A4.md)

## 데이터 베이스 생성 + 테이블 생성 및 변경 쿼리
```
CREATE DATABASE middle_user;
CREATE DATABASE middle_user_shop;
CREATE DATABASE middle_user_recommend;
CREATE DATABASE middle_user_timetable;
CREATE DATABASE middle_user_reservation;

create table member (
    id bigint not null auto_increment,
    auth varchar(255),
    email varchar(255) not null,
    password varchar(100) not null,
    real_name varchar(255) not null,
    username varchar(255) UNIQUE not null,
    primary key (id)
);
CREATE INDEX email_idx ON member (email);
CREATE INDEX username_idx ON member (username);

create table shop (
    id bigint not null auto_increment,
    city varchar(255) not null,
    detail varchar(255) not null,
    shop_name varchar(255) not null,
    street varchar(255) not null,
    tel varchar(255) not null,
    username varchar(255) UNIQUE not null,
    primary key (id)
);
CREATE INDEX username_idx ON shop (username);
CREATE INDEX city_search_idx ON shop (city, street, detail);
CREATE INDEX steet_search_idx ON shop (street, detail);
CREATE INDEX detail_search_idx ON shop (detail);

create table recommend (
    id bigint not null auto_increment,
    impression bigint not null,
    shop_id bigint UNIQUE not null,
    username varchar(255) UNIQUE not null,
    primary key (id)
);
CREATE INDEX shop_id_idx ON recommend (shop_id);
CREATE INDEX username_idx ON recommend (username);

create table timetable (
    id bigint not null auto_increment,
    basic_remaining bigint,
    remaining bigint not null,
    reservation_hour bigint not null,
    reservation_minute bigint not null,
    shop_id bigint,
    username varchar(255) not null,
    primary key (id)
);
CREATE INDEX shop_id_idx ON timetable (shop_id);
CREATE INDEX username_idx ON timetable (username);
CREATE INDEX id_remaining_idx ON timetable (id, remaining);

create table reservation (
    id bigint not null auto_increment,
    created_date date,
    reservation_state varchar(255),
    shop_id bigint not null,
    timetable_id bigint not null,
    username varchar(255) not null,
    primary key (id)
);
CREATE INDEX username_idx ON reservation (username);
CREATE INDEX shop_id_idx ON reservation (shop_id);
```

## 인덱스 설계
* 인덱스는 모든 테이블에 걸려있다.
* 인덱스는 적극 사용하되, 최대 4개가 넘지 않도록 한다.
* 인덱스는 DB에 직접 쿼리를 날린다.(jpa 도움 받지 않음)