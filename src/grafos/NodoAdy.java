package grafos;

public class NodoAdy {
    //atributos
    private NodoVert vertice;
    private NodoAdy sigAdy;
    //el TP dice etiqueta tipo numero ya que se representara en KM
    private int km;
    

    public NodoAdy(NodoVert vertice, NodoAdy adyacente,int etiqueta){
        this.vertice=vertice;
        this.sigAdy=adyacente;
        this.km=etiqueta;
    }
    public NodoVert getVertice() {
        return vertice;
    }
    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }
    public NodoAdy getSigAdy() {
        return sigAdy;
    }
    public void setSigAdy(NodoAdy sigAdy) {
        this.sigAdy = sigAdy;
    }
    public int getKm() {
        return km;
    }
    public void setKm(int km) {
        this.km = km;
    }
}
