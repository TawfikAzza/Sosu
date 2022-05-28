package gui.Controller;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.util.GlobalVariables;
import gui.Model.CitizenModel;
import gui.utils.DisplayMessage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class TemplateController implements Initializable {

    @FXML
    private AnchorPane citizenInfoControls;
    private CitizenModel citizenModel;

    @FXML
    private TableView<Citizen> tableViewTemplates;
    @FXML
    private TableColumn<Citizen, String> tableColumnTemplatesFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnTemplatesLastName;
    @FXML
    private TableView<Citizen> tableViewCitizen;
    @FXML
    private TableColumn<Citizen, Integer> tableColumnCitizenID;
    @FXML
    private TableColumn<Citizen, String> tableColumnCitizenFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnCitizenLastName;
    @FXML
    private Spinner<Integer> spinnerTemplateDuplicate;
    @FXML
    private Spinner<Integer> spinnerCitizenDuplicate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTables();
        initSpinners();
        try {
            loadCitizenInfoControls();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.citizenModel = CitizenModel.getInstance();
            this.tableViewTemplates.setItems(citizenModel.getTemplatesObs());
            this.tableViewCitizen.setItems(citizenModel.getObsListCitizens());
            
            citizenModel.getObsListCitizens().setPredicate(citizen -> true);
        } catch (CitizenException e) {
            DisplayMessage.displayError(e);
        }

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

    private void initTables()
    {
        //Templates
        this.tableColumnTemplatesFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnTemplatesLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //Citizens on template page
        this.tableColumnCitizenID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnCitizenFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnCitizenLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        selectCitizenAction();
    }

    private void selectCitizenAction() {
        tableViewCitizen.setRowFactory(param -> {
            TableRow<Citizen> row = new TableRow<>();
            row.setOnMouseClicked(event -> Optional.ofNullable(row.getItem()).ifPresent(rowData-> {
                GlobalVariables.setSelectedCitizen(row.getItem());
            }));
            return row;
        });

        tableViewTemplates.setRowFactory(param -> {
            TableRow<Citizen> row = new TableRow<>();
            row.setOnMouseClicked(event -> Optional.ofNullable(row.getItem()).ifPresent(rowData-> {
                GlobalVariables.setSelectedCitizen(row.getItem());
            }));
            return row;
        });
    }

    private void initSpinners()
    {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);

        SpinnerValueFactory<Integer> valueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);

        valueFactory.setValue(1);
        spinnerCitizenDuplicate.setValueFactory(valueFactory);
        spinnerTemplateDuplicate.setValueFactory(valueFactory2);
    }

    @FXML
    private void handleCreateCitFromTemp(ActionEvent actionEvent) {
        Citizen template = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (template ==null){
            DisplayMessage.displayMessage("Select a template to convert");
            return;
        }
        {
            Thread copyTemplateThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        citizenModel.copyTempToCit(template);
                    } catch (CitizenException e) {
                        DisplayMessage.displayError(e);
                    }
                }
            });
            copyTemplateThread.start();
        }
    }

    @FXML
    private void handleCreateTempFromCit(ActionEvent actionEvent) {
        Citizen citizen = tableViewCitizen.getSelectionModel().getSelectedItem();
        if (citizen ==null){
            DisplayMessage.displayMessage("Select a citizen to convert");
            return;
        }
        {
            Thread copyCitizenThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        citizenModel.copyCitToTemp(citizen);
                    } catch (CitizenException e) {
                        DisplayMessage.displayError(e);
                    }
                }
            });
            copyCitizenThread.start();
        }
    }

    @FXML
    private void handleCreateTemplate(ActionEvent actionEvent) {
        try {
            openCitizenForm(false,null);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    @FXML
    private void handleEditTemplate(ActionEvent actionEvent) {
        Citizen citizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (citizen == null){
            DisplayMessage.displayMessage("Select a template to edit");
            return;
        }
        try {
            openCitizenForm(true,citizen);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    @FXML
    private void handleDeleteTemplate(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (selectedCitizen == null){
            DisplayMessage.displayMessage("Select a template to delete");
            return;
        }

        ButtonType response = DisplayMessage.displayConfirmation("Confirmation","You are about to delete this template");

        if (response!=ButtonType.OK)
            return;

        Thread deleteCitizenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    citizenModel.deleteCitizen(selectedCitizen);
                    ObservableList<Citizen> underlyingList = ((ObservableList<Citizen>) citizenModel.getTemplatesObs().getSource());
                    underlyingList.remove(selectedCitizen);
                } catch (CitizenException e) {
                    DisplayMessage.displayError(e);
                    e.printStackTrace();
                }
            }
        });
        deleteCitizenThread.start();
    }

    @FXML
    private void handleDuplicateTemplate(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        int amount = spinnerTemplateDuplicate.getValue();
        if (selectedCitizen == null) {
            DisplayMessage.displayMessage("Select a template to duplicate");
            return;
        }
        Thread duplicateTemplateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    citizenModel.duplicateCitizen(selectedCitizen, amount, true);
                } catch (CitizenException e) {
                    DisplayMessage.displayError(e);
                    e.printStackTrace();
                }
            }
        });
        duplicateTemplateThread.start();

    }

    @FXML
    private void handleEditCitizen(ActionEvent actionEvent) {
        Citizen citizen = tableViewCitizen.getSelectionModel().getSelectedItem();
        if (citizen == null) {
            DisplayMessage.displayMessage("Select a citizen to edit");
            return;
        }
        try {
            openCitizenForm(true,citizen);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }

    }

    @FXML
    private void handleDeleteCitizen(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewCitizen.getSelectionModel().getSelectedItem();
        if (selectedCitizen == null){
            DisplayMessage.displayMessage("Select a citizen to delete");
            return;
        }

        ButtonType response = DisplayMessage.displayConfirmation("Confirmation","You are about to delete this Citizen");

        if (response!=ButtonType.OK)
            return;

        Thread deleteCitizenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    citizenModel.deleteCitizen(selectedCitizen);
                } catch (CitizenException e) {
                    DisplayMessage.displayError(e);
                    e.printStackTrace();
                }
            }
        });
        deleteCitizenThread.start();
    }

    @FXML
    private void handleDuplicateCitizen(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewCitizen.getSelectionModel().getSelectedItem();
        int amount = spinnerCitizenDuplicate.getValue();
        if (selectedCitizen == null) {
            DisplayMessage.displayMessage("Select a citizen to duplicate");
            return;
        }
        Thread duplicateCitizenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    citizenModel.duplicateCitizen(selectedCitizen, amount, false);
                } catch (CitizenException e) {
                    DisplayMessage.displayError(e);
                    e.printStackTrace();
                }
            }
        });
        duplicateCitizenThread.start();
    }


    @FXML
    private void handleSearchTemplate(KeyEvent keyEvent) {
        //Get search query and ignore case by setting to lowercase
        String query = ((TextField) keyEvent.getSource()).getText().toLowerCase(Locale.ROOT);
        //set predicate for each citizen in the list
        citizenModel.getTemplatesObs().setPredicate(citizen -> {
            //If the search query is empty then show citizen
            if (query.isEmpty() || query.isBlank())
                return true;

            //If to string contains query then show citizen
            if (citizen.toString().toLowerCase().contains(query))
                return true;
            //If no case was true then dont show citizen
            return false;
        });
    }

    @FXML
    private void handleSearchCitizen(KeyEvent keyEvent) {
        String query = ((TextField) keyEvent.getSource()).getText().toLowerCase(Locale.ROOT);
        citizenModel.getObsListCitizens().setPredicate(citizen -> {
            if (query.isEmpty() || query.isBlank())
                return true;
            if (citizen.toString().toLowerCase().contains(query))
                return true;
            return false;
        });
    }

    private void openCitizenForm(boolean isEditing, Citizen citizen) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/CitizenFormView.fxml"));
        Parent root = loader.load();

        CitizenFormController formController = loader.getController();
        formController.setCurrentSchoolId();
        if (isEditing) {
            formController.setCitizenToEdit(citizen);
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
