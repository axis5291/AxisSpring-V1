package Axis.Axis_Spring.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//***@RestControllerAdvice:전역예외처리 어노테이션(지역설정이 더 우선순위에 있다.)
// @Controller, @RestController 클래스에서만 발생한 예외만 처리
@RestControllerAdvice 
public class AxisSpringExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(AxisSpringExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)  //@ExceptionHandler(value = Exception.class) Exception클래스  이하의 하위 클래스에서 발생한 예외라면 이 메서드에서 처리한다."
    public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e){
        HttpHeaders responseHeadres =new HttpHeaders();
        HttpStatus httpStatus=HttpStatus.BAD_REQUEST;

        LOGGER.info("exception폴더 내에 AxisSpringExceptionHandler 클래스에서 ******** 전역 예외처리 ********* ");   //하단 콘솔창에서 뭐가 들어오는지 보자
        LOGGER.info("e.getLocalizedMessage()="+e.getLocalizedMessage());          
       
        Map<String, String> map=new HashMap<>();
        map.put("Error Type:", httpStatus.getReasonPhrase());
        map.put("Code", "400");
        map.put("Message", "에러발생");

        return new ResponseEntity<>(map, responseHeadres, httpStatus);
    }

    @ExceptionHandler(value = AxisSpringException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(AxisSpringException e){
        HttpHeaders responseHeadres =new HttpHeaders();

        Map<String, String> map=new HashMap<>();
        map.put("Error Type:", e.getHttpStatusType());
        map.put("Code", Integer.toString(e.getHttpStatusCode()));  //Map<String, String>이고  e.getHttpStatusCode()반환이 int형이므로 String형으로 변환
        map.put("Message", e.getMessage());

        return new ResponseEntity<>(map, responseHeadres, e.getHttpStatus());
    }

}

// @ExceptionHandler는 "이 메서드는 특정 예외를 처리하는 용도입니다" 라고 Spring에 알려주는 어노테이션입니다.
// value 또는 메서드 파라미터를 통해 어떤 예외를 처리할지를 명시합니다.