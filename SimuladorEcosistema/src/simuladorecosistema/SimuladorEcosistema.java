package simuladorecosistema;

import java.util.Scanner;

public class SimuladorEcosistema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ecosistema ecosistema = configurarSimulacion(scanner);

        System.out.println("\n!Ecosistema inicializado correctamente!");
        ecosistema.mostrarEstado();

        // Loop principal de simulacion (Integrante 4)
        ejecutarSimulacion(scanner, ecosistema);

        // Reporte estadistico al finalizar
        ecosistema.generarReporteFinal();
    }

    public static void ejecutarSimulacion(Scanner scanner, Ecosistema eco) {
        while (eco.getTurnoActual() < eco.getTurnosTotales() && !eco.ecosistemaColapsado()) {
            System.out.print("\n>>> Presione Enter para continuar al siguiente turno...");
            scanner.nextLine();

            eco.procesarTurno();

            // Si el ecosistema colapso o llego al ultimo turno, cortamos el ciclo
            if (eco.ecosistemaColapsado() || eco.getTurnoActual() >= eco.getTurnosTotales()) {
                break;
            }

            // Cada 3 turnos se habilita el menu de intervencion del jugador
            if (eco.getTurnoActual() % 3 == 0) {
                menuIntervencion(scanner, eco);
            }
        }
    }

    public static void menuIntervencion(Scanner scanner, Ecosistema eco) {
        boolean intervencionFinalizada = false;

        while (!intervencionFinalizada) {
            System.out.println("\n=== INTERVENCION (cada 3 turnos) ===");
            System.out.println("1. Cambiar clima (actual: " + eco.getClimaActual().getNombre() + ")");
            System.out.println("2. Agregar entidad (Lobos creados: " + eco.getTotalLobosCreados() + "/5)");
            System.out.println("3. Solo avanzar");

            int opcion = pedirEnteroEnRango(scanner, "Opcion: ", 1, 3);

            switch (opcion) {
                case 1:
                    Clima nuevoClima = pedirClimaInicial(scanner);
                    if (confirmarAccion(scanner, "xConfirma cambiar el clima a " + nuevoClima.getNombre() + "? (S/N): ")) {
                        eco.cambiarClima(nuevoClima);
                        System.out.println("Se cambio el clima a " + nuevoClima.getNombre() + ".");
                        intervencionFinalizada = true;
                    }
                    break;

                case 2:
                    System.out.print("xQue entidad agregar? (planta/conejo/lobo): ");
                    String tipo = scanner.nextLine().trim().toLowerCase();

                    if (!tipo.equals("planta") && !tipo.equals("conejo") && !tipo.equals("lobo")) {
                        System.out.println("[Error] Debe escribir 'planta', 'conejo' o 'lobo'.");
                        break;
                    }

                    if (tipo.equals("lobo") && !eco.puedeAgregarMasLobos()) {
                        System.out.println("[!] No se pueden agregar mas de 5 lobos en total en toda la simulacion.");
                        break;
                    }

                    if (confirmarAccion(scanner, "xConfirma agregar una entidad de tipo '" + tipo + "'? (S/N): ")) {
                        if (eco.agregarEntidad(tipo)) {
                            System.out.println("Se agrego una nueva entidad ('" + tipo + "') al ecosistema.");
                            intervencionFinalizada = true;
                        }
                    }
                    break;

                case 3:
                    if (confirmarAccion(scanner, "xConfirma avanzar sin intervenir? (S/N): ")) {
                        System.out.println("Avanzando sin cambios...");
                        intervencionFinalizada = true;
                    }
                    break;
            }
        }
    }

    public static boolean confirmarAccion(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String resp = scanner.nextLine().trim().toUpperCase();
        return resp.equals("S") || resp.equals("SI");
    }

    public static Ecosistema configurarSimulacion(Scanner scanner) {
        int cantPlantas = 0;
        int cantConejos = 0;
        int cantLobos = 0;
        Clima climaInicial = Clima.SOLEADO;
        int cantTurnos = 0;
        boolean confirmado = false;

        System.out.println("==================================================");
        System.out.println("       SIMULADOR DE ECOSISTEMA - CONFIGURACION    ");
        System.out.println("==================================================");

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

            System.out.println("\n--- RESUMEN DE CONFIGURACION INICIAL ---");
            System.out.println("• Plantas iniciales : " + cantPlantas);
            System.out.println("• Conejos iniciales : " + cantConejos);
            System.out.println("• Lobos iniciales   : " + cantLobos);
            System.out.println("• Clima inicial     : " + climaInicial.getNombre());
            System.out.println("• Turnos a simular  : " + cantTurnos);

            if (confirmarAccion(scanner, "xConfirma esta configuracion para iniciar? (S/N): ")) {
                confirmado = true;
            } else {
                System.out.println("\nReiniciando la configuracion...\n");
            }
        }

        Ecosistema eco = new Ecosistema(climaInicial, cantTurnos);

        for (int i = 0; i < cantPlantas; i++) {
            double energiaInicial = 45.0 + (Math.random() * 30.0);
            eco.agregarEntidad("planta", energiaInicial);
        }

        for (int i = 0; i < cantConejos; i++) {
            double energiaInicial = 50.0 + (Math.random() * 25.0);
            eco.agregarEntidad("conejo", energiaInicial);
        }

        for (int i = 0; i < cantLobos; i++) {
            double energiaInicial = 55.0 + (Math.random() * 25.0);
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
                System.out.println("[Error] Entrada invalida. Debe ingresar un numero entero.");
            }
        }
    }

    public static Clima pedirClimaInicial(Scanner scanner) {
        while (true) {
            System.out.println("Seleccione el clima:");
            System.out.println("  1. Soleado");
            System.out.println("  2. Lluvioso");
            System.out.println("  3. Sequia");
            System.out.println("  4. Invierno");
            System.out.print("Opcion (1 - 4): ");

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
                    return Clima.SEQUIA;
                case "4":
                case "invierno":
                    return Clima.INVIERNO;
                default:
                    System.out.println("[Error] Opcion de clima invalida. Elija entre 1 y 4.");
            }
        }
    }
}