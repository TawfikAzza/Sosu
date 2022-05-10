package be;

import java.time.LocalDate;

public abstract class Measurement {
    private int citizenId;
    private float floatMeasurement;
    private int measurement;
    private LocalDate observation_date;
    private int height;
    private float weight;

    public Measurement(int citizenId, float floatMeasurement, LocalDate date_observation){
        this.citizenId=citizenId;
        this.floatMeasurement=floatMeasurement;
        observation_date=date_observation;
    }

    public Measurement(int citizenId, float weight, int height,LocalDate date_observation){
        this.citizenId=citizenId;
        this.height=height;
        this.weight=weight;
        observation_date=date_observation;
    }

    public Measurement(int citizenId, int measurement, LocalDate date_observation){
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

    public LocalDate getObservation_date() {
        return observation_date;
    }

    public void setObservation_date(LocalDate observation_date) {
        this.observation_date = observation_date;
    }
}
