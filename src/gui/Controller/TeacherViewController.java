package gui.Controller;

import be.Citizen;
import be.Student;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.exceptions.UserException;
import gui.Model.CitizenModel;
import gui.Model.StudentCitizenRelationShipModel;
import gui.Model.UserModel;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeacherViewController implements Initializable {


    private CitizenModel citizenModel;
    private UserModel studentModel;
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
        try {
            this.citizenModel = CitizenModel.getInstance();
            this.studentModel = UserModel.getInstance();
            this.relationShipModel = new StudentCitizenRelationShipModel();
            ObservableList<Citizen> citizens = citizenModel.getObsListCitizens();
            this.tableViewTemplates.setItems(citizenModel.getTemplatesObs());
            this.tableViewCitizen.setItems(citizens);
            this.tableViewStudent.setItems(studentModel.getObsListStudents());
            this.tableViewFictiveCitizen.setItems(citizens);
            this.tableViewAssignedCit.setItems(relationShipModel.getObsListCit());
            initTables();
            initSpinners();
            createTableListener();
        } catch (CitizenException | UserException | IOException e) {
            DisplayMessage.displayError(e);
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
        this.tableColumnStudentFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.tableColumnStudentLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        //Assigned Fictive Citizens
        this.tableColumnAssignedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableColumnAssignedFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.tableColumnAssignedLastName.setCellValueFactory(new PropertyValueFactory<>("lName"));
    }

    private void initSpinners()
    {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);

        valueFactory.setValue(1);
        spinnerCitizenDuplicate.setValueFactory(valueFactory);
        spinnerTemplateDuplicate.setValueFactory(valueFactory);
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
        if (selectedCitizen != null) {
            Thread deleteCitizenThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        citizenModel.deleteCitizen(selectedCitizen);
                        citizenModel.getTemplatesObs().remove(selectedCitizen);
                    } catch (CitizenException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    }
                }
            });
            deleteCitizenThread.start();
        }
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
        if (selectedCitizen != null) {
            Thread deleteCitizenThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        citizenModel.deleteCitizen(selectedCitizen);
                        citizenModel.getObsListCitizens().remove(selectedCitizen);
                    } catch (CitizenException e) {
                        DisplayMessage.displayError(e);
                        e.printStackTrace();
                    }
                }
            });
            deleteCitizenThread.start();
        }
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

    public void handleDeleteStudent(ActionEvent actionEvent) {
        Student selectedStudent = tableViewStudent.getSelectionModel().getSelectedItem();
        if (selectedStudent!=null)
        {
            Thread deleteStudentThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        studentModel.deleteStudent(selectedStudent);
                        studentModel.getObsListStudents().remove(selectedStudent);
                    } catch (SQLException | UserException e) {
                        DisplayMessage.displayError(e);
                    }
                }
            });
            deleteStudentThread.start();
        }
    }

    private void createTableListener()
    {
        tableViewStudent.setRowFactory( tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    Thread loadCitizensThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Student selectedStudent = row.getItem();
                                relationShipModel.setCitizensOfStudentObs(selectedStudent);
                            } catch (CitizenException | StudentException e) {
                                DisplayMessage.displayError(e);
                                e.printStackTrace();
                            }
                        }
                    });
                    loadCitizensThread.start();
                }
            });
            return row ;
        });
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) throws IOException {
        System.out.println("here");
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

}
