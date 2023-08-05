package conjuntitas;

public class NodoAVLDicc {

    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;

    public NodoAVLDicc() {
        this.clave = null;
        this.dato = null;
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 0;
    }

    public NodoAVLDicc(Comparable elem, Object dato, NodoAVLDicc izq, NodoAVLDicc der) {
        this.clave = elem;
        this.izquierdo = izq;
        this.derecho = der;
        this.altura = 0;
        this.dato = dato;
    }

    public Comparable getClave() {
        return clave;
    }

    public void setClave(Comparable clave) {
        this.clave = clave;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NodoAVLDicc getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVLDicc derecho) {
        this.derecho = derecho;
    }

    public NodoAVLDicc getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVLDicc izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void recalcularAltura() {

        int altIzq, altDer;
        // la altura de un nodo sin HI Y HD seran -1
        altIzq = -1;
        altDer = -1;
        if (this.izquierdo != null) { // Si tiene HI calculo su altura
            altIzq = (this.izquierdo).altura;
        }
        if (this.derecho != null) { // Si tiene HD calculo su altura
            altDer = (this.derecho).altura;
        }
        this.altura = Math.max(altIzq, altDer) + 1;
    }
}
