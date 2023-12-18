package com.SoA.Fox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("EmployeeID")
    private int employeeID;

    @JsonProperty("Designation")
    private String designation;

    @JsonProperty("KnownLanguages")
    private List<KnownLanguage> knownLanguages;

    // Getters and setters for FirstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getters and setters for LastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getters and setters for EmployeeID
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    // Getters and setters for Designation
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // Getters and setters for KnownLanguages
    public List<KnownLanguage> getKnownLanguages() {
        return knownLanguages;
    }

    public void setKnownLanguages(List<KnownLanguage> knownLanguages) {
        this.knownLanguages = knownLanguages;
    }

    public static class KnownLanguage {
        @JsonProperty("LanguageName")
        private String languageName;

        @JsonProperty("ScoreOutof100")
        private int scoreOutof100;

        // Getters and setters for LanguageName
        public String getLanguageName() {
            return languageName;
        }

        public void setLanguageName(String languageName) {
            this.languageName = languageName;
        }

        // Getters and setters for ScoreOutof100
        public int getScoreOutof100() {
            return scoreOutof100;
        }

        public void setScoreOutof100(int scoreOutof100) {
            this.scoreOutof100 = scoreOutof100;
        }
    }
}
