package bll;

import be.User;

public interface UserInterface {

    User submitLogin(String username, String password) throws Exception;
}
