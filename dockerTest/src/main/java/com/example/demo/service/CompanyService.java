package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.entity.Employees;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private EmployeesRepository employeesRepository;

    @Autowired
    public  CompanyService(CompanyRepository companyRepository, EmployeesRepository employeesRepository){
        this.companyRepository = companyRepository;
        this.employeesRepository=employeesRepository;
    }

    public Company addCompany(Company company) {
        if(company.getEmployeesList().size()!=0){
            company.getEmployeesList().stream().forEach(employee -> {
                employee.setCompany(company);});
        }
        return  companyRepository.save(company);
    }

    public List<Company> getCompanies() {
        return  companyRepository.findAll();
    }

    public Company getCompanyById(long id) {
        return  companyRepository.findById(id).get();
    }

    public Company updateCompany(Company company) {
        return  companyRepository.save(company);
    }

    public Company deleteCompany(long id) {
        Company company=  companyRepository.findById(id).get();
        if(company==null){
            return  null;
        }
        else {
            companyRepository.delete(company);
            return  company;
        }
    }

    public List<Employees> getemployeesByCompanyId(long id) {
        return  companyRepository.findById(id).get().getEmployeesList();
    }

    public List<Company> getCompanyBypage(int page, int pagesize) {
        return  companyRepository.findAll(new PageRequest(page,pagesize)).getContent();
    }
}


