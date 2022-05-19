package gui.utils;

import bll.util.GlobalVariables;
import gui.Controller.RootController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoginLogoutUtil {

    private static Image appIcon = new Image("Images/sosu.png");

    public enum UserType{
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
        login(userType);
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

    private static void login(UserType userType) throws IOException {
        RootController controller = new RootController(userType);
        FXMLLoader loader = new FXMLLoader(LoginLogoutUtil.class.getResource("../View/RootLayout.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage teacherWindow = new Stage();
        teacherWindow.setScene(scene);
        teacherWindow.initStyle(StageStyle.UNDECORATED);
        teacherWindow.setTitle(userType.name() + " WINDOW");
        teacherWindow.setHeight(525);
        teacherWindow.setWidth(975);
        teacherWindow.show();
    }

    /*private static void loginTeacher() throws IOException {
        RootController controller = new RootController(UserType.TEACHER);
        FXMLLoader loader = new FXMLLoader(LoginLogoutUtil.class.getResource("../View/RootLayout.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage teacherWindow = new Stage();
        teacherWindow.setScene(scene);
        teacherWindow.getIcons().add(appIcon);
        teacherWindow.setTitle("Teacher window");
        teacherWindow.setHeight(530);
        teacherWindow.setWidth(1050);
        teacherWindow.show();
    }

    private static void loginAdmin() throws IOException {
        RootController controller = new RootController(UserType.ADMIN);
        FXMLLoader loader = new FXMLLoader(LoginLogoutUtil.class.getResource("../View/RootLayout.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage adminWindow = new Stage();
        adminWindow.setScene(scene);
        adminWindow.setTitle("Admin window");
        adminWindow.setHeight(528);
        adminWindow.setWidth(1000);
        //adminWindow.resizableProperty().set(false);
        adminWindow.show();
    }

    private static void loginStudent() throws IOException {
        RootController controller = new RootController(UserType.STUDENT);
        FXMLLoader loader = new FXMLLoader(LoginLogoutUtil.class.getResource("../View/RootLayout.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage studentWindow = new Stage();
        studentWindow.setScene(scene);
        studentWindow.setTitle("Student window window");
        studentWindow.setHeight(530);
        studentWindow.setWidth(1000);
        studentWindow.show();
    }

     */
}
