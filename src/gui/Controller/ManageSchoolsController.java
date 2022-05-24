package gui.Controller;

import be.School;
import be.Student;
import be.Teacher;
import bll.exceptions.SchoolException;
import gui.Model.SchoolModel;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageSchoolsController implements Initializable {
    @FXML
    private TableView<School> schoolsTV;
    @FXML
    private TableColumn<School,String> schoolName,schoolAddress,schoolPostalCode,schoolRegion,schoolNumber;

    @FXML
    private TextField searchSchool;
    private SchoolModel schoolModel;

    public void handleDeleteBtn(ActionEvent actionEvent) {
        if (schoolsTV.getSelectionModel().getSelectedItem()!=null)
            schoolModel.deleteSchool(schoolsTV.getSelectionModel().getSelectedItem());
    }

    public void handleEditBtn(ActionEvent actionEvent) throws IOException {
        if (schoolsTV.getSelectionModel().getSelectedItem()!=null){
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditSchool.fxml"));
            root = loader.load();
            //NewEditSchoolController.setEdit(true,schoolsTV.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            stage.setTitle("Edit school");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void handleAddBtn(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditSchool.fxml"));
        root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("New School");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            schoolModel = new SchoolModel();
        } catch (SchoolException e) {
            e.printStackTrace();
        }
        schoolName.setCellValueFactory(new PropertyValueFactory<>("name"));
        try {
            schoolsTV.setItems(schoolModel.getAllSchools());
        } catch (SchoolException e) {
            e.printStackTrace();
        }
    }
}
