package gui.Controller;

import be.Citizen;
import be.Condition;
import be.HealthCategory;
import bll.exceptions.HealthCategoryException;
import gui.Model.CategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class ConditionReportViewController implements Initializable {
    @FXML
    private RadioButton statusAkute,statusIrrelevant,statusPotentiel;
    @FXML
    private TextArea description,freeText,goal;
    @FXML
    private ToggleGroup status;


    private Citizen currentCitizen;
    private HealthCategory healthCategory;
    private CategoryModel categoryModel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            categoryModel = new CategoryModel();
            statusIrrelevant.setUserData(0);
            statusPotentiel.setUserData(1);
            statusAkute.setUserData(2);
        } catch (HealthCategoryException e) {
            e.printStackTrace();
        }
    }
    public void setCurrentCitizen(Citizen citizen) {
        this.currentCitizen = citizen;
        System.out.println("Health : "+healthCategory.getName()+ " Citizen : "+currentCitizen.getName());
    }


    public void setCurrentHealthCategory(HealthCategory healthCategory) {
        this.healthCategory = healthCategory;
    }
    public void setFields() {
        try {
            Condition condition = categoryModel.getCondition(healthCategory,currentCitizen);
            description.setText(condition.getDescription());
            freeText.setText(condition.getFreeText());
            goal.setText(condition.getGoal());
            if(condition.getStatus()==0) {
                statusIrrelevant.setSelected(true);
            }
            if(condition.getStatus()==1) {
                statusPotentiel.setSelected(true);
            }
            if(condition.getStatus()==2) {
                statusAkute.setSelected(true);
            }
            System.out.println(status.getSelectedToggle());
        } catch (HealthCategoryException e) {
            e.printStackTrace();
        }

    }

    public void confirm(ActionEvent actionEvent) {
    }
}
