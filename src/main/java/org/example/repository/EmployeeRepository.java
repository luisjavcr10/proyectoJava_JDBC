package org.example.repository;

import org.example.model.Employee;
import org.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee>
{
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<> ();
        try(Statement myState = getConnection().createStatement();
            ResultSet myResult = myState.executeQuery("SELECT * FROM Employee");){
            while(myResult.next()) {
                Employee e = createEmployee(myResult);
                employees.add(e);
            }
        }
        return employees;
    }

    @Override
    public Employee getById(Integer id) throws SQLException {
        Employee employee = null;
        try(PreparedStatement myPrepa = getConnection().prepareStatement("Select idEmployee, firstName, lastName From Employee where idEmployee=?"))
        {
            myPrepa.setInt(1,id);
            try (ResultSet myResult = myPrepa.executeQuery()) {
                if(myResult.next()) {
                    employee = createEmployee(myResult);
                }
            }
        }
        return employee;
    }

    @Override
    public void save(Employee employee) throws SQLException {
        //placeholders son marcadores de posicion : ?,?,?
        //con myStamt.setInt(1,"2") por ejemplo, le damos el valor 2 al marcador 1
        String sql= "Insert into Employee(idEmployee,firstName,lastName) values(?,?,?)";
        try(Connection myCon = getConnection();
            PreparedStatement myStamt= myCon.prepareStatement(sql)){
            myStamt.setInt(1,employee.getIdEmployee());
            myStamt.setString(2,employee.getFirstName());
            myStamt.setString(3,employee.getLastName());

            int rowsAffected = myStamt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee inserted successfully!");
            } else {
                System.out.println("No employee inserted.");
            }
        }
    }

    @Override
    public void delete(Employee employee) throws SQLException {
        String sql = "Delete from Employee where idEmployee=?";
        try(Connection myCon=getConnection();PreparedStatement myStamt=myCon.prepareStatement(sql)){
            myStamt.setInt(1,employee.getIdEmployee());
            int rowsAffected = myStamt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully!");
            }else{
                System.out.println("No employee deleted.");
            }
        }
    }

    private Employee createEmployee(ResultSet myResult) throws SQLException {
        Employee newEmployee = new Employee();
        newEmployee.setIdEmployee(myResult.getInt("idEmployee"));
        newEmployee.setFirstName(myResult.getString("firstName"));
        newEmployee.setLastName(myResult.getString("lastName"));
        return newEmployee;
    }
}
