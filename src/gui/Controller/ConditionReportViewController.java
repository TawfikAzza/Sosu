package gui.Controller;

import be.Citizen;
import be.Condition;
import be.HealthCategory;
import bll.exceptions.HealthCategoryException;
import bll.util.DateUtil;
import gui.Model.CategoryModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConditionReportViewController implements Initializable {

    @FXML
    private Button btnConfirm;
    @FXML
    private RadioButton statusAkute,statusIrrelevant,statusPotentiel,radio0,radio1,radio2;
    @FXML
    private TextArea importantNote,assessement,goal,observation;
    @FXML
    private ToggleGroup status,expectedScore;
    @FXML
    private DatePicker visitDate;
    @FXML
    private Label lblCategory;

    private Citizen currentCitizen;
    private HealthCategory healthCategory;
    private CategoryModel categoryModel;
    private String operationType;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            categoryModel = new CategoryModel();
            /* Expected score radio */
            setUserData();
            bindSizes();
        } catch (HealthCategoryException e) {
            DisplayMessage.displayError(e);
        }
    }

    private void bindSizes() {
        bindDatePicker();
    }

    private void bindDatePicker() {
        VBox datePickerParent = ((VBox) visitDate.getParent());
        visitDate.prefHeightProperty().bind(datePickerParent.heightProperty());
        visitDate.prefWidthProperty().bind(datePickerParent.widthProperty());
    }

    private void setUserData() {
        radio0.setUserData(0);
        radio1.setUserData(1);
        radio2.setUserData(2);

        statusIrrelevant.setUserData(0);
        statusPotentiel.setUserData(1);
        statusAkute.setUserData(2);
    }

    public void setCurrentCitizen(Citizen citizen) {
        this.currentCitizen = citizen;
    }


    public void setCurrentHealthCategory(HealthCategory healthCategory) {
        this.healthCategory = healthCategory;
    }
    public void setFields() {
        try {
            Condition condition = categoryModel.getCondition(healthCategory,currentCitizen);
            if(condition==null) {
                operationType="insert";
                lblCategory.setText(healthCategory.getName());
                statusIrrelevant.setSelected(true);
                btnConfirm.setText("Add Condition");
                return;
            }
            operationType="update";
            lblCategory.setText(healthCategory.getName());
            btnConfirm.setText("Update Condition");
            importantNote.setText(condition.getImportantNote());
            assessement.setText(condition.getAssessement());
            goal.setText(condition.getGoal());

            visitDate.setValue(DateUtil.parseDate(condition.getVisitDate().toString()));
            observation.setText(condition.getObservation());

            switch (condition.getExpectedScore()) {
                case 0 -> radio0.setSelected(true);
                case 1 -> radio1.setSelected(true);
                case 2 -> radio2.setSelected(true);
                default -> {
                }
            }
            if(condition.getStatus()==0) {
                statusIrrelevant.setSelected(true);
            }
            if(condition.getStatus()==1) {
                statusPotentiel.setSelected(true);
            }
            if(condition.getStatus()==2) {
                statusAkute.setSelected(true);
            }

        } catch (HealthCategoryException e) {
            DisplayMessage.displayError(e);
        }

    }

    public void confirm(ActionEvent actionEvent) {
        String message = "";
        if(operationType.equals("insert")) {
            if(!checkFields()) {
               return;
            } else {
                Condition condition = new Condition(1,healthCategory.getId()
                                                 , currentCitizen.getId()
                                                ,importantNote.getText()
                                                ,Integer.parseInt(status.getSelectedToggle().getUserData().toString())
                                                ,assessement.getText()
                                                ,goal.getText());
                condition.setExpectedScore(Integer.parseInt(expectedScore.getSelectedToggle().getUserData().toString()));
                condition.setObservation(observation.getText());
                condition.setVisitDate(visitDate.getValue());
                try {
                    categoryModel.addCondition(condition);
                     Stage stage = (Stage)(statusIrrelevant.getScene().getWindow());
                     stage.close();
                } catch (HealthCategoryException e) {
                    DisplayMessage.displayError(e);
                }
            }
        }
        if(operationType.equals("update")) {
            if(!checkFields()) {
                return;
            } else {
                Condition condition = new Condition(1,healthCategory.getId()
                        , currentCitizen.getId()
                        ,importantNote.getText()
                        ,Integer.parseInt(status.getSelectedToggle().getUserData().toString())
                        ,assessement.getText()
                        ,goal.getText());
                        condition.setExpectedScore(Integer.parseInt(expectedScore.getSelectedToggle().getUserData().toString()));
                        condition.setObservation(observation.getText());
                        condition.setVisitDate(visitDate.getValue());
                try {
                    categoryModel.updateCondition(condition);
                    Stage stage = (Stage)(statusIrrelevant.getScene().getWindow());
                    stage.close();
                } catch (HealthCategoryException e) {
                    DisplayMessage.displayError(e);
                }
            }
        }
    }
    private boolean checkFields() {
        String message="";
        if(importantNote.getText().equals(""))
            message += "- Specify a Note for the condition \n";
        if(assessement.getText().equals(""))
            message+="- Specify an assessment for the condition \n";
        if(goal.getText().equals(""))
            message+="- Specify a goal for the condition \n";
        if(status.getSelectedToggle()==null) {
            message+="- No choice as to the status has been made";
        }
        if(expectedScore.getSelectedToggle()==null)
            message += "- Specify an expected score for the condition \n";
        if(observation.getText().equals(""))
            message += "- Specify an observation \n";
        if(visitDate.getValue()==null)
            message += "- Specify a visit date \n";
        if(!message.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            return false;
        }
       return true;
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        Button sourceButton = ((Button) actionEvent.getSource());
        ((Stage) sourceButton.getParent().getScene().getWindow()).close();
    }
}
