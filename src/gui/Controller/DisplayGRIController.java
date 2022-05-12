package gui.Controller;

import be.Citizen;
import be.InfoCategory;
import bll.GIReportManger;
import bll.exceptions.CitizenException;
import bll.exceptions.GeneralInfoException;
import bll.util.GlobalVariables;
import gui.Model.GIReportModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
            giReportModel = new GIReportModel();
            giReportManger = new GIReportManger();

        } catch (GeneralInfoException | CitizenException e) {
            e.printStackTrace();
        }
        displayReport();

    }


    public void displayReport(){


        System.out.println("Selected:"+GlobalVariables.getSelectedCitizen());
        HashMap<String,String> getGIR = giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen());


            /*lblFName.setText(GlobalVariables.getSelectedCitizen().getFName());
            lblLName.setText(GlobalVariables.getSelectedCitizen().getLName());
            lblAdress.setText(GlobalVariables.getSelectedCitizen().getAddress());*/
            //lblBirthdate.setText(GlobalVariables.getSelectedCitizen().getBirthDate());
            //lblPhone.setText(GlobalVariables.getSelectedCitizen().getPhoneNumber());
            //lblSchool.setText(GlobalVariables.getSelectedCitizen().getSchoolID());

        //textMestring.setText( giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen(),selectedInfoCategory));

            for (Map.Entry entry : getGIR.entrySet()) {
                System.out.println(" key: "+entry.getKey()+" value: "+entry.getValue());
            }


        }





    public void CloseAction() {
        Stage window = (Stage) this.btnClose.getScene().getWindow();
            window.close();
        }


}
