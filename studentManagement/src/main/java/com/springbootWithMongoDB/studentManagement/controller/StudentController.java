package com.springbootWithMongoDB.studentManagement.controller;

import com.springbootWithMongoDB.studentManagement.model.Student;
import com.springbootWithMongoDB.studentManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


@RequestMapping("/api")
@RestController
public class StudentController {

    @Autowired
     private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents()
    {
        try
        {
            return  new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable BigInteger id)
    {
        try{
                Optional<Student> byId = studentRepository.findById(id);
                if(byId.isPresent())
                {
                    return new ResponseEntity<>(byId.get(), HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/student/{name}")
    public ResponseEntity<Student> getStudentByName(@PathVariable String name)
    {
        try{

            if(studentRepository.findItemByName(name)!=null)
            {
                return new ResponseEntity<>(studentRepository.findItemByName(name), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/student")
    public ResponseEntity<Student> saveStudent(@RequestBody Student s)
    {
        try {
            Student _student = studentRepository.save(new Student(s.getId(), s.getRollNum(),s.getName()));
            studentRepository.save(_student);
            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/student/{id}")
    public ResponseEntity<Student> deleteStudentById(@PathVariable BigInteger id)
    {

        System.out.println(id);
        try {

            if(studentRepository.existsById(id))
            {
                Optional<Student> byId = studentRepository.findById(id);
              studentRepository.deleteById(id);
              return new ResponseEntity<>(byId.get(), HttpStatus.OK);
            }
            else
            {
               return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable BigInteger id, @RequestParam int rollNum, @RequestParam String name )
    {
        try {
            if(studentRepository.existsById(id))
            {
                studentRepository.deleteById(id);
                studentRepository.save(new Student(id,rollNum,name));
                return new ResponseEntity<>(studentRepository.findById(id).get(), HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
