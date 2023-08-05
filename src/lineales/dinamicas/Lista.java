package lineales.dinamicas;

/**
 *
 * @author Ovaillos 
 */
public class Lista {

    //atributos
    private Nodo cabecera;
    private int longitud;

    //constructor
    public Lista() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean insertar(Object elem, int pos) {
        boolean exito = true;
        if (pos < 1 || pos > this.longitud() + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = new Nodo(elem, this.cabecera);
            } else {
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(elem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            longitud += 1;
        }
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito = false;
        if (pos > 0 && pos <= this.longitud()) {
            //el enlace del nodo que apunta a la cabecera avanza al siguiente
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            this.longitud -= 1;
            exito = true;
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Object elem = null;
        if (!(pos < 1 || pos > this.longitud())) {
            if (cabecera != null) {
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < pos) {
                    aux = aux.getEnlace();
                    i++;
                }
                elem = aux.getElem();
            }
        }
        return elem;
    }

    public int localizar(Object elemento) {
        int encontrado = -1;
        if (!this.esVacia()) {
            boolean exito = false;
            int i = 1;
            Nodo aux = this.cabecera;

            while (i <= this.longitud() && !exito) {

                if (aux.getElem().equals(elemento)) {
                    exito = true;
                    encontrado = i;
                } else {
                    aux = aux.getEnlace();
                    i++;
                }

            }
        }
        return encontrado;
    }

    public void vaciar() {
        this.cabecera = null;
        longitud = 0;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public int longitud() {
        return this.longitud;
    }

    public Lista clone() {
        Lista clon = new Lista();
        Nodo aux = this.cabecera;

        clon.cabecera = new Nodo(aux.getElem(), clon.cabecera);
        Nodo auxClon = clon.cabecera;
        while (aux.getEnlace() != null) {
            aux = aux.getEnlace();
            Nodo nuevo = new Nodo(aux.getElem(), null);
            auxClon.setEnlace(nuevo);
            auxClon = auxClon.getEnlace();
        }
        clon.longitud = this.longitud;
        return clon;
    }

    public String toString() {
        String cad = "";
        if (this.esVacia()) {
            cad = "VACIO";
        } else {
            Nodo aux = this.cabecera;
            cad = "[ ";
            while (aux != null) {
                cad += aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    cad += ", ";
                }
            }
            cad += " ]";
        }
        return cad;
    }

}
