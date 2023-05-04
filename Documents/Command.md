## Command
* CLI로 실행시 빠르게 실행하기 위한 명령어 모음집

## kafka
* 경로 이동
```
cd C:\kafka\kafka_2.13-3.4.0
```
* 주키퍼 실행
```
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```
* 카프카 실행
```
bin\windows\kafka-server-start.bat config\server.properties
```

## 빌드 명령
```
./gradlew.bat clean build --exclude-task test
```

## 서비스별 경로
* discovery-service
```
cd C:\Users\KYC\study\middle\discovery-service\discovery-service\build\libs
```
* gateway-service
```
cd C:\Users\KYC\study\middle\gateway-service\gateway-service\build\libs
```
* user-service
```
cd C:\Users\KYC\study\middle\user-service\user-service\build\libs
```
* shop-service
```
cd C:\Users\KYC\study\middle\shop-service\shop-service\build\libs
```
* recommend-service
```
cd C:\Users\KYC\study\middle\recommend-service\recommend-service\build\libs
```

## 서비스별 실행(자바)
* discovery-service
```
java -jar discovery-service-1.0.jar
```
* gateway-service
```
java -jar gateway-service-1.0.jar
```
* user-service
```
java -jar user-service-1.0.jar
```
* shop-service
```
java -jar shop-service-1.0.jar
```
* recommend-service
```
java -jar recommend-service-1.0.jar
```