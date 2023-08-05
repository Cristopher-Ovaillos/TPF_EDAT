package SistemaTrenes;

import java.util.HashMap;
import java.util.Scanner;
import grafos.GrafoEtiquetado;
import conjuntitas.Diccionario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import lineales.dinamicas.Lista;

public class TrenesSA {
    private static Scanner scanner = new Scanner(System.in);
    private static final String LOG_RUTA = "src\\Lote\\log.txt";
    private static final String LOTE_RUTA = "src\\Lote\\Lote.txt";
    private static final Diccionario estaciones = new Diccionario();
    private static final Diccionario trenes = new Diccionario();
    private static final GrafoEtiquetado rieles = new GrafoEtiquetado();
    private static final HashMap<String, Lista> lineas = new HashMap<>();

    public static void main(String[] args) {
        int opcion;
        // Aquí se cargaría inicialmente el lote fijo de estaciones, trenes y líneas
        // Por ejemplo, puedes crear listas o mapas para almacenar esta información.
        do {
            System.out.println("=== Menu de opciones ===");
            System.out.println("1. CARGAR LOTE");
            System.out.println("2. ABM de trenes");
            System.out.println("3. ABM de estaciones");
            System.out.println("4. ABM de lineas");
            System.out.println("5. ABM de la red de rieles");
            System.out.println("6. Consulta sobre trenes");
            System.out.println("7. Consultas sobre estaciones");
            System.out.println("8. Consultas sobre viajes");
            System.out.println("9. Mostrar sistema");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Realizar el ABM de trenes (Altas, Bajas, Modificaciones)
                    cargarLote();
                    break;
                case 2:
                    // Realizar el ABM de trenes (Altas, Bajas, Modificaciones)
                    ABMTrenes();
                    break;
                case 3:
                    // Realizar el ABM de estaciones (Altas, Bajas, Modificaciones)
                    ABMEstaciones();
                    break;
                case 4:
                    // Realizar el ABM de lineas (Altas, Bajas, Modificaciones)
                    ABMLineas();
                    break;
                case 5:
                    // Realizar el ABM de la red de rieles (Altas, Bajas, Modificaciones)
                    ABMRieles();
                    break;
                case 6:
                    // Consultas sobre trenes
                    consultaTrenes();
                    break;
                case 7:
                    // Consultas sobre estaciones
                    consultaEstaciones();
                    break;
                case 8:
                    // Consultas sobre viajes
                    consultasViajes();
                    break;
                case 9:
                    // Mostrar sistema (estructuras de datos cargadas)
                    mostrarSistema();
                    break;
                case 10:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    registrarLog("ESTADO FINAL");
                    registrarLog("___________________________________________");
                    registrarLog(estaciones.toString());
                    registrarLog(lineas.toString());
                    registrarLog(rieles.toString());
                    registrarLog(trenes.toString());

                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }

        } while (opcion != 10);

