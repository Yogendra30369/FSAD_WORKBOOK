package com.klu.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
@Schema(description = "Represents a student record in the system")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique student identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    @Schema(description = "Full name of the student", example = "Aarav Sharma")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Column(nullable = false, unique = true)
    @Schema(description = "Email address of the student", example = "aarav.sharma@example.com")
    private String email;

    @NotBlank(message = "Course is required")
    @Column(nullable = false)
    @Schema(description = "Course enrolled by the student", example = "Full Stack Application Development")
    private String course;

    public Student() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
