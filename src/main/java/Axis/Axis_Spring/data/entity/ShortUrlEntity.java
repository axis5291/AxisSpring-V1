import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
package Axis.Axis_Spring.data.entity;

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
