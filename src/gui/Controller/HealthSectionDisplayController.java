package gui.Controller;

import be.Citizen;
import be.Condition;
import be.HealthCategory;
import bll.exceptions.HealthCategoryException;
import bll.util.GlobalCitizen;
import gui.Model.CategoryModel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HealthSectionDisplayController implements Initializable {

    public Accordion healthContainer;

    private CategoryModel categoryModel;
    private Citizen currentCitizen;
    public HealthSectionDisplayController() throws HealthCategoryException {
        categoryModel = new CategoryModel();
        currentCitizen = GlobalCitizen.getSelectedCitizen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<HealthCategory> healthCategories;

        try {
            healthCategories = categoryModel.getHealthCategories();
            for (HealthCategory healthCategory : healthCategories) {
                TitledPane titledPane = new TitledPane();
                titledPane.setText(healthCategory.getName());

                ListView<HealthCategory> subCategoryList = new ListView<>();
                subCategoryList.setOnMouseClicked(e-> {
                    openConditionReport(subCategoryList.getSelectionModel().getSelectedItem(),currentCitizen);
                });
                for (HealthCategory subCategory : healthCategory.getSubCategories()) {
                    subCategoryList.getItems().add(subCategory);
                }
                titledPane.setContent(subCategoryList);
                healthContainer.getPanes().add(titledPane);

            }
        } catch (HealthCategoryException e) {
            e.printStackTrace();
        }
    }

    private void openConditionReport(HealthCategory healthCategory, Citizen citizen)  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ConditionReportView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConditionReportViewController conditionReportViewController = loader.getController();
        conditionReportViewController.setCurrentHealthCategory(healthCategory);
        conditionReportViewController.setCurrentCitizen(citizen);
        conditionReportViewController.setFields();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    
}
