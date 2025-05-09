package Axis.Axis_Spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Axis.Axis_Spring.common.Constants.ExceptionClass;
import Axis.Axis_Spring.common.exception.AxisSpringException;
import Axis.Axis_Spring.data.dto.ProductDto;
import Axis.Axis_Spring.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/product-api")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;  

    @Autowired   //생성자 부분에서 이 어노테이션을 쓰면 메모리에 떠 있는 productService를 끌어다 연결해준다.
    public ProductController(ProductService productService){
                this.productService=productService;
    }
   
       
    @GetMapping(value = "/product/{productId}")// id 조회로 데이터출력  http://localhost:8080/api/v1/product-api/product/Axis-Book1 이걸로 Get방식으로 해보자
    public ProductDto getProduct(@PathVariable String productId){

        long startTime = System.currentTimeMillis();
        LOGGER.info("[createProduct] perform {} of Axis_Spring API.", "createProduct");
    
        ProductDto productDto=productService.getProduct(productId);

        LOGGER.info(  //**{}순서대로 productDto.getProductId()등이 순서대로 들어간다. {}에 전달할 값은 Java의 모든 데이터 타입이 가능합니다(문자열, 숫자, 객체 등).
            "[getProduct] Response :: productId = {}, productName = {}, productPrice = {}, productStock = {}, Response Time = {}ms",
            productDto.getProductId(),
            productDto.getProductName(),
            productDto.getProductPrice(),
            productDto.getProductStock(),
            (System.currentTimeMillis() - startTime)  //수횅시간을 측정하기 위해서 시작시간과 끝시간을 빼준다.
         );
       
            return productDto; 
            
    }

    @GetMapping(value = "/productAll") //*내가 만든거 http://localhost:8080/api/v1/product-api/productAll  
    public ResponseEntity<List<ProductDto>> getProductAll(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductAll());
    }

     
     @DeleteMapping(value = "/productDelete/{productId}")  //*내가 만든거  http://localhost:8080/api/v1/product-api/productDelete/{productId}
     public ProductDto deleteProduct(@PathVariable String productId){
            return  productService.deleteProduct(productId); //삭제 후 다시 조회해보면 null값이 나와야함
     }
    
    @PostMapping(value = "/product")  //상품하나를 등록하는 메서드, 하단 아래 제이슨 데이터를 넘겨준다. 하단은 예전방식
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){     
                                                     //**@Valid의 수행대상은은 ProductDto 각 필드에 달려있는 어노테이션만 검사한다.
        LOGGER.info("[createProduct] perform {} of Axis_Spring API.", "createProduct");

    //       @Valid를 안하고 예전에는 아래와 같은 방법으로 유효성 검사를 했다.
    // if (productDto.getProductId().equals("") || productDto.getProductId().isEmpty()) {  //공백이거나 null값이 들어오면 에러가 나게 설정
    //     LOGGER.error("[createProduct] failed Response :: productId is Empty");
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productDto);
    //   }

        ProductDto response=productService.saveProduct(productDto);

        LOGGER.info(
            "[createProduct] Response >> productId : {}, productName : {}, productPrice : {}, productStock : {}",
            response.getProductId(),
            response.getProductName(),
            response.getProductPrice(),
            response.getProductStock()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping(value = "/product/exception")
    public void exceptionTest() throws AxisSpringException{   //사용자가 만든 예외처리 클래스를 이용하여 예외처리
        throw  new AxisSpringException(ExceptionClass.PRODUCT, HttpStatus.BAD_REQUEST, " 의도한 에러가 발생했습니디.");
   }
}

//     @PostMapping(value = "/product")  //상품하나를 등록하는 메서드, 하단 아래 제이슨 데이터를 넘겨준다.(예전방식)
//     public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){   
//          
//         String productId=productDto.getProductId();
//         String productName=productDto.getProductName();
//         int productPrice=productDto.getProductPrice();
//         int productStock=productDto.getProductStock();

//         ProductDto response=productService.saveProduct(productId, productName, productPrice, productStock);
//         return ResponseEntity.status(HttpStatus.OK).body(response);

//     }

//@Valid 유효성 검사를 하는 항목인데, ProductDto에서의 필드값에 유효하지 않은 값이 넘어가면 에러가 발생하게 설정하는것, 상품가격을 -500등으로 넘기면 에러가 발생하게 설정
  //http://localhost:8080/api/v1/product-api/product 페이지에서 제이슨 형태의 데이터를 메모리에 떠있는 productDto객체에 넘긴다.


    //http://localhost:8080/api/v1/product-api/product    post방식으로 postman으로 테스트 아래 제이슨테이터 삽입

    /* 상품등록 테스트에 쓸 제이슨 데이터
    {
        "productId":"Axis-Book",
        "productName":"Axis-Book-1",
        "productPrice":"5000",
        "productStock":"5"
    }   */
 

//        