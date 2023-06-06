package io.maddennis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() - 2);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() - 2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("User Thread-");
        executor.initialize();

        return executor;
    }
}
