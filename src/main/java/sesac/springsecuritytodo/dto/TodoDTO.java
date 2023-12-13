package sesac.springsecuritytodo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sesac.springsecuritytodo.entity.TodoEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data // getter, setter
public class TodoDTO {
    private Long id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
        // userId 는 숨김 처리
    }

    // DTO를 entity로 반환하는 메소드
    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}
