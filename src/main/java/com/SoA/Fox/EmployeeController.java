package com.SoA.Fox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Employee")
@CrossOrigin(origins = "http://127.0.0.1:5500")
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
    public List<Employee> getAllStudents() {

        List<Employee> Employee = new ArrayList<>();
        Employee = employeeService.readEmployeesFromJsonFile();
        return Employee;
    }


    @GetMapping("/ByID/{targetID}")
    public ResponseEntity<Map<String, Object>> getEmployeeByID(@PathVariable int targetID) {
        List<Employee> Employees = employeeService.readEmployeesFromJsonFile(); // Assuming XMLRead returns a list of all students

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
        List<Employee> Employees = employeeService.readEmployeesFromJsonFile();// Assuming XMLRead returns a list of all students

        List<Employee> studentsWithTargetName = new ArrayList<>();

        for (Employee empl : Employees) {
            if (empl.getDesignation() == targetDesignation) {
                studentsWithTargetName.add(empl);
            }
        }

        int count = studentsWithTargetName.size();

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("Employees", studentsWithTargetName);

        return ResponseEntity.ok().body(response);
    }


//    @PostMapping("/")
//    public String addStudent(@RequestBody Employee student) {
//        employeeService.addStudent(Settings.XML_FILE_PATH, student);
//        return "Student Added";
//    }

//    @DeleteMapping("/deleteByID/{ID}")
//    public String deleteStudent(@PathVariable String ID) {
//        employeeService.deleteStudentByID(ID, Settings.XML_FILE_PATH);
//        return String.format("Student with ID %s deleted successfully", ID);
//
//    }


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
                        .anyMatch(lang -> language.equals(lang.getLanguageName()) && lang.getScoreOutof100() > scoreThreshold))
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
}


