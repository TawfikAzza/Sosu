package be;

import java.util.Date;

public class Observation {
    private final float measurement;
    private final Date observation_date;

    public Observation(float measurement,Date observation_date){
        this.measurement=measurement;
        this.observation_date=observation_date;
    }

    public float getMeasurement() {
        return measurement;
    }

    public Date getObservation_date() {
        return observation_date;
    }
}
