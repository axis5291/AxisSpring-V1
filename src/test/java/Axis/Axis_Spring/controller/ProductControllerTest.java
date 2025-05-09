package Axis.Axis_Spring.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import Axis.Axis_Spring.data.dto.ProductDto;
import Axis.Axis_Spring.service.ProductService;

@WebMvcTest(ProductController.class)  //매개변수에 테스트하고자 할 클래스를 넣어준다.
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;  
    // MockMvc는 네트워크 통신을 실제로 발생시키지 않고, Spring MVC의 처리 흐름을 그대로 따라가면서  컨트롤러의 동작을 테스트할 수 있도록 도와주는 객체입니다.
    // URL, HTTP 메서드, 요청 파라미터 등을 지정하면 실제 요청처럼 내부적으로 동작하여 결과를 검증할 수 있습니다.

    @MockBean
    ProductService productService;  //가짜 객체를 만들어서 테스트하기 위한 객체

 //@WebMvcTest는 MockMvc를 빈으로 등록하기 때문에, @WebMvcTest만 선언해줘도, MockMvc 객체가 주입되게 된다.
/* @WebMvcTest에서는 Web Layer 관련 빈들만 등록하기 때문에, 컨트롤러는 주입이 정상적으로 되지만, @Component로 등록된 리포지토리와 서비스는 주입이 되지 않는다.
   따라서, @WebMvcTest에서 리포지토리와 서비스를 사용하기 위해서는 @MockBean을 사용하여 리포지토리와 서비스를 Mock 객체에 빈으로 등록해줘야 한다.*/

    // http://localhost:8080/api/v1/product-api/product/{productId}
    @Test
    @DisplayName("Product 데이터 가져오기 테스트")
    void getProductTest() throws Exception {

        // given() : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드, 사전 세팅작업..가상으로 이렇게 세팅을 해놓고 테스트한다는 의미
        given(productService.getProduct("12315"))  //productService.getProduct("12315")가 수행이 될때 .willReturn()이 수행될거다.
        .willReturn(ProductDto.builder()     //getProduct()가 ProductDto를 리턴하기 때문에 willReturn값도 ProductDto객체를 넘겨주어야 한다.
                .productId("15871")
                .productName("pen")
                .productPrice(5000)
                .productStock(2000)
                .build());

        System.out.println("#############/api/v1/product-api/product/의 get방식 Test 내용출력#############################");

        String productId = "12315";
         mockMvc.perform(get("/api/v1/product-api/product/" + productId))   //.perform(get :실제로 어떤 통신을 할 것인지 정의->httpRequest를 날릴 예정
                .andExpect(status().isOk())                  // andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드
                .andExpect(jsonPath("$.productId").exists()) // json path의 depth가 깊어지면 .을 추가하여 탐색할 수 있음 (ex : $.productId.productIdName)
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.productPrice").exists())
                .andExpect(jsonPath("$.productStock").exists())
                .andDo(print());  //존재하는지 확인후 테스트한 내용을 출력
     
        verify(productService).getProduct("12315"); 
         // verify : 해당 객체의 메소드가 실행되었는지 체크해줌 ->productService객체의 getProduct가 실행되었는데 체크
    }

    @Test
    @DisplayName("Product 데이터 생성 테스트")
    void createProductTest() throws Exception {
      
        ProductDto productDto = ProductDto.builder()
                                .productId("15871")     //이값을 15871 대신 다른값으로 바꾸면 exception으로 처리된다.
                                .productName("pen")
                                .productPrice(5000)
                                .productStock(2000)
                                .build();

        given(productService.saveProduct(productDto)).willReturn(productDto);    //given()의 의미:productService.saveProduct()의 내용이 실행되었다면 ->willReturn을 해줘라..
              

        Gson gson = new Gson();   //구글에서 json을 편하게 다루도록 제공한 라이브러리
        String content = gson.toJson(productDto);  //productDto객체를 json형태이 string값으로 만들어줌.
        System.out.println("content의 내용:" + content);

        // 아래 코드로 json 형태 변경 작업을 대체할 수 있음
        // String json = new ObjectMapper().writeValueAsString(productDto);

        System.out.println("==============/api/v1/product-api/product/의 post방식 Test 내용출력=========================");

        mockMvc.perform(post("/api/v1/product-api/product").content(content).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())  //상태가 ok인지
               .andExpect(jsonPath("$.productId").exists())  //productId값이 존재하는지..
               .andExpect(jsonPath("$.productName").exists())
               .andExpect(jsonPath("$.productPrice").exists())
               .andExpect(jsonPath("$.productStock").exists())
               .andDo(print());

        //post("/api/v1/product-api/product").content(content).contentType(MediaType.APPLICATION_JSON))으로 요청을 했을 때
        //andExpect으로 기대하는 값을 체크

        verify(productService).saveProduct(productDto);
    }
}

/*
MockMvc는 Spring 애플리케이션의 웹 계층을 테스트하기 위한 목(Mocks) 테스트 지원 클래스 입니다.
이를 사용하면 실제 HTTP 요청을 보내지 않고도 컨트롤러 엔드포인트를 호출하고 해당 엔드포인트의 응답을 검증할 수 있습니다

Junit 테스트 시에 @WebMvcTest와 @SpringBootTest를 대표적으로 사용하는데, 두 가지 Test 어노테이션의 차이가 존재한다고 한다.

※ Mock이란 실제 객체를 만들어서 테스트하기가 어려운 경우에, 가짜 객체를 만들어서 테스트하는 기술이다.

※ MockMvc란 MVC에 관련된 Mock 가짜 객체를 말한다.
웹 어플리케이션을 애플리케이션 서버에 배포하지 않고, 테스트용 MVC 환경을 만들어서 요청 및 전송, 응답 기능을 제공해주는 객체이다.
대부분의 어플리케이션 기능을 테스트하기 위해서는 MockMvc 객체를 만들어서 테스트하게 되는데, MockMvc를 @Autowired로 주입받아서 사용할 수 있다.
*/
