package Axis.Axis_Spring.data.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@RedisHash(value = "shortUrl", timeToLive = 60)
public class ShortUrlResponseDto implements Serializable{
    private static final long serialVersionUID = -214490344996507077L;

    @Id
    private String originalUrl;  // 고유 식별자 필드를 지정합니다.
    private String orgUrl;
  
    private String shortUrl;

}
