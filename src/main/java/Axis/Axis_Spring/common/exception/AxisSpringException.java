package Axis.Axis_Spring.common.exception;

import Axis.Axis_Spring.common.Constants;
import org.springframework.http.HttpStatus;

public class AxisSpringException extends Exception{
    private static final long serialVersionID=4463380430591151694L;

    private Constants.ExceptionClass exceptionClass;
    private HttpStatus httpStatus;

    public AxisSpringException(Constants.ExceptionClass exceptionClass, HttpStatus httpStatus, String message){
        super(exceptionClass.toString()+message);
        this.exceptionClass=exceptionClass;
        this.httpStatus=httpStatus;
    }

    public Constants.ExceptionClass getExceptionClass(){
        return exceptionClass;
    }

    public int getHttpStatusCode(){
        return httpStatus.value();   //에러코드
    }

    public String getHttpStatusType(){
        return httpStatus.getReasonPhrase();  //에러메세지
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;  //객체반환
    }
}
