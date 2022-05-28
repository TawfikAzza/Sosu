package gui.Controller;

import be.School;

import bll.exceptions.SchoolException;
import bll.exceptions.UserException;
import gui.Model.SchoolModel;
import gui.utils.DisplayMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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

    @FXML
    private void handleDeleteBtn(ActionEvent actionEvent) throws SQLException {
        if (schoolsTV.getSelectionModel().getSelectedItem()!=null){
            if (!(DisplayMessage.displayConfirmation("All data related to this school will be lost", "are you sure you want to delete this school ?")).getButtonData().isCancelButton());
                schoolModel.deleteSchool(schoolsTV.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void handleEditBtn(ActionEvent actionEvent) throws IOException {
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

    @FXML
    private void handleAddBtn(ActionEvent actionEvent) throws IOException {
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

        initializeSchoolTV();

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

    private void initializeSchoolTV() {
        final boolean[] test = {true};

        schoolsTV.setEditable(true);
        schoolName.setCellValueFactory(new PropertyValueFactory<>("nameProperty"));
        schoolName.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                try {
                    schoolModel.editSchool(schoolsTV.getSelectionModel().getSelectedItem(),string);
                    return string;
                } catch (SQLException ignored) {
                    return string;
                } catch (SchoolException e) {
                    DisplayMessage.displayError(e);
                    DisplayMessage.displayMessage(e.getExceptionMessage());
                    test[0] =false;
                    return schoolsTV.getSelectionModel().getSelectedItem().getName();
                }
            }
        }));
        schoolName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<School, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<School, String> event) {
                School school = event.getRowValue();
                if (test[0]){
                    try {
                        String newName= event.getNewValue();
                        schoolModel.editSchool(school,newName);
                    } catch (SQLException | SchoolException e) {
                        e.printStackTrace();
                    }
                }
        }});
    schoolsTV.setItems(schoolModel.getAllSchoolsFL());
    }
}
