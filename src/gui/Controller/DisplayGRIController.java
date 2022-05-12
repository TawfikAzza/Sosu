package gui.Controller;

import be.Citizen;
import be.GeneralInfo;
import be.InfoCategory;
import bll.GIReportManger;
import bll.exceptions.GeneralInfoException;
import bll.util.GlobalVariables;
import gui.Model.GIReportModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DisplayGRIController implements Initializable {

    @FXML
    private TextArea textBoligens, textHelbred, textHjælpe , textLivhistorie , textMestring , textMotivation;
    @FXML
    private TextArea textNetværk , textRessourcer , textRoller ,textUddannelse , textVaner;
    @FXML
    private Label lblAdress, lblBirthdate, lblFName, lblLName, lblPhone, lblSchool;
    @FXML
    private Button btnClose;

    private GIReportManger giReportManger;
    private GIReportModel giReportModel;
    private InfoCategory selectedInfoCategory;
    private Citizen citizen;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            giReportManger = new GIReportManger();

        } catch (GeneralInfoException e) {
            e.printStackTrace();
        }
        displayReport(citizen,selectedInfoCategory);

    }


    public void displayReport(Citizen citizen, InfoCategory selectedInfoCategory){

        this.citizen = citizen;
        this.selectedInfoCategory = selectedInfoCategory;

            lblFName.setText(GlobalVariables.getSelectedCitizen().getFName());
            lblLName.setText(GlobalVariables.getSelectedCitizen().getLName());
            lblAdress.setText(GlobalVariables.getSelectedCitizen().getAddress());
            //lblBirthdate.setText(GlobalVariables.getSelectedCitizen().getBirthDate());
            //lblPhone.setText(GlobalVariables.getSelectedCitizen().getPhoneNumber());
            //lblSchool.setText(GlobalVariables.getSelectedCitizen().getSchoolID());

        //textMestring.setText(giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen(),selectedInfoCategory));


        }





    public void CloseAction() {
        Stage window = (Stage) this.btnClose.getScene().getWindow();
            window.close();
        }


}
