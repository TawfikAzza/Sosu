package gui.Controller;

import be.*;
import bll.exceptions.AbilityCategoryException;
import bll.exceptions.HealthCategoryException;
import bll.util.DateUtil;
import gui.Model.CategoryModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AbilityReportViewController implements Initializable {


    @FXML
    private ImageView functionalLevel_0_1;
    @FXML
    private ImageView functionalLevel_1_1;
    @FXML
    private ImageView functionalLevel_2_1;
    @FXML
    private ImageView functionalLevel_3_1;
    @FXML
    private ImageView functionalLevel_4_1;
    @FXML
    private ImageView functionalLevel_9_1;
    @FXML
    private TextArea observation;
    @FXML
    private ImageView functionalLevel_0;
    @FXML
    private ImageView functionalLevel_1;
    @FXML
    private ImageView functionalLevel_2;
    @FXML
    private ImageView functionalLevel_3;
    @FXML
    private ImageView functionalLevel_4;
    @FXML
    private ImageView functionalLevel_9;
    @FXML
    private Button btnConfirm;
    @FXML
    private TextArea citizenGoal,importantNote;

    @FXML
    private DatePicker visitDate;
    @FXML
    private RadioButton /* status radio buttons*/ statusAkute,statusIrrelevant,statusPotentiel,
    /* functional level radio buttons*/ level0, level1, level2, level3, level4, level9,
    /* expected level radio buttons */ level0_1, level1_1, level2_1, level3_1, level4_1, level9_1,
    /* performance radio buttons  */radio02,radio12,radio22,radio32,
    /* meaning radio buttons */radio03,radio13;

    @FXML
    private ToggleGroup score,status,expectedScore,meaning,performance;
    @FXML
    private Label lblCategory;

    private Citizen currentCitizen;
    private AbilityCategory abilityCategory;
    private CategoryModel categoryModel;
    private String operationType;

    public AbilityReportViewController() {
        try {
            categoryModel = new CategoryModel();
        } catch (HealthCategoryException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            categoryModel = new CategoryModel();
            setUserData();
            bindSizes();
            setImages();
            setEventHandlers();
            setTooltips();
        } catch (HealthCategoryException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

    }

    private void setTooltips() {

    }

    private void setEventHandlers() {
        functionalLevel_0.setOnMouseClicked(event -> {
            level0.setSelected(true);});
        functionalLevel_1.setOnMouseClicked(event -> {
            level1.setSelected(true);});
        functionalLevel_2.setOnMouseClicked(event -> {
            level2.setSelected(true);});
        functionalLevel_3.setOnMouseClicked(event -> {
            level3.setSelected(true);});
        functionalLevel_4.setOnMouseClicked(event -> {
            level4.setSelected(true);});
        functionalLevel_9.setOnMouseClicked(event -> {
            level9.setSelected(true);});

        functionalLevel_0_1.setOnMouseClicked(event -> {
            level0_1.setSelected(true);});
        functionalLevel_1_1.setOnMouseClicked(event -> {
            level1_1.setSelected(true);});
        functionalLevel_2_1.setOnMouseClicked(event -> {
            level2_1.setSelected(true);});
        functionalLevel_3_1.setOnMouseClicked(event -> {
            level3_1.setSelected(true);});
        functionalLevel_4_1.setOnMouseClicked(event -> {
            level4_1.setSelected(true);});
        functionalLevel_9_1.setOnMouseClicked(event -> {
            level9_1.setSelected(true);});
    }

    private void setImages() {
        functionalLevel_0.setImage(new Image("functional_level_0.png"));
        functionalLevel_1.setImage(new Image("functional_level_1.png"));
        functionalLevel_2.setImage(new Image("functional_level_2.png"));
        functionalLevel_3.setImage(new Image("functional_level_3.png"));
        functionalLevel_4.setImage(new Image("functional_level_4.png"));

        functionalLevel_0_1.setImage(new Image("functional_level_0.png"));
        functionalLevel_1_1.setImage(new Image("functional_level_1.png"));
        functionalLevel_2_1.setImage(new Image("functional_level_2.png"));
        functionalLevel_3_1.setImage(new Image("functional_level_3.png"));
        functionalLevel_4_1.setImage(new Image("functional_level_4.png"));
    }

    private void bindSizes() {
        VBox citizenGoalParent = ((VBox) citizenGoal.getParent());
        citizenGoal.prefWidthProperty().bind(citizenGoalParent.widthProperty());
        citizenGoal.prefHeightProperty().bind(citizenGoalParent.heightProperty());

        VBox visitDateParent = ((VBox) visitDate.getParent());
        visitDate.prefWidthProperty().bind(citizenGoalParent.widthProperty());
        visitDate.prefHeightProperty().bind(citizenGoalParent.heightProperty());
    }

    private void setUserData() {
        /* Current level radio buttons */
        level0.setUserData(0);
        level1.setUserData(1);
        level2.setUserData(2);
        level3.setUserData(3);
        level4.setUserData(4);
        level9.setUserData(9);

        /* Expected level radio buttons */
        level0_1.setUserData(0);
        level1_1.setUserData(1);
        level2_1.setUserData(2);
        level3_1.setUserData(3);
        level4_1.setUserData(4);
        level9_1.setUserData(9);

        /* Performance radio buttons */
        radio02.setUserData(0);
        radio12.setUserData(1);
        radio22.setUserData(2);
        radio32.setUserData(3);

        /* Meaning radio button */
        radio03.setUserData(0);
        radio13.setUserData(1);

        /* Status radio Buttons */
        statusIrrelevant.setUserData(0);
        statusPotentiel.setUserData(1);
        statusAkute.setUserData(2);
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
                lblCategory.setText(abilityCategory.getName());
                statusIrrelevant.setSelected(true);
                btnConfirm.setText("Add Ability");
                return;
            }
            operationType="update";
            lblCategory.setText(abilityCategory.getName());
            btnConfirm.setText("Update Ability");
            switch(ability.getScore()) {
                case 0: level0.setSelected(true);
                    break;
                case 1: level1.setSelected(true);
                    break;
                case 2: level2.setSelected(true);
                    break;
                case 3: level3.setSelected(true);
                    break;
                case 4: level4.setSelected(true);
                    break;
                case 9: level9.setSelected(true);
                    break;
                default:
                    break;
            }
            switch(ability.getPerformance()) {
                case 0: radio02.setSelected(true);
                    break;
                case 1: radio12.setSelected(true);
                    break;
                case 2: radio22.setSelected(true);
                    break;
                case 3: radio32.setSelected(true);
                    break;
                default:
                    break;
            }
            switch(ability.getExpectedScore()) {
                case 0: level0_1.setSelected(true);
                    break;
                case 1: level1_1.setSelected(true);
                    break;
                case 2: level2_1.setSelected(true);
                    break;
                case 3: level3_1.setSelected(true);
                    break;
                case 4: level4_1.setSelected(true);
                    break;
                case 9: level9_1.setSelected(true);
                    break;
                default:
                    break;
            }
            switch(ability.getMeaning()) {
                case 0: radio03.setSelected(true);
                    break;
                case 1: radio13.setSelected(true);
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

            citizenGoal.setText(ability.getGoals());
            visitDate.setValue(DateUtil.parseDate(String.valueOf(ability.getVisitDate())));
            importantNote.setText(ability.getImportantNote());
            observation.setText(ability.getObservation());
        } catch (AbilityCategoryException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

    }

    public void confirm(ActionEvent actionEvent) {
        if(operationType.equals("insert")) {
            if (!checkFields()) {
                return;
            } else {
                Ability ability = new Ability(1
                        , abilityCategory.getId()
                        , currentCitizen.getId()
                        , Integer.parseInt(score.getSelectedToggle().getUserData().toString())
                        , Integer.parseInt(status.getSelectedToggle().getUserData().toString()));
                ability.setGoals(citizenGoal.getText());
                ability.setImportantNote(importantNote.getText());
                ability.setPerformance(Integer.parseInt(performance.getSelectedToggle().getUserData().toString()));
                ability.setMeaning(Integer.parseInt(meaning.getSelectedToggle().getUserData().toString()));
                ability.setExpectedScore(Integer.parseInt(expectedScore.getSelectedToggle().getUserData().toString()));
                ability.setVisitDate(DateUtil.parseDate(visitDate.getValue().toString()));
                ability.setObservation(observation.getText());
                try {
                    categoryModel.addAbility(ability);
                    Stage stage = (Stage) (statusIrrelevant.getScene().getWindow());
                    stage.close();
                } catch (AbilityCategoryException e) {
                    DisplayMessage.displayError(e);
                    e.printStackTrace();
                }
            }
        }
        if(operationType.equals("update")) {
            if(!checkFields()) {
                return;
            } else {
                Ability ability = new Ability(1,abilityCategory.getId()
                        , currentCitizen.getId()
                        ,Integer.parseInt(score.getSelectedToggle().getUserData().toString())
                        ,Integer.parseInt(status.getSelectedToggle().getUserData().toString())
                );
                ability.setGoals(citizenGoal.getText());
                 ability.setImportantNote(importantNote.getText());
                ability.setPerformance(Integer.parseInt(performance.getSelectedToggle().getUserData().toString()));
                ability.setMeaning(Integer.parseInt(meaning.getSelectedToggle().getUserData().toString()));
                ability.setExpectedScore(Integer.parseInt(expectedScore.getSelectedToggle().getUserData().toString()));
                ability.setVisitDate(DateUtil.parseDate(visitDate.getValue().toString()));
                ability.setObservation(observation.getText());
                try {
                    categoryModel.updateAbility(ability);
                    Stage stage = (Stage)(statusIrrelevant.getScene().getWindow());
                    stage.close();
                } catch (AbilityCategoryException e) {
                    DisplayMessage.displayError(e);
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkFields() {
        String message="";
        if(score.getSelectedToggle()==null)
            message += "- Specify a score for the ability \n";
        if(status.getSelectedToggle()==null)
            message+="- No choice as to the status has been made";
        if(citizenGoal.getText().equals(""))
            message+="- No goals set for the Citizen";
        if(importantNote.getText().equals(""))
            message+="- No important notes set for the Citizen";
        if(expectedScore.getSelectedToggle()==null)
            message += "- Specify an expected score for the ability \n";
        if(observation.getText().equals(""))
            message += "- Specify an observation \n";
        if(visitDate.getValue()==null)
            message += "- Specify a visit date \n";
        if(meaning.getSelectedToggle()==null)
            message += "- Specify a meaning \n";
        if(performance.getSelectedToggle()==null)
            message += "- Specify a performance \n";

        if(!message.equals("")) {
            DisplayMessage.displayMessage(message);
            return false;
        }
        return true;

    }

    @FXML void handleCancel(ActionEvent actionEvent) {
        Button sourceButton = ((Button) actionEvent.getSource());
        ((Stage) sourceButton.getParent().getScene().getWindow()).close();
    }
}
