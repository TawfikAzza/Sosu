package gui.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginLogoutUtil {

    public enum UserType{
        ADMIN(1),TEACHER(2),STUDENT(3);
        private int userType;
        UserType(int userType) {
            this.userType = userType;
        }
    }

    public static void logout(ActionEvent actionEvent) throws IOException {
        closeCurrentWindow(actionEvent);
        openLoginWindow();
    }

    public static void login(UserType userType) throws IOException {
        //closeCurrentWindow(actionEvent);
        switch (userType){
            case ADMIN -> loginAdmin();
            case STUDENT -> loginStudent();
            case TEACHER -> loginTeacher();
        }
    }


    private static void closeCurrentWindow(ActionEvent actionEvent) {
        Button actionSource = ((Button) actionEvent.getSource());
        ((Stage) actionSource.getParent().getScene().getWindow()).close();
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
        teacherWindow.show();
    }

    private static void loginAdmin() throws IOException {
        Parent root = FXMLLoader.load(LoginLogoutUtil.class.getResource("../View/AdminView.fxml"));
        Scene scene = new Scene(root);
        Stage adminWindow = new Stage();
        adminWindow.setScene(scene);
        adminWindow.show();
    }

    private static void loginStudent() throws IOException {
        Parent root = FXMLLoader.load(LoginLogoutUtil.class.getResource("../View/StudentMenuView.fxml"));
        Scene scene = new Scene(root);
        Stage studentWindow = new Stage();
        studentWindow.setScene(scene);
        studentWindow.show();
    }
}
