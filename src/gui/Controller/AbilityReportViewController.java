package gui.Controller;

import be.Ability;
import be.AbilityCategory;
import be.Citizen;
import be.HealthCategory;
import bll.exceptions.AbilityCategoryException;
import bll.exceptions.HealthCategoryException;
import gui.Model.CategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AbilityReportViewController implements Initializable {

    @FXML
    private Button btnConfirm;
    @FXML
    private RadioButton radio0,radio1,radio2,radio3,radio4,radio9,statusAkute,statusIrrelevant,statusPotentiel;

    @FXML
    private ToggleGroup score,status;

    private Citizen currentCitizen;
    private AbilityCategory abilityCategory;
    private CategoryModel categoryModel;
    private String operationType;

    public AbilityReportViewController() {
        try {
            categoryModel = new CategoryModel();
        } catch (HealthCategoryException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            categoryModel = new CategoryModel();
            radio0.setUserData(0);
            radio1.setUserData(1);
            radio2.setUserData(2);
            radio3.setUserData(3);
            radio4.setUserData(4);
            radio9.setUserData(9);
        } catch (HealthCategoryException e) {
            e.printStackTrace();
        }

    }

    public void setCurrentAbilityCategory(AbilityCategory abilityCategory) {
        this.abilityCategory=abilityCategory;
    }

    public void setCurrentCitizen(Citizen citizen) {
        this.currentCitizen=citizen;
    }

    public void setFields() {
        try {
            Ability ability = categoryModel.getAbility(abilityCategory,currentCitizen);
            if(ability==null) {
                operationType="insert";
                btnConfirm.setText("Add Condition");
                return;
            }
            operationType="update";
            btnConfirm.setText("Update Condition");
            switch(ability.getScore()) {
                case 0: radio0.setSelected(true);
                break;
                case 1: radio1.setSelected(true);
                break;
                case 2: radio2.setSelected(true);
                break;
                case 3: radio3.setSelected(true);
                break;
                case 4: radio4.setSelected(true);
                break;
                case 9: radio9.setSelected(true);
                break;
                default:
                break;
            }
            if(ability.getStatus()==0) {
                statusIrrelevant.setSelected(true);
            }
            if(ability.getStatus()==1) {
                statusPotentiel.setSelected(true);
            }
            if(ability.getStatus()==2) {
                statusAkute.setSelected(true);
            }

        } catch (AbilityCategoryException e) {
            e.printStackTrace();
        }

    }

    public void confirm(ActionEvent actionEvent) {

    }

    private boolean checkFields() {
        /*String message="";
        if(description.getText().equals(""))
            message += "- Specify a description for the condition \n";
        if(freeText.getText().equals(""))
            message+="- Specify a freeText for the condition \n";
        if(goal.getText().equals(""))
            message+="- Specify a goal for the condition \n";
        if(status.getSelectedToggle()==null) {
            message+="- No choice as to the status has been made";
        }
        if(!message.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            return false;
        }
        return true;
    */
    return true;
    }
}
