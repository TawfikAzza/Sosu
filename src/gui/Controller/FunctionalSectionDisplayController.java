package gui.Controller;

import be.AbilityCategory;
import be.Citizen;

import bll.exceptions.AbilityCategoryException;
import bll.exceptions.HealthCategoryException;
import bll.util.GlobalVariables;
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
/**
 * Author: Tawfik/Renars
 * Warning, most of the Node in this page are dynamically created,
 * so some tweaking has been made in order to accomodate this fact.
 * I'll try my best explaining what has been done, but if any question arises,
 * come to me.
 * ****/
public class FunctionalSectionDisplayController implements Initializable {
    @FXML
    private Accordion functionalAbilityContainer;

    private CategoryModel categoryModel;
    public FunctionalSectionDisplayController() throws HealthCategoryException {
        categoryModel = new CategoryModel();
    }
    /**
     * The Initialize method is kind of important as it sets the different actions the user
     * will be able to take when arriving to this page.
     * Take special note of how we managed the event of the user clicking on the subcategory
     * which is displayed.
     *
     * ***/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<AbilityCategory> abilityCategories;

        try {
            //We first start by first getting the Main categories with the subacategories associated
            //via the subcategories list present in the Main category class.
            abilityCategories = categoryModel.getAbilityCategories();
            for (AbilityCategory abilityCategory : abilityCategories) {
                //we then parse the main categories and associate the name of said category with the
                //TitlePane of the Accordion Node of the View.
                TitledPane titledPane = new TitledPane();
                titledPane.setText(abilityCategory.getName());
                //As each Main categories possess a different number/type of subcategories, we create a List
                //of subcategories which we will use for each Main categories, we reinitialize the ListView
                //variable at each loop in order for it to contain only the subactegories of the Main
                //Category currently parsed for later display when we add the total of listView to the TitlePane
                // Which in turn will be added to the root (Accordion in this case).
                ListView<AbilityCategory> subCategoryList = new ListView<>();
                //As each ListView must possess the ability to display the condition/ability input page when it is clicked on,
                // We add a method which will be called when the click does happen.
                //for this purpose, we use the built-in method of most of the JavaFX nodes setOnMouseClicked and attribute it
                // the reference of the current subcategory, the method take two arguments (the currently selected item in the
                //ListView, as well as the current citizen, as we need both the id of these object to target the right
                //COndition/ability in the database.
                //Note that it wouold not have been possible to do it this way if the relationship between the subcategories
                //report were one to many, but as it one to one, we can do it this way.
                subCategoryList.setOnMouseClicked(e-> {
                    openConditionReport(subCategoryList.getSelectionModel().getSelectedItem(), GlobalVariables.getSelectedCitizen());
                });
                //We then fill the ListView with the subcategories of the currently parsed Main category.
                for (AbilityCategory subCategory : abilityCategory.getSubCategories()) {
                    subCategoryList.getItems().add(subCategory);
                }
                //We add the bulk to the titledPane
                titledPane.setContent(subCategoryList);
                //We add the TitledPane to the Accordion node.
                functionalAbilityContainer.getPanes().add(titledPane);

            }
        } catch (AbilityCategoryException e) {
            e.printStackTrace();
        }
    }

    /**
     * Author : Tawfik
     * This method is in charge of opening a new window in Modal mode
     * after having passed the right values of category selected and the current citizen to its controller.
     *
     * *****/

    private void openConditionReport(AbilityCategory abilityCategory, Citizen citizen)  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/AbilityReportView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Setting the variables of the target controller and setting the fields of it if the report has already been
        //filed previously
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

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) functionalAbilityContainer.getScene().getWindow();
        stage.close();
    }
}
