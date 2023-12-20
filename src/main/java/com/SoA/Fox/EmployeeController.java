package com.SoA.Fox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("Test")
    public String Test() {
        return "I LOVE YOU";
    }

    @GetMapping("/")
    public List<Employee> getAllemployees() {

        List<Employee> Employee = new ArrayList<>();
        Employee = employeeService.readEmployeesFromJsonFile();
        return Employee;
    }

    @GetMapping("/ByID/{targetID}")
    public ResponseEntity<Map<String, Object>> getEmployeeByID(@PathVariable int targetID) {
        List<Employee> Employees = employeeService.readEmployeesFromJsonFile(); // Assuming XMLRead returns a list of
                                                                                // all employees

        List<Employee> EmployeeWithTargetID = new ArrayList<>();

        for (Employee empl : Employees) {
            if (empl.getEmployeeID() == targetID) {
                EmployeeWithTargetID.add(empl);
            }
        }

        int count = EmployeeWithTargetID.size();

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("Employee", EmployeeWithTargetID);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/ByDesignation/{targetDesignation}")
    public ResponseEntity<Map<String, Object>> getEmployeeByDesignation(@PathVariable String targetDesignation) {
        List<Employee> employees = employeeService.getEmployeesByDesignation(targetDesignation);

        Map<String, Object> response = new HashMap<>();
        response.put("count", employees.size());
        response.put("Employees", employees);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        try {
            employeeService.addEmployee(employee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        return ResponseEntity.ok().body("Employee added successfully");
    }

    @DeleteMapping("/deleteByID/{ID}")
    public String deleteEmployee(@PathVariable int ID) {
        employeeService.deleteEmployeeByID(ID);
        return String.format("Employee with ID %s deleted successfully", ID);
    }

    @GetMapping("/javaExperts")
    public List<Employee> getJavaExperts() {
        List<Employee> employees = employeeService.readEmployeesFromJsonFile();

        List<Employee> result = employees.stream()
                .filter(emp -> emp.getKnownLanguages().stream()
                        .anyMatch(lang -> "Java".equals(lang.getLanguageName()) && lang.getScoreOutof100() > 50))
                .sorted(Comparator.comparing(Employee::getFirstName)) // Sorting by first name
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            // If no results found, return a custom message or handle as needed
            Employee noneFound = new Employee();
            noneFound.setFirstName("None");
            noneFound.setLastName("Found");
            result.add(noneFound);
        }

        return result;
    }

    @GetMapping("/experts")
    public List<Employee> getExperts(
            @RequestParam(name = "language") String language,
            @RequestParam(name = "score") int scoreThreshold) {

        List<Employee> employees = employeeService.readEmployeesFromJsonFile();

        List<Employee> result = employees.stream()
                .filter(emp -> emp.getKnownLanguages().stream()
                        .anyMatch(lang -> language.equals(lang.getLanguageName())
                                && lang.getScoreOutof100() > scoreThreshold))
                .sorted(Employee.getComparator(language)) // Sorting by first name
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            // If no results found, return a custom message or handle as needed
            return List.of();
        }

        return result;
    }

    @PatchMapping("/updateByID/{ID}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int ID, @RequestBody Employee employee) {
        Employee e = employeeService.updateEmployee(ID, employee);

        if (e == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(e);
        }
    }

}
