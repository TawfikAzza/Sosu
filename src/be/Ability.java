package be;

import java.time.LocalDate;

public class Ability {
    private int id;
    private int score;
    private int categoryID;
    private int citizenID;
    private int status;
    private String goals;
    private int performance;
    private int meaning;
    private int expectedScore;
    private String importantNote;
    private LocalDate visitDate;
    private String observation;

    public Ability(int id, int categoryID,int citizenID, int score,int status) {
        this.id = id;
        this.categoryID=categoryID;
        this.citizenID=citizenID;
        this.score = score;
        this.status=status;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(int citizenID) {
        this.citizenID = citizenID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public int getMeaning() {
        return meaning;
    }

    public void setMeaning(int meaning) {
        this.meaning = meaning;
    }

    public int getExpectedScore() {
        return expectedScore;
    }

    public void setExpectedScore(int expectedScore) {
        this.expectedScore = expectedScore;
    }

    public String getImportantNote() {
        return importantNote;
    }

    public void setImportantNote(String importantNote) {
        this.importantNote = importantNote;
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

    public void setObservation(String observations) {
        this.observation = observations;
    }
}
