package com.springbootWithMongoDB.studentManagement.repository;


import com.springbootWithMongoDB.studentManagement.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface StudentRepository extends MongoRepository<Student, BigInteger> {

    @Query("{name:'?0'}")
    Student findItemByName(String name);

}
