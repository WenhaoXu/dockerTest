package com.example.demo.controllor;

import com.example.demo.entity.Company;
import com.example.demo.entity.Employees;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.EmployeesRepository;
import com.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CompanyControllor {

    private CompanyService companyService;
   @Autowired
    public CompanyControllor(CompanyService companyService) {
            this.companyService=companyService;
    }

    @Transactional
    @PostMapping( "/company")
    public Company addCompany(@Valid @RequestBody Company company){
       return  companyService.addCompany(company);
    }
    @Transactional
    @GetMapping("/company")
    public List<Company> getCompanies(){
       return  companyService.getCompanies();
    }

    @Transactional
    @GetMapping("/company/{id}")
    public Company getCompanyById(@PathVariable long id){
       return  companyService.getCompanyById(id);

    }
    @Transactional
    @PutMapping("/company")
    public Company updateCompany(@RequestBody Company company){
       return companyService.updateCompany(company);

    }
    @Transactional
    @DeleteMapping("/company/{id}")
    public Company deleteCompany(@PathVariable long id){
       return companyService.deleteCompany(id);
    }

    @Transactional
    @GetMapping("/company/{id}/employees")
    public List<Employees> getemployeesByCompanyId(@PathVariable long id){
        return  companyService.getemployeesByCompanyId(id);

    }

    @Transactional
    @GetMapping("/company/page/{page}/pageSize/{pagesize}")
    public List<Company> getCompanyBypage(@PathVariable int page,@PathVariable int pagesize){
        return  companyService.getCompanyBypage(page,pagesize);
    }


}
