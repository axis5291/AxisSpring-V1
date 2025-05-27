package Axis.Axis_Spring.data.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass  // This class will be the base class for all entities
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate 같은 애노테이션이 자동으로 작동하게 도와줘.
public class BaseEntity {

  @CreatedDate  //엔티티가 처음 저장될 때 자동으로 현재 시간을 넣어줘.
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate   //엔티티가 수정될 때 자동으로 현재 시간을 넣어줘.
  private LocalDateTime updatedAt;
}

// 자동으로 시간을 넣는 전제 조건
// @EnableJpaAuditing  → 보통 @SpringBootApplication 클래스에 추가해줘.(어플리케이션 메인 클래스:AxisSpringApplication)