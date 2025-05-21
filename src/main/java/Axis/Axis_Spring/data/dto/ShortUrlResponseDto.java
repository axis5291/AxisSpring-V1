package Axis.Axis_Spring.data.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   // Lombok annotation to generate getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "shortUrl", timeToLive = 60)  // 캐시 데이터 저장소로 Redis 사용하고 60초 후 만료되어 다시 디비에 조회해야 함
public class ShortUrlResponseDto implements Serializable {

    private static final long serialVersionUID = -214490344996507077L;

    @Id
    private String id;
    private String orgUrl;
    private String shortUrl;

}
