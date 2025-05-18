package Axis.Axis_Spring.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "short_url")
public class ShortUrlEntity extends BaseEntity {
    
  @Id
  private String id;  // 자동 생성 제거

  @Column(nullable = false, unique = true)
  private String orgUrl;

  @Column(nullable = false, unique = true)
  private String shortUrl;

}
