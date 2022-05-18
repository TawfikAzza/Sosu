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

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
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
    private TextFlow textFieldContent, textBoligens, textHelb, textHjælp, textLiv, textMestring, textMotivation, textNetværk;

    @FXML
    private TextFlow  textRessourcer, textRoller, textUddannelse, textVaner;


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

        Text t1 = new Text(getGIR.get("Mestring"));
        textMestring.getChildren().add(t1);

        Text t2 = new Text(getGIR.get("Motivation"));
        textMotivation.getChildren().add(t2);

        Text t3 = new Text(getGIR.get("Ressourcer"));
        textRessourcer.getChildren().add(t3);

        Text t4 = new Text(getGIR.get("Roller"));
        textRoller.getChildren().add(t4);

        Text t5 = new Text(getGIR.get("Vaner"));
        textVaner.getChildren().add(t5);

        Text t6 = new Text(getGIR.get("Uddannelse og job"));
        textUddannelse.getChildren().add(t6);

        Text t7 = new Text(getGIR.get("Livshistorie"));
        textLiv.getChildren().add(t7);

        Text t8 = new Text(getGIR.get("Netværk"));
        textNetværk.getChildren().add(t8);

        Text t9 = new Text(getGIR.get("Helbredsoplysninger"));
        textHelb.getChildren().add(t9);

        Text t10 = new Text(getGIR.get("Hjælpemidler"));
        textHjælp.getChildren().add(t10);

        Text t11 = new Text(getGIR.get("Boligens indretning"));
        textBoligens.getChildren().add(t11);


        /*
        if (getGIR.containsKey("Boligens indretning")) {
          //  textBoligens.setText(getGIR.get("Boligens indretning"));
        }

         */



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
