package gui.Controller;

import be.Citizen;
import be.Condition;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ConditionReportViewController implements Initializable {
    private Condition currentCondition;
    private Citizen currentCitizen;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setCurrentCitizen(Citizen citizen) {
        this.currentCitizen = citizen;
    }
    public void setCurrentCondition(Condition condition){
        this.currentCondition=condition;
    }
}
