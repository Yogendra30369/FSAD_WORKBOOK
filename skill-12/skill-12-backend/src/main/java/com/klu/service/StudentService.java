package com.klu.service;

import java.util.List;
import com.klu.model.Student;

public interface StudentService {

Student addStudent(Student student);

List<Student> getStudents();

Student updateStudent(Long id,Student student);

void deleteStudent(Long id);

}