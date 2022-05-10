package be;

import java.time.LocalDate;
import java.util.Date;

public abstract class Measurement {
    private int citizenId;
    private float floatMeasurement;
    private int measurement;
    private Date observation_date;
    private int height;
    private float weight;

    public Measurement(int citizenId, float floatMeasurement, Date date_observation){
        this.citizenId=citizenId;
        this.floatMeasurement=floatMeasurement;
        observation_date=date_observation;
    }

    public Measurement(int citizenId, float weight, int height,Date date_observation){
        this.citizenId=citizenId;
        this.height=height;
        this.weight=weight;
        observation_date=date_observation;
    }

    public Measurement(int citizenId, int measurement, Date date_observation){
        this.citizenId=citizenId;
        this.measurement=measurement;
        observation_date=date_observation;
    }

    public int getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(int citizenId) {
        this.citizenId = citizenId;
    }

    public float getFloatMeasurement() {
        return floatMeasurement;
    }

    public void setFloatMeasurement(float floatMeasurement) {
        this.floatMeasurement = floatMeasurement;
    }

    public int getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int measurement) {
        this.measurement = measurement;
    }

    public Date getObservation_date() {
        return observation_date;
    }

    public void setObservation_date(Date observation_date) {
        this.observation_date = observation_date;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
