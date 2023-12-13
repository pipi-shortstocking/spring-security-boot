package sesac.springsecuritytodo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sesac.springsecuritytodo.dto.ResponseDTO;
import sesac.springsecuritytodo.dto.UserDTO;
import sesac.springsecuritytodo.entity.UserEntity;
import sesac.springsecuritytodo.security.TokenProvider;
import sesac.springsecuritytodo.service.UserService;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService service;

    // [after] jwt 적용
    @Autowired
    private TokenProvider tokenProvider;

    // [after] 패스워드 암호화 적용
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO dto) {
        try {
            // req body에 담긴 사용자 정보를 이용해 저장할 사용자 엔티티 객체 생성
            UserEntity user = UserEntity.builder()
                    .email(dto.getEmail())
                    .username(dto.getUsername())
                    // .password(dto.getPassword())
                    .password(passwordEncoder.encode(dto.getPassword())) // 비번 암호화
                    .build();

            // 서비스를 이용해서 레포지터리에 사용자 저장
            UserEntity registerdUser = service.create(user);

            // 엔티티를 dto 변환
            UserDTO resDTO = UserDTO.builder()
                    .email(registerdUser.getEmail())
                    .id(registerdUser.getId())
                    .username(registerdUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(resDTO);
        } catch(Exception e) {
            ResponseDTO resDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(resDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO dto) {
//    UserEntity user = service.getByCredentials(dto.getEmail(), dto.getPassword());
        UserEntity user = service.getByCredentials(dto.getEmail(), dto.getPassword(), passwordEncoder);

        if (user != null) {
            // 이메일, 비번으로 찾은 유저 있음 = 로그인 성공
            // [before] jwt 적용
//      final UserDTO resUserDTO = UserDTO.builder()
//          .email(user.getEmail())
//          .id(user.getId())
//          .build();

            // [after] jwt 적용
            final String token = tokenProvider.create(user);
            final UserDTO resUserDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token) // jwt 토큰 설정
                    .build();

            return ResponseEntity.ok().body(resUserDTO);
        } else {
            // 이메일, 비번으로 찾은 유저 없음 = 로그인 실패
            ResponseDTO resDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();

            return ResponseEntity.badRequest().body(resDTO);
        }
    }
}