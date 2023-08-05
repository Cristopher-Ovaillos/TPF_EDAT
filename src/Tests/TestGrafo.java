package Tests;

import grafos.GrafoEtiquetado;
import lineales.dinamicas.Lista;

public class TestGrafo {

        public static void main(String[] args) {
                GrafoEtiquetado grafo = new GrafoEtiquetado();
                grafo.insertarVertice('1');
                grafo.insertarVertice('2');
                grafo.insertarVertice('3');
                grafo.insertarVertice('4');
                grafo.insertarVertice('5');
                grafo.insertarVertice('6');
                grafo.insertarArco('1', '2', 1);
                grafo.insertarArco('1', '3', 3);
                grafo.insertarArco('2', '6', 2);
                grafo.insertarArco('6', '4', 4);
                grafo.insertarArco('6', '5', 2);
                grafo.insertarArco('3', '4', 5);
                grafo.insertarArco('3', '5', 1);
                grafo.insertarArco('5', '4', 1);
                grafo.eliminarArco('2', '1');
                System.out.println("TO STRING");
                System.out.println(grafo.toString());
                System.out.println("PROFUNDIDAD");
                System.out.println(grafo.listarEnProfundidad().toString());
                System.out.println("CAMINO MAS CORTO DESDE 1 A 4");
                System.out.println(grafo.caminoMasCorto('1', '4').toString());
                System.out.println("CAMINO MAS CORTO EN KM DESDE 1 A 4");
                System.out.println(grafo.caminoMasCortoKM('1', '4').toString());
                System.out.println("CAMINOS EXCEPTO UNA ESTACION DESDE EL PUNTO 1 A 4 SIN PASAR POR 3");
                Lista aux = grafo.caminosExceptoEstacion('1', '4', '3');
                int i = 1;
                while (aux.recuperar(i) != null) {
                        System.out.println(aux.recuperar(i).toString());
                        i++;
                }
                System.out.println("CAMINO DE 1 A 4 PERO CON UN TOPE DE 4");
                System.out.println(grafo.caminoConMaximoKM('1', '4', 5).toString());

                // grafo.eliminarArco('A', 'D');
                // System.out.println(grafo.existeArco('A','D'));
                // System.out.println(grafo.existeArco('D','A'));
                // System.out.println(grafo.existeVertice('C'));
                // System.out.println(grafo.toString());

                // System.out.println(grafo.listaCaminoMasRapido('A', 'C').toString());
        }

}
