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
        //displayReport();
        display();

    }


    public void displayCitiziInfo() {

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

            //lblCategory.setText((String) entry.getKey());
            textFieldContent.setText((String) entry.getKey() + (String) entry.getValue());
        }


    }

    public void CloseAction() {
        Stage window = (Stage) this.btnClose.getScene().getWindow();
        window.close();
    }


    public void display() {
        HashMap<String, String> getGIR = giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen());
        int i = 1;
        GridPane mainPane = new GridPane();
        mainPane.setHgap(0);
        mainPane.setVgap(0);
        mainPane.setAlignment(Pos.CENTER);


        Label headerMestring = new Label("Mestring");
        headerMestring.setStyle("-fx-font-size: 16px;");
        headerMestring.setStyle("-fx-font-weight: bold;");

        Label headerMotivation = new Label("Motivation");
        headerMotivation.setStyle("-fx-font-size: 16px;");
        headerMotivation.setStyle("-fx-font-weight: bold;");

        Label headerRessourcer = new Label("Ressourcer");
        headerRessourcer.setStyle("-fx-font-size: 16px;");
        headerRessourcer.setStyle("-fx-font-weight: bold;");

        Label headerRoller = new Label("Roller");
        headerRoller.setStyle("-fx-font-size: 16px;");
        headerRoller.setStyle("-fx-font-weight: bold;");

        Label headerVaner = new Label("Vaner");
        headerVaner.setStyle("-fx-font-size: 16px;");
        headerVaner.setStyle("-fx-font-weight: bold;");

        Label headerUddannelseogjob = new Label("Uddannelse og job");
        headerUddannelseogjob.setStyle("-fx-font-size: 16px;");
        headerUddannelseogjob.setStyle("-fx-font-weight: bold;");

        Label headerLivshistorie = new Label("Livshistorie");
        headerLivshistorie.setStyle("-fx-font-size: 16px;");
        headerLivshistorie.setStyle("-fx-font-weight: bold;");

        Label headerNetværk = new Label("Netværk");
        headerNetværk.setStyle("-fx-font-size: 16px;");
        headerNetværk.setStyle("-fx-font-weight: bold;");

        Label headerHelbredsoplysninger = new Label("Helbredsoplysninger");
        headerHelbredsoplysninger.setStyle("-fx-font-size: 16px;");
        headerHelbredsoplysninger.setStyle("-fx-font-weight: bold;");

        Label headerHjælpemidler = new Label("Hjælpemidler");
        headerHjælpemidler.setStyle("-fx-font-size: 16px;");
        headerHjælpemidler.setStyle("-fx-font-weight: bold;");

        Label headerBoligensindretning = new Label("Boligens indretning");
        headerBoligensindretning.setStyle("-fx-font-size: 16px;");
        headerBoligensindretning.setStyle("-fx-font-weight: bold;");

        mainPane.add(headerMestring, 0, i);
        mainPane.add(headerMotivation, 1, i);
        mainPane.add(headerRessourcer, 2, i);
        mainPane.add(headerRoller, 3, i);
        mainPane.add(headerVaner, 4, i);
        mainPane.add(headerUddannelseogjob, 5, i);
        mainPane.add(headerLivshistorie, 6, i);
        mainPane.add(headerNetværk, 7, i);
        mainPane.add(headerHelbredsoplysninger, 8, i);
        mainPane.add(headerHjælpemidler, 9, i);
        mainPane.add(headerBoligensindretning, 10, i);
        i++;




            System.out.println(i);
            mainPane.setAlignment(Pos.CENTER);



        }


}

/*

 */
