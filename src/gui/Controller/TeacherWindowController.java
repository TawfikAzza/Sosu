package gui.Controller;

import gui.Model.TeacherModel;

import java.io.IOException;

public class TeacherWindowController {

    TeacherModel model;

    public TeacherWindowController() throws IOException {
        this.model = new TeacherModel();
    }
}
