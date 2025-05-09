package Axis.Axis_Spring.data.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//DTO Data Transfer Object
@Getter
@Setter
@ToString
public class MemberDto {
    private String name;
    private String email;
    private String group;
    private String sex;    //내가 만든거
}
//각 필드를 블럭으로 잡고  마우스옵션->Refactor->Delombok하면 각 메서드가 구현되는 것을 볼수 있다.
/*Lombok?  ->상황에 따라 쓰는곳도 있고 안쓰는 곳도 있다.

    생성자관련 Annotation
    @NoArgConstructor:파라미터가 없는 생성자를 생성
    @AllArgConstructor,:모든 필드값을 파라미터로 갖는 생성자를 생성
    @RequiredArgsConstructor:필드값 중 final이나 @NotNull이 붙어있는 생성자를 찾아서  값을 갖는 생성자를 생성


    @ToString:exclude속성을 사용하여 특정 필드를 toString에서 제외시킬수 있음
    @ToString(exclude=“email)이렇게 제외가능

    @EqualsAndHashCode
    Equals, hashCode메서드를 자동으로 생성
    callSuper 속성을 통해 메서드 생성시 부모 클래스의 필드까지 고려할지 여부 설정 가능
-callSuper=true ->부모 클래스 필드값들도 동인한지 체크

    @Data:이것을 사용하면 @Gettter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode을 한번에 추가
*/
