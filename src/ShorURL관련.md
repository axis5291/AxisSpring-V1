 
 short url
 Short URL(짧은 URL) 은 긴 웹 주소(URL) 를 짧고 간결하게 줄인 거

https://bit.ly/ 에서 서비스 이용


# # ✅ Redis를 이용하여 DB 정보를 빠르게 조회하는 방식 (캐싱 기능 구현):Redis는 Spring Boot와 별도로 독립 실행되어야 한다.
        Redis를 활용해 데이터베이스 정보를 캐싱하면 성능을 크게 향상시킬 수 있다. 아래는 Redis를 사용하는 기본적인 설정과 구현 방식이다.

## Redis 설치 및 실행 (Mac 기준)
   
  # 1. brew install redis
  # 2. brew services start redis

  # 3. Maven 의존성 추가 (pom.xml)
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

  # 4.application.properties 설정
        spring.data.redis.host=localhost
        spring.data.redis.port=6379

  # 5.캐시 대상 DTO 클래스 작성
    @RedisHash(value = "shortUrl", timeToLive = 60)  // Redis에 저장되며 60초 후 만료
    public class ShortUrlResponseDto implements Serializable {

        private static final long serialVersionUID = -214490344996507077L;

        @Id
        private String id;
        private String orgUrl;
        private String shortUrl;
    }
        @RedisHash: 이 객체가 Redis에 저장됨을 명시
        value: Redis 키 prefix로 사용됨 (예: shortUrl:{id})
        timeToLive: 저장된 데이터의 유효 시간(초 단위)
        Serializable: Redis에 객체를 바이트 형태로 저장하기 위해 필요

   # 6.Redis Repository 생성
        public interface ShortUrlRedisRepository extends CrudRepository<ShortUrlResponseDto, String> { }

✅ 정리 Redis 캐시 기능을 사용하기 위해 필요한 요소
   @RedisHash가 적용된 DTO 클래스, CrudRepository 인터페이스 구현체,
   Redis 관련 의존성 (spring-boot-starter-data-redis),  application.properties에 Redis 설정

이 구성이 완료되면, 특정 데이터를 Redis에 저장하여 일정 시간 동안 빠르게 조회하고, 이후 자동 만료되어 DB 재조회가 일어나게 된다.


# 다음에 해야 할일
  redis에서 조회가 되지 않은 문제 해결