package conjuntitas;
//4-08-2023 actual

import lineales.dinamicas.Lista;

public class Diccionario {

    private NodoAVLDicc raiz;

    public Diccionario() {
        this.raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato) {
        boolean exito;
        // si esta vacio
        if (esVacio()) {
            // le asigno un nodo
            this.raiz = new NodoAVLDicc(clave, dato, null, null);
            exito = true;
        } else {
            NodoAVLDicc padre = null;
            exito = insertarAux(this.raiz, clave, dato, padre);

        }

        return exito;
    }

    private boolean insertarAux(NodoAVLDicc n, Comparable clave, Object dato, NodoAVLDicc padre) {
        boolean exito;

        if (n.getClave().compareTo(clave) == 0) {
            exito = false;
        } else {
            // si da numero menor HD
            if (n.getClave().compareTo(clave) < 0) {
                if (n.getDerecho() == null) {
                    // no tiene HD
                    n.setDerecho(new NodoAVLDicc(clave, dato, null, null));
                    // al tener n un hijo entonces deberemos actualizar su altura...que sera 1

                    exito = true;

                } else {
                    // tiene HD
                    exito = insertarAux(n.getDerecho(), clave, dato, n);
                }
            } else {
                // voy al HI
                if (n.getIzquierdo() == null) {
                    n.setIzquierdo(new NodoAVLDicc(clave, dato, null, null));
                    // al tener n un hijo entonces deberemos actualizar su altura...que sera 1
                    exito = true;

                } else {
                    // paso recursivo
                    exito = insertarAux(n.getIzquierdo(), clave, dato, n);
                }

            }
            // recalculo la altura nuevamente porque se agrego elementos
            if (exito) {
                n.recalcularAltura();
                int balance = calcularBalance(n);
                if (balance == -2 || balance == 2) {
                    // Tu código aquí
                    realizarRotacion(n, balance == 2, padre);
                }

            }

        }

        return exito;
    }

    private int calcularBalance(NodoAVLDicc nodo) {
        int HI = -1, HD = -1;
        if (nodo != null) {

            if (nodo.getIzquierdo() != null) {
                HI = nodo.getIzquierdo().getAltura();
            }

            if (nodo.getDerecho() != null) {
                HD = nodo.getDerecho().getAltura();
            }

        }

        return HI - HD;
    }

