package gui.Controller;

import be.Citizen;
import be.Condition;
import be.HealthCategory;
import bll.exceptions.HealthCategoryException;
import gui.Model.CategoryModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BranchDisplayController implements Initializable {

    public Accordion healthContainer;

    private CategoryModel categoryModel;

    public BranchDisplayController() throws HealthCategoryException {
        categoryModel = new CategoryModel();
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
                    System.out.println(subCategoryList.getSelectionModel().getSelectedItem().getName());
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

    private void openConditionReport(Condition condition, Citizen citizen) {

    }

}
