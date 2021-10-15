package com.springbootWithMongoDB.studentManagement.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "Student")
public class Student {

    @Id
    private BigInteger id;
    private int rollNum;
    private String name;


}
