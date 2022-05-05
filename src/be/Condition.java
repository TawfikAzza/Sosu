package be;

import java.time.LocalDate;

public class Condition {
    private int id;

    private String importantNote;
    private String assessement;
    private String goal;
    private int citizenID;
    private int categoryID;
    private int status;
    private int expectedScore;
    private LocalDate visitDate;
    private String observation;

    public Condition(int id, int categoryID , int citizenID, String importantNote, int status,String assessement, String goal) {
        this.id = id;
        this.categoryID = categoryID;
        this.citizenID = citizenID;
        this.importantNote = importantNote;
        this.status=status;
        this.assessement = assessement;
        this.goal=goal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getImportantNote() {
        return importantNote;
    }

    public void setImportantNote(String importantNote) {
        this.importantNote = importantNote;
    }

    public String getAssessement() {
        return assessement;
    }

    public void setAssessement(String assessement) {
        this.assessement = assessement;
    }

    public int getExpectedScore() {
        return expectedScore;
    }

    public void setExpectedScore(int expectedScore) {
        this.expectedScore = expectedScore;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(int citizenID) {
        this.citizenID = citizenID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int catgoryID) {
        this.categoryID = catgoryID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
