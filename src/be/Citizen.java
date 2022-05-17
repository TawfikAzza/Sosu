package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Citizen {
    private int id;
    private String fName;
    private String lName;
    private String address;
    private int phoneNumber;
    private LocalDate birthDate;
    private boolean isTemplate;
    private int schoolID;
    private int caseID;

    private StringProperty fNameProperty;
    private StringProperty lNameProperty;
    private StringProperty addressProperty;
    private IntegerProperty phoneNumberProperty;

    public Citizen(int id, String fname, String lName) {
        this.id = id;
        this.fName = fname;
        this.lName = lName;

        fNameProperty = new SimpleStringProperty();
        lNameProperty = new SimpleStringProperty();
        addressProperty = new SimpleStringProperty();
        phoneNumberProperty = new SimpleIntegerProperty();

        fNameProperty.set(fname);
        lNameProperty.set(lName);
    }

    public Citizen(int id, String fName, String lName, String address, int phoneNumber, LocalDate birthDate, boolean isTemplate, int schoolID) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.isTemplate = isTemplate;
        this.schoolID = schoolID;

        fNameProperty = new SimpleStringProperty();
        lNameProperty = new SimpleStringProperty();
        addressProperty = new SimpleStringProperty();
        phoneNumberProperty = new SimpleIntegerProperty();

        fNameProperty.set(fName);
        lNameProperty.set(lName);
        addressProperty.set(address);
        phoneNumberProperty.set(phoneNumber);
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(boolean template) {
        isTemplate = template;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
        fNameProperty.set(fName);
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
        lNameProperty.set(lName);
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        addressProperty.set(address);
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
        phoneNumberProperty.set(phoneNumber);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public int getCaseID() {
        return caseID;
    }

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    @Override
    public String toString() {
        return id + " " +fName + " "+lName;
    }


    public StringProperty fNamePropertyProperty() {
        return fNameProperty;
    }

    public StringProperty lNamePropertyProperty() {
        return lNameProperty;
    }


    public StringProperty addressPropertyProperty() {
        return addressProperty;
    }


    public IntegerProperty phoneNumberPropertyProperty() {
        return phoneNumberProperty;
    }

}
