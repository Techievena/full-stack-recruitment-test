package com.rakuten.fullstackrecruitmenttest.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByName(String name);
}