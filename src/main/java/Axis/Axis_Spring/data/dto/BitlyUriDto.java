package Axis.Axis_Spring.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class BitlyUriDto {

    private String id;

    @JsonProperty("long_url")
    private String orgUrl;  // 원본 URL
    
    @JsonProperty("link")
    private String shortUrl;     // 단축 URL
    // Result 내부 클래스 정의
   
}
