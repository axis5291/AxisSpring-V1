package Axis.Axis_Spring.service.Impl;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import Axis.Axis_Spring.data.dto.ProductDto;
import Axis.Axis_Spring.data.entity.ProductEntity;
import Axis.Axis_Spring.data.handler.ProductDataHandlerImpl;
import Axis.Axis_Spring.service.ProductServiceImpl;
// @SpringBootTest와 같이 전체를 가동하면 부하가 많이 걸리므로 필요한 부분만 가져다 쓰는 것 
// @ExtendWith(SpringExtension.class)는 JUnit 5에서 Spring TestContext Framework를 확장기능으로 추가해주는 어노테이션.
// 이 어노테이션이 있어야 @Import, @MockBean, @Autowired, @ContextConfiguration 등 Spring 컨텍스트를 사용하는 테스트 기능이 동작해.
// 따라서 @Import({ProductDataHandlerImpl.class, ProductServiceImpl.class}) 를 쓰려면 반드시 @ExtendWith(SpringExtension.class)가 필요
//@ExtendWith(SpringExtension.class) ->DI(의존성 주입), 컨텍스트 관리 등 핵심 기능만
@ExtendWith(SpringExtension.class)  // @ExtendWith은 @SpringBootTest의 일부분이다.
@Import({ProductDataHandlerImpl.class, ProductServiceImpl.class})
public class ProductServiceImplTest {

    @Autowired // ***테스트 대상 클래스는 실제 빈으로 주입받고,
    ProductServiceImpl productService; 

    @MockBean //***핸들러는 @MockBean으로 mock 처리해서 실제  DB나 외부 시스템과의 실제 상호작용 작업이 일어나지 않게 한다.
    ProductDataHandlerImpl productDataHandler;
   
    @Test
    public void getProductTest() {
        //Mockito에서 메서드 이름, 매개변수 타입과 개수, 반환 타입이  정확히 일치해야 한다는 거고, 구현 내용은 전혀 상관없다
        Mockito.when(productDataHandler.getProductEntity("123"))  
               .thenReturn(new ProductEntity("123", "pen", 2000, 3000, null));  // 1번 문장
        //실제 productDataHandler.getProductEntity()에서는 ProductEntity를 DB에서 가져오지만, mock에서는 위와 같이 가짜 데이터를 리턴하도록 설정

        ProductDto productDto = productService.getProduct("123");

        Assertions.assertEquals(productDto.getProductId(), "123");    //오른쪽 123은 dto에 담긴것과 일치하는지 확인하는 것
        Assertions.assertEquals(productDto.getProductName(), "pen");
        Assertions.assertEquals(productDto.getProductPrice(), 2000);
        Assertions.assertEquals(productDto.getProductStock(), 3000);

        verify(productDataHandler).getProductEntity("123");  // 1번 문장이 제대로 수행되는지 체크
        //***verify()는 Mock객체 메서드만 대상으로 한다
        //이를 통해 목 객체 메서드가  실제로 동작하는지 확인할 수 있고, 테스트 대상 코드가 올바르게 목 객체의 메서드를 호출하는지 검증할 수 있습니다.
        //***서비스와 연결된 핸들러 메서드까지 확인해야 테스트의 의미가 있다.
    }

    @Test
    public void saveProductTest() {
        // given
        ProductDto inputDto = new ProductDto("123", "pen", 2000, 3000);
        ProductEntity savedEntity = new ProductEntity("123", "pen", 2000, 3000, null);

        Mockito.when(productDataHandler.saveProductEntity(Mockito.any(ProductEntity.class)))
                .thenReturn(savedEntity);

        ProductDto productDto = productService.saveProduct(inputDto);

        Assertions.assertEquals(productDto.getProductId(), "123");
        Assertions.assertEquals(productDto.getProductName(), "pen");
        Assertions.assertEquals(productDto.getProductPrice(), 2000);
        Assertions.assertEquals(productDto.getProductStock(), 3000);

        verify(productDataHandler).saveProductEntity(Mockito.any(ProductEntity.class));
    }
}
