package io.maddennis.controller;

import io.maddennis.entity.User;
import io.maddennis.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/saveUsers", consumes= MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public ResponseEntity<String> saveUsers(
            @Schema(type = "string", format = "binary")
            @RequestPart(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file: files) {
            userService.saveUsers(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/getAllUsers", produces = "application/json")
    public CompletableFuture<ResponseEntity> getUsers() {
        return userService.getUsers().thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/getUsersByThread", produces = "application/json")
    public  ResponseEntity findAllUsers(){

        CompletableFuture<List<User>> users1=userService.getUsers();
        CompletableFuture<List<User>> users2=userService.getUsers();
        CompletableFuture<List<User>> users3=userService.getUsers();

        CompletableFuture.allOf(users1,users2,users3).join();
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
}
