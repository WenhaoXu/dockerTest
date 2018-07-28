package com.example.demo.controllor;

import com.example.demo.entity.Employees;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employees>> showAllCompany() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employees> getOneEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.findOne(id));
    }



    @GetMapping("/page/{p}/pageSize/{s}")
    public ResponseEntity<List<Employees>> getAllEmployeeByPage(@PathVariable("p") Integer curPage, @PathVariable("s") Integer size) {
        return ResponseEntity.ok(employeeService.findByPagable(curPage,size));
    }


    @PostMapping
    public ResponseEntity addEmployee(@RequestBody Employees employee) {
        try {
            employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEmployee(@PathVariable("id") Integer employeeId, @RequestBody Employees employee) {
        try {
            employeeService.findOne(employeeId);
            employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployeeById(@PathVariable Integer id) {
        try {
            employeeService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
