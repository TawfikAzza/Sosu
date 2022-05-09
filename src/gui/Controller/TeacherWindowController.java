package gui.Controller;

import be.Citizen;
import be.Student;
import be.Teacher;
import bll.exceptions.CitizenException;
import bll.exceptions.StudentException;
import bll.exceptions.UserException;
import gui.Model.*;
import gui.utils.DisplayMessage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class TeacherWindowController implements Initializable {


    private TeacherModel model;
    private CitizenModel citizenModel;
    private UserModel userModel;
    private StudentCitizenRelationShipModel relationShipModel;
    private Teacher currentTeacher;
    private ObservableList<Citizen> citizens;
    private ObservableList<Student> students;

    @FXML
    private TextField filterStudents;
    @FXML
    private TextField filter;
    @FXML
    private TableView<Citizen> tableViewAssignments;
    @FXML
    private TableColumn<Citizen, String> fNameAssignmentsColumn;
    @FXML
    private TableColumn<Citizen, String> lNameAssignmentsColumn;
    @FXML
    private TableView<Student> tableViewStudents;
    @FXML
    private TableColumn<Student, String> fNameStudentsColumn;
    @FXML
    private TableColumn<Student, String> lNameStudentsColumn;
    @FXML
    private Button newStudentBtn,deleteStudentBtn,editStudentBtn;
    @FXML
    private TableView<Citizen> tableViewTemplates;
    @FXML
    private TableColumn<Citizen, String> fNameTemplateTableColumn;
    @FXML
    private TableColumn<Citizen, String> lNameTemplateTableColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.model = new TeacherModel();
            this.userModel = new UserModel();
            this.relationShipModel = new StudentCitizenRelationShipModel();
            this.citizenModel = new CitizenModel();
            this.citizens = FXCollections.observableArrayList();
            createFilterListener();
            createStudentFilterListener();
            createTableListener();

        } catch (IOException | CitizenException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();;
        }
    }


    private void initTables() {
        //citizens
        this.fNameTemplateTableColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.lNameTemplateTableColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //students
        this.fNameStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lNameStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        //assignments
        this.fNameAssignmentsColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        this.lNameAssignmentsColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));
    }

    @FXML
    private void handleActionDuplicate(ActionEvent actionEvent) {
        Citizen template = tableViewTemplates.getSelectionModel().getSelectedItem();
        Student student = tableViewStudents.getSelectionModel().getSelectedItem();
        ArrayList<Student> students = new ArrayList<>();

        students.add(student);

        try {
            model.copyCitizenToDB(template, students);
        } catch (CitizenException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    public void setAdminView() {
        newStudentBtn.setDisable(true);
        editStudentBtn.setDisable(true);
        deleteStudentBtn.setDisable(true);
    }

    @FXML
    private void handleCreateCitizen(ActionEvent actionEvent) throws IOException {
        openCitizenForm(false,null);
    }


    @FXML
    private void handleEditCitizen(ActionEvent actionEvent) throws IOException {
        Citizen citizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (citizen == null)
            return;
        openCitizenForm(true,citizen);
    }

    private void openCitizenForm(boolean isEditing, Citizen citizen) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/CitizenFormView.fxml"));
        Parent root = loader.load();

        CitizenFormController formController = loader.getController();
        formController.setController(this);
        formController.setCurrentSchoolId(currentTeacher);
        if (isEditing) {
            formController.setCitizenToEdit(citizen);
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleDeleteCitizen(ActionEvent actionEvent) {
        Citizen selectedCitizen = tableViewTemplates.getSelectionModel().getSelectedItem();
        if (selectedCitizen==null)
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
                citizens.remove(selectedCitizen);
            }
        });
        deleteCitizenThread.start();
    }

    @FXML
    private void handleCreateStudent(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
        root = loader.load();

        NewEditUserController newEditUserController = loader.getController();
        newEditUserController.newStudent();
        newEditUserController.setTeacherController(this);
        newEditUserController.setSchoolComboBox(currentTeacher);

        Stage stage = new Stage();
        stage.setTitle("New Student");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleEditStudent(ActionEvent actionEvent) throws IOException, SQLException {
        if (tableViewStudents.getSelectionModel().getSelectedItem()!=null){
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/NewEditUser.fxml"));
            root = loader.load();

            NewEditUserController newEditUserController = loader.getController();
            newEditUserController.editStudent(userModel.getStudentInformation(tableViewStudents.getSelectionModel().getSelectedItem()));
            newEditUserController.setTeacherController(this);
            newEditUserController.setSchoolComboBox(currentTeacher);

            Stage stage = new Stage();
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    private void handleDeleteStudent(ActionEvent actionEvent) {
        Student selectedStudent = tableViewStudents.getSelectionModel().getSelectedItem();
        if (selectedStudent==null)
            return;

        Thread deleteStudentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userModel.deleteStudent(selectedStudent);
                } catch (SQLException e) {
                    DisplayMessage.displayError(e);
                    e.printStackTrace();
                }
                students.remove(selectedStudent);
            }
        });
        deleteStudentThread.start();
    }

    private void createTableListener()
    {
        tableViewStudents.setRowFactory( tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    try {
                        Student selectedStudent = row.getItem();
                        ObservableList<Citizen> citizens = relationShipModel.getCitizensOfStudent(selectedStudent);
                        tableViewAssignments.setItems(citizens);
                    } catch (StudentException | CitizenException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }
    

    public void setCurrentTeacher(Teacher currentTeacher) {
        this.currentTeacher = currentTeacher;
    }

    public void loadData(){

        try { //You should only be able to get citizens from relevant school!
        citizens = model.getTemplates(currentTeacher);
        //Y should only be able to get students from relevant school!
        students = userModel.getStudents(currentTeacher);
        this.tableViewTemplates.setItems(citizens);
        this.tableViewStudents.setItems(students);
        this.tableViewStudents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        this.initTables();
        } catch (CitizenException | UserException  e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();;
        }
    }

    private void createFilterListener()
    {
        filter.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchCitizen();
            }
        });
    }

    private void searchCitizen() {
        ObservableList<Citizen> searchedCitizens = FXCollections.observableArrayList();

        for (Citizen citizen:citizens) {
            if(citizen.getFName().toLowerCase(Locale.ROOT).contains(filter.getText().toLowerCase(Locale.ROOT))
                    || citizen.getLName().toLowerCase(Locale.ROOT).contains(filter.getText().toLowerCase(Locale.ROOT))
                    || citizen.getCprNumber().toLowerCase(Locale.ROOT).contains(filter.getText().toLowerCase(Locale.ROOT))) {
                searchedCitizens.add(citizen);
            }

        }
        tableViewTemplates.setItems(searchedCitizens);

    }
    private void createStudentFilterListener()
    {
        filterStudents.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchStudent();
            }
        });
    }

    private void searchStudent() {
        ObservableList<Student> searchedStudents = FXCollections.observableArrayList();

        for (Student student:students) {
            if(student.getFirstName().toLowerCase(Locale.ROOT).contains(filterStudents.getText().toLowerCase(Locale.ROOT))
                    || student.getLastName().toLowerCase(Locale.ROOT).contains(filterStudents.getText().toLowerCase(Locale.ROOT))) {
                searchedStudents.add(student);
            }
        }
        tableViewStudents.setItems(searchedStudents);
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public TableView<Student> getStudentTV() {
        return tableViewStudents;
    }

    public TableView<Citizen> getTableViewTemplates() {
        return tableViewTemplates;
    }
}
