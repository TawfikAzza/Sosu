package gui.Controller;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.exceptions.UserException;
import gui.Model.CitizenModel;
import gui.Model.StudentCitizenRelationShipModel;
import gui.Model.StudentModel;
import gui.utils.DisplayMessage;
import gui.utils.LoginLogoutUtil;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class TeacherViewController implements Initializable {


    @FXML
    private TextField fCitizenSearchField;
    @FXML
    private TextField citizenSearchField;
    @FXML
    private AnchorPane duplicateAnchorPane;
    @FXML
    private AnchorPane assigningAnchorPane;

    private CitizenModel citizenModel;
    private StudentModel studentModel;
    private StudentCitizenRelationShipModel relationShipModel;

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
    private TableView<Student> tableViewStudent;
    @FXML
    private TableColumn<Student, String> tableColumnStudentFirstName;
    @FXML
    private TableColumn<Student, String> tableColumnStudentLastName;
    @FXML
    private TableView<Citizen> tableViewFictiveCitizen;
    @FXML
    private TableColumn<Citizen, Integer> tableColumnFictiveCitizenID;
    @FXML
    private TableColumn<Citizen, String> tableColumnFictiveCitizenFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnFictiveCitizenLastName;
    @FXML
    private TableView<Citizen> tableViewAssignedCit;
    @FXML
    private TableColumn<Citizen, Integer> tableColumnAssignedID;
    @FXML
    private TableColumn<Citizen, String> tableColumnAssignedFirstName;
    @FXML
    private TableColumn<Citizen, String> tableColumnAssignedLastName;
    @FXML
    private Spinner<Integer> spinnerTemplateDuplicate;
    @FXML
    private Spinner<Integer> spinnerCitizenDuplicate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTables();
        initTableEvents();
        initBinders();
        //initSpinners();

        try {
            this.citizenModel = CitizenModel.getInstance();
            this.studentModel = StudentModel.getInstance();
            relationShipModel = new StudentCitizenRelationShipModel();

            this.tableViewTemplates.setItems(citizenModel.getTemplatesObs());
            this.tableViewCitizen.setItems(citizenModel.getObsListCitizens());
            this.tableViewFictiveCitizen.setItems(citizenModel.getObsListCitizens());
            this.tableViewStudent.setItems(studentModel.getObsStudents());


        } catch (CitizenException | UserException | IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    private void initBinders() {
        fCitizenSearchField.textProperty().bindBidirectional(citizenSearchField.textProperty());
    }

    private void initTableEvents() {
        tableViewStudent.setRowFactory(param -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> Optional.ofNullable(row.getItem()).ifPresent(rowData-> {
                if(event.getClickCount() == 2 && rowData.equals(tableViewStudent.getSelectionModel().getSelectedItem())){
                    showAssignedCitizens(row);
                }
            }));
            return row;
        });
    }


    /*
        Method called after double clicking a row in student table
     */
    private void showAssignedCitizens(TableRow<Student> row) {
        Student selectedStudent = row.getItem();
        try {
            tableViewAssignedCit.setItems(relationShipModel.getCitizensOfStudent(selectedStudent));
        } catch (StudentException | CitizenException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

    }

    private void initTables() {
        //Select multiple students from TV
        this.tableViewStudent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Templates
        this.tableColumnTemplatesFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnTemplatesLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //Citizens on template page
        this.tableColumnCitizenID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnCitizenFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnCitizenLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //Fictive Citizens on student page
        this.tableColumnFictiveCitizenID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnFictiveCitizenFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnFictiveCitizenLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //Students
        this.tableColumnStudentFirstName.setCellValueFactory(new PropertyValueFactory<>("fNameProperty"));
        this.tableColumnStudentLastName.setCellValueFactory(new PropertyValueFactory<>("lNameProperty"));

        //Assigned Fictive Citizens
        this.tableColumnAssignedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnAssignedFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnAssignedLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));
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

    public void handleCreateCitFromTemp(ActionEvent actionEvent) {
        Citizen template = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (template !=null)
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

    public void handleCreateTempFromCit(ActionEvent actionEvent) {
        Citizen citizen = tableViewCitizen.getSelectionModel().getSelectedItem();
        if (citizen !=null)
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

    public void handleCreateTemplate(ActionEvent actionEvent) {
        try {
            openCitizenForm(false,null);
        } catch (IOException e) {
            DisplayMessage.displayError(e);
        }
    }

    public void handleEditTemplate(ActionEvent actionEvent) {
        Citizen citizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (citizen != null)
        {
            try {
                openCitizenForm(true,citizen);
            } catch (IOException e) {
                DisplayMessage.displayError(e);
            }
        }
    }

    public void handleDeleteTemplate(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (selectedCitizen == null)
            return;

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


    public void handleDuplicateTemplate(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        int amount = spinnerTemplateDuplicate.getValue();
        if (selectedCitizen != null) {
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
    }

    public void handleEditCitizen(ActionEvent actionEvent) {
        Citizen citizen = tableViewCitizen.getSelectionModel().getSelectedItem();
        if (citizen != null)
        {
            try {
                openCitizenForm(true,citizen);
            } catch (IOException e) {
                DisplayMessage.displayError(e);
            }
        }
    }

    public void handleDeleteCitizen(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewCitizen.getSelectionModel().getSelectedItem();
        if (selectedCitizen == null)
            return;

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

    public void handleDuplicateCitizen(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewCitizen.getSelectionModel().getSelectedItem();
        int amount = spinnerCitizenDuplicate.getValue();
        if (selectedCitizen != null) {
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
    }

    public void handleAssignClick(ActionEvent actionEvent) {
        ArrayList<Student> students = new ArrayList<>(tableViewStudent.getSelectionModel().getSelectedItems());
        Citizen templateCitizen = tableViewFictiveCitizen.getSelectionModel().getSelectedItem();

        if (templateCitizen!=null)
        {
            Thread assignCitizenThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        relationShipModel.assignCitizensToStudents(templateCitizen, students);
                    } catch (CitizenException e) {
                        DisplayMessage.displayError(e);
                    }
                }
            });
            assignCitizenThread.start();
        }
    }

    public void handleRemoveCitClick(ActionEvent actionEvent) {
        Student student = tableViewStudent.getSelectionModel().getSelectedItem();
        Citizen toRemove = tableViewAssignedCit.getSelectionModel().getSelectedItem();

        if (student!=null && toRemove!=null)
        {
            Thread removeRelationThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        relationShipModel.removeRelation(student, toRemove);
                    } catch (CitizenException e) {
                        DisplayMessage.displayError(e);
                    }
                }
            });
            removeRelationThread.start();
        }
    }

    public void handleCreateStudent(ActionEvent actionEvent) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
            root = loader.load();

            NewEditUserController newEditUserController = loader.getController();
            newEditUserController.newStudent();

            Stage stage = new Stage();
            stage.setTitle("New Student");
            stage.setScene(new Scene(root));
            stage.show();
        }
         catch(IOException e){
             DisplayMessage.displayError(e);
            }
    }

    public void handleEditStudent(ActionEvent actionEvent) {
        try {

            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
            root = loader.load();

            NewEditUserController newEditUserController = loader.getController();
            newEditUserController.editStudent(studentModel.getStudentInformation(tableViewStudent.getSelectionModel().getSelectedItem()));

            Stage stage = new Stage();
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException | SQLException e) {
            DisplayMessage.displayError(e);
        }
    }

    public void handleDeleteStudent(ActionEvent actionEvent){
        ObservableList<Student> selectedStudents = tableViewStudent.getSelectionModel().getSelectedItems();
        Student selectedStudent = selectedStudents.get(0);
        if (selectedStudents.size()>1 || selectedStudents.get(0) == null)
            return;
        ButtonType response = DisplayMessage.displayConfirmation("Confirmation","You are about to delete this student");

        if (response!=ButtonType.OK)
            return;

        Thread deleteStudentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    studentModel.deleteStudent(selectedStudent);
                } catch (StudentException e) {
                    DisplayMessage.displayError(e);
                }
            }
        });
        deleteStudentThread.start();
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) throws IOException {
        LoginLogoutUtil.logout(actionEvent);
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

    @FXML
    private void handleSearchStudent(KeyEvent keyEvent) {
        String query = ((TextField) keyEvent.getSource()).getText().toLowerCase(Locale.ROOT);
        studentModel.getObsStudents().setPredicate(student -> {
            if (query.isEmpty() || query.isBlank())
                return true;
            if (student.getFirstName().toLowerCase().contains(query) || student.getLastName().toLowerCase().contains(query)
                || String.valueOf(student.getPhoneNumber()).contains(query))
                return true;
            return false;
        });
    }

    @FXML
    private void handleSearchFictiveCitizen(KeyEvent keyEvent) {
        String query = ((TextField) keyEvent.getSource()).getText().toLowerCase(Locale.ROOT);
        citizenModel.getObsListCitizens().setPredicate(citizen -> {
            if (query.isEmpty() || query.isBlank())
                return true;
            if (citizen.toString().toLowerCase().contains(query))
                return true;
            return false;
        });

    }

    @FXML
    private void handleSearchAssignedCitizen(KeyEvent keyEvent) {
        String query = ((TextField) keyEvent.getSource()).getText().toLowerCase(Locale.ROOT);
        relationShipModel.getObsListCit();
    }

    public AnchorPane getDuplicateAnchorPane() {
        return duplicateAnchorPane;
    }

    public AnchorPane getAssigningAnchorPane() {
        return assigningAnchorPane;
    }

}
