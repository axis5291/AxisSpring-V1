package Axis.Axis_Spring.data.entity;

import  Axis.Axis_Spring.data.dto.ProductDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString  //ToString을 사용하면 객체의 필드 값을 문자열로 출력할 수 있다.
@Table(name="product")  //ProductEntity를 기반으로 디비에 테이블을 자동으로 생성해주는 옵션
public class ProductEntity extends BaseEntity {

    @Id //DB의 프라이머리 키와 동일한 의미이고 productId에 속성을 부여하였다.
    private String productId;
    private String productName;
    private Integer productPrice;
    private Integer productStock;

    public ProductDto toDto() {
               return ProductDto.builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .productStock(productStock)
                .build();
    }

}
