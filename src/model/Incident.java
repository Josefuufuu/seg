package model;

import java.time.LocalDate;

public class Incident {

    private LocalDate dateReport;
    private String description;
    private boolean solution;
    private int solutionHours;

    public Incident(String description) {
        this.dateReport = LocalDate.now();
        this.description = description;
        this.solution = false;
        this.solutionHours = 0;
    }

    public LocalDate getDateReport() {
        return dateReport;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSolution() {
        return solution;
    }

    public int getSolutionHours() {
        return solutionHours;
    }

    public void solve(int hours) {
        this.solution = true;
        this.solutionHours = hours;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "date=" + dateReport +
                ", desc='" + description + '\'' +
                ", solved=" + solution +
                '}';
    }
}
