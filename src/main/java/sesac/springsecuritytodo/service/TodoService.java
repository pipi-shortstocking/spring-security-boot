package sesac.springsecuritytodo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sesac.springsecuritytodo.entity.TodoEntity;
import sesac.springsecuritytodo.repository.TodoRepository;

import java.util.List;

@Slf4j
// - Simple Logging Facade for Java
// - 로그 라이브러리
// - 용도에 따라서 info, debug, warn, error 를 나눠서 로깅
// - 로깅을 하는 클래스에 붙여주면 되는 어노테이션
@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    // create todo
    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity); // 유효성 검사

        repository.save(entity); // 생성

        log.info("Entity id: {} is saved. ", entity.getId()); // 로깅

        return repository.findByUserId(entity.getUserId());
    }

    // read todo
    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    // entity 유효성 검사 메소드
    // - create, update, delete 할 때 사용되므로 메소드로 처리
    private void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
