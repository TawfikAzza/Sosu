package gui.Controller;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.util.GlobalVariables;
import gui.Model.StudentCitizenRelationShipModel;
import gui.Model.StudentModel;
import gui.utils.DisplayMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private AnchorPane citizenInfoControls;
    @FXML
    private TableView<Citizen> citizenTableview;
    @FXML
    private TableColumn<Citizen, String> fnameColumn;
    @FXML
    private TableColumn<Citizen, String> lnameColumn;
    @FXML
    private Label lblAdress,lblFname,lblLname,lblPhone,lblSchool;

    private StudentCitizenRelationShipModel studentCitizenRelationShipModel;


    private Student currentStudent;
    private Citizen currentCitizen;

    public StudentViewController() {
        currentStudent = GlobalVariables.getCurrentStudent();
        System.out.println(currentStudent);
        try {
            studentCitizenRelationShipModel = new StudentCitizenRelationShipModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //currentStudent = new Student(51,2, "Miskine", "Nurse");
    }

    private void updateTableCitizen() {
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        try {
            citizenTableview.getItems().addAll(studentCitizenRelationShipModel.getCitizensOfStudent(currentStudent));
        } catch (StudentException | CitizenException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    public void displayCitizen() {
        if(citizenTableview.getSelectionModel().getSelectedIndex()==-1)
            return;
        currentCitizen = citizenTableview.getSelectionModel().getSelectedItem();
        //System.out.println(currentCitizen.getId());
        GlobalVariables.setSelectedCitizen(currentCitizen);
        lblFname.setText(currentCitizen.getFName());
        lblLname.setText(currentCitizen.getLName());
        lblAdress.setText(currentCitizen.getAddress());
        lblPhone.setText(""+currentCitizen.getPhoneNumber());
        lblSchool.setText(""+currentCitizen.getSchoolName());
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTableCitizen();
        try {
            loadCitizenInfoControls();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCitizenInfoControls() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/CitizenInfoControls.fxml"));
        VBox controlsParent = (VBox) root;
        AnchorPane.setBottomAnchor(controlsParent,0.0);
        AnchorPane.setTopAnchor(controlsParent,0.0);
        AnchorPane.setLeftAnchor(controlsParent,0.0);
        AnchorPane.setRightAnchor(controlsParent,0.0);
        citizenInfoControls.getChildren().setAll(controlsParent);
    }
}
