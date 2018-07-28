package com.example.demo;

import com.example.demo.entity.Company;
import com.example.demo.entity.Employees;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.EmployeesRepository;
import com.example.demo.service.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;

    @Mock
    EmployeesRepository employeesRepository;



    @Test
    public void should_return_company_when_addCompany(){
        CompanyService companyService=new CompanyService(companyRepository,employeesRepository);
        Company company=new Company(1,"ad");
        given(companyRepository.save(company)).willReturn(company);
        companyService.addCompany(company);
        verify(companyRepository).save(company);

    }


}
