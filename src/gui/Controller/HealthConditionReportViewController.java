package gui.Controller;

import be.*;
import bll.exceptions.CitizenReportException;
import bll.exceptions.HealthCategoryException;
import gui.Model.ReportModel;
import gui.utils.DisplayMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
public class HealthConditionReportViewController implements Initializable {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBoxContent;

    private ReportModel reportModel;
    Citizen currentCitizen;
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
     * This method is quite straightforward, it search through the
     * HashMap<Integer(Id of main category),List<Pair<HealthCategory, HealthCategory>>>
     * and displays it for the user to see.
     * The creation of this HashMap is available in the HealthConditionDAO class
     * It alse make use of another HashMap<Integer,HealthCategory> to make the connection between the id of the main category
     * and the name of the main category for ease of reading.
     *
     * Note: A nice addition to it could be to create a .doc of pdf report based on these values, but as has not been
     * demanded, we will wait and ask the customer about it during the first sprint review.
     * ***/
    public void displayCitizenReport() {
        HashMap<Integer, List<Pair<HealthCategory, Condition>>> hashMap = null;
        HashMap<Integer,HealthCategory> categoryHashMap = new HashMap<>();
        try {
            hashMap = reportModel.getConditionsFromCitizen(currentCitizen);
            categoryHashMap = reportModel.getAllConditionsMainCategories();
        } catch (CitizenReportException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

        //Parsing through the HashMap
        assert hashMap != null;
        for (Map.Entry<Integer, List<Pair<HealthCategory, Condition>>> entry : hashMap.entrySet()) {
            Integer sid = entry.getKey();
            List<Pair<HealthCategory,Condition>> list = entry.getValue();
            //Creating a Vbox for display purpose as well as a lable associated which hold the name of the Main category
            VBox vBox = new VBox();
            Label mainCat = new Label();
            mainCat.setStyle("-fx-font-weight: bold");
            //We add the Main Category label to the Vbox
            vBox.getChildren().add(mainCat);
            mainCat.setText(categoryHashMap.get(sid).getName());
            //we then parse the subcategories held in the Pair List.
            for (Pair<HealthCategory,Condition> pair : list){
                //we create a TextFlow holding the currently parses subcategory and its value
                //and add it in the Vbox
                //Note that this is only temporary but as don't know what the customer wants displayed,
                TextFlow textFlow;
                Text text = new Text();
                text.setText(pair.getKey().getName()+": \n "
                 //       +pair.getValue().getDescription()
                   //     +"\n "+pair.getValue().getFreeText()
                        +"\n "+pair.getValue().getGoal()
                        +"\n Status : "+pair.getValue().getStatus());


                textFlow = new TextFlow(text);
                vBox.getChildren().add(textFlow);
                
            }
            vBoxContent.getChildren().add(vBox);
        }
        scrollPane.setContent(vBoxContent);
    }
}
