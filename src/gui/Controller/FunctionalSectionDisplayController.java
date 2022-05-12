package gui.Controller;

import be.AbilityCategory;
import be.Citizen;

import bll.exceptions.AbilityCategoryException;
import bll.exceptions.HealthCategoryException;
import bll.util.GlobalVariables;
import gui.Model.CategoryModel;
import gui.utils.DisplayMessage;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
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
 * ****/
public class FunctionalSectionDisplayController implements Initializable {
    @FXML
    private Accordion functionalAbilityContainer;

    private final double LISTVIEW_HEIGHT_VALUE = 23.75;
    private CategoryModel categoryModel;
    final int ROW_HEIGHT = 24;
    private final Citizen currentCitizen;
    public FunctionalSectionDisplayController() throws HealthCategoryException {
        categoryModel = new CategoryModel();
        currentCitizen  = GlobalVariables.getSelectedCitizen();
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
            //We first start by first getting the gui.Main categories with the subacategories associated
            //via the subcategories list present in the gui.Main category class.
            abilityCategories = categoryModel.getAbilityCategories();
            for (AbilityCategory abilityCategory : abilityCategories) {
                //we then parse the main categories and associate the name of said category with the
                //TitlePane of the Accordion Node of the View.
                TitledPane titledPane = new TitledPane();
                titledPane.setText(abilityCategory.getName());
                VBox vBox=new VBox();
                //As each gui.Main categories possess a different number/type of subcategories, we create a List
                //of subcategories which we will use for each gui.Main categories, we reinitialize the ListView
                //variable at each loop in order for it to contain only the subactegories of the gui.Main
                //Category currently parsed for later display when we add the total of listView to the TitlePane
                // Which in turn will be added to the root (Accordion in this case).
                ListView<AbilityCategory> subCategoryList = new ListView<>();
                subCategoryList.getStyleClass().add("category-list");
                //As each ListView must possess the ability to display the condition/ability input page when it is clicked on,
                // We add a method which will be called when the click does happen.
                //for this purpose, we use the built-in method of most of the JavaFX nodes setOnMouseClicked and attribute it
                // the reference of the current subcategory, the method take two arguments (the currently selected item in the
                //ListView, as well as the current citizen, as we need both the id of these object to target the right
                //COndition/ability in the database.
                //Note that it wouold not have been possible to do it this way if the relationship between the subcategories
                //report were one to many, but as it one to one, we can do it this way.
                subCategoryList.setOnMouseClicked(e-> {
                    if(subCategoryList.getSelectionModel().getSelectedIndex() == -1)
                        return;
                    openConditionReport(subCategoryList.getSelectionModel().getSelectedItem(),currentCitizen);
                });
                //We then fill the ListView with the subcategories of the currently parsed gui.Main category.
                for (AbilityCategory subCategory : abilityCategory.getSubCategories()) {
                    subCategoryList.getItems().add(subCategory);
                }
                //We add the bulk to the titledPane
                subCategoryList.setPrefHeight(subCategoryList.getItems().size() * LISTVIEW_HEIGHT_VALUE);
                vBox.getChildren().add(subCategoryList);
                titledPane.setContent(vBox);
                //subCategoryList.maxHeight(200);
                titledPane.setMaxHeight(vBox.getHeight());


                //We add the TitledPane to the Accordion node.
                functionalAbilityContainer.getPanes().add(titledPane);


            }
            
        } catch (AbilityCategoryException e) {
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

    private void openConditionReport(AbilityCategory abilityCategory, Citizen citizen)  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/AbilityReportView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            DisplayMessage.displayError(e);
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
        stage.getIcons().add(new Image("sosu.png"));
        stage.setTitle(abilityCategory.getName());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) functionalAbilityContainer.getScene().getWindow();
        stage.close();
    }

    public void openFAReportMgr(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/FunctionalReportView.fxml"));
        Parent root = loader.load();
        FunctionalReportViewController functionalReportViewController = loader.getController();
        //Citizen citizen = new Citizen(138,"Jeppe", "moritz","1254789636587");
        functionalReportViewController.setCurrentCitizen(GlobalVariables.getSelectedCitizen());
        // functionalReportViewController.displayCitizenReport();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.show();
    }
}
