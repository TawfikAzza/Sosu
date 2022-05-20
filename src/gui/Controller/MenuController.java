package gui.Controller;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public abstract class MenuController {

    private VBox iconBox;
    private VBox btnBox;
    private AnchorPane mainPane;

    public MenuController(AnchorPane mainPane)
    {
        this.mainPane = mainPane;
    }

    public VBox getIconBox() {
        return iconBox;
    }

    public VBox getBtnBox() {
        return btnBox;
    }

}
