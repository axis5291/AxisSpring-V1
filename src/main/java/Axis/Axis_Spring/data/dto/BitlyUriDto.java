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

    private String id;
    private String link;      // 단축된 URL
    private String long_url;  // 원래 URL
}
