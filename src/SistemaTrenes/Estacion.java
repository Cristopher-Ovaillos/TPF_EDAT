package SistemaTrenes;

public class Estacion {
    // Atributos
    private String nombre;
    private String calle;
    private String numero;
    private String ciudad;
    private String codigoPostal;
    private int cantidadVias;
    private int cantidadPlataformas;

    // Constructor
    public Estacion(){
        
    }
    public Estacion(String nombre, String calle, String numero, String ciudad, String codigoPostal,
            int cantidadVias, int cantidadPlataformas) {
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.cantidadVias = cantidadVias;
        this.cantidadPlataformas = cantidadPlataformas;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public int getCantidadVias() {
        return cantidadVias;
    }

    public int getCantidadPlataformas() {
        return cantidadPlataformas;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setCantidadVias(int cantidadVias) {
        this.cantidadVias = cantidadVias;
    }

    public void setCantidadPlataformas(int cantidadPlataformas) {
        this.cantidadPlataformas = cantidadPlataformas;
    }
    @Override
    public String toString() {
        return "Estacion{" +
                "nombre='" + nombre + '\'' +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", cantidadVias=" + cantidadVias +
                ", cantidadPlataformas=" + cantidadPlataformas +
                '}';
    }
}