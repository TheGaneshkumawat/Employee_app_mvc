package com.example.cruddemo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;

import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.exception.EmployeeNotFoundException;
import com.example.cruddemo.service.EmployeeService;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeRestControllerTest {

	@MockBean
	private EmployeeService service;
	
	@MockBean
	private Model model;
	
	@Autowired
	private EmployeeRestController controller;
	
		
	@Test
	@DisplayName("POST /employees - Create Employee")
	void testCreateEmployee() throws Exception{
				
		Employee mockEmployee = new Employee("John", "wick", "test@gmail.com");
		mockEmployee.setId(1);
		
		doReturn(mockEmployee).when(service).save(any());		
		
		assertEquals("redirect:/employees/list", controller.addNew(mockEmployee, null));
	}
	
	@Test
	@DisplayName("PUT /employees - Update Employee")
	void testUpdateEmployee() throws Exception{
		Employee putEmployee = new Employee("John", "wick", "test@gmail.com");
		putEmployee.setId(1);
		
		Employee mockEmployee = new Employee("John", "wick", "test@gmail.com");		
		mockEmployee.setId(1);
		
		//fetch employee mock
		doReturn(Optional.of(mockEmployee)).when(service).findById(1);
		doReturn(mockEmployee).when(service).save(any());
		assertEquals("redirect:/employees/list", controller.addNew(mockEmployee, null));
		
		
	}
	
		
	@Test
	@DisplayName("Delete /employees - Delete Employee")
	void testDeleteEmployee() throws Exception{
		Employee deleteEmployee = new Employee("John", "wick", "test@gmail.com");
		deleteEmployee.setId(1);
		
				
		//fetch employee mock
		doReturn(Optional.of(deleteEmployee)).when(service).findById(1);
		doNothing().when(service).deleteById(1);
		
		assertEquals("redirect:/employees/list", controller.deleteEmployee(1));
		
		
	}
	
	@Test
	@DisplayName("Delete /employees - Delete when Employee not Found")
	void testDeleteEmployeeNotFound() throws Exception{

		//fetch employee mock
		doReturn(Optional.empty()).when(service).findById(1);		
		
		Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
			controller.deleteEmployee(1);
		  });		
	}
	
	@Test
	@DisplayName("GET /employees - Found")
	void testGetAllEmployees() throws Exception{
		//Setup our mocked service
		Employee mockEmployee = new Employee("John", "wick", "test@gmail.com");
		mockEmployee.setId(1);
		
		Employee mockEmployee1 = new Employee("Mike", "connor", "test1@gmail.com");
		mockEmployee1.setId(2);
		
		doReturn(Arrays.asList(mockEmployee,mockEmployee1)).when(service).findAll();		
		
		assertEquals("employees/list-employees", controller.getAllEmployees(model));	
		
	}	
	
}
