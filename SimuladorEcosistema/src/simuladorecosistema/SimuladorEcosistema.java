package simuladorecosistema;

import java.util.Scanner;

public class SimuladorEcosistema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ecosistema ecosistema = configurarSimulacion(scanner);

        System.out.println("\n¡Ecosistema inicializado correctamente!");
        ecosistema.mostrarEstado();

        // El Integrante 4 conectará aquí el loop principal de turnos y el reporte final
    }

    public static Ecosistema configurarSimulacion(Scanner scanner) {
        int cantPlantas = 0;
        int cantConejos = 0;
        int cantLobos = 0;
        Clima climaInicial = Clima.SOLEADO;
        int cantTurnos = 0;
        boolean confirmado = false;

        System.out.println("---");
        System.out.println("---");
        System.out.println("---");

        while (!confirmado) {
            cantPlantas = pedirEnteroEnRango(scanner, 
                    "Ingrese cantidad inicial de Plantas (5 - 30): ", 5, 30);

            cantConejos = pedirEnteroEnRango(scanner, 
                    "Ingrese cantidad inicial de Conejos (2 - 15): ", 2, 15);

            cantLobos = pedirEnteroEnRango(scanner, 
                    "Ingrese cantidad inicial de Lobos (1 - 5): ", 1, 5);

            climaInicial = pedirClimaInicial(scanner);

            cantTurnos = pedirEnteroEnRango(scanner, 
                    "Ingrese cantidad de turnos totales (10 - 50): ", 10, 50);

            System.out.println("\n--- RESUMEN DE CONFIGURACIÓN INICIAL ---");
            System.out.println("• Plantas iniciales : " + cantPlantas);
            System.out.println("• Conejos iniciales : " + cantConejos);
            System.out.println("• Lobos iniciales   : " + cantLobos);
            System.out.println("• Clima inicial     : " + climaInicial.getNombre());
            System.out.println("• Turnos a simular  : " + cantTurnos);
            System.out.print("¿Confirma esta configuración para iniciar? (S/N): ");

            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (respuesta.equals("S") || respuesta.equals("SI") || respuesta.equals("SÍ")) {
                confirmado = true;
            } else {
                System.out.println("\nReiniciando la configuración...\n");
            }
        }

        // Crear el ecosistema y poblar las entidades iniciales con energía aleatoria
        Ecosistema eco = new Ecosistema(climaInicial, cantTurnos);

        for (int i = 0; i < cantPlantas; i++) {
            double energiaInicial = 45.0 + (Math.random() * 30.0); // Entre 45 y 75
            eco.agregarEntidad("planta", energiaInicial);
        }

        for (int i = 0; i < cantConejos; i++) {
            double energiaInicial = 50.0 + (Math.random() * 25.0); // Entre 50 y 75
            eco.agregarEntidad("conejo", energiaInicial);
        }

        for (int i = 0; i < cantLobos; i++) {
            double energiaInicial = 55.0 + (Math.random() * 25.0); // Entre 55 y 80
            eco.agregarEntidad("lobo", energiaInicial);
        }

        return eco;
    }

    public static int pedirEnteroEnRango(Scanner scanner, String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.println("[Error] El valor debe estar entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error] Entrada inválida. Debe ingresar un número entero.");
            }
        }
    }

    public static Clima pedirClimaInicial(Scanner scanner) {
        while (true) {
            System.out.println("Seleccione el clima inicial:");
            System.out.println("  1. Soleado");
            System.out.println("  2. Lluvioso");
            System.out.println("  3. Sequía");
            System.out.println("  4. Invierno");
            System.out.print("Opción (1 - 4): ");

            String entrada = scanner.nextLine().trim().toLowerCase();
            switch (entrada) {
                case "1":
                case "soleado":
                    return Clima.SOLEADO;
                case "2":
                case "lluvioso":
                    return Clima.LLUVIOSO;
                case "3":
                case "sequia":
                case "sequía":
                    return Clima.SEQUIA;
                case "4":
                case "invierno":
                    return Clima.INVIERNO;
                default:
                    System.out.println("[Error] Opción de clima inválida. Elija entre 1 y 4.");
            }
        }
    }
}