    private void realizarRotacion(NodoAVLDicc nodo, Boolean caido, NodoAVLDicc padre) {
        Boolean esRaiz = (padre == null);

        if (caido) {
            if (calcularBalance(nodo.getIzquierdo()) != -1) {
                // simple rotacion
                if (esRaiz) {
                    this.raiz = rotarDerecha(nodo);
                } else {
                    if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                        padre.setDerecho(rotarDerecha(nodo));
                    } else {
                        padre.setIzquierdo(rotarDerecha(nodo));
                    }

                }
            } else {
                // doble rotacion
                if (esRaiz) {
                    this.raiz = rotarIzquierdaDerecha(nodo);

                } else {

                    if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                        padre.setDerecho(rotarIzquierdaDerecha(nodo));
                    } else {
                        padre.setIzquierdo(rotarIzquierdaDerecha(nodo));
                    }

                }

            }
        } else {
            if (calcularBalance(nodo.getDerecho()) != 1) {
                // simple rotacion
                if (esRaiz) {
                    this.raiz = rotarIzquierda(nodo);
                } else {
                    if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                        padre.setDerecho(rotarIzquierda(nodo));
                    } else {

                        NodoAVLDicc aux = rotarIzquierda(nodo);

                        padre.setIzquierdo(aux);
                    }
                }
            } else {
                // doble rotacion
                if (esRaiz) {
                    this.raiz = rotarDerechaIzquierda(nodo);

                } else {
                    if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                        padre.setDerecho(rotarDerechaIzquierda(nodo));
                    } else {
                        padre.setIzquierdo(rotarDerechaIzquierda(nodo));
                    }

                }
            }
        }

    }

    // las rotaciones devuelven la raiz de la subraiz
    private NodoAVLDicc rotarDerechaIzquierda(NodoAVLDicc padre) {
        // hacemos girar hacia la derecha el hijo
        NodoAVLDicc hijoDerecha = rotarDerecha(padre.getDerecho());
        padre.setDerecho(hijoDerecha);
        // hacemos girar padre a la izquierda
        NodoAVLDicc nuevoPadre = rotarIzquierda(padre);
        // recalculamos alturas
        padre.recalcularAltura();
        hijoDerecha.recalcularAltura();
        return nuevoPadre;
    }

    private NodoAVLDicc rotarIzquierdaDerecha(NodoAVLDicc padre) {

        // hacemos rotar hacia la izquierda al hijo izquierdo de padre
        NodoAVLDicc hijoIzquierdo = rotarIzquierda(padre.getIzquierdo());

        padre.setIzquierdo(hijoIzquierdo);
        // hacemos rotar hacia la derecha a el padre
        NodoAVLDicc nuevoPadre = rotarDerecha(padre);

        // recalculasmos alturas
        padre.recalcularAltura();
        hijoIzquierdo.recalcularAltura();
        return nuevoPadre;
    }

    private NodoAVLDicc rotarDerecha(NodoAVLDicc r) {
        // hijo derecho de n
        NodoAVLDicc h = r.getIzquierdo();
        // hijo izquierdo del hijo derecho (h)
        NodoAVLDicc temp = null;
        if (h.getDerecho() != null) {
            temp = h.getDerecho();
        }
        // el n que entra por parametro que es padre de h, ahora sera hijo derecho de su
        // hijo Izquierdo

        h.setDerecho(r);
        // el temp que es hijo derecho del hijo izquierdo de n ahora pasa a sera hijo
        // izquierdo de n
        r.setIzquierdo(temp);

        // se recalcula
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVLDicc rotarIzquierda(NodoAVLDicc r) {
        // System.out.println(r.getElem());
        NodoAVLDicc h = r.getDerecho();
        // System.out.println(h.getElem());
        NodoAVLDicc temp = null;
        if (h.getIzquierdo() != null) {
            temp = h.getIzquierdo();
        }

        h.setIzquierdo(r);

        r.setDerecho(temp);

        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    // ----
    public boolean eliminar(Comparable elem) {
        boolean eliminado = false;
        if (!this.esVacio()) {
            // no es vacio
            NodoAVLDicc aux = null;
            eliminado = eliminarAux(this.raiz, elem, aux);
        }
        return eliminado;
    }

    private boolean eliminarAux(NodoAVLDicc n, Comparable buscar, NodoAVLDicc padre) {
        boolean retornar = false;
        if (n != null) {
            if (n.getClave().compareTo(buscar) == 0) {
                // encontrado
                int caso = casoHijo(n);
                switch (caso) {
                    case 1:
                        eliminarCaso1(n, padre);
                        break;
                    case 2:
                        eliminarCaso2(n, padre);
                        break;
                    case 3:
                        eliminarCaso3(n.getIzquierdo(), n);
                        break;
                }
                //987 ES n, padre 745
                retornar = true;
                n.recalcularAltura();
                int bal = this.calcularBalance(n);
                if (bal == -2 || bal == 2) {
                    this.realizarRotacion(n, bal == 2, padre);
                }
            } else {
                if (n.getClave().compareTo(buscar) < 0) {
                    // derecha
                    retornar = eliminarAux(n.getDerecho(), buscar, n);
                } else {
                    retornar = eliminarAux(n.getIzquierdo(), buscar, n);
                }
                if (retornar) {
                    // solo voy a haber o recalcular la altura solamente si hubo una modificacion en
                    // el arbol
                    n.recalcularAltura();
                    int bal = this.calcularBalance(n);
                    if (bal == -2 || bal == 2) {
                        this.realizarRotacion(n, bal == 2, padre);
                    }
                }
            }
        }
        return retornar;
    }

    private void eliminarCaso1(NodoAVLDicc n, NodoAVLDicc padre) {
        // caso donde no tiene hijos
        if (padre == null) {
            // caso raiz
            this.raiz = null;
        } else {
            // caso no raiz
            if (padre.getClave().compareTo(n.getClave()) < 0) {
                padre.setDerecho(null);
            } else {
                padre.setIzquierdo(null);
            }

        }
    }

    private void eliminarCaso2(NodoAVLDicc n, NodoAVLDicc padre) {
        // caso donde al menos tiene un hijo (n)

        if (padre == null) {
            if (n.getDerecho() != null) {
                this.raiz = n.getDerecho();
            } else {
                this.raiz = n.getIzquierdo();
            }

        } else {
            int num = padre.getClave().compareTo(n.getClave());
            if (num < 0) {
                // veo el lado donde esta el nodo a eliminar
                if (n.getDerecho() != null) {
                    padre.setDerecho(n.getDerecho());

                } else {
                    padre.setDerecho(n.getIzquierdo());

                }

            } else {
                if (n.getDerecho() != null) {
                    padre.setIzquierdo(n.getDerecho());
                } else {
                    padre.setIzquierdo(n.getIzquierdo());
                }

            }

        }

    }

    private void eliminarCaso3(NodoAVLDicc cand, NodoAVLDicc eliminar) {
       
        NodoAVLDicc aux;
        if (cand.getDerecho() == null) {
            // no tiene HD
            eliminar.setClave(cand.getClave());
            eliminar.setDato(cand.getDato());

            if (cand.getIzquierdo() != null) {
                // tiene HI
                eliminar.setIzquierdo(cand.getIzquierdo());
            } else {
                eliminar.setIzquierdo(null);
            }
        } else {
           
            aux = candidato(cand, eliminar);
            eliminar.setClave(aux.getClave());
            eliminar.setDato(aux.getDato());

        }

    }

    private NodoAVLDicc candidato(NodoAVLDicc n, NodoAVLDicc padreCandidato) {
        // caso donde si o si tiene HD
        NodoAVLDicc retornar;
        if (n.getDerecho() == null) {
            // el nodo no tiene a donde ya ir
            retornar = n;
            if (n.getIzquierdo() != null) {
                // si el hijo candidato tiene hijo izquierdo
                padreCandidato.setDerecho(n.getIzquierdo());
            } else {
                padreCandidato.setDerecho(null);
            }

        } else {
            retornar = candidato(n.getDerecho(), n); 
        }
        n.recalcularAltura();
            int bal = calcularBalance(n);
            if (bal == -2 || bal == 2) {
                realizarRotacion(n, bal == 2, padreCandidato);
            }
        return retornar;

    }

    private int casoHijo(NodoAVLDicc n) {
        int retornar;

        if (n.getDerecho() == null && n.getIzquierdo() == null) {// es hoja
            retornar = 1;

        } else {
            if (n.getDerecho() != null && n.getIzquierdo() != null) {
                retornar = 3;
            } else {
                retornar = 2;
            }
        }

        return retornar;
    }

    // ----
    public Object obtenerDato(Comparable clave) {
        Object retornar = null;

        if (!esVacio()) {
            NodoAVLDicc aux = buscarNodo(this.raiz, clave);
            if (aux != null) {
                retornar = aux.getDato();
            }

        }

        return retornar;

    }

    public boolean existeClave(Comparable clave) {
        boolean exito = false;
        if (!esVacio()) {
            if (buscarNodo(this.raiz, clave) != null) {
                exito = true;
            }
        }

        return exito;
    }

    private NodoAVLDicc buscarNodo(NodoAVLDicc n, Comparable clave) {
        NodoAVLDicc nodoEncontrado = null;

        if (n != null) {
            int comparacion = n.getClave().compareTo(clave);

            if (comparacion == 0) {
                nodoEncontrado = n;
            } else {
                if (comparacion < 0) {
                    nodoEncontrado = buscarNodo(n.getDerecho(), clave);
                } else {
                    nodoEncontrado = buscarNodo(n.getIzquierdo(), clave);
                }
            }
        }

        return nodoEncontrado;
    }

    public Lista listarClaves() {
        Lista lista = new Lista();

        if (!esVacio()) {
            listarClavesAux(this.raiz, lista);

        }

        return lista;

    }

    private void listarClavesAux(NodoAVLDicc n, Lista lis) {

        if (n != null) {

            listarClavesAux(n.getIzquierdo(), lis);
            lis.insertar(n.getClave(), lis.longitud() + 1);
            listarClavesAux(n.getDerecho(), lis);

        }

    }

    public Lista listarDatos() {
        Lista lista = new Lista();

        if (!esVacio()) {
            listarDatosAux(this.raiz, lista);

        }

        return lista;

    }

    public Lista listarRango(Comparable min, Comparable max) {
        Lista retornar = new Lista();
        if (!esVacio()) {
            listarRangoAux(this.raiz, retornar, min, max);
        }
        return retornar;
    }

    private void listarRangoAux(NodoAVLDicc n, Lista lista, Comparable min, Comparable max) {

        if (n != null) {
            Comparable clave = n.getClave();
            int comparadoMin = clave.compareTo(min);
            int comparadoMax = clave.compareTo(max);
            if (comparadoMin >= 0 && comparadoMax <= 0) {
                // la clave esta dentro del rango
                lista.insertar(clave, lista.longitud() + 1);
            }

            if (comparadoMin > 0) {
                listarRangoAux(n.getIzquierdo(), lista, min, max);
            }

            if (comparadoMax < 0) {
                listarRangoAux(n.getDerecho(), lista, min, max);
            }

        }

    }

    private void listarDatosAux(NodoAVLDicc n, Lista lis) {

        if (n != null) {

            listarDatosAux(n.getIzquierdo(), lis);
            lis.insertar(n.getDato(), lis.longitud() + 1);
            listarDatosAux(n.getDerecho(), lis);

        }

    }

    public String toString() {
        String cadena;
        if (esVacio()) {
            cadena = "Arbol vacio";
        } else {
            cadena = auxString(this.raiz);
        }

        return cadena;
    }

    private String auxString(NodoAVLDicc r) {
        String cad = "";

        if (r != null) {

            cad = r.getClave() + ": ALTURA(" + r.getAltura() + ")\n \t";

            if (r.getIzquierdo() != null) {
                cad = cad + "HI=" + r.getIzquierdo().getClave() + "\t";
            } else {
                cad = cad + "HI= -  \t";
            }

            if (r.getDerecho() != null) {
                cad = cad + "HD=" + r.getDerecho().getClave() + "\t";
            } else {
                cad = cad + "HD= -  \t";
            }
            // salto
            cad = cad + "\n";
            // paso recursivo
            // izquierdo

            if (r.getIzquierdo() != null) {
                cad = cad + auxString(r.getIzquierdo());
            }

            if (r.getDerecho() != null) {
                cad = cad + auxString(r.getDerecho());
            }

        }

        return cad;
    }

}
