package bll;

import be.User;
import dal.db.UsersDAO;

import java.io.IOException;

public class UserManger implements UserInterface {

    UsersDAO usersDAO;
    {
        try{
            usersDAO = new UsersDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public User submitLogin(String username, String password) throws Exception {

        User user = UsersDAO.compareLogins(username, password);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
}
