# ⛔ 해당 프로젝트에 대해
  
해당 프로젝트는 팀 스파르타 코딩클럽의 Spring 과제 제출용 입니다.

클라이언트는 건드리지 않고 서버쪽에 있는 Spring Boot 코드만을 수정해 문제를 풀었습니다.  

하단에는 간단하게 해결한 방식을 설명 했습니다.

<br>

# 🔧 프로젝트 문제들 (Lv 1 ~ 12)

## 필수 (Lv 1 ~ 8) 
### Lv 1. 설정 파일 작성: Docker MySQL 연결 (필수)
~~~bash
docker run -d --name mysql -e MYSQL_ROOT_PASSWORD=12345678 -p 3306:3306 mysql:8.4
~~~
Window 11 환경에서 Docker Desktop을 설치하고 위의 명령어를 콘솔창에 입력하여 MySQL을 3306포트로 실행시켰습니다.

이후, `application.properties`에 **DB**와 **JPA**의 설정값을 입력했습니다.   

[Lv 1 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/ce57cac5951b400a42723c886589496d3914bd93)

---

### Lv 2. 빈 등록 고치기: 의존성 주입 (필수)
`GameService` 클래스에 `@Service`를 의존성으로 주입했습니다. 

[Lv 2 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/e6e354e84d13f7944e4403b9527ad4e20dce3cb2)

---

### Lv 3. RESTful 경로 맞추기: 게임 목록 API (필수)
클라이언트에서 `GET`에 대한 경로들이 전부 `/games`로 받고 있어서 `/game`을 `/games`로 수정했습니다.

[Lv 3 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/a63d1218434d438fac041c9e6f2921a0789f4704)

---

### Lv 4. @Transactional 버그 고치기 (필수)
`GameService` 클래스의 `createGame()`함수에서 `readOnly`를 사용하고 있어서 새 게임을 DB에 생성하지 못하고 있어서 `readOnly`기능을 삭제했습니다.

[Lv 4 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/698219bab645ecb8b6aab653fb6bb72e42d543c1)

---

### Lv 5. 요청 검증과 응답 DTO: 게임 생성 (필수)

`RunCardRequest`의 `playerName`변수는 공백만으로 된 값은 허용하지 않게 만들었고 `acquiredFloor`변수는 `0~10`까지 받을 수 있게 했습니다.

`CardResponse`에는 JSON 형식에 맞게 클라이언트에게 전달 할 데이터를 생성했습니다.

[Lv 5 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/2361f5f17902c245efce192b04bb1c4aabdf6b73)

---

### Lv 6. 보상 카드 선택과 진행 저장 (필수)
`GameController`에서 `@PutMapping("/games/{gameId}/progress")`로 매핑되어 있는 함수의 주석을 해제했더니 바로 작동했습니다.

[Lv 6 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/c88ed82a9575c56ffc1284966ff9d61d450ab5d3)

---

### Lv 7. 목록·상세 조회: 저장된 여정 이어하기 (필수) 
`GameService`클래스에서 정해진 `getGames()`함수와 `getGame()`함수안에 API 명세서를 확인한 뒤 알맞은 `GameSummaryResponse`와 `GameDetailResponse`를 반환 할 수 있도록 했습니다.  

`GameController`클래스에서는 구현한 `getGames()`함수와 `getGame()`함수가 잘 작동 할 수 있도록 매핑에 맞게 반환시켰습니다. 

[Lv 7 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/156b752a24dcf841cc3b4f1f7128d992ac037bbb)

---

### Lv 8. 변경 감지로 이름 수정, 자식부터 삭제 (필수)
`games`테이블에 대한 `playerName`컬럼의 수정은, `Game`클래스가 `Entity`이기 때문에 멤버변수가 오류없이 수정만 된다면 더티체크에 의해 자동으로 DB가 수정되는 점을 이용하여 `Game`클래스 내부 함수인 `rename()`를 사용하였습니다.

`games`테이블에 대한 삭제는 `run_cards`가 `game_id`로 참조중이여서 참조중인 `run_cards`데이터를 모두 삭제하고 `games`데이터를 삭제 할 수 있게 만들었습니다.

[Lv 8 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/407ac17685a453e024fc74c82b3c717104ad62f8)

---

## 도전 (Lv 9 ~ 12)

### Lv 9. 끝난 게임 덮어쓰기 막기: 409 (도전)
~~~java
if(game.isFinished()){
    throw new ResponseStatusException(HttpStatus.CONFLICT);
}
~~~
끝난 게임을 확인하는 `isFinished()`함수를 사용하여 만약 끝났다면 `Error 409`를 반환 할 수 있게 했습니다.

[Lv 9 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/aee43df283a974466b8675e925b818bb43494f9f)

---

### Lv 10. 전역 예외 처리: 404·409에 message 붙이기 (도전)
이미 만들어진 `GameNotFoundException`와 `GameFinishedException`를 사용하여 각각 `GlobalExceptionHandler`에서 전역 예외처리로 등록 했습니다.

이후 `throw`시에 `Error 404`와 `Error 409`에 대해 만들어진 `Exception`클래스를 적절히 사용했습니다

[Lv 10 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/cf476c5dd03eebbb40d38c9b945df0f37996b5b0)

---

### Lv 11. N+1 없는 카드 수 집계와 저장 시간 (도전)
시간을 저장하기 위해 `@EnableJpaAuditing`를 Application에 어노테이션으로 달아주고 `@EntityListeners(AuditingEntityListener.class)`를 `Entity`에 어노테이션으로 달아 준 뒤 `LocalDateTime`를 자료형으로 `@CreatedDate`와 `@LastModifiedDate`를 추가해줬습니다.

[Lv 11 - 1 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/f09e0f7cf3458af16c821f7654f8039bc04aa94b)

카드 수 집계는 JPQL을 사용하여 `run_cards`테이블의 `game_id`컬럼과 `games`테이블의 `id`가 참조되어있는 점을 이용하여 `GROUP BY`로 묶어서 조회 한 뒤 카드 수를 던져줍니다.

[Lv 11 - 2 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/f28b77c1a2d8d793d00a5250094546b63928c2f3)

---

### Lv 12. 랭킹 (도전)
먼저 RestClient를 사용하여 외부 JSON 데이터를 받았고, 해당 데이터들을 담아두기 위해 `RankingSource`클래스를 만들고, 클라이언트 연결 테스트 해보기 위해 `RankingResponse`클래스를 만들었습니다.
이후, URL 접속 시 `RankingController`랑 `RankingService`가 잘 작동되고 JSON 데이터가 잘 담기는지 확인했습니다.

[Lv 12 - 1 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/72aeac0319d87dbc8d0e89dc7565c390dbe4e15a)

이후 버그성 플레이로 보이는 기록과 형식이 어긋난 기록을 제외하고 중복되는 아이디는 맨 위에 순위만 반환할 수 있도록 만들었습니다.

[Lv 12 - 2 커밋으로 바로 이동](https://github.com/tree6316mte/spring-advanced/commit/ef80a5d50e197ddfc2ed34504a40f88f74993fd8)

---
