/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.earg.vuelo;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author edgar
 */
public class MenuVuelo {
    public void ejecutarMenu() {
        GestorVuelos gestor = new GestorVuelos();
        Scanner sc = new Scanner(System.in);
        gestor.cargarVuelos();
        gestor.iniciarCargaPeriodica();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Agregar vuelo");
            System.out.println("2. Eliminar vuelo");
            System.out.println("3. Editar vuelo");
            System.out.println("4. Mostrar vuelos ordenados");
            System.out.println("5. Mostrar ultimo vuelo en llegar");
            System.out.println("6. Mostrar vuelo con menos pasajeros");
            System.out.println("7. Guardar y salir");
            System.out.print("Elige una opcion: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Codigo: ");
                    String codigo = sc.nextLine();
                    System.out.print("Origen: ");
                    String origen = sc.nextLine();
                    System.out.print("Destino: ");
                    String destino = sc.nextLine();
                    System.out.print("Fecha de llegada (dd/MM/yyyy HH:mm): ");
                    LocalDateTime fecha = LocalDateTime.parse(sc.nextLine(), Vuelo.FORMATO_FECHA);
                    System.out.print("Numero de pasajeros: ");
                    int pasajeros = sc.nextInt();
                    sc.nextLine();
                    gestor.agregarVuelo(new Vuelo(codigo, origen, destino, fecha, pasajeros));
                    break;

                case 2:
                    System.out.print("Codigo del vuelo a eliminar: ");
                    if (gestor.eliminarVuelo(sc.nextLine())) {
                        System.out.println("Vuelo eliminado.");
                    } else {
                        System.out.println("Vuelo no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Codigo del vuelo a editar: ");
                    Vuelo vuelo = gestor.buscarVuelo(sc.nextLine());
                    if (vuelo != null) {
                        System.out.print("Nuevo origen: ");
                        vuelo.setOrigen(sc.nextLine());
                        System.out.print("Nuevo destino: ");
                        vuelo.setDestino(sc.nextLine());
                        System.out.print("Nueva fecha de llegada (dd/MM/yyyy HH:mm): ");
                        vuelo.setFechaLlegada(LocalDateTime.parse(sc.nextLine(), Vuelo.FORMATO_FECHA));
                        System.out.print("Nuevo numero de pasajeros: ");
                        vuelo.setPasajeros(sc.nextInt());
                        sc.nextLine();
                        System.out.println("Vuelo actualizado.");
                    } else {
                        System.out.println("Vuelo no encontrado.");
                    }
                    break;

                case 4: gestor.listarVuelosOrdenados(); break;
                case 5: gestor.mostrarUltimoVuelo(); break;
                case 6: gestor.mostrarVueloMenosPasajeros(); break;
                case 7:
                    gestor.guardarVuelos();
                    gestor.detenerCargaPeriodica();
                    System.out.println("Vuelos guardados. Saliendo...");
                    return;

                default: System.out.println("Opcion no valida.");
            }
        }
    }

    public static void main(String[] args) {
        new MenuVuelo().ejecutarMenu();
    }
}