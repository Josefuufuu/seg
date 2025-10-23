package model;

public class SchoolController {

    /*
     * Atributos (relaciones) necesarios
     */
    public static final int FLOORS = 5;
    public static final int COL = 10;
    public static final int HOURMAXSUPPORT = 100;

    private Computer[][] computersMatrix;

    public SchoolController() {
        computersMatrix = new Computer[FLOORS][COL];
    }

    /**
     * Agrega un computador en el piso indicado.
     * @param serial número serial (único)
     * @param nextWindow si está junto a ventana
     * @param floor índice de piso (0..FLOORS-1)
     * @return mensaje de estado
     */
    public String agregarComputador(String serial, boolean nextWindow, int floor) {
        if (serial == null || serial.isEmpty()) {
            return "Serial inválido.";
        }
        if (floor < 0 || floor >= FLOORS) {
            return "Piso inválido. Debe estar entre 1 y " + FLOORS;
        }

        // Validar que no exista serial repetido
        for (int i = 0; i < FLOORS; i++) {
            for (int j = 0; j < COL; j++) {
                Computer c = computersMatrix[i][j];
                if (c != null && c.getSerialNumber().equalsIgnoreCase(serial)) {
                    return "Ya existe un computador con ese número serial.";
                }
            }
        }

        // Buscar primera columna disponible en el piso
        for (int j = 0; j < COL; j++) {
            if (computersMatrix[floor][j] == null) {
                computersMatrix[floor][j] = new Computer(serial, nextWindow);
                return "Computador agregado en piso " + (floor + 1) + ", columna " + (j + 1);
            }
        }

        return "No hay espacio disponible en el piso " + (floor + 1);
    }

    /**
     * Agrega un incidente a un computador identificado por serial.
     * @param serial serial del computador
     * @param descripcion descripción del incidente
     * @return mensaje de estado
     */
    public String agregarIncidenteEnComputador(String serial, String descripcion) {
        if (serial == null || serial.isEmpty()) {
            return "Serial inválido.";
        }
        for (int i = 0; i < FLOORS; i++) {
            for (int j = 0; j < COL; j++) {
                Computer c = computersMatrix[i][j];
                if (c != null && c.getSerialNumber().equalsIgnoreCase(serial)) {
                    c.addIncident(descripcion);
                    return "Incidente registrado en computador (piso " + (i + 1) + ", columna " + (j + 1) + ").";
                }
            }
        }
        return "No se encontró un computador con el serial indicado.";
    }

    /**
     * Calcula el computador con más incidentes.
     * @return String con la información del computador con más incidentes o mensaje si no hay computadores.
     */
    public String consultarComputadorConMasIncidentes() {
        Computer maxComp = null;
        int maxInc = -1;
        int pisoMax = -1;
        int colMax = -1;

        for (int i = 0; i < FLOORS; i++) {
            for (int j = 0; j < COL; j++) {
                Computer c = computersMatrix[i][j];
                if (c != null) {
                    int size = c.getIncidents().size();
                    if (size > maxInc) {
                        maxInc = size;
                        maxComp = c;
                        pisoMax = i;
                        colMax = j;
                    }
                }
            }
        }

        if (maxComp == null) {
            return "No hay computadores registrados.";
        }

        return "Computador con más incidentes: serial=" + maxComp.getSerialNumber()
                + ", incidentes=" + maxInc
                + " (piso " + (pisoMax + 1) + ", columna " + (colMax + 1) + ").";
    }

    /**
     * Imprime un listado simple de computadores por piso y columna.
     * Método solicitado como void getComputerList().
     */
    public void getComputerList() {
        System.out.println("Listado de computadores:");
        for (int i = 0; i < FLOORS; i++) {
            System.out.print("Piso " + (i + 1) + ": ");
            boolean any = false;
            for (int j = 0; j < COL; j++) {
                Computer c = computersMatrix[i][j];
                if (c != null) {
                    System.out.print("[" + (j + 1) + ":" + c.getSerialNumber() + "(" + c.getIncidents().size() + ")] ");
                    any = true;
                }
            }
            if (!any) {
                System.out.print(" (sin computadores)");
            }
            System.out.println();
        }
    }

}
