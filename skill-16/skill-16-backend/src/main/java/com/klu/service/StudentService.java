package com.klu.service;

import com.klu.exception.ResourceNotFoundException;
import com.klu.model.Student;
import com.klu.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student existingStudent = getStudentById(id);
        existingStudent.setName(studentDetails.getName());
        existingStudent.setEmail(studentDetails.getEmail());
        existingStudent.setCourse(studentDetails.getCourse());
        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        Student existingStudent = getStudentById(id);
        studentRepository.delete(existingStudent);
    }
}
