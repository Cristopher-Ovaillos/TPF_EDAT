package SistemaTrenes;

public class Tren {
    // Atributos
    private int identificador;
    private String tipoPropulsion;
    private int cantidadVagonesPasajeros;
    private int cantidadVagonesCarga;
    private String lineaAsignada;

    // Constructor
    public Tren(){
        
    }
    public Tren(int identificador, String tipoPropulsion, int cantidadVagonesPasajeros,
            int cantidadVagonesCarga, String lineaAsignada) {
        this.identificador = identificador;
        this.tipoPropulsion = tipoPropulsion;
        this.cantidadVagonesPasajeros = cantidadVagonesPasajeros;
        this.cantidadVagonesCarga = cantidadVagonesCarga;
        this.lineaAsignada = lineaAsignada;
    }

    // Getters y Setters
    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getTipoPropulsion() {
        return tipoPropulsion;
    }

    public void setTipoPropulsion(String tipoPropulsion) {
        this.tipoPropulsion = tipoPropulsion;
    }

    public int getCantidadVagonesPasajeros() {
        return cantidadVagonesPasajeros;
    }

    public void setCantidadVagonesPasajeros(int cantidadVagonesPasajeros) {
        this.cantidadVagonesPasajeros = cantidadVagonesPasajeros;
    }

    public int getCantidadVagonesCarga() {
        return cantidadVagonesCarga;
    }

    public void setCantidadVagonesCarga(int cantidadVagonesCarga) {
        this.cantidadVagonesCarga = cantidadVagonesCarga;
    }

    public String getLineaAsignada() {
        return lineaAsignada;
    }

    public void setLineaAsignada(String lineaAsignada) {
        this.lineaAsignada = lineaAsignada;
    }
    @Override
    public String toString() {
        return "Tren{" +
                "identificador=" + identificador +
                ", tipoPropulsion='" + tipoPropulsion + '\'' +
                ", cantidadVagonesPasajeros=" + cantidadVagonesPasajeros +
                ", cantidadVagonesCarga=" + cantidadVagonesCarga +
                ", lineaAsignada='" + lineaAsignada + '\'' +
                '}';
    }
}