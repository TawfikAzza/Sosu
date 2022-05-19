package gui.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class TeacherMenuController {
    @FXML
    private VBox iconBox;
    @FXML
    private VBox btnBox;

    public VBox getIconBox() {
        return iconBox;
    }

    public VBox getBtnBox() {
        return btnBox;
    }
}
