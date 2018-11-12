package com.student.service;

import java.util.ArrayList;
import java.util.List;

import com.student.model.Student;

public interface StudentService {

    Student findById(int id);
    
    List<Student> findBySearchValue(String value);
         
    void saveStudent(Student student);
     
    void updateStudent(Student student);
     
    void deleteStudentById(int id);
 
    List<Student> findAllStudents(); 
     
    void deleteAllStudents();
    
    public boolean isStudentExist(Student student);
}
