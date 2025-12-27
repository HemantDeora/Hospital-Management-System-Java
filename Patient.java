package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }


    public void addPatient() {

        scanner.nextLine();

        System.out.print("Enter Patient Name : ");
        String name = scanner.nextLine();

        System.out.print("Enter Patient Age : ");
        int age = scanner.nextInt();

        System.out.print("Enter Patient Gender : ");
        String gender = scanner.next();

        String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(" Patient Added Successfully!");
            } else {
                System.out.println(" Failed to add patient");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewPatients() {

        String query = "SELECT * FROM patients";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nPatients List:");
            System.out.println("+------------+--------------------+------+----------+");
            System.out.println("| Patient Id | Name               | Age  | Gender   |");
            System.out.println("+------------+--------------------+------+----------+");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");

                System.out.printf("| %-10d | %-18s | %-4d | %-8s |\n",
                        id, name, age, gender);
            }

            System.out.println("+------------+--------------------+------+----------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean getPatientById(int id) {

        String query = "SELECT id FROM patients WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
