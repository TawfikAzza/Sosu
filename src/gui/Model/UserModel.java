package gui.Model;


import be.User;
import bll.UserInterface;
import bll.UserManger;

public class UserModel {


    private static UserModel single_instance = null;

    public static UserModel getInstance()
    {
        if (single_instance == null)
            single_instance = new UserModel();

        return single_instance;
    }

    private UserInterface userManger;

    public UserModel(){
        this.userManger = new UserManger();

    }

    public User submitLogin (String username , String password) throws Exception {
        return this.userManger.submitLogin(username,password);
    }



}
