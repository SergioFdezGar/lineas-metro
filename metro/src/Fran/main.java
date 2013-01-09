package Fran;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import net.datastructures.AdjacencyListGraph;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.Vertex;

/**
 * @author Sergio Fernandez Garcia, Fco. Jose Lucas Violero
 * 
 * 
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Graph<ElementoDecorado<Estacion>, Tramo> grafo = new AdjacencyListGraph<ElementoDecorado<Estacion>, Tramo>();
		File f = new File("metro.dat");

		try {
			Scanner datos = new Scanner(f);
			grafo = construyeGrafo(datos);
			System.out.println("Listado de estaciones y adyacentes");
			mostrarGrafo(grafo);

			// Parte en la que recogemos dos estaciones y marcamos el camino mas
			// corto.
			// Por ahora vamos a dar dos estaciones por codigo.
			do{
				shortestPath(grafo);
			
			}while(quererintroducr());
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean quererintroducr() {
		Scanner leer = new Scanner(System.in);
		System.out.print("Quieres buscar mas recorridos? [Si/No]: ");
		return respSiNO(leer.nextLine());
	}

	private static boolean respSiNO(String respuesta) {
		boolean b = false;
		if ((respuesta.compareToIgnoreCase("SI") == 0)
				|| (respuesta.compareToIgnoreCase("S") == 0)) {
			b = true;
		}
		return b;
	}

	private static void shortestPath(Graph<ElementoDecorado<Estacion>, Tramo> G) {
		// Buscamos los nodos con la informacion entrante

		
		Vertex<ElementoDecorado<Estacion>> est_origen=null;
		Edge<Tramo> tramo= null;
		ElementoDecorado<Tramo> decorado= null;
		Vertex<ElementoDecorado<Estacion>> est_destino=null;
		
		
		System.out.print("Introduce el nombre de la estacion de origen\n");
		est_origen=introducirdatos(G);
		
		System.out.print("Introduce el nombre de la estacion de destino\n");
		est_destino=introducirdatos(G);
	
	
		Iterator<Edge<Tramo>> iter_edges = G.incidentEdges(est_origen).iterator();
		tramo= iter_edges.next();
		decorado= new ElementoDecorado<Tramo>(tramo.element());
		
//		LLamamos a la funcíon de Dijkstra.
		Dijkstra<ElementoDecorado<Estacion>, Tramo> dijkstra= new Dijkstra<ElementoDecorado<Estacion>, Tramo>();
		dijkstra.execute(G,est_origen);
		System.out.println("Distancia : "+ dijkstra.getDist(est_destino)+ " min");
		System.out.println("Estacion Origen: "+dijkstra.devolverCamino(est_destino));
		
	}
	
	private static Vertex<ElementoDecorado<Estacion>> introducirdatos(Graph<ElementoDecorado<Estacion>, Tramo> G) {
		Vertex<ElementoDecorado<Estacion>> estacion=null;
		Scanner leer=new Scanner(System.in);
		while(estacion==null){
			estacion=obtenerestacion(G,leer.nextLine());
			if(estacion==null){
				System.out.print("ERROR, estacion no encontrada\n");
				System.out.print("Introduce de nuevo la estacion\n");
			}
		}
		return estacion;
	}

	private static Vertex<ElementoDecorado<Estacion>> obtenerestacion(Graph<ElementoDecorado<Estacion>, Tramo> grafo,String nombre){
			
			Vertex<ElementoDecorado<Estacion>> estacion=null;
			Iterator <Vertex<ElementoDecorado<Estacion>>> iter= grafo.vertices().iterator();
			
			while(iter.hasNext()){
				Vertex<ElementoDecorado<Estacion>> v_est1= iter.next();
				if(nombre.equalsIgnoreCase(v_est1.element().elemento().getNombre())){
					estacion= v_est1;
					break;
				}
			}
			return estacion;
	}

	private static void mostrarGrafo(
			Graph<ElementoDecorado<Estacion>,Tramo> grafo) {
		// TODO Auto-generated method stub
		Iterator<Vertex<ElementoDecorado<Estacion>>> iter = grafo.vertices()
				.iterator();

		while (iter.hasNext()) {
			// Vamos pasando de estación en estacion y mostramos los vértices
			// adyacentes
			// Con la correspondiente arista.

			// Recogemos el vétice
			Vertex<ElementoDecorado<Estacion>> v_est = iter.next();
			System.out.print(v_est.element().elemento().getNombre());
			System.out.println("\nCorrespondencias con:");
			Iterator<Edge<Tramo>> iter_edges = grafo
					.incidentEdges(v_est).iterator();

			while (iter_edges.hasNext()) {
				Edge<Tramo> ed_est = iter_edges.next();
				Tramo elemento_tramo = ed_est.element();
				// Hay que mostrar el nombre de la estacion destino teniendo el
				// cuenta la arista (tramo)
				System.out.println("Estacion -> "
						+ grafo.opposite(v_est, ed_est).element().elemento().getNombre());
				System.out.println("\t --> Linea:"
						+ elemento_tramo.getLinea());
				System.out.println("\t --> Duracion:"
						+ elemento_tramo.getduracion());

			}

		}

	}

	private static Graph<ElementoDecorado<Estacion>, Tramo> construyeGrafo(
			Scanner datos) {
		// TODO Auto-generated method stub
		Graph<ElementoDecorado<Estacion>, Tramo> grafo = new AdjacencyListGraph<ElementoDecorado<Estacion>, Tramo>();

		ElementoDecorado<Estacion> n1 = null;
		ElementoDecorado<Estacion> n2 = null;
		Tramo tramo = null;

		// Declaración de objetos y variables
		Iterator<Vertex<ElementoDecorado<Estacion>>> iter = null;

		// Lectura del archivo de texto
		datos.useDelimiter("[\\;\\n]"); // Delimitadores ';' y salto de linea

		int pasada = 1;
		while (datos.hasNext()) {
			//System.out.println("\tPasada # " + pasada);
			iter = grafo.vertices().iterator();

			// Creamos las estaciones de origen y destino
			Estacion est_origen = new Estacion(datos.next());
			Estacion est_destino = new Estacion(datos.next());
			// Creamos el elementoDecorado para crear los vertices
			n1 = new ElementoDecorado<Estacion>(est_origen);
			n2 = new ElementoDecorado<Estacion>(est_destino);

			// Creamos el tramo entre las estaciones de origen y destino
			int linea = new Integer(datos.nextInt());
			int duracion = new Integer(datos.nextInt());

			tramo = new Tramo(linea, duracion);

			Vertex<ElementoDecorado<Estacion>> u = null;
			Vertex<ElementoDecorado<Estacion>> v = null;
			Vertex<ElementoDecorado<Estacion>> aux = null;
			boolean n1_existe = false;
			boolean n2_existe = false;

			// Parte en la que vemos si coinciden los vertices
			while (iter.hasNext()) {

				// Comprobamos si ya existen los vértices
				aux = iter.next();

				// Si la estación origen existe.
				if (aux.element().equals(n1)) {
					// System.out.println("Estacion Origen "+
					// aux.element().toString() + " repetida.");
					u = aux;
					n1_existe = true;
				}

				if (aux.element().equals(n2)) {

					// System.out.println("Estacion Destino "+
					// aux.element().toString() + " repetida.");
					v = aux;
					n2_existe = true;
				}
			}

			// Creamos los vertices para poder agregarlos al grafo
			if (!n1_existe) {
				u = grafo.insertVertex(n1);
			}

			if (!n2_existe) {
				v = grafo.insertVertex(n2);
			}

			if (!grafo.areAdjacent(u, v)) {
				grafo.insertEdge(u, v, tramo);
			} else {
				// System.out.println("El vertice ya existe");
			}

		}

		return grafo;
	}

}