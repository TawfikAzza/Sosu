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
            String sql = "SELECT id, content, caseName FROM [Case]";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt(1);
                String content = rs.getString(2);
                String caseName = rs.getString(3);
                Case newCase = new Case(id, content, caseName);
                cases.add(newCase);
            }

        } catch (SQLException throwables) {
            throw new CaseException("Could not retrieve cases", throwables);
        }
        return cases;
    }


    public int addCase(Case newCase) throws CaseException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO [Case] VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            String caseName = newCase.getCaseName();
            String content = newCase.getContent();

            ps.setString(1, content);
            ps.setString(2, caseName);

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throw new CaseException("Could not add case to Database", throwables);
        }
        return -1;
    }

    public Case getCitizenCase(Citizen citizen) throws CaseException {
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT id, content, caseName  FROM [Case] WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, citizen.getCaseID());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt(1);
                String content = rs.getString(2);
                String caseName = rs.getString(3);

                return new Case(id, content, caseName);
            }
        } catch (SQLException throwables) {
            throw new CaseException("Could not retrieve cases", throwables);
        }
        return null;
    }

    public void editCase(int id, Case newCase) {
        try (Connection connection = cm.getConnection()) {
            String sql = "UPDATE [Case] SET content = ?, caseName = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            String content = newCase.getContent();
            String caseName = newCase.getCaseName();

            ps.setString(1, content);
            ps.setString(2, caseName);
            ps.setInt(3, id);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
