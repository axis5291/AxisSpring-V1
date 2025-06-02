package Axis.Axis_Spring.data.dto;

import Axis.Axis_Spring.data.entity.ListenerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    public ListenerEntity toEntity() {
        return ListenerEntity.builder()
                .id(id)
                .name(name)
                .build();
    }
}
