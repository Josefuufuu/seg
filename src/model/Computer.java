package model;

import java.util.ArrayList;

public class Computer {


    
    private String serialNumber;
    private boolean nextWindow;
    private ArrayList<Incident> incidents;

    
    public Computer(String serialNumber, boolean nextWindow) {
        this.serialNumber = serialNumber;
        this.nextWindow = nextWindow;
        this.incidents = new ArrayList<>();
    }

   
    public String getSerialNumber() {
        return serialNumber;
    }

    public boolean isNextWindow() {
        return nextWindow;
    }

    public ArrayList<Incident> getIncidents() {
        return incidents;
    }

    /**
     * Añade un incidente con la descripcion dada.
     * @param description descripción del incidente
     */
    public void addIncident(String description) {
        incidents.add(new Incident(description));
    }

    @Override
    public String toString() {
        return "Computer{" +
                "serial='" + serialNumber + '\'' +
                ", nextWindow=" + nextWindow +
                ", incidents=" + incidents.size() +
                '}';
    }

}
