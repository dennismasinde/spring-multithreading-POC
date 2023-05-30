package io.maddennis.service;

import io.maddennis.entity.User;
import io.maddennis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Async
    public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();

        List<User> users = parseCSVFile(file);
        log.info("Saving {} users to database. Thread {}", users.size(), Thread.currentThread().getName());
        userRepository.saveAll(users);
        long end = System.currentTimeMillis();
        log.info("Processed in {}", end - start);
        return CompletableFuture.completedFuture(users);
    }

    private List<User> parseCSVFile(final MultipartFile file) throws Exception {
        final List<User> users = new ArrayList<>();
        log.info("Inside parseCSV function....");
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String [] data = line.split(",");
                    final User user = new User();

                    user.setFirstName(data[0]);
                    user.setLastName(data[1]);
                    user.setEmail(data[2]);
                    user.setGender(data[3]);
                    user.setIpAddress(data[4]);
                    users.add(user);
                }
                return users;
            }
        } catch (IOException e) {
            log.error("Failed to parse CSV file ", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }

    @Async
    public CompletableFuture<List<User>> getUsers(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("Fetching users from the DB. Thread {}", Thread.currentThread().getName());

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pagedResult = userRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return CompletableFuture.completedFuture(pagedResult.getContent());
        } else {
            return CompletableFuture.completedFuture(new ArrayList<>());
        }
        //return CompletableFuture.completedFuture(userRepository.findAll());
    }
}
