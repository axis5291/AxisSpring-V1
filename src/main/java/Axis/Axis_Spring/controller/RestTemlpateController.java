package Axis.Axis_Spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Axis.Axis_Spring.data.dto.MemberDto;
import Axis.Axis_Spring.service.RestTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

//서버간의 통신을 위한 RestTemplate을 이용한 예제 컨트롤러, 
@Tag(name = "RestTemplate를 이용한 서버간의 통신", description = "2대의 Axis_Spring과 AxisServerBox간의 RestTemplate를 이용하여 통신하는 예제")
@RestController
@RequestMapping(value = "/api/rest-template")
public class RestTemlpateController {

    RestTemplateService restTemplateService;

    @Autowired
    public RestTemlpateController(RestTemplateService restTemplateService){
        this.restTemplateService=restTemplateService;
    }

    @GetMapping(value = "/axis")
    public String getAxis() {
        return restTemplateService.getAxis();
    }

    @GetMapping(value = "/name1")
    public String getName1() {
        return restTemplateService.getName1();
    }

    @GetMapping(value = "/name2")
    public String getName2() {
        return restTemplateService.getName2();
    }

    @Operation(summary = "POST 요청:내용만 전달", description = "POST 요청을 통해 DTO를 전송하는 예제입니다.")
    @PostMapping(value = "/dto")
    public ResponseEntity<MemberDto> postDto() {
        return restTemplateService.postDto();
    }

    @Operation(summary = "POST 요청:헤더를 포함 추가적으로 세부내용 전달", description = "POST 요청을 통해 DTO와 커스텀 헤더를 전송하는 예제입니다.")
    @PostMapping(value = "/add-header")
    public ResponseEntity<MemberDto> addHeader() {
        return restTemplateService.addHeader();
    }

}
