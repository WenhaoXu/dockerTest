package com.example.demo;

import com.example.demo.entity.Company;
import com.example.demo.entity.Employees;
import com.example.demo.repository.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_return_CompanyList_when_findByAll(){
        List<Employees> employeesList=new ArrayList<>();
        Company savedcompany=entityManager.persistFlushFind(new Company("first",employeesList));
        List<Company> list=companyRepository.findAll();
        Assertions.assertThat(list.get(0)).isEqualTo(savedcompany);
    }

    @Test
    public void should_return_Company_when_save(){
        List<Employees> employeesList=new ArrayList<>();
        Company company=new Company("first",employeesList);
        company.setId(2);
        Company company1=   companyRepository.save(company);
        Assertions.assertThat(company.getName()).isEqualTo("first");
    }

    @Test
    public void should_return_Company_when_findById(){
        List<Employees> employeesList=new ArrayList<>();
        Company savedcompany=entityManager.persistFlushFind(new Company("first",employeesList));
        Company  company=companyRepository.findById(savedcompany.getId()).get();
        Assertions.assertThat(company.getName()).isEqualTo("first");
    }


    @Test
    public void should_return_CompanyList_when_findByAllByPage(){
        List<Employees> employeesList=new ArrayList<>();
        Company savedcompany=entityManager.persistFlushFind(new Company("first",employeesList));
        Company savedcompany1=entityManager.persistFlushFind(new Company("first1",employeesList));
        Company savedcompany2=entityManager.persistFlushFind(new Company("first2",employeesList));

        List<Company> list=companyRepository.findAll(new PageRequest(0,2)).getContent();
        Assertions.assertThat(list.get(0).getName()).isEqualTo("first");
    }

}
