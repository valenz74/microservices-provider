package com.example.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RecommendationService {

    private final Logger logger = LoggerFactory.getLogger(RecommendationService.class);
    private final List<String> books = List.of("Clean Code", "Building Microservices", "Head First Design Patterns", "Spring In Action", "Cloud Native Java");
    private boolean fail = false;

    @GetMapping(value = "/")
    public ResponseEntity<Mono<String>> recommendations() {
        logger.info("Solicitud recibida");
        if (fail) {
            logger.info("Induciendo a error");
            return ResponseEntity.internalServerError().build();
        }
        int index = ThreadLocalRandom.current().nextInt(0, books.size());
        return ResponseEntity.ok(Mono.just(books.get(index)));
    }

    @GetMapping(value = "/change")
    public Mono<String> changeToFail() {
        this.fail = !fail;
        return Mono.just("Changed to " + this.fail);
    }
}
