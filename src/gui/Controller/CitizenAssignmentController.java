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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class CitizenAssignmentController implements Initializable {

    private StudentModel studentModel;
    private StudentCitizenRelationShipModel relationShipModel;
    private CitizenModel citizenModel;


    @FXML
    private TextField fCitizenSearchField;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTables();
        initTableEvents();

        try {
            this.citizenModel = CitizenModel.getInstance();
            this.studentModel = StudentModel.getInstance();
            relationShipModel = new StudentCitizenRelationShipModel();

            this.tableViewFictiveCitizen.setItems(citizenModel.getObsListCitizens());
            this.tableViewStudent.setItems(studentModel.getObsStudents());
        } catch (IOException | UserException | CitizenException e) {
            e.printStackTrace();
        }
    }

    private void initTables() {
        //Select multiple students from TV
        this.tableViewStudent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
            relationShipModel.setCitizensOfStudentObs(selectedStudent);
            tableViewAssignedCit.setItems(relationShipModel.getObsListCit());
        } catch (StudentException | CitizenException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }

    }


    public void handleCreateStudent(ActionEvent actionEvent) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
            NewEditUserController newEditUserController = new NewEditUserController(LoginLogoutUtil.UserType.STUDENT);
            loader.setController(newEditUserController);
            root = loader.load();

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

            NewEditUserController newEditUserController = new NewEditUserController(LoginLogoutUtil.UserType.STUDENT);
            loader.setController(newEditUserController);

            root = loader.load();

            newEditUserController.isNewUser(false,studentModel.getStudentInformation(tableViewStudent.getSelectionModel().getSelectedItem()));

            Stage stage = new Stage();
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException  e) {
            DisplayMessage.displayError(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteStudent(ActionEvent actionEvent){
        ObservableList<Student> selectedStudents = tableViewStudent.getSelectionModel().getSelectedItems();
        if (selectedStudents==null || selectedStudents.size()!=1){
            DisplayMessage.displayMessage("Select a single student to delete");
            return;
        }
        Student selectedStudent = selectedStudents.get(0);

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

    public void handleAssignClick(ActionEvent actionEvent) {
        ArrayList<Student> students = new ArrayList<>(tableViewStudent.getSelectionModel().getSelectedItems());
        Citizen templateCitizen = tableViewFictiveCitizen.getSelectionModel().getSelectedItem();

        if (templateCitizen==null){
            DisplayMessage.displayMessage("Select a citizen to assign");
            return;
        }
        if (students.isEmpty()){
            DisplayMessage.displayMessage("Select 1 or more students to assign to");
            return;
        }
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

        if (student==null){
            DisplayMessage.displayMessage("Select a student, whose citizen you want to remove");
            return;
        }
        if (toRemove==null){
            DisplayMessage.displayMessage(
                    "Select a citizen who you wish to remove from this student" +
                    "\n" +
                    "To view a students citizens double click a student");
        }

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
        relationShipModel.getObsListCit().setPredicate(citizen -> {
            if (query.isEmpty() || query.isBlank())
                return true;
            if (citizen.toString().toLowerCase().contains(query))
                return true;
            return false;
        });
    }
}
