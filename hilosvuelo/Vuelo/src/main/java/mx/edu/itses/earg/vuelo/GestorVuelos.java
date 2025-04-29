// Archivo: GestorVuelos.java
package mx.edu.itses.earg.vuelo;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class GestorVuelos {
    protected List<Vuelo> vuelos = new ArrayList<>();
    private static final String ARCHIVO = "vuelos.txt";
    private static final String ARCHIVO_CSV = "vuelos.csv";

    public synchronized void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
    }

    public synchronized boolean eliminarVuelo(String codigo) {
        return vuelos.removeIf(v -> v.getCodigo().equalsIgnoreCase(codigo));
    }

    public synchronized Vuelo buscarVuelo(String codigo) {
        return vuelos.stream().filter(v -> v.getCodigo().equalsIgnoreCase(codigo)).findFirst().orElse(null);
    }

    public synchronized void listarVuelosOrdenados() {
        vuelos.stream()
              .sorted(Comparator.comparing(Vuelo::getFechaLlegada))
              .forEach(System.out::println);
    }

    public synchronized void mostrarUltimoVuelo() {
        vuelos.stream().max(Comparator.comparing(Vuelo::getFechaLlegada)).ifPresent(System.out::println);
    }

    public synchronized void mostrarVueloMenosPasajeros() {
        vuelos.stream().min(Comparator.comparingInt(Vuelo::getPasajeros)).ifPresent(System.out::println);
    }

    public synchronized void guardarVuelos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Vuelo v : vuelos) {
                writer.write(v.getCodigo() + "," + v.getOrigen() + "," + v.getDestino() + "," +
                             v.getFechaLlegada().format(Vuelo.FORMATO_FECHA) + "," + v.getPasajeros());
                writer.newLine();
            }
            System.out.println("Vuelos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar vuelos: " + e.getMessage());
        }
    }

    public synchronized void cargarVuelos() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("No se encontro el archivo. Se creara al guardar vuelos.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 5) continue;

                LocalDateTime fecha = LocalDateTime.parse(datos[3], Vuelo.FORMATO_FECHA);
                Vuelo vuelo = new Vuelo(datos[0], datos[1], datos[2], fecha, Integer.parseInt(datos[4]));
                vuelos.add(vuelo);
            }
            System.out.println("Vuelos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar vuelos: " + e.getMessage());
        }
    }

    public synchronized void cargarDesdeCSV() {
        File archivo = new File(ARCHIVO_CSV);
        if (!archivo.exists()) {
            System.out.println("Archivo vuelos.csv no encontrado.");
            return;
        }

        List<Vuelo> vuelosTemporales = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 5) continue;

                LocalDateTime fecha = LocalDateTime.parse(datos[3], Vuelo.FORMATO_FECHA);
                Vuelo vuelo = new Vuelo(datos[0], datos[1], datos[2], fecha, Integer.parseInt(datos[4]));
                vuelosTemporales.add(vuelo);
            }

            synchronized (this) {
                this.vuelos = vuelosTemporales;
            }

            System.out.println("Vuelos actualizados automáticamente desde vuelos.csv");
        } catch (Exception e) {
            System.out.println("Error al cargar vuelos desde CSV: " + e.getMessage());
        }
    }

    void detenerCargaPeriodica() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void iniciarCargaPeriodica() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
