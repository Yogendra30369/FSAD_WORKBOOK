package com.klu.controller;

import com.klu.dto.ErrorResponse;
import com.klu.model.Student;
import com.klu.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@Tag(name = "Student API", description = "CRUD operations for student management")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Add a new student", description = "Creates a student record with name, email, and course")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Student created successfully",
                    content = @Content(schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Student> createStudent(
            @RequestBody(
                    description = "Student payload to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Student.class),
                            examples = @ExampleObject(
                                    name = "Create student",
                                    value = """
                                            {
                                              \"name\": \"Aarav Sharma\",
                                              \"email\": \"aarav.sharma@example.com\",
                                              \"course\": \"Full Stack Application Development\"
                                            }
                                            """
                            )
                    )
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody Student student) {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retrieve all students", description = "Fetches the complete list of students")
    @ApiResponse(responseCode = "200", description = "Students fetched successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a student by ID", description = "Returns one student if found")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student found",
                    content = @Content(schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Student not found",
                                    value = """
                                            {
                                              \"status\": 404,
                                              \"error\": \"Not Found\",
                                              \"message\": \"Student not found with id: 999\",
                                              \"path\": \"/students/999\",
                                              \"timestamp\": \"2026-04-09T13:35:21\"
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<Student> getStudentById(
            @Parameter(description = "Student ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a student", description = "Updates student details for the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student updated successfully",
                    content = @Content(schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Student> updateStudent(
                        @Parameter(description = "Student ID", example = "1")
                        @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody Student studentDetails) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student", description = "Deletes the student record for the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
        public ResponseEntity<Void> deleteStudent(
                        @Parameter(description = "Student ID", example = "1")
                        @PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
