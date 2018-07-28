package com.example.demo.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "company",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Employees> employeesList= new ArrayList<>();

    public Company( String name,List<Employees> employeesList) {
        this.name = name;

        this.employeesList=employeesList;
    }

    public Company(long id, String name) {
        this.name = name;
        this.id=id;
    }
    public Company(String name) {this.name = name;
    }
    public Company() {
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employees> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employees> employeesList) {
        this.employeesList = employeesList;
    }
}
