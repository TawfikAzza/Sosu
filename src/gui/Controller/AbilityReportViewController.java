package gui.Controller;

import be.*;
import bll.exceptions.AbilityCategoryException;
import bll.exceptions.HealthCategoryException;
import gui.Model.CategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AbilityReportViewController implements Initializable {

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
    private TextArea citizenGoal;
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
            setUserData();
            bindSizes();
            setImages();
            setEventHandlers();
            setTooltips();
        } catch (HealthCategoryException e) {
            e.printStackTrace();
        }

    }

    private void setTooltips() {

    }

    private void setEventHandlers() {
        functionalLevel_0.setOnMouseClicked(event -> {radio0.setSelected(true);});
        functionalLevel_1.setOnMouseClicked(event -> {radio1.setSelected(true);});
        functionalLevel_2.setOnMouseClicked(event -> {radio2.setSelected(true);});
        functionalLevel_3.setOnMouseClicked(event -> {radio3.setSelected(true);});
        functionalLevel_4.setOnMouseClicked(event -> {radio4.setSelected(true);});
        functionalLevel_9.setOnMouseClicked(event -> {radio9.setSelected(true);});
    }

    private void setImages() {
        functionalLevel_0.setImage(new Image("functional_level_0.png"));
        functionalLevel_1.setImage(new Image("functional_level_1.png"));
        functionalLevel_2.setImage(new Image("functional_level_2.png"));
        functionalLevel_3.setImage(new Image("functional_level_3.png"));
        functionalLevel_4.setImage(new Image("functional_level_4.png"));
    }

    private void bindSizes() {
        HBox citizenGoalParent = ((HBox) citizenGoal.getParent());
        citizenGoal.prefWidthProperty().bind(citizenGoalParent.widthProperty());
        citizenGoal.prefHeightProperty().bind(citizenGoalParent.heightProperty());
    }

    private void setUserData() {
        radio0.setUserData(0);
        radio1.setUserData(1);
        radio2.setUserData(2);
        radio3.setUserData(3);
        radio4.setUserData(4);
        radio9.setUserData(9);
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
            citizenGoal.setText(ability.getGoals());
        } catch (AbilityCategoryException e) {
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
                try {
                    categoryModel.addAbility(ability);
                    Stage stage = (Stage) (statusIrrelevant.getScene().getWindow());
                    stage.close();
                } catch (AbilityCategoryException e) {
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
                try {
                    categoryModel.updateAbility(ability);
                    Stage stage = (Stage)(statusIrrelevant.getScene().getWindow());
                    stage.close();
                } catch (AbilityCategoryException e) {
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
        if(!message.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            return false;
        }
        return true;

    }

    @FXML void handleCancel(ActionEvent actionEvent) {
        Button sourceButton = ((Button) actionEvent.getSource());
        ((Stage) sourceButton.getParent().getScene().getWindow()).close();
    }
}
