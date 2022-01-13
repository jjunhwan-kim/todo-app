package com.app.todo.controller;


import com.app.todo.dto.ResponseDto;
import com.app.todo.dto.UserDto;
import com.app.todo.model.UserEntity;
import com.app.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {

        try {
            UserEntity user = UserEntity.builder()
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .build();

            UserEntity registeredUser = userService.create(user);
            UserDto responseUserDto = UserDto.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {

        UserEntity user = userService.getByCredentials(userDto.getEmail(), userDto.getPassword());

        if (user != null) {
            final UserDto responseUserDto = UserDto.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        } else {
            ResponseDto responseDto = ResponseDto.builder()
                    .error("Login failed.")
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
