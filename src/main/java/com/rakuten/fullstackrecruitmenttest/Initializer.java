package com.rakuten.fullstackrecruitmenttest;

import com.rakuten.fullstackrecruitmenttest.model.Employee;
import com.rakuten.fullstackrecruitmenttest.model.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final EmployeeRepository repository;

    public Initializer(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
                "Richmond JUG").forEach(name ->
                repository.save(new Employee(name))
        );

        repository.findAll().forEach(System.out::println);
    }
}
