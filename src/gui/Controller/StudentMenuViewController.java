package gui.Controller;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.util.GlobalCitizen;
import gui.Model.StudentModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentMenuViewController implements Initializable {
    @FXML
    private TableView<Citizen> citizenTableview;
    @FXML
    private TableColumn<Citizen, String> fnameColumn;
    @FXML
    private TableColumn<Citizen, String> lnameColumn;
    @FXML
    private Label lblAdress,lblCpr,lblFname,lblLname,lblPhone,lblSchool;

    private StudentModel studentModel;
    private Student currentStudent;
    private Citizen currentCitizen;
    public StudentMenuViewController() {
        studentModel = new StudentModel();
        currentStudent = new Student(28,2, "Miskine", "Nurse");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        upadateTableCitizen();
    }

    public void upadateTableCitizen() {
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));
        try {
            citizenTableview.getItems().addAll(studentModel.getCitizensOfStudent(currentStudent));
        } catch (StudentException | CitizenException e) {
            DisplayMessage.displayError(e);
        }

    }

    public void displayCitizen() {
        if(citizenTableview.getSelectionModel().getSelectedIndex()==-1)
            return;
        currentCitizen = citizenTableview.getSelectionModel().getSelectedItem();
        System.out.println(currentCitizen.getId());
        GlobalCitizen.setSelectedCitizen(currentCitizen);
        lblFname.setText(currentCitizen.getFName());
        lblLname.setText(currentCitizen.getLName());
        lblCpr.setText(currentCitizen.getCprNumber());
        lblAdress.setText(currentCitizen.getAddress());
        lblPhone.setText(""+currentCitizen.getPhoneNumber());
    }

    public void openAbilities() {
        if(GlobalCitizen.getSelectedCitizen()==null)
            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/FunctionalSectionDisplay.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();

    }

    public void openHealthCondition() {
        if(GlobalCitizen.getSelectedCitizen()==null)
            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/HealthSectionDisplay.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    public void openGeneralInfo() {
        if(GlobalCitizen.getSelectedCitizen()==null)
            return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/GeneralInfoReportView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}
