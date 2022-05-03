package dal.db;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.CitizenStudentRelationException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetStudentsOfCitizenFacade {

    private final ConnectionManager cm;

    public GetStudentsOfCitizenFacade() throws IOException {
        cm = new ConnectionManager();
    }

    private ArrayList<Integer> getStudentIDs(Citizen citizen) throws CitizenStudentRelationException {
        ArrayList<Integer> IDs = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT * FROM CitizenStudentRelation WHERE citizenID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            int citizenID = citizen.getId();

            ps.setInt(1, citizenID);

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                int studentID = rs.getInt(2);
                IDs.add(studentID);
            }
        } catch (SQLException throwables) {
            throw new CitizenStudentRelationException("Failed to get citizens", throwables);
        }
        return IDs;
    }


    private ArrayList<Student> getCitizensFromID(ArrayList<Integer> IDs) throws CitizenException {
        ArrayList<Student> students = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM [user] WHERE id = ? AND roleID = ?";
            PreparedStatement ps = connection.prepareStatement(sqlSelect);
            for(Integer id: IDs)
            {
                ps.setInt(1, id);
                ps.setInt(2, 3);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    int schoolID = rs.getInt(2);
                    String firstName = rs.getString(3);
                    String lastName = rs.getString(4);

                    students.add(new Student(id, schoolID, firstName, lastName));
                }
            }
        } catch (SQLException throwables) {
            throw new CitizenException("Failed to get citizens", throwables);
        }
        return students;
    }

    public ArrayList<Student> getStudentsOfCitizen(Citizen citizen) throws CitizenStudentRelationException, CitizenException {
        ArrayList<Integer> IDs = getStudentIDs(citizen);
        return getCitizensFromID(IDs);
    }

}
