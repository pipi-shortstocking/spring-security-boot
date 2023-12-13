package sesac.springsecuritytodo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Todo")
// 지정을 하지 않으면 entity에 지정하는 이름이 table명, 그래도 안하면 클래스 명
public class TodoEntity {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="userId", nullable = false)
    private String userId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name = "done", nullable = false)
    private boolean done;
}
