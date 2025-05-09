package Axis.Axis_Spring.service;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import Axis.Axis_Spring.data.dto.MemberDto;

@Service
public class RestTemplateServiceImpl implements RestTemplateService {

    private final Logger LOGGER= LoggerFactory.getLogger(RestTemplateServiceImpl.class);

    @Override
    public String getAxis(){
        System.out.println("getAxis() 메서드 실행");
        URI uri= UriComponentsBuilder   //*1.URI설정 UriComponentsBuilder : Spring에서 URI(주소)를 안전하고 깔끔하게 생성해주는 도구
                .fromUriString("http://localhost:9090")
                .path("/api/server/jong-ho")   //뒤에 붙는 경로
                .encode()    //UTF-8로 인코딩
                .build()
                .toUri();  //위에 build()로 반환이 component로 됨으로 uri로 변경,   build().toUri(): 최종 URI 객체로 변환

        RestTemplate restTemplate=new RestTemplate();  //*2.통신을 위한 RestTemplate 객체 생성
        
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(uri, String.class);  
       //3.* 실제로 HTTP 통신을 실행해서, 서버로부터 응답을 받아오는 핵심 코드, String.class->ResponseEntity<String>타입을 밎추기 위해서

            LOGGER.info("status code:{}", responseEntity.getStatusCode());
            LOGGER.info("body: {}", responseEntity.getBody());

            return responseEntity.getBody();
    }

    @Override
    public String getName1(){
        System.out.println("getName1() 메서드 실행");
        URI uri= UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/name1")  
                .queryParam("name", "Erlia")  //requestParameter->키값과 value값을 넣는다
                .encode()   
                .build()
                .toUri();  

        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(uri, String.class);
       
        LOGGER.info("status code:{}", responseEntity.getStatusCode());
        LOGGER.info("body: {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

    @Override
    public String getName2(){
        System.out.println("getName2() 메서드 실행");
        URI uri= UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/path-variable/{name}")   //.expand("Erlia")의 erlia를 넣어주게 된다
                .encode()  
                .build()
                .expand("Erlia2")  //복수의 값을 넣어야 할 경우 , 추가하여 구분
                .toUri(); 

        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(uri, String.class);
      
        LOGGER.info("status code:{}", responseEntity.getStatusCode());
        LOGGER.info("body: {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

   
    @Override
    public ResponseEntity<MemberDto> postDto() {   // [✔] 단순히 요청 본문만 전송하는 POST 예제
        System.out.println("postDto() 메서드 실행");
    
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/member")
                .queryParam("name", "Erlia22")   // [❗] 실제 서버에서 이 파라미터를 RequestParam으로 받을 경우에만 의미 있음
                .queryParam("email", "erlia22@navre.com")
                .queryParam("group", "Axis22")
                .encode()
                .build()
                .toUri();
    
        MemberDto memberDTO = new MemberDto();   // 요청 본문에 담길 DTO 생성
        memberDTO.setName("erlia22");
        memberDTO.setEmail("aaa22@ssa.com");
        memberDTO.setGroup("Axis22");
    
        RestTemplate restTemplate = new RestTemplate();
        
        // [✔] postForEntity(): URI, 요청 바디, 응답 타입을 받아 POST 요청을 전송
        ResponseEntity<MemberDto> responseEntity = restTemplate.postForEntity(uri, memberDTO, MemberDto.class);
    
        LOGGER.info("status code:{}", responseEntity.getStatusCode());
        LOGGER.info("body: {}", responseEntity.getBody());
    
        return responseEntity;
    }
    
    @Override  // DTO가 잘 전송되고, dto가 잘 응답으로 돌아오는지 확인하는 일종의 검증 코드
    public ResponseEntity<MemberDto> addHeader() {   // [✔] 요청 본문 + 커스텀 헤더까지 포함하여 전송하는 POST 예제
        System.out.println("addHeader() 메서드 실행");
    
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/add-header")
                .encode()
                .build()
                .toUri();
    
        MemberDto memberDTO = new MemberDto();
        memberDTO.setName("erlia5291");
        memberDTO.setEmail("aaa@ssa.com");
        memberDTO.setGroup("Axis5291");
    
        // [✔] RequestEntity를 사용하면 메서드, 접속할 URI, 헤더, 본문까지 모두 설정 한번에 가능하여 exchange()에 별도로 uri를 넘길 필요 없음
        RequestEntity<MemberDto> requestEntity = RequestEntity
                .post(uri)
                .header("Axis-Header", "Axis Spring에서 보낸 header값입니다.")  // [✔] 커스텀 헤더 설정
                .body(memberDTO);
    
        RestTemplate restTemplate = new RestTemplate();
        
        // [✔] exchange(): RequestEntity를 사용해 요청 전송, 응답 타입은 MemberDto
        ResponseEntity<MemberDto> responseEntity = restTemplate.exchange(requestEntity, MemberDto.class);
    
        LOGGER.info("status code:{}", responseEntity.getStatusCode());
        LOGGER.info("body: {}", responseEntity.getBody());
    
        return responseEntity;
    }
    
}

// 🔍 왜 postForEntity()를 쓰지 않고 exchange() + RequestEntity?
// postForEntity()는 간단하지만, 헤더를 따로 설정하거나 요청 옵션을 세밀하게 제어하기 어렵습니다.
// exchange()는 RequestEntity를 받아서 헤더 포함 전체 요청을 유연하게 처리할 수 있는 메서드입니다.

// restTemplate.postForEntity(uri, memberDTO, MemberDto.class);
// 헤더 설정 불가, 기본 Content-Type만 가능