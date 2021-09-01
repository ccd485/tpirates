# tpirates
### 1. 설치 및 환경설정 가이드
- Spring Boot
- Maven
- JPA
- H2 database : http://localhost:8080/h2-console
- IDE : Intelli J
- server : Apache Tomcat9
- Postman : 설치하여 API 간편하게 요청하였습니다.
- Swagger : http://localhost:8080/swagger-ui/index.html#/
> eclipse에서 구동해보았을 때 Lombok이 적용되지않아 혹시 Getter/Setter 메소드가 실행하지않는다면 따로 생성해줘야합니다.
---
### 2. 테이블 생성 SQL
#### 주의 ❗ JPA를 사용하여 점포 추가 API 시 자동으로 테이블이 생성됩니다 > API 사용 시 점포추가 먼저 진행
1. Goods

![image](https://user-images.githubusercontent.com/53583470/131659677-a33df7f7-db26-425f-9d26-bfcb36cf911e.png)

2. Delivery

 ![image](https://user-images.githubusercontent.com/53583470/131661759-2ef50155-4b6d-4d67-a02d-719d87b9830b.png)

3. Options

![image](https://user-images.githubusercontent.com/53583470/131661794-7dbb9aae-0987-42c5-ae73-3070167913d3.png)

> H2 console 로그인 http://localhost:8080/h2-console

![image](https://user-images.githubusercontent.com/53583470/131662177-2c19740b-b5cc-41bc-b5f7-03b3321f8750.png)

---
### 3. API 사용가이드
#### Swagger 사용하였습니다. http://localhost:8080/swagger-ui/index.html#/
![image](https://user-images.githubusercontent.com/53583470/131661236-c8739d8a-f576-4a8f-a240-6bd1bf9f5d00.png)

> Postman 사용 모습( 점포 추가 요청 파라미터 )
![image](https://user-images.githubusercontent.com/53583470/131661404-1d1c7cda-7971-42e0-a43c-3963c492c6f7.png)

---
