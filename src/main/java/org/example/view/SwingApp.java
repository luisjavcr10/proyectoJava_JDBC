package org.example.view;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SwingApp extends JFrame {

    private final Repository<Employee> employeeRepository;
    private final JTable employeeTable;

    public SwingApp() {
        // Configurar la ventana
        setTitle("Gestión de Empleados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 230);
        setLocationRelativeTo(null); // Centrar la ventana

        // Crear una tabla para mostrar los empleados
        employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Crear botones para acciones
        JButton agregarButton = new JButton("Agregar");
        JButton actualizarButton = new JButton("Actualizar");
        JButton eliminarButton = new JButton("Eliminar");

        // Configurar el panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarButton);
        buttonPanel.add(actualizarButton);
        buttonPanel.add(eliminarButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Establecer estilos para los botones
        configurarBoton(agregarButton, new Color(46, 204, 113));
        configurarBoton(actualizarButton, new Color(52, 152, 219));
        configurarBoton(eliminarButton, new Color(231, 76, 60));

        // Crear el objeto Repository para acceder a la base de datos
        employeeRepository = new EmployeeRepository();

        // Cargar los empleados iniciales en la tabla
        refreshEmployeeTable();

        // Agregar ActionListener para los botones
        agregarButton.addActionListener(e -> {
            try {
                agregarEmpleado();
            } catch (SQLException ex) {
                mostrarError("Error al agregar el empleado: " + ex.getMessage());
            }
        });

        actualizarButton.addActionListener(e -> actualizarEmpleado());

        eliminarButton.addActionListener(e -> eliminarEmpleado());
    }

    private void configurarBoton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void refreshEmployeeTable() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Apellido");

            for (Employee employee : employees) {
                model.addRow(new Object[]{
                        employee.getIdEmployee(),
                        employee.getFirstName(),
                        employee.getLastName()
                });
            }
            employeeTable.setModel(model);
        } catch (SQLException e) {
            mostrarError("Error al obtener los empleados de la base de datos: " + e.getMessage());
        }
    }

    private void agregarEmpleado() throws SQLException {
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();

        Object[] fields = {
                "Nombre:", nombreField,
                "Apellido:", apellidoField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Agregar Empleado", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Employee employee = new Employee();
            employee.setFirstName(nombreField.getText());
            employee.setLastName(apellidoField.getText());
            employeeRepository.save(employee);
            refreshEmployeeTable();
            JOptionPane.showMessageDialog(this, "Empleado agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarEmpleado() {
        String empleadoIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del empleado a actualizar:", "Actualizar Empleado", JOptionPane.QUESTION_MESSAGE);
        if (empleadoIdStr != null) {
            try {
                int empleadoId = Integer.parseInt(empleadoIdStr);
                Employee empleado = employeeRepository.getById(empleadoId);

                if (empleado != null) {
                    JTextField nombreField = new JTextField(empleado.getFirstName());
                    JTextField apellidoField = new JTextField(empleado.getLastName());
                    Object[] fields = {
                            "Nombre:", nombreField,
                            "Apellido:", apellidoField
                    };

                    int confirmResult = JOptionPane.showConfirmDialog(this, fields, "Actualizar Empleado", JOptionPane.OK_CANCEL_OPTION);
                    if (confirmResult == JOptionPane.OK_OPTION) {
                        empleado.setFirstName(nombreField.getText());
                        empleado.setLastName(apellidoField.getText());
                        employeeRepository.save(empleado);
                        refreshEmployeeTable();
                    }
                } else {
                    mostrarError("No se encontró ningún empleado con el ID especificado");
                }
            } catch (NumberFormatException e) {
                mostrarError("Ingrese un valor numérico válido para el ID");
            } catch (SQLException e) {
                mostrarError("Error al actualizar los datos del empleado: " + e.getMessage());
            }
        }
    }

    private void eliminarEmpleado() {
        String empleadoIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del empleado a eliminar:", "Eliminar Empleado", JOptionPane.QUESTION_MESSAGE);
        if (empleadoIdStr != null) {
            try {
                int empleadoId = Integer.parseInt(empleadoIdStr);
                int confirmResult = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el empleado?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    Employee employee = employeeRepository.getById(empleadoId);
                    if (employee != null) {
                        employeeRepository.delete(employee);
                        refreshEmployeeTable();
                    } else {
                        mostrarError("No se encontró ningún empleado con el ID especificado");
                    }
                }
            } catch (NumberFormatException e) {
                mostrarError("Ingrese un valor numérico válido para el ID del empleado");
            } catch (SQLException e) {
                mostrarError("Error al eliminar el empleado: " + e.getMessage());
            }
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingApp app = new SwingApp();
            app.setVisible(true);
        });
    }
}
