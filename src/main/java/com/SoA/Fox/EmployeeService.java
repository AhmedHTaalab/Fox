package com.SoA.Fox;

import com.SoA.Fox.Employee.KnownLanguage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.SoA.Fox.Settings.XML_FILE_PATH;

@Service
public class EmployeeService {

    public List<Employee> readEmployeesFromJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Employee> employees = null;

        try {
            // Read JSON file and convert to List<Employee>
            employees = objectMapper.readValue(new File(XML_FILE_PATH), new TypeReference<List<Employee>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees != null ? employees : new ArrayList<>();
    }

    public void deleteEmployeeByID(int targetID) {
        List<Employee> employees = readEmployeesFromJsonFile();

        for (Employee empl : employees) {
            if (empl.getEmployeeID() == targetID) {
                employees.remove(empl);
                break;
            }
        }

        writeEmployeesToJsonFile(employees);
    }

    public void writeEmployeesToJsonFile(List<Employee> employees) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Write List<Employee> as JSON to file
            objectMapper.writeValue(new File(XML_FILE_PATH), employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        List<Employee> employees = readEmployeesFromJsonFile();

        Employee withSameID = employees.stream().filter(empl -> empl.getEmployeeID() == employee.getEmployeeID())
                .findFirst().orElse(null);

        if (withSameID != null) {
            throw new IllegalArgumentException("Employee with same ID already exists");
        }

        employees.add(employee);
        writeEmployeesToJsonFile(employees);
    }

    public Employee updateEmployee(int ID, Employee employee) {
        // Get all employees
        List<Employee> employees = readEmployeesFromJsonFile();

        Employee employeeToUpdate = null;

        // Find employee to update
        for (Employee empl : employees) {
            // If employee found
            if (empl.getEmployeeID() == ID) {
                employeeToUpdate = empl;

                // Remove old employee
                employees.remove(empl);
                break;
            }
        }

        // If employee not found
        if (employeeToUpdate == null) {
            return null;
        }

        // Update employee
        if (employee.getFirstName() != null)
            employeeToUpdate.setFirstName(employee.getFirstName());

        if (employee.getLastName() != null)
            employeeToUpdate.setLastName(employee.getLastName());

        if (employee.getDesignation() != null)
            employeeToUpdate.setDesignation(employee.getDesignation());

        if (employee.getKnownLanguages() != null)
            employeeToUpdate.setKnownLanguages(employee.getKnownLanguages());

        // Add updated employee
        employees.add(employeeToUpdate);

        // Write updated employees to file
        writeEmployeesToJsonFile(employees);

        return employeeToUpdate;
    }

    public List<Employee> getEmployeesByDesignation(String targetDesignation) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetDesignation = new ArrayList<>();

        for (Employee empl : employees) {
            if (empl.getDesignation().equals(targetDesignation)) {
                employeesWithTargetDesignation.add(empl);
            }
        }

        return employeesWithTargetDesignation;
    }

    public List<Employee> getEmployeesByFirstName(String targetFirstName) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetFirstName = new ArrayList<>();

        for (Employee empl : employees) {
            if (empl.getFirstName().equals(targetFirstName)) {
                employeesWithTargetFirstName.add(empl);
            }
        }

