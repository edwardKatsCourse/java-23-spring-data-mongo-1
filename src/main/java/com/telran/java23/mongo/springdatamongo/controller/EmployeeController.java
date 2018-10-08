package com.telran.java23.mongo.springdatamongo.controller;

import com.telran.java23.mongo.springdatamongo.entity.Employee;
import com.telran.java23.mongo.springdatamongo.exceptions.EmployeeNotFoundException;
import com.telran.java23.mongo.springdatamongo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/save")
    public Employee save(@RequestBody Employee employee) {

        if (employeeRepository.existsByUsername(employee.getUsername())) {
            throw new RuntimeException("User already exists!");
        }
        //employee without ID
        return employeeRepository.save(employee);
        //employee with ID
    }

    @PutMapping("/update/{id}")
    public Employee update(@PathVariable("id") String id,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "company", required = false) String company) {


        Employee employee = employeeRepository.findById(id)
                .orElseThrow(
                        () -> new EmployeeNotFoundException()
                );

        if (name != null) {
            employee.setName(name);
        }

        if (company != null) {
            employee.setCompanyName(company);
        }

        return employeeRepository.save(employee);
    }

    @GetMapping("/")
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable("id") String id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException()
        );
    }

    @GetMapping("/company/{companyName}")
    public List<Employee> getByCompany(@PathVariable("companyName") String companyName) {
        return employeeRepository.findAllByCompanyName(companyName);
    }

}
