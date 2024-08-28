package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.controller.dto.NewPassword;
import ru.skypro.homework.controller.dto.UpdateUser;
import ru.skypro.homework.controller.dto.UserDto;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Обновление пароля
    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword newPasswordDto,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        userService.setPassword(newPasswordDto, userDetails.getUsername());
        return ResponseEntity.ok(newPasswordDto);
    }

    //Получение информации об авторизованном пользователе
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUser(userDetails.getUsername()));
    }

    //Обновление информации об авторизованном пользователе
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUser updateUser,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.updateUser(updateUser, userDetails.getUsername()));
    }

    //Обновление аватара авторизованного пользователя
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam MultipartFile image,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        userService.updateAvatar(image, userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    //Ху из Денис?)
    @GetMapping(value = "/image/{name}", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getImages(@PathVariable String name) throws IOException {
        return userService.getImage(name);
    }
}
