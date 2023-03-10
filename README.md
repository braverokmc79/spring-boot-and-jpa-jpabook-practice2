# spring-boot-and-jpa-jpabook-practice1
실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발

### 실제 동작하는 화면을 먼저 확인한다.


<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgjttxSheDeYbMuNowlrYrpfwMqLCVnC0kzpUeAyd3-tTpBiNbq14vYZNs6O9lrQCnaHESe9lsuf_RQLEI1LBA3aaYLUzTXKzXxXJ7CF-clrUqttp3hhDMODgT7KW9zMhDoln4yj2hYtm9h9QSKYQzRURgEJQVcMV0CFp_42jaMXwlDIHHsjyDvfJ5a3w/s16000/2023-03-02%2016%2034%2023.png">
<hr> 
<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEibjltVXuFBJwm0OBrBUnI8eyQwVq4Jaf7YjxwwkRpzFnMA2ROHbjsuAVkvsV__PBiC5t2yJm_UbmIWWl8rdnCWPoWdITxK-wn9O48usbKz9UfyyFeU-AhYn1Y1WS6r5ukszb9tbmce3VpsdaMQr7haNN8XO2xF8K8q84po83wZSI8-obnTFLA20W15ew/s16000/2023-03-02%2016%2035%2041.png">
<hr> 
<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEj8rbU-jKJk5PYnQj0d-lI1R0Lxy6VJ2C0C33b_YuCgdmNSG5E6Nxw18TUT_SudFiyxwAXRAnL5L_6QWXErcvR_7gFZRWLmRLBlmqLt_QID5uU3yjuHTq6WLHQkADk03odLmvJfmxiwT-1KA8kMfGRw2jm4yHHoBq5XsbiSKshqVL1Cvem2qcQRXtHcjA/s16000/2023-03-02%2016%2036%2006.png">
<hr> 
<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEinQdp04_VZuew19ORombP6aTX3Dk_coaxKoLzSUtPL0X58dRYaCLZrjeSgSNRspzn6536br6Iy0Z67HkVm3FmudY085hrSqpicM8-MzYU3eAQTIWe1AdjkeelhKElyXj0X34nAFkmiby9hyIeWyPichzNHHJ3paPeEQwck4ct0fjQeuK4e5WYHHPZ86g/s16000/2023-03-02%2016%2036%2026.png">
<hr> 
<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgzO7Z0mj6sjJ0MAUuUcJUGMD5Ypxo6xRf_IW6YgvvqzNz46WzI1FG1Bn-fEyLu0bMJ_xpBDE5AdRsTTING-L5Rsca6-MBRhiCbwNPlmbDymuh1k_ojGor4dI0SPY5sOwEcfW90Hw3HjBfARFtBiRPqmlhv3gWI1Z9GF1CR6lNxwzV1h-5S7YHDXXRzEg/s16000/2023-03-02%2016%2037%2033.png">
<hr> 
<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhvUPkyzzH13GzmesOSyqU2HeILN7nRYfenCuAs4z_teplhCnLmfLlj0-azSi3Di70EKcm0tQ2i-OHMusAoP8OTWwgPnK8pTUvfleyf80dIT-orkdkLOC7NeWxixAeKf4V5jkFf7_blTrEwFE3NlDKr5VMUoUMyvW60BFpn1CB2nRRDA4zsU7Q7k2tU2g/s16000/2023-03-02%2016%2037%2059.png">


기능 목록

### 회원 기능
회원 등록\
회원 조회

### 상품 기능
상품 등록\
상품 수정\
상품 조회

###  주문 기능
상품 주문\
주문 내역 조회\
주문 취소

###  기타 요구사항
상품은 재고 관리가 필요하다.\
상품의 종류는 도서, 음반, 영화가 있다.\
상품을 카테고리로 구분할 수 있다.\
상품 주문시 배송 정보를 입력할 수 있다.



## 도메인 모델과 테이블 설계

<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgueM6uv7M_6GCZX6PCHTeg8tkWEGNCZvxbz0lAtS6bdesKz_8Fmqzk49edl2FguMRoDgZLhOjyg8AXvqv1dF7J7i_4MfmCDTpL_yOD0xwyUZwOyg8BYrPLcgAOLwVTD0Fk0HPK9tc8jhbEZqXEPaCWOYpvC9PJPezxG-uzuvr_12-NEgEV-x30X1Gexg/s16000/2023-03-02%2016%2049%2031.png">
