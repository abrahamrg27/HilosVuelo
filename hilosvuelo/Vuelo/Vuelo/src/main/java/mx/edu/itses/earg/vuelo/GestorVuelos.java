package mx.edu.itses.earg.vuelo;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class GestorVuelos {
    private List<Vuelo> vuelos = new ArrayList<>();
    private static final String ARCHIVO = "vuelos.txt";

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
    }

    public boolean eliminarVuelo(String codigo) {
        return vuelos.removeIf(v -> v.getCodigo().equalsIgnoreCase(codigo));
    }

    public Vuelo buscarVuelo(String codigo) {
        return vuelos.stream().filter(v -> v.getCodigo().equalsIgnoreCase(codigo)).findFirst().orElse(null);
    }

    public void listarVuelosOrdenados() {
        vuelos.stream()
                .sorted(Comparator.comparing(Vuelo::getFechaLlegada))
                .forEach(System.out::println);
    }

    public void mostrarUltimoVuelo() {
        vuelos.stream().max(Comparator.comparing(Vuelo::getFechaLlegada)).ifPresent(System.out::println);
    }

    public void mostrarVueloMenosPasajeros() {
        vuelos.stream().min(Comparator.comparingInt(Vuelo::getPasajeros)).ifPresent(System.out::println);
    }

    public void guardarVuelos() {
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

    public void cargarVuelos() {
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
}