package com.example.demo;



import com.example.demo.entity.Company;
import com.example.demo.entity.Employees;
import com.example.demo.repository.EmployeesRepository;
import com.example.demo.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeesRepository repository;

    @Test
    public void should_return_employees_when_call_findAll() {
        when(repository.findAll()).thenReturn(new ArrayList<Employees>() {{
            add(new Employees(1L,"ad",new Company("a")));
            add(new Employees(2L,"asd",new Company("a")));
        }});
        EmployeeService service = new EmployeeService(repository);
        List<Employees> employees = service.findAll();
        Assertions.assertThat(employees).hasSize(2);
        Assertions.assertThat(employees.get(0).getName()).isEqualTo("ad");
    }

    @Test
    public void should_return_a_employee_when_call_findOne() {

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(new Employees(1L,"ad",new Company("a"))));
        EmployeeService service = new EmployeeService(repository);
        Employees employee = service.findOne(1);
        Assertions.assertThat(employee.getName()).isEqualTo("ad");
    }

    @Test
    public void should_success_when_call_add_a_employee() {
        Employees employee = new Employees(1L,"ad",new Company("a"));
        EmployeeService service = new EmployeeService(repository);
        service.save(employee);
        verify(repository).save(employee);
    }

    @Test
    public void should_success_when_call_update_a_employee() {
        Employees employee = new Employees(1L,"ad",new Company("a"));
        EmployeeService service = new EmployeeService(repository);
        service.save(employee);
        verify(repository).save(employee);
    }

    @Test
    public void should_success_when_call_delete_a_employee() {

        EmployeeService service = new EmployeeService(repository);
        service.delete(1L);
        verify(repository).deleteById(1L);
    }


    @Test
    public void should_return_employees_when_call_find_employee_by_pageable(){
        Integer curPage = 0;
        Integer size = 2;
        PageRequest request = PageRequest.of(curPage,size);
        when(repository.findAll(request)).thenReturn(new PageImpl<Employees>(new LinkedList<Employees>(){{
            add(new Employees(1L,"ad",new Company("a")));
            add(new Employees(2L,"asd",new Company("a")));
        }}));
        EmployeeService service = new EmployeeService(repository);
        List<Employees> employees = service.findByPagable(curPage, size);
        Assertions.assertThat(employees).hasSize(2);
        Assertions.assertThat(employees.get(0).getName()).isEqualTo("ad");
    }
}