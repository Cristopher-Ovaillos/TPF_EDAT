package SistemaTrenes;

import lineales.dinamicas.Lista;

public class Camino {
    private Lista verticesVisitados;
    private int distanciaTotal;

    public Camino() {
        verticesVisitados = new Lista();
        distanciaTotal = 0;
    }

    public Camino(Lista colVertices, int distancia) {
        this.verticesVisitados = colVertices;
        this.distanciaTotal = distancia;
    }

    public Lista getVerticesVisitados() {
        return verticesVisitados;
    }

    public int getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setVerticesVisitados(Lista verticesVisitados) {
        this.verticesVisitados = verticesVisitados;
    }

    public void setDistanciaTotal(int distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public void agregarVertice(Object vertice, int distancia) {
        verticesVisitados.insertar(vertice, verticesVisitados.longitud() + 1);
        distanciaTotal += distancia;
    }

    public boolean verticesVisitadosEsVacia(){
        return verticesVisitados.esVacia();
    }

    public String toString() {
        String txt = "";

        if (!verticesVisitados.esVacia()) {
            txt = "CAMINO:    " + verticesVisitados.toString() + "    |   DISTANCIA(KM):  " + distanciaTotal;
        }

        return txt;
    }
}