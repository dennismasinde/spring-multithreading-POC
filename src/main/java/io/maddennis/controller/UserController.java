package io.maddennis.controller;

import io.maddennis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/saveUsers", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity<String> saveUsers(@RequestParam (value = "files")MultipartFile[] files) throws Exception {
        for (MultipartFile data: files) {
            userService.saveUsers(data);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getAllUsers")
    public CompletableFuture<ResponseEntity> getUsers() {
        return userService.getUsers().thenApply(ResponseEntity::ok);
    }
}
