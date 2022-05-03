package dal.db;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GetCitizensOfStudentFacade {

    private final ConnectionManager cm;

    public GetCitizensOfStudentFacade() throws IOException {
        cm = new ConnectionManager();
    }

    private ArrayList<Integer> getCitizenIDs(Student student) throws StudentException {
        ArrayList<Integer> IDs = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT * FROM CitizenStudentRelation WHERE studentID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            int studentID = student.getId();

            ps.setInt(1, studentID);

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                int citizenID = rs.getInt(1);
                IDs.add(citizenID);
            }
        } catch (SQLException throwables) {
            throw new StudentException("Failed to get citizens", throwables);
        }
        return IDs;
    }

    private ArrayList<Citizen> getCitizensFromID(ArrayList<Integer> IDs) throws CitizenException {
        ArrayList<Citizen> citizens = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Citizen WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sqlSelect);
            for(Integer id: IDs)
            {
                    ps.setInt(1, id);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {

                        String firstName = rs.getString(2);
                        String lastName = rs.getString(3);
                        String address = rs.getString(4);
                        LocalDate birthday = rs.getDate(5).toLocalDate();
                        int phoneNumber = rs.getInt(6);
                        boolean isTemplate = rs.getBoolean(7);
                        int schoolID = rs.getInt(8);
                        String cpr = rs.getString(9);

                        citizens.add(new Citizen(id, firstName, lastName, cpr, address, phoneNumber, birthday, isTemplate, schoolID));
                    }
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Failed to get citizens", throwables);
        }
        return citizens;
    }

    public ArrayList<Citizen> getCitizensOfStudent(Student student) throws StudentException, CitizenException {
        ArrayList<Integer> IDs = getCitizenIDs(student);
        return getCitizensFromID(IDs);
    }
}
