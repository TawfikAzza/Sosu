package gui.utils;

import bll.util.GlobalVariables;
import com.jfoenix.controls.JFXButton;
import gui.Controller.TeacherViewController;
import gui.View.images.MainWindowController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoginLogoutUtil {

    private static Image appIcon = new Image("Images/sosu.png");
    static double x=0;
    static double y = 0;
    static JFXButton duplicateCitizen = new JFXButton("Duplicate citizen");
    static JFXButton assignCitizen = new JFXButton("Assign citizen");
    static JFXButton logOut = new JFXButton("Log out");





    private enum UserType{
        ADMIN(1),TEACHER(2),STUDENT(3);
        private int userType;

        UserType(int userType) {
            this.userType = userType;
        }

        public int getUserType() {
            return userType;
        }

        private static final Map<Integer,UserType> valuesMap = Arrays.stream(UserType.values()).
                collect(Collectors.toMap(UserType::getUserType, Function.identity()));

        public static UserType fromRole(int userRole){
            return valuesMap.get(userRole);
        }
    }
    public static void logout(Event actionEvent) throws IOException {
        closeCurrentWindow(actionEvent);
        GlobalVariables.resetVariables();
        openLoginWindow();

    }

    public static void login(Event actionEvent, int userRole) throws IOException {
        closeCurrentWindow(actionEvent);
        UserType userType = UserType.fromRole(userRole);
        switch (userType){
            case ADMIN -> loginAdmin();
            case STUDENT -> loginStudent();
            case TEACHER -> loginTeacher();
        }
    }


    private static void closeCurrentWindow(Event actionEvent) {
         ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    private static void openLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginLogoutUtil.class.getResource("/gui/View/MainWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage loginWindow = new Stage();
        loginWindow.setScene(scene);
        loginWindow.setWidth(635);
        loginWindow.setHeight(500);
        loginWindow.setTitle("Main window");
        loginWindow.show();
    }

    private static void loginTeacher() throws IOException {
        FXMLLoader teacherWindowLoader = new FXMLLoader();
        Parent root;
        teacherWindowLoader.setLocation(LoginLogoutUtil.class.getResource("/gui/View/TestViewTeacher.fxml"));
        teacherWindowLoader.load();

        FXMLLoader mainWindowLoader = new FXMLLoader();
        mainWindowLoader.setLocation(LoginLogoutUtil.class.getResource("/gui/View/images/MainWindow.fxml"));
        root = mainWindowLoader.load();

        TeacherViewController teacherViewController = teacherWindowLoader.getController();
        MainWindowController mainWindowController = mainWindowLoader.getController();
        final boolean[] isDuplicatePaneOn = {true};
        final int[] counter = {0};

        List<String>allFiles = List.of("src/gui/View/images/duplicate-outline_1.png", "src/gui/View/images/assign.png","src/gui/View/images/logout.png");
        for (String s : allFiles){
            ImageView icon = icon(s);
            mainWindowController.getIconsVBox().getChildren().add(icon);
        }


        AnchorPane duplicatePane = teacherViewController.getDuplicateAnchorPane();
        AnchorPane assignPane = teacherViewController.getAssigningAnchorPane();
        List<AnchorPane>panes = List.of(duplicatePane,assignPane);
        for (AnchorPane anchorPane :panes){
            anchorPane.setPrefHeight(460);
            anchorPane.setPrefWidth(756);
            anchorPane.setLayoutX(15);
        }

        mainWindowController.getMainAnchorPane().getChildren().add(duplicatePane);
        List<JFXButton>allBtns = List.of(duplicateCitizen,assignCitizen,logOut);
        for (JFXButton jfxButton : allBtns){
            mainWindowController.getvBoxBtn().getChildren().add(jfxButton);
            btnShape(jfxButton);
        }


        assignCitizen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                duplicatePane.setVisible(false);
                if (counter[0]==0){
                mainWindowController.getMainAnchorPane().getChildren().add(teacherViewController.getAssigningAnchorPane());
                counter[0]++;}
                else    assignPane.setVisible(true);
                isDuplicatePaneOn[0] = false;
                mainWindowController.translate();
            }
        });

        duplicateCitizen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!isDuplicatePaneOn[0]){
                    duplicatePane.setVisible(true);
                    assignPane.setVisible(false);
                    isDuplicatePaneOn[0]=true;
                    mainWindowController.translate();
                }
            }
        });


        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    private static void loginAdmin() throws IOException {
        Parent root = FXMLLoader.load(LoginLogoutUtil.class.getResource("../View/images/MainWindow.fxml"));
        Scene scene = new Scene(root);
        Stage adminWindow = new Stage();
        adminWindow.setScene(scene);
        adminWindow.setTitle("Admin window");
        adminWindow.setHeight(528);
        adminWindow.setWidth(800);
        adminWindow.resizableProperty().set(false);
        adminWindow.show();
    }

    private static void loginStudent() throws IOException {
        Parent root = FXMLLoader.load(LoginLogoutUtil.class.getResource("../View/images/MainWindow.fxml"));
        Scene scene = new Scene(root);
        Stage studentWindow = new Stage();
        studentWindow.setScene(scene);
        studentWindow.setTitle("Student window window");
        studentWindow.setHeight(431.5);
        studentWindow.setWidth(700);
        studentWindow.show();
    }

    public static ImageView icon(String filePath){
            File file = new File(filePath);
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(46);
            imageView.setFitWidth(32);
            return imageView;
        }

        public static void btnShape(JFXButton jfxButton){
            jfxButton.setButtonType(JFXButton.ButtonType.RAISED);
            jfxButton.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
        }
    }

