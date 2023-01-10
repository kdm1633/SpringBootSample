package com.example.hello;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
	@Autowired
	private HelloRepository repository;
	
	public Employee getEmployee(String id) {
		Map<String, Object> map = repository.findById(id);
		
		String employeeId = (String)map.get("id");
		String employeeName = (String)map.get("name");
		int employeeAge = (Integer)map.get("age");
		
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeName(employeeName);
		employee.setEmployeeAge(employeeAge);
		
		return employee;
	}
}
