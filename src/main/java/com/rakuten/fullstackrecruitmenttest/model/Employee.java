package com.rakuten.fullstackrecruitmenttest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "employee_group")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Size(min=2, max=30)
    @Pattern(regexp = "^[A-Za-z ]*$")
    private String name;
    @Size(min=2, max=30)
    @Pattern(regexp = "^[A-Za-z0-9-_*]*$")
    private String department;
    private Designation designation;
    private Integer salary;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past
    private Date joindate;

    public enum Designation {
        DEVELOPER, SENIOR_DEVELOPER, MANAGER, TEAM_LEAD, VP, CEO;
    }

    public Employee(Long id, String name, String department, Designation designation, Integer salary, Date joindate) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.joindate = joindate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name
                + ", department=" + department
                + ", designation=" + designation
                + ", salary=" + salary
                + ", joindate=" + joindate + '}';
    }
}