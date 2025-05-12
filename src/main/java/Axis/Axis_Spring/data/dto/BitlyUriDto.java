package Axis.Axis_Spring.data.dto;

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

    private String message;
    private String code;
    private Result result; // result 객체 추가

    // Result 내부 클래스 정의
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private String hash;
        private String url;    // 단축된 URL
        private String orgUrl; // 원래 URL
    }
}
