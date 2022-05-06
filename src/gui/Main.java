package gui;
import be.Student;
import be.Teacher;
import be.User;
import gui.Controller.*;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

    private User user;

    private Stage primaryStage;
    private StringProperty layoutChosen;


    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        layoutChosen = new SimpleStringProperty("");
        initLogin();
    }

    public void initLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/MainWindow.fxml"));
        Parent root = loader.load();

        MainController mainController =loader.getController();
        mainController.setMainApp(this);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setWidth(635);
        primaryStage.setHeight(429);
        primaryStage.setTitle("Main window");
        primaryStage.show();
    }

    public void initRootLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class
                .getResource("/gui/View/RootLayout.fxml"));
        AnchorPane rootLayout = loader.load();

        if (layoutChosen.get().equals("admin")) {
            FXMLLoader loaderAdmin = new FXMLLoader();
            loaderAdmin.setLocation(getClass().getResource("/gui/View/AdminView.fxml"));
            TabPane adminDisplay = loaderAdmin.load();

            AdminViewController adminViewController = loaderAdmin.getController();
            adminViewController.setMain(this);

            adminDisplay.prefHeightProperty().bind(rootLayout.heightProperty());
            adminDisplay.prefWidthProperty().bind(rootLayout.widthProperty());
            rootLayout.getChildren().add(adminDisplay);
            primaryStage.setTitle("Admin window");
            primaryStage.setHeight(398);
            primaryStage.setWidth(755);
        }
        if (layoutChosen.get().equals("teacher")) {
            FXMLLoader loaderTeacher = new FXMLLoader();
            loaderTeacher.setLocation(getClass().getResource("/gui/View/TeacherWindow.fxml"));
            GridPane teacherDisplay = loaderTeacher.load();

            TeacherWindowController teacherWindowController = loaderTeacher.getController();
            teacherWindowController.setCurrentTeacher((Teacher) user);
            teacherWindowController.loadData();

            teacherDisplay.prefHeightProperty().bind(rootLayout.heightProperty());
            teacherDisplay.prefWidthProperty().bind(rootLayout.widthProperty());
            rootLayout.getChildren().add(teacherDisplay);
            primaryStage.setTitle("Teacher window");
            primaryStage.setHeight(400);
            primaryStage.setWidth(791);
        }

        if (layoutChosen.get().equals("student")) {
            FXMLLoader loaderStudent = new FXMLLoader();
            loaderStudent.setLocation(getClass().getResource("/gui/View/StudentMenuView.fxml"));
            TabPane studentDisplay = loaderStudent.load();

            StudentMenuViewController studentMenuViewController = loaderStudent.getController();
            studentMenuViewController.setCurrentStudent((Student) user);
            studentMenuViewController.upadateTableCitizen();

            studentDisplay.prefHeightProperty().bind(rootLayout.heightProperty());
            studentDisplay.prefWidthProperty().bind(rootLayout.widthProperty());
            rootLayout.getChildren().add(studentDisplay);
            primaryStage.setTitle("Student window window");
            primaryStage.setHeight(431.5);
            primaryStage.setWidth(700);
        }

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

        RootLayoutController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }

    public void setLayoutChosen(String layoutChosen) {
        this.layoutChosen.set(layoutChosen);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
