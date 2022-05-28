package gui.Controller;

import be.Citizen;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.util.GlobalVariables;
import gui.Model.StudentCitizenRelationShipModel;
import gui.utils.DisplayMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private TableColumn<Citizen, String> addressColumn;
    @FXML
    private TableColumn<Citizen, Integer> phoneColumn;
    @FXML
    private TableColumn<Citizen, LocalDate> bDateColumn;
    @FXML
    private TextField citizenSearchField;
    @FXML
    private AnchorPane citizenInfoControls;
    @FXML
    private TableView<Citizen> citizenTableview;
    @FXML
    private TableColumn<Citizen, String> fnameColumn;
    @FXML
    private TableColumn<Citizen, String> lnameColumn;

    private StudentCitizenRelationShipModel studentCitizenRelationShipModel;


    public StudentViewController() {
        try {
            studentCitizenRelationShipModel = new StudentCitizenRelationShipModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTableCitizen() {
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        bDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        try {
            studentCitizenRelationShipModel.setCitizensOfStudentObs(GlobalVariables.getCurrentStudent());
            citizenTableview.setItems(studentCitizenRelationShipModel.getObsListCit());
        } catch (StudentException | CitizenException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    public void displayCitizen() {
        if(citizenTableview.getSelectionModel().getSelectedIndex()==-1)
            return;
        GlobalVariables.setSelectedCitizen(citizenTableview.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTableCitizen();
        bindSizes();
        try {
            loadCitizenInfoControls();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bindSizes() {
        AnchorPane textFieldParent = ((AnchorPane) citizenSearchField.getParent());
        AnchorPane.setRightAnchor(citizenSearchField,0.0);
        AnchorPane.setLeftAnchor(citizenSearchField,0.0);
        AnchorPane.setBottomAnchor(citizenSearchField,0.0);
        AnchorPane.setTopAnchor(citizenSearchField,0.0);
        textFieldParent.getChildren().setAll(citizenSearchField);
    }

    private void loadCitizenInfoControls() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/CitizenInfoControls.fxml"));
        VBox controlsParent = (VBox) root;
        AnchorPane.setBottomAnchor(controlsParent,0.0);
        AnchorPane.setTopAnchor(controlsParent,0.0);
        AnchorPane.setLeftAnchor(controlsParent,0.0);
        AnchorPane.setRightAnchor(controlsParent,0.0);
        citizenInfoControls.getChildren().setAll(controlsParent);
    }

    @FXML
    private void searchCitizen(KeyEvent keyEvent){
        String query = ((TextField) keyEvent.getSource()).getText().toLowerCase(Locale.ROOT);
        studentCitizenRelationShipModel.getObsListCit().setPredicate(citizen -> {
            if (query.isEmpty() || query.isBlank())
                return true;
            if (citizen.toString().toLowerCase().contains(query))
                return true;
            return false;
        });
    }
}
