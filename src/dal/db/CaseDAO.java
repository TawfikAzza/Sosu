package dal.db;

import be.Case;
import be.Citizen;
import bll.exceptions.CaseException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CaseDAO {

    private final ConnectionManager cm;

    public CaseDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public List<Case> getAllCases() throws CaseException {
        List<Case> cases = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT id, caseName FROM [Case]";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt(1);
                String caseName = rs.getString(2);
                Case newCase = new Case(id, caseName);
                cases.add(newCase);
            }

        } catch (SQLException throwables) {
            throw new CaseException("Could not retrieve cases", throwables);
        }
        return cases;
    }


    public void addCase(Case newCase) throws CaseException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO [Case] VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            String caseName = newCase.getCaseName();
            String content = newCase.getContent();

            ps.setString(1, content);
            ps.setString(2, caseName);

            ps.execute();
        } catch (SQLException throwables) {
            throw new CaseException("Could not add case to Database", throwables);
        }
    }

    public Case getCitizenCase(Citizen citizen) throws CaseException {
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT id, caseName FROM [Case] WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, citizen.getCaseID());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt(1);
                String caseName = rs.getString(2);

                return new Case(id, caseName);
            }
        } catch (SQLException throwables) {
            throw new CaseException("Could not retrieve cases", throwables);
        }
        return null;
    }
}
