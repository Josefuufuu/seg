package ui;

import java.util.Scanner;
import model.SchoolController;


public class SchoolApp {

    /*
     * Atributos para conectar con el modelo
     */
    private Scanner input;
    private SchoolController controller;

    public static void main(String[] args) {
        SchoolApp ui = new SchoolApp();
        ui.menu();
    }

    // Constructor
    public SchoolApp() {
        input = new Scanner(System.in);
        controller = new SchoolController(); // inicializa el controlador
    }

    public void menu() {

        System.out.println("Bienvenido a Computaricemos");

        int option = 0;
        do {
            System.out.println("\nMenu Principal");
            System.out.println("--------------------------------------------------------");
            System.out.println("Digite alguna de las siguientes opciones");
            System.out.println("1) Registrar computador");
            System.out.println("2) Registrar incidente en computador");
            System.out.println("3) Consultar el computador con más incidentes");
            System.out.println("4) Listar todos los computadores (por piso y columna)");
            System.out.println("0) Salir del sistema");
            option = input.nextInt();
            input.nextLine(); // limpiar buffer

            switch (option) {
                case 1:
                    registrarComputador();
                    break;
                case 2:
                    registrarIncidenteEnComputador();
                    break;
                case 3:
                    consultarComputadorConMasIncidentes();
                    break;
                case 4:
                    controller.getComputerList(); // imprimir listado
                    break;
                case 0:
                    System.out.println("\nGracias por usar nuestros servicios. Adios!");
                    break;
                default:
                    System.out.println("\nOpcion invalida. Intente nuevamente.");
                    break;
            }

        } while (option != 0);

    }

    // Lee los datos y pide al controlador agregar un computador
    public void registrarComputador() {
        System.out.print("Ingrese número serial: ");
        String serial = input.nextLine().trim();

        System.out.print("¿Está junto a la ventana? (true/false): ");
        boolean nextWindow = false;
        try {
            nextWindow = input.nextBoolean();
        } catch (Exception e) {
            System.out.println("Entrada inválida, se asume false.");
            input.nextLine();
        }
        input.nextLine(); // limpiar buffer

        System.out.print("Ingrese número de piso (1 a " + SchoolController.FLOORS + "): ");
        int piso = input.nextInt();
        input.nextLine();

        String resultado = controller.agregarComputador(serial, nextWindow, piso - 1); // usuario ingresa 1..FLOORS
        System.out.println(resultado);
    }

    // Lee datos para registrar incidente y lo delega al controlador
    public void registrarIncidenteEnComputador() {
        System.out.print("Ingrese número serial del computador: ");
        String serial = input.nextLine().trim();

        System.out.print("Describa el incidente: ");
        String descripcion = input.nextLine();

        String resultado = controller.agregarIncidenteEnComputador(serial, descripcion);
        System.out.println(resultado);
    }

    // Pide al controlador la info del computador con más incidentes y la muestra
    public void consultarComputadorConMasIncidentes() {
        String resultado = controller.consultarComputadorConMasIncidentes();
        System.out.println(resultado);
    }

}