        return employeesWithTargetFirstName;
    }

    public List<Employee> getEmployeesByLastName(String targetLastName) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetLastName = new ArrayList<>();

        for (Employee empl : employees) {
            if (empl.getLastName().equals(targetLastName)) {
                employeesWithTargetLastName.add(empl);
            }
        }

        return employeesWithTargetLastName;
    }

    public List<Employee> getEmployeesByKnownLanguages(String targetKnownLanguage) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetKnownLanguage = new ArrayList<>();

        for (Employee empl : employees) {
            for (KnownLanguage knownLanguage : empl.getKnownLanguages()) {
                if (knownLanguage.getLanguageName().equals(targetKnownLanguage)) {
                    employeesWithTargetKnownLanguage.add(empl);
                    break;
                }
            }
        }

        return employeesWithTargetKnownLanguage;
    }

    public List<Employee> getEmployeesByEmployeeID(int targetEmployeeID) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetEmployeeID = new ArrayList<>();

        for (Employee empl : employees) {
            if (empl.getEmployeeID() == targetEmployeeID) {
                employeesWithTargetEmployeeID.add(empl);
            }
        }

        return employeesWithTargetEmployeeID;
    }

    public List<Employee> getEmployeesByKnownLanguageScore(int targetScore) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetScore = new ArrayList<>();

        for (Employee empl : employees) {
            for (KnownLanguage knownLanguage : empl.getKnownLanguages()) {
                if (knownLanguage.getScoreOutof100() == targetScore) {
                    employeesWithTargetScore.add(empl);
                    break;
                }
            }
        }

        return employeesWithTargetScore;
    }

    public List<Employee> getEmployeesByKnownLanguageScoreGreaterThan(int targetScore) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetScore = new ArrayList<>();

        for (Employee empl : employees) {
            for (KnownLanguage knownLanguage : empl.getKnownLanguages()) {
                if (knownLanguage.getScoreOutof100() > targetScore) {
                    employeesWithTargetScore.add(empl);
                    break;
                }
            }
        }

        return employeesWithTargetScore;
    }

    public List<Employee> getEmployeesByKnownLanguageScoreLessThan(int targetScore) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetScore = new ArrayList<>();

        for (Employee empl : employees) {
            for (KnownLanguage knownLanguage : empl.getKnownLanguages()) {
                if (knownLanguage.getScoreOutof100() < targetScore) {
                    employeesWithTargetScore.add(empl);
                    break;
                }
            }
        }

        return employeesWithTargetScore;
    }

    public List<Employee> getEmployeesByKnownLanguageScoreBetween(int targetScore1, int targetScore2) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetScore = new ArrayList<>();

        for (Employee empl : employees) {
            for (KnownLanguage knownLanguage : empl.getKnownLanguages()) {
                if (knownLanguage.getScoreOutof100() >= targetScore1
                        && knownLanguage.getScoreOutof100() <= targetScore2) {
                    employeesWithTargetScore.add(empl);
                    break;
                }
            }
        }

        return employeesWithTargetScore;
    }

    public List<Employee> getEmployeesByKnownLanguageScoreBetweenExclusive(int targetScore1, int targetScore2) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetScore = new ArrayList<>();

        for (Employee empl : employees) {
            for (KnownLanguage knownLanguage : empl.getKnownLanguages()) {
                if (knownLanguage.getScoreOutof100() > targetScore1
                        && knownLanguage.getScoreOutof100() < targetScore2) {
                    employeesWithTargetScore.add(empl);
                    break;
                }
            }
        }

        return employeesWithTargetScore;
    }

    public List<Employee> getEmployeesByKnownLanguageScoreGreaterThanEqualTo(int targetScore) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetScore = new ArrayList<>();

        for (Employee empl : employees) {
            for (KnownLanguage knownLanguage : empl.getKnownLanguages()) {
                if (knownLanguage.getScoreOutof100() >= targetScore) {
                    employeesWithTargetScore.add(empl);
                    break;
                }
            }
        }

        return employeesWithTargetScore;
    }

    public List<Employee> getEmployeesByKnownLanguageScoreLessThanEqualTo(int targetScore) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetScore = new ArrayList<>();

        for (Employee empl : employees) {
            for (KnownLanguage knownLanguage : empl.getKnownLanguages()) {
                if (knownLanguage.getScoreOutof100() <= targetScore) {
                    employeesWithTargetScore.add(empl);
                    break;
                }
            }
        }

        return employeesWithTargetScore;
    }

    public List<Employee> getEmployeesByKnownLanguageScoreNotEqualTo(int targetScore) {
        List<Employee> employees = readEmployeesFromJsonFile();
        List<Employee> employeesWithTargetScore = new ArrayList<>();

        for (Employee empl : employees) {
            for (KnownLanguage knownLanguage : empl.getKnownLanguages()) {
                if (!(knownLanguage.getScoreOutof100() == targetScore)) {
                    employeesWithTargetScore.add(empl);
                    break;
                }
            }
        }

        return employeesWithTargetScore;
    }
}
