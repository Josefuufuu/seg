package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SchoolController {

    private static final int DEFAULT_FLOOR_COUNT = 5;
    private static final int DEFAULT_ROWS_PER_FLOOR = 5;
    private static final int DEFAULT_COLUMNS_PER_FLOOR = 5;

    private final int floorCount;
    private final int rowsPerFloor;
    private final int columnsPerFloor;

    private final Computer[][][] computers;
    private final Map<String, Position> computerIndex;

    public SchoolController() {
        this(DEFAULT_FLOOR_COUNT, DEFAULT_ROWS_PER_FLOOR, DEFAULT_COLUMNS_PER_FLOOR);
    }

    public SchoolController(int floorCount, int rowsPerFloor, int columnsPerFloor) {
        this.floorCount = validateDimension("floorCount", floorCount);
        this.rowsPerFloor = validateDimension("rowsPerFloor", rowsPerFloor);
        this.columnsPerFloor = validateDimension("columnsPerFloor", columnsPerFloor);
        this.computers = new Computer[this.floorCount][this.rowsPerFloor][this.columnsPerFloor];
        this.computerIndex = new HashMap<>();
    }

    public Computer agregarComputador(String serialNumber, int floor) {
        String normalizedSerial = normalizeSerial(serialNumber);
        int floorIndex = toFloorIndex(floor);
        ensureSerialIsUnique(normalizedSerial);

        for (int row = 0; row < rowsPerFloor; row++) {
            for (int column = 0; column < columnsPerFloor; column++) {
                if (computers[floorIndex][row][column] == null) {
                    Computer computer = new Computer(normalizedSerial, floorIndex + 1, row + 1, column + 1);
                    computers[floorIndex][row][column] = computer;
                    computerIndex.put(normalizedSerial, new Position(floorIndex, row, column));
                    return computer;
                }
            }
        }

        throw new IllegalStateException("No available slots on floor " + floor);
    }

    public Incident agregarIncidenteEnComputador(String serialNumber, String description) {
        return agregarIncidenteEnComputador(serialNumber, description, null, null, null);
    }

    public Incident agregarIncidenteEnComputador(String serialNumber, String description, Integer floor,
            Integer row, Integer column) {
        Computer computer = findComputer(serialNumber, floor, row, column);
        Incident incident = new Incident(description);
        computer.addIncident(incident);
        return incident;
    }

    public List<Computer> getComputerList() {
        List<Computer> computerList = new ArrayList<>();
        for (int floorIndex = 0; floorIndex < floorCount; floorIndex++) {
            for (int rowIndex = 0; rowIndex < rowsPerFloor; rowIndex++) {
                for (int columnIndex = 0; columnIndex < columnsPerFloor; columnIndex++) {
                    Computer computer = computers[floorIndex][rowIndex][columnIndex];
                    if (computer != null) {
                        computerList.add(computer);
                    }
                }
            }
        }
        return Collections.unmodifiableList(computerList);
    }

    public Optional<Computer> getComputerWithMostIncidents() {
        Computer selected = null;
        int highestIncidentCount = -1;

        for (Computer computer : getComputerList()) {
            int incidentCount = computer.getIncidents().size();
            if (incidentCount > highestIncidentCount
                    || (incidentCount == highestIncidentCount && selected != null
                            && isCandidatePreferred(computer, selected))) {
                selected = computer;
                highestIncidentCount = incidentCount;
            }
        }

        return Optional.ofNullable(selected);
    }

    private Computer findComputer(String serialNumber, Integer floor, Integer row, Integer column) {
        boolean serialProvided = serialNumber != null && !serialNumber.trim().isEmpty();
        if (serialProvided) {
            String normalizedSerial = normalizeSerial(serialNumber);
            Position position = computerIndex.get(normalizedSerial);
            if (position == null) {
                throw new IllegalArgumentException("No computer registered with serial " + normalizedSerial);
            }
            if (floor != null && position.floorIndex != toFloorIndex(floor)) {
                throw new IllegalArgumentException("Serial " + normalizedSerial + " is not located on floor " + floor);
            }
            if (row != null && position.rowIndex != toRowIndex(row)) {
                throw new IllegalArgumentException("Serial " + normalizedSerial + " is not located at row " + row);
            }
            if (column != null && position.columnIndex != toColumnIndex(column)) {
                throw new IllegalArgumentException(
                        "Serial " + normalizedSerial + " is not located at column " + column);
            }
            return computers[position.floorIndex][position.rowIndex][position.columnIndex];
        }

        if (floor == null || row == null || column == null) {
            throw new IllegalArgumentException("Serial number or full location must be provided");
        }

        int floorIndex = toFloorIndex(floor);
        int rowIndex = toRowIndex(row);
        int columnIndex = toColumnIndex(column);
        Computer computer = computers[floorIndex][rowIndex][columnIndex];
        if (computer == null) {
            throw new IllegalArgumentException(
                    String.format("No computer registered at floor %d, row %d, column %d", floor, row, column));
        }
        return computer;
    }

    private boolean isCandidatePreferred(Computer candidate, Computer current) {
        if (candidate == current) {
            return false;
        }
        if (candidate.getFloor() != current.getFloor()) {
            return candidate.getFloor() < current.getFloor();
        }
        if (candidate.getRow() != current.getRow()) {
            return candidate.getRow() < current.getRow();
        }
        if (candidate.getColumn() != current.getColumn()) {
            return candidate.getColumn() < current.getColumn();
        }
        return false;
    }

    private String normalizeSerial(String serialNumber) {
        Objects.requireNonNull(serialNumber, "serialNumber must not be null");
        String trimmed = serialNumber.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("serialNumber must not be blank");
        }
        return trimmed;
    }

    private void ensureSerialIsUnique(String serialNumber) {
        if (computerIndex.containsKey(serialNumber)) {
            throw new IllegalArgumentException("A computer with serial " + serialNumber + " already exists");
        }
    }

    private int validateDimension(String fieldName, int value) {
        switch (Integer.signum(value)) {
            case -1:
            case 0:
                throw new IllegalArgumentException(fieldName + " must be greater than zero");
            default:
                return value;
        }
    }

    private int toFloorIndex(int floor) {
        if (floor < 1 || floor > floorCount) {
            throw new IllegalArgumentException("Floor must be between 1 and " + floorCount);
        }
        return floor - 1;
    }

    private int toRowIndex(int row) {
        if (row < 1 || row > rowsPerFloor) {
            throw new IllegalArgumentException("Row must be between 1 and " + rowsPerFloor);
        }
        return row - 1;
    }

    private int toColumnIndex(int column) {
        if (column < 1 || column > columnsPerFloor) {
            throw new IllegalArgumentException("Column must be between 1 and " + columnsPerFloor);
        }
        return column - 1;
    }

    private static final class Position {
        private final int floorIndex;
        private final int rowIndex;
        private final int columnIndex;

        private Position(int floorIndex, int rowIndex, int columnIndex) {
            this.floorIndex = floorIndex;
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;
        }
    }

}
