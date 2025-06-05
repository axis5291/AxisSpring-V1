package Axis.Axis_Spring.data.dto;

import Axis.Axis_Spring.data.entity.ProductEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//상품의 정보를 등록하거나 빼거나 수정, 조회 작업을 할려고 만들었다. 데이터를 넘길려고 만듬
@Data  //lombok에서 제공하는 어노테이션으로 getter, setter, equals, hashCode, toString 메서드를 자동으로 생성해준다.
@NoArgsConstructor  //인자가 없는 생성자를 자동으로 생성해준다.
@AllArgsConstructor  //모든 필드를 인자로 받는 생성자를 자동으로 생성해준다.
@Builder  //빌더 패턴을 사용하여 객체를 생성할 수 있게 해준다. 빌더 패턴은 객체의 생성과정을 단순화하고 가독성을 높여준다.
public class ProductDto {

    @NotNull  //dependencies에서 spring-boot-starter-validation을 추가하여 데이터의 유효성 검사를 부여할 수 있다.
    private String productId;

    @NotNull
    private String productName;

    @NotNull
    @Min(value = 500)
    @Max(value = 3000000)  //validation 디펜던시
    private int productPrice;

    @NotNull
    @Min(value = 0)
    @Max(value = 9999)
    private int productStock;


    public ProductEntity toEntity(){
        return ProductEntity.builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .productStock(productStock)
                .build();
    }
}
