package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Computer {

    /*
     * ATENCION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Agregue los atributos (relaciones) necesarios para satisfacer los
     * requerimientos.
     */

    private String serialNumber;
    private int floor;
    private int row;
    private int column;
    private final List<Incident> incidents;

    public Computer() {
        this(null, 0, 0, 0);
    }

    public Computer(String serialNumber, int floor, int row, int column) {
        this.serialNumber = serialNumber;
        this.floor = floor;
        this.row = row;
        this.column = column;
        this.incidents = new ArrayList<>();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<Incident> getIncidents() {
        return Collections.unmodifiableList(incidents);
    }

    /*
     * ATENCION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * El siguiente metodo esta incompleto.
     * Agregue los parametros y retorno que sean pertinentes.
     * Agregue la logica necesaria.
     */
    public boolean addIncident(Incident incident) {
        Objects.requireNonNull(incident, "incident must not be null");
        incidents.add(incident);
        return true;
    }

}
