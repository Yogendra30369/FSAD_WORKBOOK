package com.klu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.model.Student;
import com.klu.service.StudentService;

@RestController
@RequestMapping("/students")
@CrossOrigin("*")
public class StudentController {

@Autowired
private StudentService service;

@PostMapping
public ResponseEntity<Student> addStudent(@RequestBody Student student){
return ResponseEntity.ok(service.addStudent(student));
}

@GetMapping
public ResponseEntity<List<Student>> getStudents(){
return ResponseEntity.ok(service.getStudents());
}

@PutMapping("/{id}")
public ResponseEntity<Student> updateStudent(
@PathVariable Long id,
@RequestBody Student student){

return ResponseEntity.ok(service.updateStudent(id,student));
}

@DeleteMapping("/{id}")
public ResponseEntity<String> deleteStudent(@PathVariable Long id){

service.deleteStudent(id);
return ResponseEntity.ok("Deleted Successfully");

}

}