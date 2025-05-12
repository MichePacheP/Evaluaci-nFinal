/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package evaluacion.pkgfinal.transversal;

import java.util.*;

public class EvaluacionFinalTransversal {

    static String nombreTeatro = "Teatro Moro";
    static int totalAPagar = 0;
    static LinkedList<String> ids = new LinkedList<>(Collections.nCopies(100, null));

    static final List<String> listaZonas = Arrays.asList("VIP", "Platea Baja", "Platea Alta", "Palco", "Galeria");
    static final Map<String, Integer[]> zonas = Map.of(
        "VIP", new Integer[]{0, 14},
        "Platea Baja", new Integer[]{15, 34},
        "Platea Alta", new Integer[]{35, 54},
        "Palco", new Integer[]{55, 69},
        "Galeria", new Integer[]{70, 99}
    );
    static final Map<String, Integer> preciosZona = new HashMap<>();
    static {
        preciosZona.put("VIP", 30000);
        preciosZona.put("Platea Baja", 18000);
        preciosZona.put("Platea Alta", 15000);
        preciosZona.put("Palco", 13000);
        preciosZona.put("Galeria", 10000);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        String[] asientos = new String[100];
        LinkedList<String> nombres = new LinkedList<>(Collections.nCopies(100, null));

        Arrays.fill(asientos, "D");
        System.out.println("Bienvenido/a a " + nombreTeatro);
        int continuar;

        do {
            System.out.println("MENU");
            System.out.println("1. Reservar entradas.");
            System.out.println("2. Comprar asientos.");
            System.out.println("3. Modificar compra.");
            System.out.println("4. Imprimir boleta.");
            System.out.println("5. Salir del sistema.");
            System.out.println("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> reservarEntradas(asientos, nombres, sc);
                case 2 -> comprarAsiento(asientos, nombres, sc);
                case 3 -> modificarCompra(asientos, nombres, sc);
                case 4 -> imprimirBoleta(asientos, nombres);
                case 5 -> System.out.println("Gracias por usar el sistema.");
                default -> System.out.println("Opcion invalida.");
            }
            System.out.println("Si desea volver al menu principal, presione 1. Por el contrario 2: ");
            continuar = sc.nextInt();
            sc.nextLine();
        } while (continuar == 1);
        System.out.println("Programa finalizado. Hasta pronto.");
    }
    public static void reservarEntradas(String[] asientos, LinkedList<String> nombres, Scanner sc) {
        mostrarAsientos(asientos);
        System.out.println("Indique cuantos asientos desea reservar: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < cantidad; i++) {
            for (int j = 0; j < listaZonas.size(); j++) {
                System.out.println((j + 1) + ". " + listaZonas.get(j));
            }
            System.out.println("Ingrese el numero de la zona: ");
            int opcionZona = sc.nextInt();
            sc.nextLine();

            if (opcionZona < 1 || opcionZona > listaZonas.size()) {
                System.out.println("Zona invalida.");
                i--;
                continue;
            }
            String zona = listaZonas.get(opcionZona - 1);
            Integer[] rango = zonas.get(zona);

            System.out.println("Ingrese el numero del asiento (1-100): ");
            int asiento = sc.nextInt();
            sc.nextLine();

            int index = asiento - 1;
            if (index < rango[0] || index > rango[1] || asiento < 1 || asiento > 100) {
                System.out.println("Ese asiento no pertenece a la zona seleccionada.");
                i--;
                continue;
            }
            // DEPURACIÓN: Se corrigió condición de validación para evitar errores al seleccionar asientos fuera de zona.
            if (asientos[index].equals("D")) {
                System.out.println("Ingrese su nombre (en minusculas): ");
                String nombre = sc.nextLine();
                asientos[index] = "R";
                nombres.set(index, nombre);
                ids.set(index, "ID" + asiento);
                System.out.println("Asiento " + asiento + " reservado exitosamente. ID: " + ids.get(index));
            } else {
                System.out.println("Asiento no disponible.");
                i--;
            }
        }
    }
    public static void comprarAsiento(String[] asientos, LinkedList<String> nombres, Scanner sc) {
        System.out.println("Indique cuantos asientos desea comprar: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < cantidad; i++) {
            mostrarAsientos(asientos);

            for (int j = 0; j < listaZonas.size(); j++) {
                System.out.println((j + 1) + ". " + listaZonas.get(j));
            }
            System.out.println("Ingrese el numero de la zona: ");
            int opcionZona = sc.nextInt();
            sc.nextLine();

            if (opcionZona < 1 || opcionZona > listaZonas.size()) {
                System.out.println("Zona invalida.");
                i--;
                continue;
            }
            String zona = listaZonas.get(opcionZona - 1);
            Integer[] rango = zonas.get(zona);
            int precioBase = preciosZona.get(zona);

            System.out.println("Ingrese el numero de asiento (1-100): ");
            int asiento = sc.nextInt();
            sc.nextLine();
            int index = asiento - 1;

            if (asiento < 1 || asiento > 100 || index < rango[0] || index > rango[1]) {
                System.out.println("Asiento invalido.");
                i--;
                continue;
            }
            if (asientos[index].equals("R")) {
                System.out.println("Ingrese su nombre para confirmar (en minusculas): ");
                String nombre = sc.nextLine();
                if (nombres.get(index) != null && nombres.get(index).equalsIgnoreCase(nombre)) {
                    System.out.println("Ingrese su edad: ");
                    int edad = sc.nextInt();
                    if (edad < 0 || edad > 100) {
                        System.out.println("Edad invalida. Debe estar entre 0 y 100 anos.");
                        i--;
                        continue;
                    }
                    // DEPURACIÓN: Antes no validaba edad máxima. Se agregó condición edad > 100 para evitar entradas inválidas.
                    double precioFinal = aplicarDescuento(precioBase, edad);
                    sc.nextLine();
                    
                    System.out.println("Ingrese su sexo: ");
                    System.out.println("1. Mujer.");
                    System.out.println("2. Hombre.");
                    System.out.println("3. Otro.");
                    int sexo = sc.nextInt();
                    if (precioFinal == precioBase && sexo == 1) {
                        precioFinal = precioBase * 0.80;
                    }
                    asientos[index] = "C";
                    System.out.println("Valor de entrada: $" + (int) precioFinal);
                    System.out.println("Compra confirmada. ID: " + "ID" + asiento);
                    totalAPagar += precioFinal;
                } else {
                    System.out.println("Nombre no coincide con la reserva.");
                    i--;
                }
                // DEPURACIÓN: Se agregó validación nombres.get(index) != null para evitar NullPointerException.
            } else if (asientos[index].equals("D")) {
                System.out.println("Ingrese su nombre: ");
                String nombre = sc.nextLine();
                System.out.println("Ingrese su edad: ");
                int edad = sc.nextInt();
                if (edad < 0 || edad > 100) {
                    System.out.println("Edad invalida. Debe estar entre 0 y 100 anos.");
                    i--;
                    continue;
                    }
                double precioFinal = aplicarDescuento(precioBase, edad);
                sc.nextLine();
                System.out.println("Ingrese su sexo: ");
                System.out.println("1. Mujer.");
                System.out.println("2. Hombre.");
                System.out.println("3. Otro.");
                int sexo = sc.nextInt();
                if (precioFinal == precioBase && sexo == 1) {
                    precioFinal = precioBase * 0.80;
                }
                asientos[index] = "C";
                nombres.set(index, nombre);
                ids.set(index, "ID" + asiento);
                System.out.println("Valor de entrada: $" + (int) precioFinal);
                System.out.println("Compra realizada. ID: " + ids.get(index));
                totalAPagar += precioFinal;
            } else {
                System.out.println("Asiento ya comprado.");
                i--;
            }
        }
        System.out.println("Total pagado: $" + totalAPagar);
    }
    public static void modificarCompra(String[] asientos, LinkedList<String> nombres, Scanner sc) {
        mostrarAsientos(asientos);
        System.out.println("Ingrese el numero de asiento (1-100): ");
        int asiento = sc.nextInt();
        sc.nextLine();

        if (asiento < 1 || asiento > 100) {
            System.out.println("Numero invalido.");
            return;
        }
        int index = asiento - 1;
        
        if (asientos[index].equals("D")) {
            System.out.println("Asiento disponible. No hay nada que modificar.");
        } else {
            System.out.println("Ingrese su nombre para confirmar: ");
            String nombre = sc.nextLine();

            if (nombres.get(index) != null && nombres.get(index).equalsIgnoreCase(nombre)) {
                System.out.println("Si desea cancelar, presione 1. Por el contrario, 2: ");
                int confirmacion = sc.nextInt();
                sc.nextLine();

                if (confirmacion == 1) {
                    if (asientos[index].equals("C")) {
                        totalAPagar -= preciosZona.get(getZonaPorAsiento(index));
                    }
                    asientos[index] = "D";
                    nombres.set(index, null);
                    ids.set(index, null);
                    System.out.println("Asiento liberado.");
                } else {
                    System.out.println("Operacion cancelada.");
                }
            } else {
                System.out.println("Nombre incorrecto.");
            }
        }
    }
    private static void imprimirBoleta(String[] asientos, LinkedList<String> nombres) {
        System.out.println("-------- BOLETA --------");
        System.out.println("       " + nombreTeatro + "       ");
        int cantidadEntradas = 0;

        for (int i = 0; i < asientos.length; i++) {
            if (asientos[i].equals("C")) {
                cantidadEntradas++;
                String zona = getZonaPorAsiento(i);
                System.out.println("Asiento: " + (i + 1) + " - Zona: " + zona + " - Nombre: " + nombres.get(i) + " - ID: " + ids.get(i));
            }
        }
        if (cantidadEntradas == 0) {
            System.out.println("No hay entradas compradas.");
        } else {
            System.out.println("-------------------------");
            System.out.println("Cantidad de entradas: " + cantidadEntradas);
            System.out.println("Total pagado: $" + totalAPagar);
        }
        System.out.println("-------------------------");
    }
    private static void mostrarAsientos(String[] asientos) {
        System.out.println("Estado de los asientos:");
        for (String zona : listaZonas) {
            Integer[] rango = zonas.get(zona);
            int inicio = rango[0];
            int fin = rango[1];

            System.out.println("ZONA: " + zona);
            for (int i = inicio; i <= fin; i++) {
            System.out.printf("%3d[%s] ", (i + 1), asientos[i]);
            if ((i - inicio + 1) % 10 == 0) System.out.println();
            }
            System.out.println("\n");
        }
    }
    private static double aplicarDescuento(double precioBase, int edad) {
        if (edad < 10) return precioBase * 0.90;
        if (edad <= 18) return precioBase * 0.85;
        if (edad >= 65 && edad <= 100) return precioBase * 0.75;
        return precioBase;
    }
    private static String getZonaPorAsiento(int index) {
        for (Map.Entry<String, Integer[]> entry : zonas.entrySet()) {
            if (index >= entry.getValue()[0] && index <= entry.getValue()[1]) {
                return entry.getKey();
            }
        }
        return "Desconocida";
    }
}
