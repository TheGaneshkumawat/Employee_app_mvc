package com.example.cruddemo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.exception.EmployeeNotFoundException;
import com.example.cruddemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeRestController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/showFormForAdd")
	public String addEmployeeForm(Model model){		
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "employees/employee-form";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int id, Model model){		
		Optional<Employee> employee = employeeService.findById(id);		
		if(!employee.isPresent()) {
			throw new EmployeeNotFoundException("Employee not found with Id - "+id);
		}
		model.addAttribute("employee", employee.get());
		return "employees/employee-form";
	}
	
	@GetMapping("/list")
	public String getAllEmployees(Model model){		
		List<Employee> list = employeeService.findAll(); 
		model.addAttribute("employees", list);
		return "employees/list-employees";
	}
	
	@PostMapping("/save")	
	public String addNew(@Valid @ModelAttribute("employee") Employee employee, Errors errors) {		
		 if (null != errors && errors.getErrorCount() > 0) {
	            return "employees/employee-form";
	        } 
		employeeService.save(employee);	
		return "redirect:/employees/list";
	}
	
	/*
	 * @PutMapping() public Employee updateExisting(@Valid @RequestBody Employee
	 * emp) { Optional<Employee> employee = employeeService.findById(emp.getId());
	 * if(!employee.isPresent()) { throw new
	 * EmployeeNotFoundException("Employee not found with Id - "+emp.getId()); }
	 * employeeService.save(emp); return employee.get(); }
	 */
	
	@GetMapping("/delete")	
	public String deleteEmployee(@RequestParam("employeeId") int id) {
		Optional<Employee> employee = employeeService.findById(id);		
		if(!employee.isPresent()) {
			throw new EmployeeNotFoundException("Employee not found with Id - "+id);
		}			
		employeeService.deleteById(id);
		return "redirect:/employees/list";
	}
	
	/*
	 * @DeleteMapping(path = "/{id}",produces = {MediaType.TEXT_PLAIN_VALUE}) public
	 * String delete(@PathVariable int id) { Optional<Employee> employee =
	 * employeeService.findById(id); if(!employee.isPresent()) { throw new
	 * EmployeeNotFoundException("Employee not found with Id - "+id); }
	 * employeeService.deleteById(id); return "Employee Deleted Successfully"; }
	 */
	
	/*
	 * @GetMapping("/{id}") public Employee getmployee(@PathVariable int id){
	 * Optional<Employee> employee = employeeService.findById(id);
	 * if(!employee.isPresent()) { throw new
	 * EmployeeNotFoundException("Employee not found with Id - "+id); } return
	 * employee.get(); }
	 */
	
}










