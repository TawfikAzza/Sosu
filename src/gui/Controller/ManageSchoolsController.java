package gui.Controller;

import be.School;

import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import gui.Model.SchoolModel;
import gui.utils.LoginLogoutUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ManageSchoolsController implements Initializable {
    @FXML
    private TableView<School> schoolsTV;
    @FXML
    private TableColumn<School,String> schoolName;

    @FXML
    private TextField searchSchool;
    private SchoolModel schoolModel;
    private final ObservableList <School>allSchools= FXCollections.observableArrayList();

    public void handleDeleteBtn(ActionEvent actionEvent) throws SQLException {
        if (schoolsTV.getSelectionModel().getSelectedItem()!=null)
            schoolModel.deleteSchool(schoolsTV.getSelectionModel().getSelectedItem());
    }

    public void handleEditBtn(ActionEvent actionEvent) throws IOException {
        if (schoolsTV.getSelectionModel().getSelectedItem()!=null){
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditSchool.fxml"));
            root = loader.load();
            NewEditSchoolController newEditSchoolController = loader.getController();
            newEditSchoolController.setEdit(schoolsTV.getSelectionModel().getSelectedItem());

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
            schoolModel = SchoolModel.getInstance();
        } catch (SchoolException | UserException e) {
            e.printStackTrace();
        } catch (IOException ignored) {
        }

        schoolName.setCellValueFactory(new PropertyValueFactory<>("nameProperty"));
        schoolsTV.setItems(schoolModel.getAllSchoolsFL());

        searchSchool.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String query = (searchSchool.getText().toLowerCase(Locale.ROOT));
                schoolModel.getAllSchoolsFL().setPredicate(school -> {
                    if (query.isEmpty() || query.isBlank())
                        return true;
                    if (school.toString().toLowerCase().contains(query))
                        return true;
                    return false;
                });
            }
        });
    }
}
