package com.example.demo;

import com.example.demo.entity.Company;
import com.example.demo.entity.Employees;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.EmployeesRepository;
import com.example.demo.service.CompanyService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void should_return_a_company_when_call_findBy_ID(){
        Company company=new  Company("ad");
        when(companyRepository.findById(1l).get()).thenReturn(company);
        CompanyService companyService=new CompanyService(companyRepository,employeesRepository);
        Company company1 = companyService.getCompanyById(1);
        Assertions.assertThat(company.getName()).isEqualTo("ad");
    }

    @Test
    public void should_success_when_call_update_a_company(){
        Company company = new Company("a",new LinkedList<Employees>(){{
            add(new Employees(1L,"ad",new Company("a")));
            add(new Employees(2L,"ad2",new Company("a")));
        }});
        CompanyService service=new CompanyService(companyRepository,employeesRepository);
        service.addCompany(company);
        verify(companyRepository).save(company);
    }

    @Test
    public void should_success_when_call_delete_a_company(){
        CompanyService service=new CompanyService(companyRepository,employeesRepository);
        service.deleteCompany(1L);
        verify(companyRepository).deleteById(1L);
    }

    @Test
    public void should_return_companies_when_call_find_company_by_condition(){

        when(companyRepository.findById(1l)).thenReturn(java.util.Optional.of(new Company("a", new LinkedList<Employees>() {{
            add(new Employees(1L, "ad",new Company("a")));
            add(new Employees(3L, "ad2", new Company("a")));
        }})));
        CompanyService service=new CompanyService(companyRepository,employeesRepository);
        List<Employees> employees = service.getCompanyById(1L).getEmployeesList();
        Assertions.assertThat(employees).hasSize(2);
        Assertions.assertThat(employees.get(0).getName()).isEqualTo("ad");
    }

    @Test
    public void should_return_companies_when_call_find_company_by_pageable(){
        when(companyRepository.findAll(new PageRequest(0,2))).thenReturn(new PageImpl<Company>(new LinkedList<Company>(){{
            add(new Company("a"));
            add(new Company("b",new LinkedList<Employees>(){{
                add(new Employees(1L,"ad",new Company("b")));
            }}));
        }}));
        Integer curPage=0;
        Integer size=2;

        CompanyService service=new CompanyService(companyRepository,employeesRepository);
        List<Company> companies = service.getCompanyBypage(curPage, size);
        Assertions.assertThat(companies).hasSize(2);
        Assertions.assertThat(companies.get(0).getName()).isEqualTo("a");

    }


}
