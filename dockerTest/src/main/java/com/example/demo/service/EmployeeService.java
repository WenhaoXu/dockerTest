package com.example.demo.service;



import com.example.demo.entity.Employees;
import com.example.demo.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeesRepository repository;

    public EmployeeService(EmployeesRepository repository) {
        this.repository = repository;
    }


    public List<Employees> findAll() {
        return repository.findAll();
    }


    public Employees findOne(long employeeId) {
        try {
            return repository.findById(employeeId).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void save(Employees employee) {
        repository.save(employee);
    }

    public void delete(long employeeId) {
        try {
            repository.deleteById(employeeId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<Employees> findByPagable(Integer curPage, Integer size) {
        return repository.findAll(PageRequest.of(curPage, size)).getContent();
    }
}
