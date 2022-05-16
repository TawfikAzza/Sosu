package gui.utils;

import bll.util.GlobalVariables;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoginLogoutUtil {

    private static Image appIcon = new Image("sosu.png");

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
        Parent root = FXMLLoader.load(LoginLogoutUtil.class.getResource("../View/TeacherView.fxml"));
        Scene scene = new Scene(root);
        Stage teacherWindow = new Stage();
        teacherWindow.setScene(scene);
        teacherWindow.getIcons().add(appIcon);
        teacherWindow.setTitle("Teacher window");
        teacherWindow.setHeight(480);
        teacherWindow.setWidth(880);
        teacherWindow.show();
    }

    private static void loginAdmin() throws IOException {
        Parent root = FXMLLoader.load(LoginLogoutUtil.class.getResource("../View/AdminView.fxml"));
        Scene scene = new Scene(root);
        Stage adminWindow = new Stage();
        adminWindow.setScene(scene);
        adminWindow.setTitle("Admin window");
        adminWindow.setHeight(398);
        adminWindow.setWidth(755);
        adminWindow.show();
    }

    private static void loginStudent() throws IOException {
        Parent root = FXMLLoader.load(LoginLogoutUtil.class.getResource("../View/StudentMenuView.fxml"));
        Scene scene = new Scene(root);
        Stage studentWindow = new Stage();
        studentWindow.setScene(scene);
        studentWindow.setTitle("Student window window");
        studentWindow.setHeight(431.5);
        studentWindow.setWidth(700);
        studentWindow.show();
    }
}
