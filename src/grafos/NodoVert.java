package grafos;

public class NodoVert {
    //atributos
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    public NodoVert(Object elem, NodoVert vertice){
        this.elem=elem;
        this.sigVertice=vertice;
       
    }
    public NodoVert(Object elem, NodoVert vertice, NodoAdy adyacente){
        this.elem=elem;
        this.sigVertice=vertice;
        this.primerAdy=adyacente;
    }
    public Object getElem() {
        return elem;
    }
    public void setElem(Object elem) {
        this.elem = elem;
    }
    public NodoVert getSigVertice() {
        return sigVertice;
    }
    public void setSigVertice(NodoVert sigVertice) {
        this.sigVertice = sigVertice;
    }
    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }
    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }  
}
