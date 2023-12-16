package com.SoA.Fox;


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
            employees = objectMapper.readValue(new File(XML_FILE_PATH), new TypeReference<List<Employee>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees != null ? employees : new ArrayList<>();
    }

}



