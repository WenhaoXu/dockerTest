package com.example.demo;

import com.example.demo.controllor.EmployeeController;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employees;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;


    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void should_return_employees_list_when_call_findAll_api() throws Exception {
        when(service.findAll()).thenReturn(new ArrayList<Employees>() {{
            add(new Employees(1L,"ad",new Company("a")));
            add(new Employees(2L, "ad2",  new Company("b")));
        }});
        mockMvc.perform(get("/employees")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",is("ad")));
    }

    @Test
    public void should_return_a_employee_when_call_findOne_api() throws Exception {
        Integer employeeId = 1;
        when(service.findOne(employeeId)).thenReturn(new Employees(1L,"ad",new Company("a")));

        mockMvc.perform(get("/employees/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("ad")))
                .andExpect(jsonPath("$.id",is(1)))
                .andDo(print());
    }


    @Test
    public void should_success_when_call_add_a_employee_api() throws Exception {
        Employees employee = new Employees(1L,"ad",new Company("a"));
        ResultActions perform = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(employee)));
        perform.andExpect(status().is2xxSuccessful());
    }

    @Test
    public void should_success_when_call_update_employee_api() throws Exception {
        Employees employee = new Employees(1L,"ad",new Company("a"));
        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andExpect(status().is2xxSuccessful());
        mockMvc.perform(put("/employees/a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void should_success_when_call_delete_employee_api() throws Exception {
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().is2xxSuccessful());
        mockMvc.perform(delete("/employees/a"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void should_success_when_call_pageable_api() throws Exception {
        when(service.findByPagable(0,2)).thenReturn(new ArrayList<Employees>(){{
            add(new Employees(1L,"ad",new Company("a")));
            add(new Employees(2L,"ad2",new Company("b")));
        }});
        Integer curPage=0;
        Integer size=2;
        mockMvc.perform(get("/employees/page/"+curPage+"/pageSize/"+size))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",is("ad")))
                .andExpect(jsonPath("$[1].name",is("ad2")))
                .andDo(print());
    }
}
