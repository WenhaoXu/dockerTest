package com.example.demo;

import com.example.demo.controllor.CompanyControllor;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employees;
import com.example.demo.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.binding.When;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@WebMvcTest(CompanyControllor.class)
public class CompanyControllorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getCompany_returnCompanylist()throws Exception{
        List<Company> list=new ArrayList<>();
        Company company=new Company(1,"ad");
        list.add(company);
        given(companyService.getCompanies()).willReturn(list);
        mockMvc.perform(get("/company")).andExpect(jsonPath("$[0].name").value("ad"));
    }

    @Test
    public void postCompany_returnCompany()throws Exception{
        Company company=new Company(1,"ad");
        given(companyService.addCompany(any(Company.class))).willReturn(company);
        ResultActions resultActions= mockMvc.perform(post("/company").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(company)));
        resultActions.andExpect(jsonPath("$.name").value("ad"));
    }

    @Test
    public void should_getCompany_when_findlById()throws Exception{
        List<Company> list=new ArrayList<>();
        Company company=new Company(1,"ad");
        list.add(company);
        given(companyService.getCompanyById(1)).willReturn(company);
        mockMvc.perform(get("/company/{0}",1)).andExpect(jsonPath("$.name").value("ad"));
    }
    @Test
    public void should_updateCompany_when_putCompany()throws Exception {
        List<Company> list=new ArrayList<>();
        Company company=new Company(1,"ad");
        Company company1=new Company(1,"ad2");
        list.add(company);
        given(companyService.updateCompany(any(Company.class))).willReturn(company1);
        ResultActions resultActions= mockMvc.perform(put("/company").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(company1)));
        resultActions.andExpect(jsonPath("$.name").value("ad2"));
    }
    @Test
    public void should_true_when_Delete_Company()throws Exception {
        List<Company> list=new ArrayList<>();
        Company company=new Company(1,"ad");
        list.add(company);
        given(companyService.deleteCompany(1)).willReturn(company);
        mockMvc.perform(delete("/company/{0}",1)).andExpect(jsonPath("$.name").value("ad"));
    }

    @Test
    public void should_getEmployees_when_findlById()throws Exception{

        List<Employees> list1=new ArrayList<>();
        Company company=new Company(1,"ad");
        Employees employees=new Employees(1L,"ba",company);
        list1.add(employees);
        given(companyService.getemployeesByCompanyId(1L)).willReturn(list1);
        mockMvc.perform(get("/company/{0}/employees",1L)).andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void should_returnCompanylist_when_get_by_page()throws Exception{
        List<Company> list=new ArrayList<>();
        Company company=new Company(1,"ad");
        Company company1=new Company(2,"ad1");
        list.add(company);
        list.add(company1);
        given(companyService.getCompanyBypage(1,2)).willReturn(list);
        mockMvc.perform(get("/company/page/{0}/pageSize/{1}",1,2)).andExpect(jsonPath("$[0].name").value("ad"));
    }

}
