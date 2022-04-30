package gui.Controller;

import be.AbilityCategory;
import be.Citizen;
import be.HealthCategory;
import bll.exceptions.AbilityCategoryException;
import bll.exceptions.HealthCategoryException;
import gui.Model.CategoryModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FunctionalSectionDisplayController implements Initializable {
    @FXML
    private Accordion functionalAbilityContainer;
    private CategoryModel categoryModel;
    private Citizen currentCitizen;
    public FunctionalSectionDisplayController() throws HealthCategoryException {
        categoryModel = new CategoryModel();
        currentCitizen = new Citizen(1,"Jeppe Moritz","15-12-2015","123");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<AbilityCategory> abilityCategories;

        try {
            abilityCategories = categoryModel.getAbilityCategories();
            for (AbilityCategory abilityCategory : abilityCategories) {
                TitledPane titledPane = new TitledPane();
                titledPane.setText(abilityCategory.getName());

                ListView<AbilityCategory> subCategoryList = new ListView<>();
                subCategoryList.setOnMouseClicked(e-> {
                    openConditionReport(subCategoryList.getSelectionModel().getSelectedItem(),currentCitizen);
                });
                for (AbilityCategory subCategory : abilityCategory.getSubCategories()) {
                    subCategoryList.getItems().add(subCategory);
                }
                titledPane.setContent(subCategoryList);
                functionalAbilityContainer.getPanes().add(titledPane);

            }
        } catch (AbilityCategoryException e) {
            e.printStackTrace();
        }
    }
    private void openConditionReport(AbilityCategory abilityCategory, Citizen citizen)  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/AbilityReportView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbilityReportViewController abilityReportViewController = loader.getController();
        abilityReportViewController.setCurrentAbilityCategory(abilityCategory);
        abilityReportViewController.setCurrentCitizen(citizen);
        abilityReportViewController.setFields();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();


    }
}
