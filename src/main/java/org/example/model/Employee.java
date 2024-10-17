package org.example.model;

public class Employee
{
    private int idEmployee;
    private String firstName;
    private String lastName;

    public Employee() {
    }

    public Employee(int idEmployee, String firstName, String lastName) {
        this.idEmployee = idEmployee;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "idEmployee=" + idEmployee +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
