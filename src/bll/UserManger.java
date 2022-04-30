package bll;

import be.User;
import dal.db.UsersDAO;

public class UserManger implements UserInterface {


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
