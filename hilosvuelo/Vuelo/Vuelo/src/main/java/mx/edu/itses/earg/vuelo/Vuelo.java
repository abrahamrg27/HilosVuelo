package mx.edu.itses.earg.vuelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vuelo {
    private String codigo;
    private String origen;
    private String destino;
    private LocalDateTime fechaLlegada;
    private int pasajeros;

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Vuelo(String codigo, String origen, String destino, LocalDateTime fechaLlegada, int pasajeros) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.fechaLlegada = fechaLlegada;
        this.pasajeros = pasajeros;
    }

    public String getCodigo() { return codigo; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public LocalDateTime getFechaLlegada() { return fechaLlegada; }
    public int getPasajeros() { return pasajeros; }

    public void setOrigen(String origen) { this.origen = origen; }
    public void setDestino(String destino) { this.destino = destino; }
    public void setFechaLlegada(LocalDateTime fechaLlegada) { this.fechaLlegada = fechaLlegada; }
    public void setPasajeros(int pasajeros) { this.pasajeros = pasajeros; }

    @Override
    public String toString() {
        return "Vuelo{" +
                "codigo='" + codigo + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", fechaLlegada=" + fechaLlegada.format(FORMATO_FECHA) +
                ", pasajeros=" + pasajeros +
                '}';
    }
}