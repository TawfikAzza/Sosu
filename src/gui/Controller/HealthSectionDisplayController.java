package gui.Controller;

import be.Citizen;
import be.HealthCategory;
import bll.exceptions.HealthCategoryException;
import bll.util.GlobalVariables;
import gui.Model.CategoryModel;
import gui.utils.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
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
 * Note2: Some of the implementation took place with Renars as a tandem, so let me get this straight...
 * If ANY bug/malfunction is found during the execution, this will be Renars's fault.
 * The cool part of the program which work would be mine then....
 * ****/
public class HealthSectionDisplayController implements Initializable {

    public Accordion healthContainer;

    private final CategoryModel categoryModel;
    private final Citizen currentCitizen;
    public HealthSectionDisplayController() throws HealthCategoryException {
        categoryModel = new CategoryModel();
        currentCitizen = GlobalVariables.getSelectedCitizen();
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
        List<HealthCategory> healthCategories;

        try {
            //We first start by first getting the gui.Main categories with the subacategories associated
            //via the subcategories list present in the gui.Main category class.

            healthCategories = categoryModel.getHealthCategories();
            for (HealthCategory healthCategory : healthCategories) {
                //we then parse the main categories and associate the name of said category with the
                //TitlePane of the Accordion Node of the View.

                TitledPane titledPane = new TitledPane();
                titledPane.setText(healthCategory.getName());
                //As each gui.Main categories possess a different number/type of subcategories, we create a List
                //of subcategories which we will use for each gui.Main categories, we reinitialize the ListView
                //variable at each loop in order for it to contain only the subactegories of the gui.Main
                //Category currently parsed for later display when we add the total of listView to the TitlePane
                // Which in turn will be added to the root (Accordion in this case).
                ListView<HealthCategory> subCategoryList = new ListView<>();
                //As each ListView must possess the ability to display the condition/ability input page when it is clicked on,
                // We add a method which will be called when the click does happen.
                //for this purpose, we use the built-in method of most of the JavaFX nodes setOnMouseClicked and attribute it
                // the reference of the current subcategory, the method take two arguments (the currently selected item in the
                //ListView, as well as the current citizen, as we need both the id of these object to target the right
                //COndition/ability in the database.
                //Note that it wouold not have been possible to do it this way if the relationship between the subcategories
                //report were one to many, but as it one to one, we can do it this way.
                subCategoryList.setOnMouseClicked(e-> openConditionReport(subCategoryList.getSelectionModel().getSelectedItem(),currentCitizen));
                //We then fill the ListView with the subcategories of the currently parsed gui.Main category.
                for (HealthCategory subCategory : healthCategory.getSubCategories()) {
                    subCategoryList.getItems().add(subCategory);
                }

                //We add the bulk to the titledPane
                titledPane.setContent(subCategoryList);

                //We add the TitledPane to the Accordion node.
                healthContainer.getPanes().add(titledPane);
            }
        } catch (HealthCategoryException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }
    /**
     * Author : Tawfik
     * This method is in charge of opening a new window in Modal mode
     * after having passed the right values of category selected and the current citizen to its controller.
     *
     * *****/
    private void openConditionReport(HealthCategory healthCategory, Citizen citizen)  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ConditionReportView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
        //Setting the variables of the target controller and setting the fields of it if the report has already been
        //filed previously
        ConditionReportViewController conditionReportViewController = loader.getController();
        conditionReportViewController.setCurrentHealthCategory(healthCategory);
        conditionReportViewController.setCurrentCitizen(citizen);
        conditionReportViewController.setFields();
        assert root != null;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }


    public void closeWindow() {
        Stage stage = (Stage) healthContainer.getScene().getWindow();
        stage.close();
    }

    public void openHealthReportMgr() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/HealthConditionReportView.fxml"));
        Parent root = loader.load();
        HealthConditionReportViewController healthConditionReportViewController = loader.getController();
        //Citizen citizen = new Citizen(138,"Jeppe", "moritz","1254789636587");
        healthConditionReportViewController.setCurrentCitizen(GlobalVariables.getSelectedCitizen());
        // functionalReportViewController.displayCitizenReport();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }
}
