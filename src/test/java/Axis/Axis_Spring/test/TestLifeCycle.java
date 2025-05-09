package Axis.Axis_Spring.test;

import org.junit.jupiter.api.*;

public class TestLifeCycle {
//별도로 실행을 할수 있다.
    //아래는 순차적으로 각각 메서드들이 실행이 된다.
    @BeforeAll
    static void beforeAll() {
        System.out.println("## BeforeAll Annotation 호출 ##");
        System.out.println();
    }

    @AfterAll
    //2번째줄에 있음에도 불구하고 출력창을 보면 맨마지막에 출력이 되었다..
    // 각 어노테이션에 따라 실행순서가 있다라는 것에 주목
    static void afterAll() {
        System.out.println("## afterAll Annotation 호출 ##");
        System.out.println();
    }

    @BeforeEach  //@Test붙은거 이전에 실행되는거
    void beforeEach() {
        System.out.println("## beforeEach Annotation 호출 ##");
        System.out.println();
    }

    @AfterEach  //@Test 붙은거 이후에 실행되는거
    void afterEach() {
        System.out.println("## afterEach Annotation 호출 ##");
        System.out.println();
    }

    //왼쪽하단을 보면 통과하면 체크박스 표시가 되고 안되면 멈춤표시가 있다.

    @Test
    void test1() {
        System.out.println("## test1 시작 ##");
        System.out.println();
    }

    @Test
    @DisplayName("Test Case 2!!!")   //이것으로 지정하면 이명칭으로 생성된다. 왼쪽하단 실행창 볼것
    void test2() {
        System.out.println("## test2 시작 ##");
        System.out.println();
    }

    @Test
    @Disabled         // Disabled Annotation : 테스트를 실행하지 않게 설정하는 어노테이션
    void test3() {
        System.out.println("## test3 시작 ##");
        System.out.println();
    }

}
