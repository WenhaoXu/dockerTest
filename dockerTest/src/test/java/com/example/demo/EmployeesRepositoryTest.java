package com.example.demo;




import com.example.demo.entity.Company;
import com.example.demo.entity.Employees;
import com.example.demo.repository.EmployeesRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeesRepositoryTest {

    @Autowired
    private EmployeesRepository repository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void should_return_Employees_when_call_findAll(){
        entityManager.persistAndFlush(new Employees("as",new Company("a")));
        entityManager.persistAndFlush(new Employees("bs",new Company("a")));
        List<Employees> all = repository.findAll();
        Assertions.assertThat(all).hasSize(2);
        Assertions.assertThat(all.get(0).getName()).isEqualTo("a");
    }

    @Test
    public void should_return_a_Employees_when_call_findOne(){
        entityManager.persistAndFlush(new Employees("bs",new Company("a")));
        Employees Employees = repository.findById(1L).get();
        Assertions.assertThat(Employees.getName()).isEqualTo("a");
    }

    @Test
    public void should_success_when_call_add_a_Employees(){
        Employees save = repository.save(new Employees("bs",new Company("a")));
        Employees Employees = repository.findAll().get(0);
        Assertions.assertThat(Employees.getId()).isEqualTo(save.getId());
    }

    @Test
    public void should_success_when_call_update_a_Employees(){
        Employees Employees = entityManager.persistAndFlush(new Employees("bs",new Company("a")));
        Employees.setName("22222");
        repository.save(Employees);
        Assertions.assertThat(Employees.getName()).isEqualTo("22222");
    }

    @Test
    public void should_success_when_call_delete_a_Employees(){
        Employees Employees = entityManager.persistAndFlush(new Employees("bs",new Company("a")));
        repository.delete(Employees);
        Assertions.assertThat(repository.findAll()).isEmpty();
    }


    @Test
    public void should_return_Employeess_when_call_find_Employees_by_pageable(){
        entityManager.persistAndFlush(new Employees("bs",new Company("a")));
        entityManager.persistAndFlush(new Employees("bss",new Company("a")));
        entityManager.persistAndFlush(new Employees("bas",new Company("a")));
        entityManager.persistAndFlush(new Employees("bcs",new Company("a")));
        entityManager.persistAndFlush(new Employees("bssa",new Company("a")));
        Page<Employees> pageEmployees = repository.findAll(PageRequest.of(0, 2));
        Assertions.assertThat(pageEmployees.getContent()).hasSize(2);
        Assertions.assertThat(pageEmployees.getContent().get(0).getName()).isEqualTo("bs");

    }
}
