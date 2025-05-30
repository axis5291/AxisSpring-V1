package Axis.Axis_Spring.data.entity;

import Axis.Axis_Spring.data.dto.ListenerDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "listener")
@EntityListeners(CustomListener.class)  //엔티티의 변화를 감지하는 대상이라는 표시와 로그를 CustomListener 클래스에서 처리한다라고 지정하는 어노테이션
public class ListenerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

      public ListenerDto toDto(){
        return ListenerDto.builder()
                .id(id)
                .name(name)
                .build();
    }
}
