package gui.Controller;

import be.*;
import bll.exceptions.CitizenReportException;
import bll.exceptions.HealthCategoryException;
import gui.Model.ReportModel;
import gui.utils.DisplayMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
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
        int i = 1;
        GridPane mainPane = new GridPane();
        //GridPane.setMargin(mainPane, new Insets(10, 10, 10, 10));
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        Label headerCategory = new Label("Category Name");
        headerCategory.setStyle("-fx-font-size: 16px;");
        headerCategory.setStyle("-fx-font-weight: bold;");
        Label headerImportantNotes = new Label("Important Notes");
        headerImportantNotes.setStyle("-fx-font-size: 16px;");
        headerImportantNotes.setStyle("-fx-font-weight: bold;");
        Label headerStatus = new Label("Status");
        headerStatus.setStyle("-fx-font-size: 16px;");
        headerStatus.setStyle("-fx-font-weight: bold;");
        Label headerAssessment = new Label("Assessment");
        headerAssessment.setStyle("-fx-font-size: 16px;");
        headerAssessment.setStyle("-fx-font-weight: bold;");
        Label headerCitizenGoals = new Label("Citizen Goals");
        headerCitizenGoals.setStyle("-fx-font-size: 16px;");
        headerCitizenGoals.setStyle("-fx-font-weight: bold;");
        Label headerExpectedScore = new Label("Expected Score");
        headerExpectedScore.setStyle("-fx-font-size: 16px;");
        headerExpectedScore.setStyle("-fx-font-weight: bold;");
        Label headerVisitDate = new Label("Visit Date");
        headerVisitDate.setStyle("-fx-font-size: 16px;");
        headerVisitDate.setStyle("-fx-font-weight: bold;");
        Label headerObservations = new Label("Observations");
        headerObservations.setStyle("-fx-font-size: 16px;");
        headerObservations.setStyle("-fx-font-weight: bold;");
        mainPane.add(headerCategory,0,i);
        mainPane.add(headerImportantNotes,1,i);
        mainPane.add(headerStatus,2,i);
        mainPane.add(headerAssessment,3,i);
        mainPane.add(headerCitizenGoals,4,i);
        mainPane.add(headerExpectedScore,5,i);
        mainPane.add(headerVisitDate,6,i);
        mainPane.add(headerObservations,7,i);
        i++;
        //Parsing through the HashMap
        assert hashMap != null;
        for (Map.Entry<Integer, List<Pair<HealthCategory, Condition>>> entry : hashMap.entrySet()) {
            Integer sid = entry.getKey();
            List<Pair<HealthCategory,Condition>> list = entry.getValue();
            //Creating a Vbox for display purpose as well as a lable associated which hold the name of the gui.Main category
            VBox vBox = new VBox();
            Label mainCat = new Label();
            mainCat.setStyle("-fx-font-weight: bold");
            //We add the gui.Main Category label to the Vbox
            vBox.getChildren().add(mainCat);
            mainCat.setText(categoryHashMap.get(sid).getName());
            //we then parse the subcategories held in the Pair List.
            mainPane.add(mainCat,0,i);
            mainPane.add(new Label(""),1,i);
            mainPane.add(new Label(""),2,i);
            mainPane.add(new Label(""),3,i);
            mainPane.add(new Label(""),4,i);
            mainPane.add(new Label(""),5,i);
            mainPane.add(new Label(""),6,i);
            mainPane.add(new Label(""),7,i);
            i++;
            for (Pair<HealthCategory,Condition> pair : list){
                //we create a TextFlow holding the currently parses subcategory and its value
                //and add it in the Vbox
                //Note that this is only temporary but as don't know what the customer wants displayed,
//                TextFlow textFlow;
//                Text text = new Text();
//                text.setText(pair.getKey().getName()+": \n "
//                        +pair.getValue().getImportantNote()
//                       +"\n "+pair.getValue().getAssessement()
//                        +"\n "+pair.getValue().getGoal()
//                        +"\n Status : "+pair.getValue().getStatus());
                Label categoryName = new Label(pair.getKey().getName());
                categoryName.setStyle("-fx-font-weight: bold;");
                categoryName.setStyle("-fx-font-style: italic;");
                TextFlow textFlowImportantNote;
                Text textImportantNote = new Text();
                textImportantNote.setText(pair.getValue().getImportantNote());
                Label status = new Label(""+pair.getValue().getStatus());
                TextFlow textFlowAssessment;
                Text textAssessment = new Text();
                textAssessment.setText(pair.getValue().getAssessement());
                TextFlow textflowCitizenGoal;
                Text textCitizenGoal = new Text();
                textCitizenGoal.setText(pair.getValue().getGoal());
                Label expectedScore = new Label(""+pair.getValue().getExpectedScore());
                Label visitDate = new Label(""+pair.getValue().getVisitDate());
                TextFlow textFlowObservation;
                Text textObservation = new Text();
                textObservation.setText(pair.getValue().getObservation());
                textFlowImportantNote = new TextFlow(textImportantNote);
                textFlowAssessment = new TextFlow(textAssessment);
                textflowCitizenGoal = new TextFlow(textCitizenGoal);
                textFlowObservation = new TextFlow(textObservation);

//                textFlow = new TextFlow(text);
//                vBox.getChildren().add(textFlow);
                mainPane.add(categoryName,0,i);
                mainPane.add(textFlowImportantNote,1,i);
                mainPane.add(status,2,i);
                mainPane.add(textFlowAssessment,3,i);
                mainPane.add(textCitizenGoal,4,i);
                mainPane.add(expectedScore,5,i);
                mainPane.add(visitDate,6,i);
                mainPane.add(textFlowObservation,7,i);
                i++;
            }
            //vBoxContent.getChildren().add(vBox);
        }
        mainPane.setAlignment(Pos.CENTER);
        //  scrollPane.setContent(vBoxContent);
        scrollPane.setContent(mainPane);
    }
}
