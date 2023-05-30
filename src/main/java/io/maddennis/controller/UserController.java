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
    public CompletableFuture<ResponseEntity> getUsers(Integer pageNo, Integer pageSize, String sortBy) {
        return userService.getUsers(pageNo,pageSize,sortBy).thenApply(ResponseEntity::ok);
    }


}
