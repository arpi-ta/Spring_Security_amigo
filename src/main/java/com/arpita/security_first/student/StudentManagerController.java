package com.arpita.security_first.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("manager/api/student")
public class StudentManagerController {

    private static final List<Student> STUDENTS= Arrays.asList(
            new Student(1,"Arpita"),
            new Student(2,"Mohammed Sohail"),
            new Student(3,"Linda")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public static List<Student> getAllSTUDENTS() {
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void insertStudent(@RequestBody Student student){
        System.out.println("Insert");
        System.out.println(student);
    }

    @DeleteMapping(path="{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudents(@PathVariable("studentId") Integer studentId){
        System.out.println("Delete");
        System.out.println(studentId);
    }
    @PutMapping(path="{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudents(@PathVariable("studentId") Integer studentId,@RequestBody Student student){
        System.out.println("Update");
        System.out.println(String.format("%s %s",studentId,student));
    }
}

