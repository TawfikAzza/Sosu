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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private TextField textFieldContent, textBoligens, textHelb;

    @FXML
    private TextField textHjælp;

    @FXML
    private TextField textLiv;

    @FXML
    private TextField textMestring;

    @FXML
    private TextField textMotivation;

    @FXML
    private TextField textNetværk;

    @FXML
    private TextField textRessourcer;

    @FXML
    private TextField textRoller;

    @FXML
    private TextField textUddannelse;

    @FXML
    private TextField textVaner;


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
        //displayReport();
        displayplay();

    }


    public void displayCitiziInfo() {

        lblFName.setText(GlobalVariables.getSelectedCitizen().getFName());

        lblLName.setText(GlobalVariables.getSelectedCitizen().getLName());
        lblAdress.setText(GlobalVariables.getSelectedCitizen().getAddress());
        lblBirthdate.setText("" +GlobalVariables.getSelectedCitizen().getBirthDate());
        lblPhone.setText("" +GlobalVariables.getSelectedCitizen().getPhoneNumber());
        lblSchool.setText("" +GlobalVariables.getSelectedCitizen().getSchoolID());

    }


    public void displayReport() {


        System.out.println("Selected:" + GlobalVariables.getSelectedCitizen());
        HashMap<String, String> getGIR = giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen());

        for (Map.Entry entry : getGIR.entrySet()) {
            System.out.println(" key: " + entry.getKey() + " value: " + entry.getValue());


        }


    }

    public void CloseAction() {
        Stage window = (Stage) this.btnClose.getScene().getWindow();
        window.close();
    }


    public void displayplay() {
        HashMap<String, String> getGIR = giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen());

        if (getGIR.containsKey("Mestring")) {
            textMestring.setText(getGIR.get("Mestring"));
        }
        if (getGIR.containsKey("Motivation")) {
            textMotivation.setText(getGIR.get("Motivation"));
        }
        if (getGIR.containsKey("Ressourcer")) {
            textRessourcer.setText(getGIR.get("Ressourcer"));
        }
        if (getGIR.containsKey("Roller")) {
            textRoller.setText(getGIR.get("Roller"));
        }
        if (getGIR.containsKey("Vaner")) {
            textVaner.setText(getGIR.get("Vaner"));
        }
        if (getGIR.containsKey("Uddannelse og job")) {
            textUddannelse.setText(getGIR.get("Uddannelse og job"));
        }
        if (getGIR.containsKey("Livshistorie")) {
            textLiv.setText(getGIR.get("Livshistorie"));
        }
        if (getGIR.containsKey("Netværk")) {
            textNetværk.setText(getGIR.get("Netværk"));
        }
        if (getGIR.containsKey("Helbredsoplysninger")) {
            textHelb.setText(getGIR.get("Helbredsoplysninger"));
        }
        if (getGIR.containsKey("Hjælpemidler")) {
            textHjælp.setText(getGIR.get("Hjælpemidler"));
        }
        if (getGIR.containsKey("Boligens indretning")) {
            textBoligens.setText(getGIR.get("Boligens indretning"));
        }




    }
}

/*

 */
