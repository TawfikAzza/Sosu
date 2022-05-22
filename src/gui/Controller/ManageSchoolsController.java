package gui.Controller;

import be.School;
import be.Student;
import be.Teacher;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageSchoolsController {
    @FXML
    private TableView<School> schoolsTV;
    @FXML
    private TableColumn<School,String> schoolName,schoolAddress,schoolPostalCode,schoolRegion,schoolNumber;

    @FXML
    private TextField searchSchool;

    public void handleDeleteBtn(ActionEvent actionEvent) {
    }

    public void handleEditBtn(ActionEvent actionEvent) {
    }

    public void handleAddBtn(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditSchool.fxml"));
        root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("New Student");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
