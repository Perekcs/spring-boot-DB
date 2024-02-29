package org.example.springbootpr1.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Person {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    long id;
    String name;
    boolean gender;
    int age;

    public Person(String name, boolean gender, int age){
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
}
