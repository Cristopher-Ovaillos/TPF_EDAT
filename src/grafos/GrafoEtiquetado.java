package grafos;
//4-08-2023
import SistemaTrenes.Camino;

import lineales.dinamicas.Lista;

public class GrafoEtiquetado {
    // grafo etiqueta hay arcos entre si
    private NodoVert inicio = null;

    public boolean insertarVertice(Object elem) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(elem);
        if (aux == null) {
            this.inicio = new NodoVert(elem, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        // Si el vértice no existe (aux es null), significa que podemos insertar el
        // nuevo vértice.
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice(Object eliminar) {
        boolean exito = false, caso = false;
        NodoVert anterior = null;
        NodoVert actual = this.inicio;
        NodoVert nodoEliminar=null;
        while (actual != null && !exito) {
            if (actual.getElem().equals(eliminar)) {
                if (anterior == null) {
                    // El vértice a eliminar es el nodo inicio
                    caso = true;
                }
                exito = true;
                //encontre el nodo que quiero eliminar asi que guardo 
                //la Ref
                nodoEliminar=actual;
            } else {
                anterior = actual;
                actual = actual.getSigVertice();
            }
        }
        if (exito) {
            //'nodoEliminar' no va a ser nunca null porque si 
            //exito es true es porque lo encontro
            eliminarArcosConVertice(nodoEliminar);
            if (caso) {
                this.inicio = actual.getSigVertice();
            } else {
                anterior.setSigVertice(actual.getSigVertice());
            }
        }
        return exito;
    }

    private void eliminarArcosConVertice(NodoVert eliminar) {
        NodoAdy aux = eliminar.getPrimerAdy();
        while (aux != null) {       
                eliminarArcosConVerticeAux(aux.getVertice(), eliminar.getElem());       
            aux = aux.getSigAdy();
        }
    }

    private boolean eliminarArcosConVerticeAux(NodoVert nodo, Object eliminar) {

        boolean exito = false;
        NodoAdy actual = nodo.getPrimerAdy();
        NodoAdy anterior = null;

        while (actual != null && !exito) {

            if (actual.getVertice().getElem().equals(eliminar)) {
                // el vertice es el encontrado

                if (anterior == null) {
                    // caso primer Ady
                    nodo.setPrimerAdy(actual.getSigAdy());
                } else {
                    anterior.setSigAdy(actual.getSigAdy());
                }
                exito = true;
            } else {
                anterior = actual;
                actual = actual.getSigAdy();
            }
        }
        return exito;
    }

    public boolean existeVertice(Object elem) {
        NodoVert aux = this.inicio;
        boolean existe = false;

        while (aux != null) {
            if (aux.getElem().equals(elem)) {
                existe = true;
            }
            aux = aux.getSigVertice();
        }

        return existe;

    }

    public boolean insertarArco(Object origen, Object destino, int etiqueta) {
        boolean exito = false, encontradoO = false, encontradoD = false;
        NodoVert aux = this.inicio;
        NodoVert nodoO = null, nodoD = null;
        if (origen != destino) {
            while ((!encontradoO || !encontradoD) && aux != null) {

                if (!encontradoO && aux.getElem().equals(origen)) {

                    nodoO = aux;
                    encontradoO = true;

                }
                if (!encontradoD && aux.getElem().equals(destino)) {

                    nodoD = aux;
                    encontradoD = true;
                }
                aux = aux.getSigVertice();
            }

            if (encontradoD && encontradoO) {
                // ENCONTRO A LOS 2
                boolean existe = false;
                NodoAdy auxAdy = nodoO.getPrimerAdy();
                while (auxAdy != null && !existe) {

                    if (auxAdy.getVertice().getElem().equals(nodoD.getElem())) {
                        existe = true;
                    }
                    auxAdy = auxAdy.getSigAdy();

                }
                if (!existe) {
                    nodoO.setPrimerAdy(new NodoAdy(nodoD, nodoO.getPrimerAdy(), etiqueta));
                    nodoD.setPrimerAdy(new NodoAdy(nodoO, nodoD.getPrimerAdy(), etiqueta));
                    exito = true;
                }

            }

        }

        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        boolean exito = false, stop = false;
        NodoVert aux = this.inicio;
        while (aux != null && !stop) {

            if (aux.getElem().equals(origen)) {

                if (eliminarArcosConVerticeAux(aux, destino)) {
                    // solo entra si existio el nodo destino
                    exito = true;

                }
                stop = true;

            } else {
                aux = aux.getSigVertice();
            }

        }

        return exito;
    }

    public boolean existeArco(Object origen, Object destino) {
        boolean exito = false, stop = false;
        NodoVert auxV = this.inicio;

        while (!stop && auxV != null) {

            if (auxV.getElem().equals(origen)) {
                stop = true;
            } else {
                auxV = auxV.getSigVertice();
            }

        }

        if (stop) {
            // encontro ya q
            boolean encontrado = false;
            NodoAdy auxA = auxV.getPrimerAdy();

            while (!encontrado && auxA != null) {

                if (auxA.getVertice().getElem().equals(destino)) {
                    encontrado = true;
                    exito = true;
                }
                auxA = auxA.getSigAdy();
            }

        }

        return exito;
    }

    public boolean vacio() {
        return this.inicio == null;
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        // define un vertice donde comenzar a recorrer

        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // si el vertice no fue visitado aun, avanza en profundidad
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();

        }

        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista vis) {

        if (n != null) {
            // marca al vertice n como visitado
            vis.insertar(n.getElem(), vis.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();

            while (ady != null) {
                // visita en profundidad los adyacentes de n aun no visitados
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdy();

            }
        }

    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        // veriifica si ambos verices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxO != null && auxD != null) {
            // si ambos vertices existen busca si existe camino entre ambos
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);

        }
        return exito;

    }

