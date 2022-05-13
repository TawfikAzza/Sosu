package gui.Controller;

import be.Ability;
import be.AbilityCategory;
import be.Citizen;
import be.InfoCategory;
import bll.GIReportManger;
import bll.exceptions.CitizenException;
import bll.exceptions.CitizenReportException;
import bll.exceptions.GeneralInfoException;
import bll.util.GlobalVariables;
import gui.Model.GIReportModel;
import gui.utils.DisplayMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.*;
import java.net.URL;
import java.util.*;

public class DisplayGRIController implements Initializable {


    @FXML
    private Label lblAdress, lblBirthdate, lblFName, lblLName, lblPhone, lblSchool;
    @FXML
    private Button btnClose;
    @FXML
    private Label lblCategory;
    @FXML
    private TextField textFieldContent;


    private GIReportManger giReportManger;
    private GIReportModel giReportModel;
    private InfoCategory selectedInfoCategory;
    private Citizen citizen;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            giReportModel = new GIReportModel();
            giReportManger = new GIReportManger();

        } catch (GeneralInfoException | CitizenException e) {
            e.printStackTrace();
        }
        displayCitiziInfo();
        displayReport();

    }



    public void displayCitiziInfo(){

        lblFName.setText(GlobalVariables.getSelectedCitizen().getFName());
        lblLName.setText(GlobalVariables.getSelectedCitizen().getLName());
        lblAdress.setText(GlobalVariables.getSelectedCitizen().getAddress());
        //lblBirthdate.setText(GlobalVariables.getSelectedCitizen().getBirthDate());
        //lblPhone.setText(GlobalVariables.getSelectedCitizen().getPhoneNumber());
        //lblSchool.setText(GlobalVariables.getSelectedCitizen().getSchoolID());

    }
    public void displayReport() {


        System.out.println("Selected:" + GlobalVariables.getSelectedCitizen());
        HashMap<String, String> getGIR = giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen());

        for (Map.Entry entry : getGIR.entrySet()) {
            System.out.println(" key: " + entry.getKey() + " value: " + entry.getValue());


            lblCategory.setText((String) entry.getKey());
            textFieldContent.setText((String) entry.getValue());
        }


    }


    public void CloseAction() {
        Stage window = (Stage) this.btnClose.getScene().getWindow();
        window.close();
    }


}

/*
Iterator iterator = labels.entrySet().iterator();
    while (iterator.hasNext()) {
        Map.Entry pairs = (Map.Entry)iterator.next();
        entries.add(new JLabel(pairs.getKey() + ": £" + pairs.getValue()));
        for(JLabel entry : entries) {
            mainPanel.add(entry);
            for(int i = 0; i < entries.size() - 1; i+=2) {
                JLabel labelOne = entries.get(i);
                JLabel labelTwo = entries.get(i+1);
 */