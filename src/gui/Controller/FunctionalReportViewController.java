package gui.Controller;

import be.Ability;
import be.AbilityCategory;
import be.Citizen;
import bll.exceptions.CitizenReportException;
import bll.exceptions.HealthCategoryException;
import gui.Model.ReportModel;
import gui.utils.DisplayMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
/**
 * Author : Tawfik
 * This controller is in charge of displaying a report retracing all the values the nurse in training/teacher
 * inputted for this specific citizen
 * The nature of the project as well as the data to be managed forces it to be a dynamically generated page.
 *
 * ***/
public class FunctionalReportViewController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBoxContent;

    private ReportModel reportModel;
    private Citizen currentCitizen;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reportModel = new ReportModel();
        } catch (HealthCategoryException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }
    /**
     * Method which sets the citizen to be displayed.
     * */
    public void setCurrentCitizen(Citizen currentCitizen) {
        this.currentCitizen = currentCitizen;
        displayCitizenReport();
    }
    /**
     * This method is quite straightforward, it search through the HashMap<Integer(Id of main category),List<Pair<AbilityCategory, Ability>>>
     * and displays it for the user to see.
     * The creation of this HashMap is available in the FunctionalAbilityDAO class
     * It alse make use of another HashMap<Integer,AbilityCategory> to make the connection between the id of the main category
     * and the name of the main category for ease of reading.
     *
     * Note: A nice addition to it could be to create a .doc of pdf report based on these values, but as has not been
     * demanded, we will wait and ask the customer about it during the first sprint review.
     * ***/

    public void displayCitizenReport() {
        HashMap<Integer, List<Pair<AbilityCategory, Ability>>> hashMap = null;
        HashMap<Integer,AbilityCategory> categoryHashMap = new HashMap<>();
        try {
            hashMap = reportModel.getAbilitiesFromCitizen(currentCitizen);
            categoryHashMap = reportModel.getAllFAMainCategories();
        } catch (CitizenReportException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

        //Parsing through the HashMap
        assert hashMap != null;
        for (Map.Entry<Integer, List<Pair<AbilityCategory, Ability>>> entry : hashMap.entrySet()) {
            Integer sid = entry.getKey();
            List<Pair<AbilityCategory,Ability>> list = entry.getValue();
            //Creating a Vbox for display purpose as well as a lable associated which hold the name of the Main category
            VBox vBox = new VBox();
            Label mainCat = new Label();
            //We add the Main Category label to the Vbox
            mainCat.setStyle("-fx-font-weight: bold");
            vBox.getChildren().add(mainCat);
            mainCat.setText(categoryHashMap.get(sid).getName());
            //we then parse the subcategories held in the Pair List.
            for (Pair<AbilityCategory,Ability> pair : list){
                //we create a label holding the currently parses subcategory and its value
                //and add it in the Vbox
                //Note that this is only temporary but as don't know what the customer wants displayed,
                //we are currently displaying only the score of the ability.
                //if the customer wants the Motivation as well, a TextFlow may need to be used instead of a Label.
                Label subCat = new Label();
                subCat.setText(pair.getKey().getName()+" "+pair.getValue().getScore()+" "+pair.getValue().getGoals());
                vBox.getChildren().add(subCat);
            }
            vBoxContent.getChildren().add(vBox);
        }
        scrollPane.setContent(vBoxContent);
    }
}
