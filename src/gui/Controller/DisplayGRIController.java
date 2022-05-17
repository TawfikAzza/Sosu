package gui.Controller;

import be.*;
import bll.GIReportManger;
import bll.exceptions.CitizenException;
import bll.exceptions.CitizenReportException;
import bll.exceptions.GIReportException;
import bll.exceptions.GeneralInfoException;
import bll.util.GlobalVariables;
import gui.Model.GIReportModel;
import gui.utils.DisplayMessage;
import gui.utils.GeneratePdf;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;



public class DisplayGRIController implements Initializable {


    @FXML
    private Label lblAdress, lblBirthdate, lblFName, lblLName, lblPhone, lblSchool;
    @FXML
    private Button btnClose, btnPrint;
    @FXML
    private Label lblCategory;
    @FXML
    private TextField textFieldContent, textBoligens, textHelb, textHjælp, textLiv, textMestring, textMotivation, textNetværk;

    @FXML
    private TextField textRessourcer, textRoller, textUddannelse, textVaner;


    private GIReportManger giReportManger;
    private GIReportModel giReportModel;
    private InfoCategory selectedInfoCategory;
    private Citizen citizen;
    private School school;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            giReportModel = new GIReportModel();
            giReportManger = new GIReportManger();

        } catch (CitizenException | GIReportException e) {
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
        lblBirthdate.setText("" + GlobalVariables.getSelectedCitizen().getBirthDate());
        lblPhone.setText("" + GlobalVariables.getSelectedCitizen().getPhoneNumber());

        lblSchool.setText("" + GlobalVariables.getSelectedCitizen().getSchoolName());
        


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

    public void printResult() {
        HashMap<String, String> getGIR = null;
        try {
            getGIR = giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(btnPrint.getScene().getWindow());
        //GeneratePdf.
    }

}