        scanner.close();
    }

    private static void consultasViajes() {
        int opcion;
        do {
            System.out.println("=== Menu de Viajes ===");
            System.out.println("1. Obtener camino que pase por menos estaciones");
            System.out.println("2. Obtener camino de menor distancia en kilometros");
            System.out.println("3. Obtener todos los caminos sin pasar por una estacion C");
            System.out.println("4. Verificar si es posible llegar con maximo X kilometros");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            System.out.print("");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    obtenerCaminoMenosEstaciones();
                    break;
                case 2:
                    obtenerCaminoMenorDistancia();
                    break;
                case 3:
                    obtenerCaminosSinEstacionC();
                    break;
                case 4:
                    verificarPosibilidadLlegarMaximoKilometros();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 5);
        System.out.println("");

    }

    private static void obtenerCaminoMenosEstaciones() {
        mostrarEstaciones();
        System.out.print("Ingrese el nombre de la estacion A: ");
        String estacionA = scanner.nextLine().trim();
        System.out.print("Ingrese el nombre de la estacion B: ");
        String estacionB = scanner.nextLine().trim();

        Lista camino = rieles.caminoMasCorto(estacionA, estacionB);
        if (camino.esVacia()) {
            System.out.println("    NO EXISTE CONEXION ESTRE LAS ESTACIONES");
        } else {
            System.out.println("Camino de menos estaciones: \n     " + camino.toString());

        }

    }

    private static void obtenerCaminoMenorDistancia() {
        System.out.print("Ingrese el nombre de la estacion A: ");
        String estacionA = scanner.nextLine().trim();
        System.out.print("Ingrese el nombre de la estacion B: ");
        String estacionB = scanner.nextLine().trim();

        Camino camino = rieles.caminoMasCortoKM(estacionA, estacionB);
        if (camino.verticesVisitadosEsVacia()) {
            System.out.println("    NO EXISTE CONEXION ESTRE LAS ESTACIONES");
        } else {
            System.out.println("Camino de menos estaciones: \n     " + camino.toString());
        }

    }

    private static void obtenerCaminosSinEstacionC() {
        System.out.print("Ingrese el nombre de la estacion A: ");
        String estacionA = scanner.nextLine().trim();
        System.out.print("Ingrese el nombre de la estacion B: ");
        String estacionB = scanner.nextLine().trim();
        System.out.print("Ingrese el nombre de la estacion C a evitar: ");
        String estacionC = scanner.nextLine().trim();

        Lista caminos = rieles.caminosExceptoEstacion(estacionA, estacionB, estacionC);
        // devuelve una lista el cual tiene adentro una instancia camino
        if (caminos.esVacia() || estacionC == estacionB) {
            System.out.println("    No hay ruta");
        } else {
            int tamanio = caminos.longitud();
            int i = 1;
            System.out.println("Caminos sin pasar por " + estacionC);
            System.out.println("");
            while (i <= tamanio) {
                System.out.println(i + "_" + caminos.recuperar(i).toString());
                System.out.println("");
                i++;
            }
        }

    }

    private static void verificarPosibilidadLlegarMaximoKilometros() {
        System.out.print("Ingrese el nombre de la estacion A: ");
        String estacionA = scanner.nextLine().trim();
        System.out.print("Ingrese el nombre de la estacion B: ");
        String estacionB = scanner.nextLine().trim();
        System.out.print("Ingrese la cantidad maxima de kilometros permitidos: ");
        int maxKilometros = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        Camino camino = rieles.caminoConMaximoKM(estacionA, estacionB, maxKilometros);
        if (camino.verticesVisitadosEsVacia()) {
            System.out.println("    No es posible");
        } else {
            System.out
                    .println("Es posible llegar con maximo " + maxKilometros + " kilometros: \n  " + camino.toString());
        }

    }

    //
    private static void consultaEstaciones() {
        System.out.println("=== Consulta de Estaciones ===");
        int opcion;

        do {
            System.out.println("1. Mostrar informacion de una estacion por nombre.");
            System.out.println("2. Listar estaciones por subcadena de nombre.");
            System.out.println("3. Salir");
            System.out.print("Ingrese la opcion deseada: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

            switch (opcion) {
                case 1:
                    mostrarInformacionEstacion();
                    break;
                case 2:
                    listarEstacionesPorSubcadena();
                    break;
                case 3:
                    System.out.println("Volver al menu anterior.");
                    break;
                default:
                    System.out.println("Opcion invalida. Por favor, ingrese una opcion valida.");
            }

        } while (opcion != 3);

        System.out.println("");
    }

    private static void mostrarInformacionEstacion() {
        String buscar;

        System.out.println("MOSTRAR INFORMACION DE ESTACION");
        mostrarEstaciones();
        System.out.print("Ingrese el NOMBRE de la estacion: ");
        buscar = scanner.nextLine();

        if (estaciones.existeClave(buscar)) {
            Estacion mostrar = (Estacion) estaciones.obtenerDato(buscar);
            System.out.println("    " + mostrar.toString());
        } else {
            System.out.println("No se encontro la estacion con el nombre especificado.");
        }
    }

    private static void listarEstacionesPorSubcadena() {
        String buscar;
        System.out.print("Ingrese el NOMBRE de la estacion: ");
        buscar = scanner.nextLine().trim(); // Usamos trim() para eliminar espacios en blanco al inicio y al final.
        Lista aux = estaciones.listarRango(buscar, buscar + "ZZZZZZZZZZZZZZZZ");
        if (buscar.equals("") && aux.esVacia()) {
            System.out.println("    No se encontraron estaciones que empiecen con '" + buscar + "'.");
        } else {
            System.out.println("Estaciones que empiezan con '" + buscar + "':");
            System.out.println(aux.toString());
        }
    }

    //
    private static void consultaTrenes() {
        System.out.println("=== Consulta de Trenes ===");
        int opcion;

        do {
            System.out.println("1. Ver informacion de tren");
            System.out.println("2. Verificar si esta destinado a alguna linea y mostrar las ciudades que visitaria");
            System.out.println("3. Salir");
            System.out.print("Ingrese la opcion deseada: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

            switch (opcion) {
                case 1:
                    verInformacionTren();
                    break;
                case 2:
                    verLineaDeTren();
                    break;
                case 3:
                    System.out.println("Volver al menu anterior.");
                    break;
                default:
                    System.out.println("Opcion invalida. Por favor, ingrese una opcion valida.");
            }

        } while (opcion != 3);

        System.out.println("");
    }

    private static void verInformacionTren() {
        Lista lis = new Lista();
        int ver;
        lis = trenes.listarClaves();
        String colClaves = lis.toString();
        System.out.println("    LISTA DE TRENES: " + colClaves);
        System.out.print("    ingrese el codigo para ver la informacion: ");
        ver = scanner.nextInt();
        scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

        while (!trenes.existeClave(ver)) {
            System.out.println("    El codigo no existe. INTENTELO DE NUEVO");
            ver = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea pendiente
        }
        Tren obj = (Tren) trenes.obtenerDato(ver);
        String mostrar = obj.toString();
        System.out.println(mostrar);

        System.out.println("");
    }

    private static void verLineaDeTren() {
        Lista lis = trenes.listarClaves();
        System.out.println("    LISTA DE TRENES: " + lis.toString());
        System.out.print("    Ingrese el codigo para ver la informacion: ");
        int ver = scanner.nextInt();
        scanner.nextLine(); // Consumir el carácter de nueva linea pendiente

        if (trenes.existeClave(ver)) {
            Tren obj = (Tren) trenes.obtenerDato(ver);
            String nombreLinea = obj.getLineaAsignada();

            if (nombreLinea.equals("no-asignada")) {
                System.out.println("    No tiene linea asignada.");
            } else {
                Lista aux = lineas.get(nombreLinea);
                if (aux != null) {
                    System.out.println("    Pertenece a la linea " + nombreLinea);
                    int longitud = aux.longitud();
                    int pos = 1;
                    System.out.println("    CIUDADES POR LAS CUALES PASARIA: ");
                    while (pos <= longitud) {
                        Estacion est = (Estacion) aux.recuperar(pos);
                        System.out.println("    *" + est.getCiudad());
                        pos++;
                    }
                } else {
                    System.out.println("    No hay estaciones asociadas a esta línea.");
                }
            }
        } else {
            System.out.println("    El codigo no existe. INTENTELO DE NUEVO");
        }
        System.out.println("");
    }

    private static void cargarLote() {

        try (FileReader fileReader = new FileReader(LOTE_RUTA);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String linea;
            while ((linea = bufferedReader.readLine()) != null) {

                cargarLoteAux(linea);

            }

        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
        registrarLog(estaciones.toString());
        registrarLog(lineas.toString());
        registrarLog(rieles.toString());
        registrarLog(trenes.toString());

    }

    private static void cargarLoteAux(String linea) {
        if (!linea.isEmpty()) {
            StringTokenizer dato = new StringTokenizer(linea, ";");
            switch (dato.nextToken()) {
                case "E":
                    String nombre = dato.nextToken();
                    String calle = dato.nextToken();
                    String numero = dato.nextToken();
                    String ciudad = dato.nextToken();
                    String codigoPostal = dato.nextToken();
                    int cantidadVias = Integer.parseInt(dato.nextToken());
                    int cantidadPlataformas = Integer.parseInt(dato.nextToken());
                    Estacion e = new Estacion(nombre, calle, numero, ciudad, codigoPostal, cantidadVias,
                            cantidadPlataformas);
                    // cargo diccionario estaciones y ademas lo agrego al grafo
                    if (estaciones.insertar(nombre, e) && rieles.insertarVertice(nombre)) {
                        System.out.println("ESTACION " + nombre + " CARGADO.");
                    }
                    break;
                case "T":
                    // T;234;diesel;5;6;Mitre
                    //
                    int id = Integer.parseInt(dato.nextToken());
                    String tipoPropulsion = dato.nextToken();
                    int cantPasajeros = Integer.parseInt(dato.nextToken());
                    int cantVagones = Integer.parseInt(dato.nextToken());
                    String lin = dato.nextToken();
                    Tren t = new Tren(id, tipoPropulsion, cantPasajeros, cantVagones, lin);

                    // corregir
                    if (trenes.insertar(id, t)) {
                        if (lineas.containsKey(lin)) {
                            // osea la linea existe entonces lo puedo agregar
                            System.out.println("TREN " + id + " CARGADO A LINEA " + lin);
                            trenes.insertar(id, t);
                        } else {

                            System.out.println("TREN " + id + " CARGADO SIN LINEA");
                        }

                    } else {
                        System.out.println("TREN NO AGREGADO");
                    }

                    break;
                case "R":
                    String est1 = dato.nextToken();
                    String est2 = dato.nextToken();
                    int km = Integer.parseInt(dato.nextToken());
                    if (rieles.insertarArco(est1, est2, km)) {
                        System.out.println("RIEL CARGADO: (" + est1 + "<--" + km + " KM-->" + est2 + ").");
                    }

                    break;
                case "L":

                    String nombreLinea = dato.nextToken();

                    if (lineas.containsKey(nombreLinea)) {
                        System.out.println("La linea ya existe.");
                    } else {
                        Lista LisEstaciones = new Lista();
                        // hasMoreTokens() Este método devuelve un valor booleano que indica si hay más
                        // tokens disponibles en la cadena o no.
                        while (dato.hasMoreTokens()) {
                            String nombreEstacion = dato.nextToken();
                            // veo si existe la estacion en mi diccionario de estaciones
                            if (estaciones.existeClave(nombreEstacion)) {

                                Object aux = estaciones.obtenerDato(nombreEstacion);
                                LisEstaciones.insertar(aux, LisEstaciones.longitud() + 1);
                            }
                        }
                        lineas.put(nombreLinea, LisEstaciones);
                        System.out.println("LINEA   " + nombreLinea + " YA AGREGADA.");

                    }
                    break;

            }

        }
    }

    // Por ejemplo, métodos para realizar las ABM, consultas, etc.
    private static void ABMTrenes() {
        int opcion;

        do {
            System.out.println("=== ABM de trenes ===");
            System.out.println("1. Alta de tren");
            System.out.println("2. Baja de tren");
            System.out.println("3. Modificar tren");
            System.out.println("4. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

            switch (opcion) {
                case 1:
                    agregarTrenes();
                    break;
                case 2:
                    eliminarTren();
                    break;
                case 3:
                    modificarTren();
                    break;
                case 4:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 4);
        System.out.println("");
    }

    private static void mostrarTrenes() {
        Lista lis = new Lista();
        lis = trenes.listarClaves();
        String colClaves = lis.toString();
        System.out.println("    LISTA DE TRENES: " + colClaves);
    }

    private static void eliminarTren() {
        int eliminar;
        System.out.println("ELIMINAR TREN");
        mostrarTrenes();
        System.out.print("    Ingrese el codigo del tren a eliminar: ");
        eliminar = scanner.nextInt();
        scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

        if (!trenes.eliminar(eliminar)) {
            System.out.println("    El codigo no existe.");
        } else {
            System.out.println("TREN ELIMINADO");
            registrarLog("  Se elimino el Tren " + eliminar);
        }

    }

    private static void modificarTren() {

        int modificar;
        System.out.println("MODIFICAR TREN");
        mostrarTrenes();
        System.out.print("    Elija el codigo del tren a modificar: ");
        modificar = scanner.nextInt();
        scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

        if (!trenes.existeClave(modificar)) {
            System.out.println("    El codigo no existe.");
        } else {
            Tren dato = (Tren) trenes.obtenerDato(modificar);
            modificarTrenAux(dato);
        }

    }

    private static void modificarTrenAux(Tren a) {
        int id = a.getIdentificador();
        System.out.println("MODIFICACION DEL TREN " + id);
        int mod;
        boolean stop = false;
        while (!stop) {
            System.out.println("    1-TIPO DE PROPULSION");
            System.out.println("    2-CANTIDAD DE VAGONES DE PASAJEROS");
            System.out.println("    3-CANTIDAD DE VAGONES DE CARGA");
            System.out.println("    4-LINEA ASIGNADA");
            System.out.println("    5-TERMINAR MODIFICACION");
            System.out.println("");
            mod = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

            switch (mod) {
                case 1:
                    String s = ingresarTipoPropulsion();
                    a.setTipoPropulsion(s);
                    registrarLog("  El Tren " + id + " se le modifico la propulsion");
                    break;
                case 2:
                    int c = ingresarCantidadPasajeros();
                    a.setCantidadVagonesPasajeros(c);
                    registrarLog("  El Tren " + id + " se le modifico la cantidad de pasajeros");
                    break;
                case 3:
                    int v = ingresarCantidadVagonesCarga();
                    a.setCantidadVagonesCarga(v);
                    registrarLog("  El Tren " + id + " se le modifico la cantidad de vagones");
                    break;
                case 4:
                    String d = ingresarLinea();
                    a.setLineaAsignada(d);
                    registrarLog("  El Tren " + id + " se le modifico la linea");
                    break;
                case 5:
                    stop = true;
                    break;
            }
        }
    }

    private static int ingresarCodigoTren() {
        int codigo;
        do {
            System.out.print("Ingrese el codigo del tren: ");
            codigo = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            if (trenes.existeClave(codigo)) {
                System.out.println("El codigo del tren ya existe. Ingrese otro codigo.");
            }
        } while (trenes.existeClave(codigo));

        return codigo;
    }

    private static String ingresarTipoPropulsion() {

        String tipoPropulsion;
        do {
            System.out.println("Ingrese el tipo de propulsion del tren:");
            System.out.println("1. Electricidad");
            System.out.println("2. Diesel");
            System.out.println("3. Fuel oil");
            System.out.println("4. Gasolina");
            System.out.println("5. Hibrido");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    tipoPropulsion = "Electricidad";
                    break;
                case 2:
                    tipoPropulsion = "Diesel";
                    break;
                case 3:
                    tipoPropulsion = "Fuel oil";
                    break;
                case 4:
                    tipoPropulsion = "Gasolina";
                    break;
                case 5:
                    tipoPropulsion = "Hibrido";
                    break;
                default:
                    tipoPropulsion = null;
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (tipoPropulsion == null);
        return tipoPropulsion;
    }

    private static int ingresarCantidadPasajeros() {
        int cant;
        System.out.print("Ingrese la cantidad de vagones para pasajeros: ");
        cant = scanner.nextInt();
        return cant;
    }

    private static int ingresarCantidadVagonesCarga() {

        int cant;
        System.out.print("Ingrese la cantidad de vagones para carga: ");
        cant = scanner.nextInt();
        return cant;
    }

    private static String ingresarLinea() {
        String retornar;
        int opcion;
        Lista texto = new Lista();
        System.out.println("Ingrese la linea en la que esta siendo utilizado : ");
        mostrarLineasEligir(texto);
        System.out.print("Opcion N: ");
        opcion = scanner.nextInt();
        while (opcion < 1 || opcion > texto.longitud()) {
            System.out.println("Opcion INCORRECTO");
            System.out.print("ingrese una nuevamente: ");
            opcion = scanner.nextInt();
        }
        retornar = (String) texto.recuperar(opcion);
        return retornar;
    }

    private static void mostrarLineasEligir(Lista texto) {
        int contador = 1;
        // este es un for each
        for (String linea : lineas.keySet()) {
            String lineaFormateada = contador + "-" + linea;
            System.out.println(lineaFormateada);
            texto.insertar(linea, contador);
            contador++;
        }

        System.out.println(contador + "- 'no-asignado'");
        texto.insertar("no-asignado", contador);

    }

    private static void agregarTrenes() {

        System.out.println("=== Alta de Tren ===");

        int codigo = ingresarCodigoTren();
        String tipoPropulsion = ingresarTipoPropulsion();
        int cantPasajeros = ingresarCantidadPasajeros();
        int cantVagonesCarga = ingresarCantidadVagonesCarga();
        String linea = ingresarLinea();

        Tren tren = new Tren(codigo, tipoPropulsion, cantPasajeros, cantVagonesCarga, linea);
        if (trenes.insertar(codigo, tren)) {
            System.out.println("Tren agregado exitosamente.");
            registrarLog("  Se agrego un Tren: " + codigo);
        } else {
            System.out.println("El codigo del tren ya existe en el sistema.");
        }
    }

    //
    private static void ABMEstaciones() {
        int opcion;
        do {
            System.out.println("=== ABM de estaciones ===");
            System.out.println("1. Alta de estacion");
            System.out.println("2. Baja de estacion");
            System.out.println("3. Modificar estacion");
            System.out.println("4. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

            switch (opcion) {
                case 1:
                    agregarEstacion();
                    break;
                case 2:
                    eliminarEstacion();
                    break;
                case 3:
                    modificarEstacion();
                    break;
                case 4:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 4);
        System.out.println("");
    }

    private static void agregarEstacion() {
        System.out.println("=== Alta de Estacion ===");

        String nombEstacion = ingresarNombreEstacion();
        String calle = ingresarCalle();
        String numCalle = ingresarNumeroCalle();
        String nombCiudad = ingresarCiudad();
        String codP = ingresarCodigoPostal();
        int cantV = ingresarCantidadVias();
        int cantP = ingresarCantidadPlataformas();
        Estacion est = new Estacion(nombEstacion, calle, numCalle, nombCiudad, codP, cantV, cantP);

        if (estaciones.insertar(nombEstacion, est) && rieles.insertarVertice(nombEstacion)) {
            System.out.println("ESTACION " + nombEstacion + " CARGADO.");
            registrarLog("  Se agrego una Estacion: " + nombEstacion);
        }
    }

    private static String ingresarNombreEstacion() {
        String retornar;
        System.out.println("Ingrese nombre de la estacion:");
        retornar = scanner.nextLine();

        while (estaciones.existeClave(retornar)) {
            System.out.println("El nombre de la estacion ya existe. Ingrese otro nombre:");
            retornar = scanner.nextLine();
        }

        return retornar;
    }

    private static String ingresarCalle() {
        String retornar;
        System.out.println("Ingrese la calle de la estacion:");
        retornar = scanner.nextLine();
        return retornar;
    }

    private static String ingresarNumeroCalle() {
        String retornar;
        System.out.println("Ingrese el numero de la calle de la estacion:");
        retornar = scanner.nextLine();
        return retornar;
    }

    private static String ingresarCiudad() {
        String retornar;
        System.out.println("Ingrese la ciudad:");
        retornar = scanner.nextLine();
        return retornar;
    }

    private static String ingresarCodigoPostal() {
        String retornar;
        System.out.println("Ingrese el codigo postal:");
        retornar = scanner.nextLine();
        return retornar;
    }

    private static int ingresarCantidadVias() {
        int retornar;
        System.out.println("Ingrese la cantidad de vias:");
        retornar = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de nueva línea pendiente
        return retornar;
    }

    private static int ingresarCantidadPlataformas() {
        int retornar;
        System.out.println("Ingrese la cantidad de plataformas:");
        retornar = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de nueva línea pendiente
        return retornar;
    }

    private static void eliminarEstacion() {

        String eliminar;

        System.out.println("ELIMINAR ESTACION");
        mostrarEstaciones();
        System.out.println("    ingrese el NOMBRE de la estacion a eliminar: ");
        eliminar = scanner.nextLine().trim();

        if (!estaciones.eliminar(eliminar)) {
            System.out.println("    el NOMBRE de la estacion no existe. ");
        } else {
            rieles.eliminarVertice(eliminar);
            System.out.println("ESTACION " + eliminar + " ELIMINADO ");
            registrarLog("  Se elimino la estacion: " + eliminar);
        }

    }

    private static void mostrarEstaciones() {
        Lista lis = new Lista();
        lis = estaciones.listarClaves();
        String colClaves = lis.toString();
        System.out.println("    LISTA DE ESTACIONES: " + colClaves);
    }

    private static void modificarEstacion() {

        Estacion dato;
        String modificar;
        System.out.println("MODIFICAR ESTACION");
        mostrarEstaciones();
        System.out.println("    Elige el NOMBRE de la estacion a modificar");
        modificar = scanner.nextLine();

        if (!estaciones.existeClave(modificar)) {
            System.out.println("    el codigo no existe.");
        } else {
            dato = (Estacion) estaciones.obtenerDato(modificar);
            modificarEstacionAux(dato);
        }

    }

    private static void modificarEstacionAux(Estacion obj) {
        String nombre = obj.getNombre();
        System.out.println("MODIFICACION DE LA ESTACION " + nombre);
        int mod;
        boolean stop = false;
        while (!stop) {
            System.out.println("    1-DOMICILIO");
            System.out.println("    2-CANTIDAD DE VIAS");
            System.out.println("    3-CANTIDAD DE PLATAFORMAS");
            System.out.println("    4-TERMINAR MODIFICACION");
            System.out.println("");
            mod = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

            switch (mod) {
                case 1:
                    // supuse que se puede modificar el domicilio tipo si hubo un error al ingresar
                    // el domicilio
                    String calle = ingresarCalle();
                    String numCalle = ingresarNumeroCalle();
                    String nombCiudad = ingresarCiudad();
                    String codP = ingresarCodigoPostal();
                    obj.setCalle(calle);
                    obj.setNumero(numCalle);
                    obj.setCiudad(nombCiudad);
                    obj.setCodigoPostal(codP);
                    registrarLog("  La estacion " + nombre + " modifico la direccion");
                    break;
                case 2:
                    int cantV = ingresarCantidadVias();
                    obj.setCantidadVias(cantV);
                    registrarLog("  La estacion " + nombre + " modifico la cantidad de vias");
                    break;
                case 3:
                    int cantP = ingresarCantidadPlataformas();
                    obj.setCantidadPlataformas(cantP);
                    registrarLog("  La estacion " + nombre + " modifico la cantidad de plataformas");
                    break;
                case 4:
                    stop = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    //
    private static void ABMLineas() {

        int opcion;
        do {
            System.out.println("=== ABM de lineas ===");
            System.out.println("1. Alta de linea");
            System.out.println("2. Baja de linea");
            System.out.println("3. Modificar linea");
            System.out.println("4. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Alta de línea: Llamar al método que permita agregar una nueva línea
                    // completando los datos necesarios.
                    // Puedes solicitar los datos al usuario por consola.
                    agregarLinea();
                    break;
                case 2:
                    // Baja de línea: Llamar al método que permita eliminar una línea existente
                    // según un nombre de línea proporcionado por el usuario.
                    eliminarLinea();
                    break;
                case 3:
                    // Modificar línea: Llamar al método que permita modificar los datos mutables
                    // de una línea existente, como la cantidad de estaciones, etc.
                    // Puedes solicitar al usuario el nombre de la línea y los datos a modificar.
                    modificarLinea();
                    break;
                case 4:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 4);
        System.out.println("");
    }

    private static void agregarLinea() {
        System.out.println("=== Alta de Linea ===");
        Lista colEst = new Lista();
        String nombLinea = ingresarNombreLinea();
        lineas.put(nombLinea, colEst);

    }

    private static void eliminarLinea() {

        String nombreLin;
        System.out.println("=== Baja de Linea ===");
        System.out.println("Lista de Lineas: " + lineas.keySet());
        System.out.println("Ingrese el NOMBRE de la linea a eliminar");
        nombreLin = scanner.nextLine().trim();

        if (!lineas.containsKey(nombreLin)) {
            System.out.println("   El nombre de linea no existe. ");
        } else {
            lineas.remove(nombreLin);
            System.out.println("    " + nombreLin + " ELIMINADA");
        }
    }

    private static void modificarLinea() {
        String nombreLin;
        System.out.println("=== Modificacion Linea ===");
        System.out.println("Lista de Lineas: " + lineas.keySet());
        nombreLin = scanner.nextLine();

        if (!lineas.containsKey(nombreLin)) {
            System.out.println("   El nombre de linea no existe.");
        } else {
            modificarLineaAux(nombreLin);
        }
    }

    private static void modificarLineaAux(String name) {
        System.out.println("MODIFICAR LA LINEA " + name);
        Lista aux = lineas.get(name);

        int mod;
        boolean stop = false;
        while (!stop) {
            System.out.println();
            System.out.println("    1-AGREGAR ESTACIONES A LA LISTA");
            System.out.println("    2-ELIMINAR ESTACIONES DE LA LISTA");
            System.out.println("    3-VER ESTACIONES DE LINEA");
            System.out.println("    4-TERMINAR MODIFICACION");
            System.out.println("");
            mod = scanner.nextInt();

            switch (mod) {
                case 1:
                    agregarEstacionesALinea(aux, name);

                    break;
                case 2:
                    eliminarEstacionesALinea(aux, name);

                    break;
                case 3:
                    System.out.println("    ESTACIONES DE LA LINEA: ");
                    int longitud = aux.longitud();
                    int pos = 1;
                    if (!aux.esVacia()) {
                        while (pos <= longitud) {
                            Estacion est = (Estacion) aux.recuperar(pos);
                            System.out.println(est.getNombre());
                            pos++;
                        }
                    }
                    break;
                case 4:
                    stop = true;
                    break;

            }

        }
    }

    private static void agregarEstacionesALinea(Lista lis, String name) {
        // la lista lis es el objeto de la key osea nuestra linea a modificar
        // la lis es una col de estaciones

        System.out.println("Estaciones en el sistema:");
        mostrarEstaciones();
        System.out.println("");
        System.out.println("Estaciones actualmente en la linea: ");
        int longitud = lis.longitud();
        int pos = 1;
        if (!lis.esVacia()) {
            while (pos <= longitud) {
                Estacion est = (Estacion) lis.recuperar(pos);
                System.out.println(est.getNombre());
                pos++;
            }
        }

        System.out.println("    Ingrese el nombre de la estacion a agregar");
        String agregar = scanner.nextLine().trim();

        if (estaciones.existeClave(agregar)) {
            // la estacion existe entonces la agrego
            Estacion agr = (Estacion) estaciones.obtenerDato(agregar);
            if (lis.localizar(agr) == -1) {
                // no lo encontro entonces lo agrego
                System.out.println("    Estacion agregada a la linea");
                registrarLog("    Linea " + name + " agrego estaciones");
                lis.insertar(agr, lis.longitud() + 1);
            } else {
                System.out.println("    La Estacion ingresado con anterioridad");
            }
        } else {
            System.out.println("    Nombre de estacion equivocada");
        }

    }

    private static void eliminarEstacionesALinea(Lista lis, String name) {

        System.out.println("Estaciones actualmente en la linea: ");
        int longitud = lis.longitud();
        int pos = 1;
        if (!lis.esVacia()) {
            while (pos <= longitud) {
                Estacion est = (Estacion) lis.recuperar(pos);
                System.out.println(pos + "-" + est.getNombre());
                pos++;
            }
        }
        System.out.println(pos + "- SALIR");
        System.out.println("    Ingrese el Numero de la estacion a eliminar");
        int eliminar = scanner.nextInt();

        if (pos != eliminar) {
            // veo si la estacion a eliminar existe

            if (lis.eliminar(eliminar)) {
                System.out.println("    La Estacion " + eliminar + " ELIMINADA de la lista");
                registrarLog("    Linea " + name + " elimino estaciones");
            } else {
                System.out.println("    Estacion no existente en la lista");
            }

        } else {
            System.out.println("    Fin proceso");
        }

    }

    private static String ingresarNombreLinea() {

        String retornar;
        System.out.println("Ingrese el nombre de la linea");
        retornar = scanner.nextLine();
        while (lineas.containsKey(retornar)) {
            System.out.println("    El Nombre de la linea ya existe. Intentelo de nuevo");
            retornar = scanner.nextLine();

        }

        return retornar;
    }

    private static void ABMRieles() {

        int opcion;
        do {
            System.out.println("=== ABM de la red de rieles ===");
            System.out.println("1. Alta de riel");
            System.out.println("2. Baja de riel");
            System.out.println("3. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            System.out.println("");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada
            switch (opcion) {
                case 1:
                    agregarRiel();
                    ;
                    break;
                case 2:
                    // según las estaciones de origen y destino proporcionadas por el usuario(se
                    // eliminan ambos sentidos).
                    eliminarRiel();
                    break;
                case 3:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 3);
        System.out.println("");
    }

    private static void agregarRiel() {
        String est1, est2;
        int km;
        System.out.println("=== ALTA RIEL ===");
        mostrarEstaciones();
        System.out.println("  Seleccione una estacion ");
        est1 = scanner.nextLine().trim();
        while (!estaciones.existeClave(est1)) {
            // no lo encontro
            System.out.println("    Error la estacion no existe. Intentelo de nuevo");
            est1 = scanner.nextLine();
        }
        System.out.println("  Seleccione otra estacion ");
        est2 = scanner.nextLine().trim();
        while (!estaciones.existeClave(est1)) {
            // no lo encontro
            System.out.println("    Error la estacion no existe. Intentelo de nuevo");
            est2 = scanner.nextLine();
        }

        System.out.println("    Ingrese los KM entre (" + est1 + " - " + est2 + ")");
        km = scanner.nextInt();

        rieles.insertarArco(est1, est2, km);
        registrarLog("  Se agrego un riel entre " + est1 + "-" + est2);
        System.out.println("RIEL INGRESADO CON EXITO");
    }

    private static void eliminarRiel() {

        System.out.println("=== BAJA RIEL ===");
        Lista aux = estaciones.listarClaves();
        String est1, est2;
        System.out.println("  Lista de estaciones: ");
        System.out.println(aux.toString());
        System.out.println("  Seleccione una estacion ");
        est1 = scanner.nextLine();
        while (!estaciones.existeClave(est1)) {
            // lo encontro
            System.out.println("    Error la estacion no existe. Intentelo de nuevo");
            est1 = scanner.nextLine();
        }
        System.out.println("  Seleccione otra estacion ");
        est2 = scanner.nextLine();
        while (!estaciones.existeClave(est2)) {
            // no lo encontro
            System.out.println("    Error la estacion no existe. Intentelo de nuevo");
            est2 = scanner.nextLine();
        }
        if (rieles.existeArco(est1, est2)) {
            // caso rieles
            rieles.eliminarArco(est1, est2);
            rieles.eliminarArco(est2, est1);
            System.out.println("RIEL ELIMINADO CON EXITO");
            registrarLog("  Se elimino un riel entre " + est1 + "-" + est2);

        } else {
            System.out.println("    No existe riel estre las estaciones");
        }
    }

    private static void mostrarSistema() {
        System.out.println("=== Mostrar Sistema ===");

        System.out.println("    *Estaciones:");
        System.out.println(estaciones.toString());

        System.out.println("    *Trenes:");
        System.out.println(trenes.toString());

        System.out.println("    *Red de Rieles:");
        System.out.println(rieles.toString());

        System.out.println("    *Lineas:");
        System.out.println(lineas.keySet());
    }

    private static void registrarLog(String mensaje) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_RUTA, true))) {
            writer.write(mensaje);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }

}
