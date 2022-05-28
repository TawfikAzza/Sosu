package gui.Model;

import be.User;
import bll.UserManager;
import bll.exceptions.UserException;
import gui.Controller.ManageUsersController;

import java.io.IOException;
import java.sql.SQLException;

public class LogInModel {
    UserManager userManager;
    public LogInModel () throws SQLException, IOException, UserException {
        userManager = new UserManager();
    }
    public User submitLogin (String username , String password) throws Exception {
        return this.userManager.submitLogin(username,password);
    }
}
