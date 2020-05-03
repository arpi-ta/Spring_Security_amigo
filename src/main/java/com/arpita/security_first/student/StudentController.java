package com.arpita.security_first.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {


    private static final List<Student> STUDENTS=Arrays.asList(
            new Student(1,"Arpita"),
            new Student(2,"Mohammed Sohail"),
            new Student(3,"Linda")
     );

    @GetMapping(path="{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
    return STUDENTS.stream()
            .filter(student -> studentId.equals(student.getStudentId()))
            .findFirst()
            .orElseThrow(()->new IllegalStateException("studentId"+studentId+"doesn't exist"));
    }
}
