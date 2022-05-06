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
        int i = 1;
        GridPane mainPane = new GridPane();
        //GridPane.setMargin(mainPane, new Insets(10, 10, 10, 10));

        mainPane.setHgap(0);
        mainPane.setVgap(0);
        mainPane.setAlignment(Pos.CENTER);
        Label headerCategory = new Label("Category Name");
        headerCategory.setStyle("-fx-font-size: 16px;");
        headerCategory.setStyle("-fx-font-weight: bold;");
        Label headerImportantNotes = new Label("Important Notes");
        headerImportantNotes.setStyle("-fx-font-size: 16px;");
        headerImportantNotes.setStyle("-fx-font-weight: bold;");
        Label headerStatus = new Label("Status");
        headerStatus.setStyle("-fx-font-size: 16px;");
        headerStatus.setStyle("-fx-font-weight: bold;");
        Label headerAssessment = new Label("Performance Assessment");
        headerAssessment.setStyle("-fx-font-size: 16px;");
        headerAssessment.setStyle("-fx-font-weight: bold;");
        Label headerMeaning = new Label("Meaning");
        headerMeaning.setStyle("-fx-font-size: 16px;");
        headerMeaning.setStyle("-fx-font-weight: bold;");
        Label headerCitizenGoals = new Label("Citizen Goals");
        headerCitizenGoals.setStyle("-fx-font-size: 16px;");
        headerCitizenGoals.setStyle("-fx-font-weight: bold;");
        Label headerScore = new Label("Score");
        headerScore.setStyle("-fx-font-size: 16px;");
        headerScore.setStyle("-fx-font-weight: bold;");
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
        mainPane.add(headerMeaning,4,i);
        mainPane.add(headerCitizenGoals,5,i);
        mainPane.add(headerScore,6,i);
        mainPane.add(headerExpectedScore,7,i);
        mainPane.add(headerVisitDate,8,i);
        mainPane.add(headerObservations,9,i);
        i++;
        //Parsing through the HashMap
        assert hashMap != null;
        for (Map.Entry<Integer, List<Pair<AbilityCategory, Ability>>> entry : hashMap.entrySet()) {
            Integer sid = entry.getKey();
            List<Pair<AbilityCategory,Ability>> list = entry.getValue();
            //Creating a Vbox for display purpose as well as a lable associated which hold the name of the gui.Main category
            //  VBox vBox = new VBox();
            Label mainCat = new Label();
            //We add the gui.Main Category label to the Vbox
            mainCat.setStyle("-fx-font-weight: bold");
            //vBox.getChildren().add(mainCat);
            mainCat.setText(categoryHashMap.get(sid).getName());
            mainPane.add(mainCat,0,i);
            mainPane.add(new Label(""),1,i);
            mainPane.add(new Label(""),2,i);
            mainPane.add(new Label(""),3,i);
            mainPane.add(new Label(""),4,i);
            mainPane.add(new Label(""),5,i);
            mainPane.add(new Label(""),6,i);
            mainPane.add(new Label(""),7,i);
            mainPane.add(new Label(""),8,i);
            mainPane.add(new Label(""),9,i);
            i++;
            //we then parse the subcategories held in the Pair List.
            for (Pair<AbilityCategory,Ability> pair : list){
                //we create a label holding the currently parses subcategory and its value
                //and add it in the Vbox
                //Note that this is only temporary but as don't know what the customer wants displayed,
                //we are currently displaying only the score of the ability.
                //if the customer wants the Motivation as well, a TextFlow may need to be used instead of a Label.
//                Label subCat = new Label();
//                subCat.setText(pair.getKey().getName()+" "+pair.getValue().getScore()+" "+pair.getValue().getGoals());

                Label categoryName = new Label(pair.getKey().getName());
                categoryName.setStyle("-fx-font-weight: bold;");
                categoryName.setStyle("-fx-font-style: italic;");
                TextFlow textFlowImportantNote;
                Text textImportantNote = new Text();
                textImportantNote.setText(pair.getValue().getImportantNote());
                Label status = new Label(""+pair.getValue().getStatus());
                Label performance = new Label(""+pair.getValue().getPerformance());
                TextFlow textflowCitizenGoal;
                Text textCitizenGoal = new Text();
                textCitizenGoal.setText(pair.getValue().getGoals());
                Label score = new Label(""+pair.getValue().getScore());
                Label expectedScore = new Label(""+pair.getValue().getExpectedScore());
                Label visitDate = new Label(""+pair.getValue().getVisitDate());
                Label meaning = new Label(""+pair.getValue().getMeaning());
                TextFlow textFlowObservation;
                Text textObservation = new Text();
                textObservation.setText(pair.getValue().getObservation());
                textFlowImportantNote = new TextFlow(textImportantNote);
                textflowCitizenGoal = new TextFlow(textCitizenGoal);
                textFlowObservation = new TextFlow(textObservation);
                mainPane.add(categoryName,0,i);
                mainPane.add(textFlowImportantNote,1,i);
                mainPane.add(status,2,i);
                mainPane.add(performance,3,i);
                mainPane.add(meaning,4,i);
                mainPane.add(textflowCitizenGoal,5,i);
                mainPane.add(score,6,i);
                mainPane.add(expectedScore,7,i);
                mainPane.add(visitDate,8,i);
                mainPane.add(textFlowObservation,9,i);

                i++;

                //     vBox.getChildren().add(subCat);
            }
            //  vBoxContent.getChildren().add(vBox);
        }
        System.out.println(i);
        mainPane.setAlignment(Pos.CENTER);
        scrollPane.setContent(mainPane);
    }
}
