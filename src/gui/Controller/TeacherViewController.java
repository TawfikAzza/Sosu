package gui.Controller;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import gui.Model.CitizenModel;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherViewController implements Initializable {


    private CitizenModel citizenModel;

    @FXML
    private TableView<Citizen> tableViewTemplates;
    @FXML
    private TableColumn<Citizen, String> tableColumnTemplatesFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnTemplatesLastName;
    @FXML
    private TableView<Citizen> tableViewCitizen;
    @FXML
    private TableColumn<Citizen, Integer> tableColumnCitizenID;
    @FXML
    private TableColumn<Citizen, String> tableColumnCitizenFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnCitizenLastName;
    @FXML
    private TableView<Student> tableViewStudent;
    @FXML
    private TableColumn<Student, String> tableColumnStudentFirstName;
    @FXML
    private TableColumn<Student, String> tableColumnStudentLastName;
    @FXML
    private TableView<Citizen> tableViewFictiveCitizen;
    @FXML
    private TableColumn<Citizen, Integer> tableColumnFictiveCitizenID;
    @FXML
    private TableColumn<Citizen, String> tableColumnFictiveCitizenFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnFictiveCitizenLastName;
    @FXML
    private TableView<Citizen> tableViewAssignedCit;
    @FXML
    private TableColumn<Citizen, Integer> tableColumnAssignedID;
    @FXML
    private TableColumn<Citizen, String> tableColumnAssignedFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnAssignedLastName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.citizenModel = CitizenModel.getInstance();
            this.tableViewTemplates.setItems(citizenModel.getTemplatesObs());
            this.tableViewCitizen.setItems(citizenModel.getObsListCitizens());
            initTables();
        } catch (CitizenException e) {
            DisplayMessage.displayError(e);
        }
    }

    private void initTables() {
        //Templates
        this.tableColumnTemplatesFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnTemplatesLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //Citizens on template page
        this.tableColumnCitizenID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnCitizenFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnCitizenLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //Fictive Citizens on student page
        this.tableColumnFictiveCitizenID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnFictiveCitizenFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnFictiveCitizenLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //Students
        this.tableColumnStudentFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.tableColumnStudentLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        //Assigned Fictive Citizens
        this.tableColumnAssignedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnAssignedFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnAssignedLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));
    }

    public void handleCreateCitFromTemp(ActionEvent actionEvent) {
        Citizen template = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (template !=null)
        {
            Thread copyTemplateThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        citizenModel.copyTempToCit(template);
                    } catch (CitizenException e) {
                        DisplayMessage.displayError(e);
                    }
                }
            });
            copyTemplateThread.start();
        }
    }

    public void handleCreateTempFromCit(ActionEvent actionEvent) {
        Citizen citizen = tableViewCitizen.getSelectionModel().getSelectedItem();
        if (citizen !=null)
        {
            Thread copyCitizenThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        citizenModel.copyCitToTemp(citizen);
                    } catch (CitizenException e) {
                        DisplayMessage.displayError(e);
                    }
                }
            });
            copyCitizenThread.start();
        }
    }

    public void handleCreateTemplate(ActionEvent actionEvent) {
        try {
            openCitizenForm(false,null);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    public void handleEditTemplate(ActionEvent actionEvent) {
        Citizen citizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (citizen != null)
        {
            try {
                openCitizenForm(true,citizen);
            } catch (IOException e) {
                DisplayMessage.displayError(e);
            }
        }
    }

    public void handleDeleteTemplate(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (selectedCitizen != null) {
            Thread deleteCitizenThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        citizenModel.deleteCitizen(selectedCitizen);
                        citizenModel.getTemplatesObs().remove(selectedCitizen);
                    } catch (CitizenException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    }
                }
            });
            deleteCitizenThread.start();
        }
    }


    public void handleDuplicateTemplate(ActionEvent actionEvent) {
    }

    public void handleEditCitizen(ActionEvent actionEvent) {
    }

    public void handleDeleteCitizen(ActionEvent actionEvent) {
    }

    public void handleDuplicateCitizen(ActionEvent actionEvent) {
    }

    public void handleRemoveCitClick(ActionEvent actionEvent) {
    }

    public void handleCreateStudent(ActionEvent actionEvent) {
    }

    public void handleEditStudent(ActionEvent actionEvent) {
    }

    public void handleDeleteStudent(ActionEvent actionEvent) {
    }

    public void handleAssignClick(ActionEvent actionEvent) {
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) throws IOException {
        System.out.println("here");
        LoginLogoutUtil.logout(actionEvent);
    }

    private void openCitizenForm(boolean isEditing, Citizen citizen) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/CitizenFormView.fxml"));
        Parent root = loader.load();

        CitizenFormController formController = loader.getController();
        formController.setCurrentSchoolId();
        if (isEditing) {
            formController.setCitizenToEdit(citizen);
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