    public boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // si vertice n es el destino: HAY CAMINO!
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                // si no es el destino verifica si hay camino entre n y destino
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdy();

                }
            }

        }

        return exito;
    }

    public String toString() {
        String texto = "";
        NodoVert aux = this.inicio;

        while (aux != null) {

            texto = texto + aux.getElem() + "->\n \t " + arcoVertice(aux.getPrimerAdy()) + "\n";

            aux = aux.getSigVertice();

        }

        return texto;
    }

    private String arcoVertice(NodoAdy a) {
        String str = "";

        if (a != null) {
            String aux = "";

            aux = arcoVertice(a.getSigAdy());
            str = a.getVertice().getElem() + "(" + a.getKm() + ")" + " - " + aux;
        }

        return str;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista caminoCorto = new Lista();
        NodoVert vertO = ubicarVertice(origen);
        NodoVert vertD = ubicarVertice(destino);

        if (vertO != null && vertD != null) {

            // Crear una lista para mantener los nodos visitados
            Lista visitados = new Lista();

            // Iniciar la búsqueda del camino más corto
            caminoCorto = caminoMasCortoAux(vertO, destino, visitados, caminoCorto);
        }

        return caminoCorto;
    }

    private Lista caminoMasCortoAux(NodoVert actual, Object destino, Lista visitados, Lista caminoCorto) {
        if (actual != null) {
            visitados.insertar(actual.getElem(), visitados.longitud() + 1);

            if (actual.getElem().equals(destino)) {
                // Si se llegó al destino, se compara con el camino más corto actual
                if (visitados.longitud() < caminoCorto.longitud() || caminoCorto.esVacia()) {
                    caminoCorto = visitados.clone();
                }
            } else {
                NodoAdy ady = actual.getPrimerAdy();

                while (ady != null) {
                    // Verificar si el nodo adyacente no ha sido visitado
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        if (caminoCorto.esVacia() || visitados.longitud() + 1 < caminoCorto.longitud()) {
                            caminoCorto = caminoMasCortoAux(ady.getVertice(), destino, visitados, caminoCorto);
                        }
                    }
                    ady = ady.getSigAdy();
                }
            }

            // Eliminar el último nodo visitado para continuar con otros caminos
            visitados.eliminar(visitados.longitud());
        }

        return caminoCorto;
    }

    public boolean esVacio() {
        return this.inicio == null;
    }

    // Obtener el camino que llegue de A a B de menor distancia en kilómetros

    public Camino caminoMasCortoKM(Object origen, Object destino) {
        Camino caminoCorto = new Camino();
        NodoVert vertO = ubicarVertice(origen);
        NodoVert vertD = ubicarVertice(destino);

        if (vertO != null && vertD != null) {
            // Crear una lista para mantener los nodos visitados
            Lista visitados = new Lista();

            // Iniciar la búsqueda del camino más corto
            caminoCorto = caminoMasCortoKMAux(vertO, destino, visitados, caminoCorto, 0);
        }

        return caminoCorto;
    }

    public Camino caminoMasCortoKMAux(NodoVert actual, Object destino, Lista visitados, Camino caminoCorto,
            int distanciaRecorrida) {
        if (actual != null) {
            visitados.insertar(actual.getElem(), visitados.longitud() + 1);

            if (actual.getElem().equals(destino)) {
                // Si se llegó al destino, se compara con el camino más corto actual
                if (distanciaRecorrida < caminoCorto.getDistanciaTotal()
                        || caminoCorto.getVerticesVisitados().esVacia()) {
                    caminoCorto.setDistanciaTotal(distanciaRecorrida);
                    caminoCorto.setVerticesVisitados(visitados.clone());

                }
            } else {
                NodoAdy ady = actual.getPrimerAdy();

                while (ady != null) {
                    // Verificar si el nodo adyacente no ha sido visitado
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        if (caminoCorto.getVerticesVisitados().esVacia()|| distanciaRecorrida + ady.getKm() < caminoCorto.getDistanciaTotal()) {
                            caminoCorto = caminoMasCortoKMAux(ady.getVertice(), destino, visitados, caminoCorto,
                                    distanciaRecorrida + ady.getKm());
                        }
                    }
                    ady = ady.getSigAdy();
                }
            }

            // Eliminar el último nodo visitado para continuar con otros caminos
            visitados.eliminar(visitados.longitud());
        }

        return caminoCorto;
    }

    // Obtener todos los caminos posibles para llegar de A a B sin pasar por una
    // estación C dada
    public Lista caminosExceptoEstacion(Object origen, Object destino, Object except) {
        Lista caminos = new Lista();
        NodoVert vertO = ubicarVertice(origen);
        NodoVert vertD = ubicarVertice(destino);

        if (vertO != null && vertD != null) {
            Lista visitados = new Lista();
            caminosExceptoEstacionAux(vertO, visitados, except, destino, caminos, 0);
        }

        return caminos;
    }

    private void caminosExceptoEstacionAux(NodoVert actual, Lista vis, Object except, Object dest,
            Lista caminos, int distanciaRecorrida) {

        if (actual != null) {
            vis.insertar(actual.getElem(), vis.longitud() + 1);

            if (actual.getElem().equals(dest)) {
                // Si se llegó al destino, agregamos el camino a la lista de caminos
                caminos.insertar(new Camino(vis.clone(), distanciaRecorrida), caminos.longitud() + 1);
            } else {
                NodoAdy ady = actual.getPrimerAdy();

                while (ady != null) {
                    // Verificar si el nodo adyacente no ha sido visitado y no es la estación que
                    // queremos evitar
                    if (!actual.getElem().equals(except) && vis.localizar(ady.getVertice().getElem()) < 0) {
                        caminosExceptoEstacionAux(ady.getVertice(), vis, except, dest, caminos,
                                distanciaRecorrida + ady.getKm());
                    }
                    ady = ady.getSigAdy();
                }
            }

            // Eliminar el último nodo visitado para continuar con otros caminos
            vis.eliminar(vis.longitud());
        }
    }

    // Verificar si es posible llegar de A a B recorriendo como máximo una cantidad
    // X de kilómetros

    public Camino caminoConMaximoKM(Object origen, Object destino, int maxKm) {
        Camino camino = new Camino();
        NodoVert vertO = ubicarVertice(origen);
        NodoVert vertD = ubicarVertice(destino);

        if (vertO != null && vertD != null) {
            // Crear una lista para mantener los nodos visitados
            Lista visitados = new Lista();
            int kmRecorridos = 0;
            // Iniciar la búsqueda del camino más corto
            caminoConMaximoKMAux(vertO, destino, visitados, camino, maxKm, kmRecorridos);
        }

        return camino;

    }

    private void caminoConMaximoKMAux(NodoVert actual, Object dest, Lista vis, Camino camino, int topeKM,int kmRecorrido) {
        if (actual != null) {
            vis.insertar(actual.getElem(), vis.longitud() + 1);

            if (actual.getElem().equals(dest)) {
                // Si se llegó al destino, agregamos el camino a la lista de caminos
                camino.setDistanciaTotal(kmRecorrido);
                camino.setVerticesVisitados(vis.clone());
            } else {
                NodoAdy ady = actual.getPrimerAdy();
                while (ady != null) {
                    // Verificar si el nodo adyacente no ha sido visitado y no es la estación que
                    // queremos evitar
                    if (vis.localizar(ady.getVertice().getElem()) < 0 && kmRecorrido + ady.getKm() <= topeKM) {
                        caminoConMaximoKMAux(ady.getVertice(), dest, vis, camino, topeKM,kmRecorrido + ady.getKm());
                    }
                    ady = ady.getSigAdy();
                }
            }
            // Eliminar el último nodo visitado para continuar con otros caminos
            vis.eliminar(vis.longitud());
        }

    }
}
