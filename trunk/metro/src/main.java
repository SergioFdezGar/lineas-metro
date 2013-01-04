import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import net.datastructures.AdjacencyListGraph;
import net.datastructures.Dijkstra;
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

		Graph<Estacion, Tramo> grafo = new AdjacencyListGraph<Estacion, Tramo>();
		//Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> grafo = new AdjacencyListGraph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>>();
		File f = new File("metro.dat");

		try {
			Scanner datos = new Scanner(f);
			grafo = construyeGrafo(datos);
			System.out.println("Listado de estaciones y adyacentes");
			mostrarGrafo(grafo);

			// Parte en la que recogemos dos estaciones y marcamos el camino más
			// corto.
			// Por ahora vamos a dar dos estaciones por codigo.
			shortestPath(grafo, "Alonso Cano", "Colombia");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static <V, E> void shortestPath(
			Graph<Estacion, Tramo> G,
			String origen, String destino) {
		// TODO Auto-generated method stub
		// Buscamos los nodos con la información entrante

		Vertex<Estacion> est_origen=null;
		Edge<Tramo> tramo= null;
		Vertex<Estacion> est_destino=null;
		System.out.print("Introduce el nombre de la estacion de origen\n");
		est_origen=obtenerestacion(G,origen);
		if(est_origen==null){
			System.out.print("ERROR");
		}
		System.out.print("Introduce el nombre de la estacion de destino\n");
		est_destino=obtenerestacion(G,destino);
		if(est_destino==null){
			System.out.print("ERROR");
		}
		Iterator<Edge<Tramo>> iter_edges = G
				.incidentEdges(est_origen).iterator();
		tramo= iter_edges.next();
		
		//LLamamos a la funcíon de Dijkstra.
		Dijkstra<Estacion, Tramo> dijkstra= new Dijkstra<Estacion, Tramo>();
		dijkstra.execute(G,est_origen,(Integer) tramo.element().getduracion());
		System.out.println("Distancia: "+ dijkstra.getDist(est_destino));
	}
	
	private static Vertex<Estacion> obtenerestacion(Graph<Estacion, Tramo> grafo,String nombre){
			
			Vertex<Estacion> estacion=null;
			Iterator <Vertex<Estacion>> iter= grafo.vertices().iterator();
			
			while(iter.hasNext()){
				Vertex<Estacion> v_est1= iter.next();
				if(nombre.equals(v_est1.element().getNombre())){
					estacion= v_est1;
					break;
				}
			}
			return estacion;
	}

	private static void mostrarGrafo(
			Graph<Estacion, Tramo> grafo) {
		// TODO Auto-generated method stub
		Iterator<Vertex<Estacion>> iter = grafo.vertices()
				.iterator();

		while (iter.hasNext()) {
			// Vamos pasando de estación en estacion y mostramos los vértices
			// adyacentes
			// Con la correspondiente arista.

			// Recogemos el vétice
			Vertex<Estacion> v_est = iter.next();
			System.out.print(v_est.element().getNombre());
			System.out.println("\nCorrespondencias con:");
			Iterator<Edge<Tramo>> iter_edges = grafo
					.incidentEdges(v_est).iterator();

			while (iter_edges.hasNext()) {
				Edge<Tramo> ed_est = iter_edges.next();
				Tramo elemento_tramo = ed_est.element();
				// Hay que mostrar el nombre de la estacion destino teniendo el
				// cuenta la arista (tramo)
				System.out.println("Estacion -> "
						+ grafo.opposite(v_est, ed_est).element().getNombre());
				System.out.println("\t --> Linea:"
						+ elemento_tramo.getLinea());
				System.out.println("\t --> Duracion:"
						+ elemento_tramo.getduracion());

			}

		}

	}

	private static Graph<Estacion, Tramo> construyeGrafo(
			Scanner datos) {
		// TODO Auto-generated method stub
		Graph<Estacion, Tramo> grafo =
				new AdjacencyListGraph<Estacion, Tramo>();

//		Estacion n1 = null;
//		Estacion n2 = null;
		Tramo tramo = null;

		// Declaración de objetos y variables
		Iterator<Vertex<Estacion>> iter = null;

		// Lectura del archivo de texto
		datos.useDelimiter("[\\;\\n]"); // Delimitadores ';' y salto de linea

		while (datos.hasNext()) {
			iter = grafo.vertices().iterator();

			// Creamos las estaciones de origen y destino
			Estacion est_origen = new Estacion(datos.next());
			Estacion est_destino = new Estacion(datos.next());
			// Creamos el elementoDecorado para crear los vertices
//			n1 = new ElementoDecorado<Estacion>(est_origen);
//			n2 = new ElementoDecorado<Estacion>(est_destino);

			// Creamos el tramo entre las estaciones de origen y destino
			int linea = new Integer(datos.nextInt());
			int duracion = new Integer(datos.nextInt());

			tramo= new Tramo(linea, duracion);
			// Tramo camino= new Tramo(datos.nextInt(), datos.nextInt());
			// Creamos el elementoDecorado para crear la arista
//			tramo = new ElementoDecorado<Tramo>(camino);

			Vertex<Estacion> u = null;
			Vertex<Estacion> v = null;
			Vertex<Estacion> aux = null;
			boolean n1_existe = false;
			boolean n2_existe = false;

			// Parte en la que vemos si coinciden los vertices
			while (iter.hasNext()) {

				// Comprobamos si ya existen los vértices
				aux = iter.next();

				// Si la estación origen existe.
				if (aux.element().equals(est_origen)) {
					u = aux;
					n1_existe = true;
				}

				if (aux.element().equals(est_destino)) {
					v = aux;
					n2_existe = true;
				}
			}

			// Creamos los vertices para poder agregarlos al grafo
			if (!n1_existe) {
				u = grafo.insertVertex(est_origen);
			}

			if (!n2_existe) {
				v = grafo.insertVertex(est_destino);
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