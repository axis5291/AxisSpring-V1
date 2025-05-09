package Axis.Axis_Spring.common;

public class Constants {
    public enum ExceptionClass {
        PRODUCT("Product"), ORDER("Order"), PROVIDER("Provider");

        private String exceptionClass;

        ExceptionClass(String exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        public String getExceptionClass(){
            return exceptionClass;
        }
        @Override
        public String toString(){
            return getExceptionClass()+" Exception";
        }

    }
}
//클래스명.이넘명.상수 형식으로 사용하면 된다. 
//예를 들어 Constants.ExceptionClass.PRODUCT.getExceptionClass()는 "Product"를 반환한다.