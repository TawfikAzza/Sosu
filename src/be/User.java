package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class User {
    private int id;
    private int schoolId;
    private String schoolName;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private String email;
    private int phoneNumber;
    private int roleID;

    private StringProperty fNameProperty;
    private StringProperty lNameProperty ;
    private StringProperty userNameProperty;
    private StringProperty passwordProperty;
    private StringProperty emailProperty;
    private IntegerProperty phoneNumberProperty;

    public User (){}


    public User (int id, String firstName, String lastName,String userName,String passWord,String email,int phoneNumber){
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName= userName;
        this.passWord = passWord;
        this.email= email;
        this.phoneNumber= phoneNumber;

        fNameProperty= new SimpleStringProperty(firstName);
        lNameProperty= new SimpleStringProperty(lastName);
        userNameProperty= new SimpleStringProperty(userName);
        passwordProperty= new SimpleStringProperty(passWord);
        emailProperty = new SimpleStringProperty(email);
        phoneNumberProperty = new SimpleIntegerProperty(phoneNumber);
    }

    public User(int id, String userName, String passWord, String email, int roleID) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.roleID = roleID;

    }

    public User(int id, int roleID) {
        this.id = id;
        this.roleID = roleID;

    }

    public User(int id, int schoolId, String firstName, String lastName) {
        this.id = id;
        this.schoolId = schoolId;
        this.firstName = firstName;
        this.lastName = lastName;

        fNameProperty= new SimpleStringProperty(firstName);
        lNameProperty= new SimpleStringProperty(lastName);
    }

    public User (int id, String firstName, String lastName,String userName,String passWord,String email,int phoneNumber,int schoolId){
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName= userName;
        this.passWord = passWord;
        this.email= email;
        this.phoneNumber= phoneNumber;
        this.schoolId=schoolId;

        fNameProperty= new SimpleStringProperty(firstName);
        lNameProperty= new SimpleStringProperty(lastName);
    }

    public User(int id, String firstName, String lastName) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;

        fNameProperty= new SimpleStringProperty(firstName);
        lNameProperty= new SimpleStringProperty(lastName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        fNameProperty.set(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        lNameProperty.setValue(lastName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        userNameProperty.setValue(userName);
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
        passwordProperty.setValue(passWord);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        emailProperty.setValue(email);
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }


    public StringProperty fNamePropertyProperty() {
        return fNameProperty;
    }

    public StringProperty lNamePropertyProperty() {
        return lNameProperty;
    }


    public String getUserNameProperty() {
        return userNameProperty.get();
    }

    public StringProperty userNamePropertyProperty() {
        return userNameProperty;
    }

    public String getPasswordProperty() {
        return passwordProperty.get();
    }

    public StringProperty passwordPropertyProperty() {
        return passwordProperty;
    }

    public String getEmailProperty() {
        return emailProperty.get();
    }

    public StringProperty emailPropertyProperty() {
        return emailProperty;
    }

    public int getPhoneNumberProperty() {
        return phoneNumberProperty.get();
    }

    public IntegerProperty phoneNumberPropertyProperty() {
        return phoneNumberProperty;
    }
}
