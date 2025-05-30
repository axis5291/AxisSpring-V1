package Axis.Axis_Spring.data.dto;

import Axis.Axis_Spring.data.entity.ListenerEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class ListenerDto {

    private Long id;
    private String name;

    public ListenrerEntity toEntity() {
        return ListenerEntity.builder()
                .id(id)
                .name(name)
                .build();
    }
}
