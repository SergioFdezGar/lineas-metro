

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

import net.datastructures.AdjacencyListGraph;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.Vertex;

/**
 * @author Sergio Fernandez Garcia, Fco. Jose Lucas Violero
 * 
 * 
 */
public class FernandezLucas_Fernandez {

	public static void main(String[] args) {
	
		Graph<ElementoDecorado<Estacion>, Tramo> grafo = new AdjacencyListGraph<ElementoDecorado<Estacion>, Tramo>();
		File f = new File("metro.dat");
	
		try {
			Scanner datos = new Scanner(f);
			grafo = construyeGrafo(datos);
			mostrarGrafo(grafo);
	
			do {
			/* Parte en la que recogemos dos estaciones y marcamos el camino
				mas corto.*/
				shortestPath(grafo);
	
			} while (quererintroducr());
	
			System.out.print("\n\t***BUEN VIAJE***");
	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static Graph<ElementoDecorado<Estacion>, Tramo> construyeGrafo(
			Scanner datos) {
		Graph<ElementoDecorado<Estacion>, Tramo> grafo = new AdjacencyListGraph<ElementoDecorado<Estacion>, Tramo>();

		ElementoDecorado<Estacion> n1 = null;
		ElementoDecorado<Estacion> n2 = null;
		Tramo tramo = null;

		/*Declaracion de objetos y variables*/
		Iterator<Vertex<ElementoDecorado<Estacion>>> iter = null;

		/*Lectura del archivo de texto*/
		datos.useDelimiter("[\\;\\n]"); /* Delimitadores ';' y salto de linea*/

		while (datos.hasNext()) {
			iter = grafo.vertices().iterator();

			/* Creamos las estaciones de origen y destino*/
			Estacion est_origen = new Estacion(datos.next());
			Estacion est_destino = new Estacion(datos.next());
			
			/* Creamos el elementoDecorado para crear los vertices*/
			n1 = new ElementoDecorado<Estacion>(est_origen);
			n2 = new ElementoDecorado<Estacion>(est_destino);

			/* Creamos el tramo entre las estaciones de origen y destino*/
			int linea = new Integer(datos.nextInt());
			int duracion = new Integer(datos.nextInt());
			tramo = new Tramo(linea, duracion);
			
			Vertex<ElementoDecorado<Estacion>> u = null;
			Vertex<ElementoDecorado<Estacion>> v = null;
			Vertex<ElementoDecorado<Estacion>> aux = null;
			boolean n1_existe = false;
			boolean n2_existe = false;

			/*Parte en la que vemos si coinciden los vertices*/
			while (iter.hasNext()) {

				/* Comprobamos si ya existen los vertices*/
				aux = iter.next();

				/*Si la estacion origen existe.*/
				if (aux.element().equals(n1)) {
					u = aux;
					n1_existe = true;
				}

				if (aux.element().equals(n2)) {
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
			} 

		}

		return grafo;
	}

	private static Vertex<ElementoDecorado<Estacion>> introducirdatos(
			Graph<ElementoDecorado<Estacion>, Tramo> G) {
		Vertex<ElementoDecorado<Estacion>> estacion = null;
		Scanner leer = new Scanner(System.in);
		/*buscamos si el nombre de la estacion corresponde con alguna del grafo*/
		while (estacion == null) {
			estacion = obtenerestacion(G, leer.nextLine());
			if (estacion == null) {
				System.out.print("\n\t**ERROR, estacion no encontrada**\n");
				System.out.print("\nIntroduce el  nombre de la estacion\n");
			}
		}
		return estacion;
	}

	private static void mostrarCamino(
			Graph<ElementoDecorado<Estacion>, Tramo> grafo) {

		Iterator<Vertex<ElementoDecorado<Estacion>>> iter = grafo.vertices()
				.iterator();
		/*
		 * El primer vertice no puede ser la primera estacion, porque no tiene
		 * arista 
		 */
		iter.next();
		/*
		 * La linea de control es la que une la estacion origen con la estacion
		 * siguiente que es la de destion
		 */
		int control = iter.next().element().aristaParent.getLinea();
		int ntransbordos = 0;
		/* Incializo los dos vertices el origen y destion */
		iter = grafo.vertices().iterator();
		Vertex<ElementoDecorado<Estacion>> v_est = iter.next();
		Vertex<ElementoDecorado<Estacion>> v_dest = null;
		
		System.out.print("\nEstacion: "+ v_est.element().elemento().getNombre()
				+ "\nCorrespondencia con: ");
		
		while (iter.hasNext()) {
			v_dest = iter.next();
			if (control != v_dest.element().aristaParent.getLinea()) {
				/*informacion del transbordo*/
				System.out.print("\n**Transbordo a la linea "+ v_dest.element().aristaParent.getLinea()
						+ " Direccion: "+ v_dest.element().elemento.getNombre() + "**");
				
				control = v_dest.element().aristaParent.getLinea();
				ntransbordos++;
			} 
			
			System.out.print(" \n \t--> "+v_dest.element().elemento.getNombre());
			v_est = v_dest;
		}
		System.out.print("\n\t**FINAL DE TRAYECTO**\nTotal transbordos: "
				+ ntransbordos);

	}

	private static void mostrarGrafo(
			Graph<ElementoDecorado<Estacion>, Tramo> grafo) {
		Iterator<Vertex<ElementoDecorado<Estacion>>> iter = grafo.vertices()
				.iterator();
		System.out.println("Listado de estaciones y adyacentes");
		while (iter.hasNext()) {
			/* Vamos pasando de estacion en estacion y mostramos los vertices adyacentes
			 Con la correspondiente arista. Recogemos el vertice*/
			
			Vertex<ElementoDecorado<Estacion>> v_est = iter.next();
			System.out.print("\nOrigen en: "+v_est.element().elemento().getNombre());
			System.out.println("\nCorrespondencias con:");
			Iterator<Edge<Tramo>> iter_edges = grafo.incidentEdges(v_est)
					.iterator();

			while (iter_edges.hasNext()) {
				/*Hay que mostrar el nombre de la estacion destino teniendo el
				 cuenta la arista (tramo)*/
				
				Edge<Tramo> ed_est = iter_edges.next();
				Tramo elemento_tramo = ed_est.element();
				/*Mostramos la informacion*/
				System.out.println("Estacion -> "
						+ grafo.opposite(v_est, ed_est).element().elemento()
								.getNombre());
				System.out.println("\t --> Linea:" + elemento_tramo.getLinea());
				System.out.println("\t --> Duracion:"
						+ elemento_tramo.getduracion());

			}

		}

	}

	private static Vertex<ElementoDecorado<Estacion>> obtenerestacion(
			Graph<ElementoDecorado<Estacion>, Tramo> grafo, String nombre) {
		/*Buscamos en el grafo el vertices correspondiente*/

		Vertex<ElementoDecorado<Estacion>> estacion = null;
		Iterator<Vertex<ElementoDecorado<Estacion>>> iter = grafo.vertices()
				.iterator();

		while (iter.hasNext()) {
			Vertex<ElementoDecorado<Estacion>> v_est1 = iter.next();
			if (nombre
					.equalsIgnoreCase(v_est1.element().elemento().getNombre())) {
				estacion = v_est1;
				break;
			}
		}
		return estacion;
	}

	
	private static boolean quererintroducr() {
		/*Preguntamos si el usuario quiere buscar mas recorridos*/
		
		Scanner leer = new Scanner(System.in);
		System.out.print("\nQuieres buscar mas recorridos? [Si/No]: ");
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
		/*Buscamos los nodos con la informacion entrante*/
		Vertex<ElementoDecorado<Estacion>> est_origen = null;
		Vertex<ElementoDecorado<Estacion>> est_destino = null;
		
		/*Si la estacion origen es igual que la estacion destino */
		do{
			System.out.print("\nIntroduce el nombre de la estacion de origen\n");
			est_origen = introducirdatos(G);

			System.out.print("Introduce el nombre de la estacion de destino\n");
			est_destino = introducirdatos(G);
			
		/*Si las estaciones son iguales nos informa del error*/
		if(est_origen.element().equals(est_destino.element())){
			System.out.print("\t**Error las estaciones son iguales**");
		}
		}while(est_origen.element().equals(est_destino.element()));
		

		// LLamamos a la funcion de Dijkstra.
		Dijkstra<ElementoDecorado<Estacion>, Tramo> dijkstra = new Dijkstra<ElementoDecorado<Estacion>, Tramo>();
		dijkstra.execute(G, est_origen);

		G = dijkstra.devolverCamino(est_destino);
		System.out.println("\n\t*** INFORMACION DEL CAMINO MAS CORTO ***");
		mostrarCamino(G);
		System.out.println("\nDistancia total: "
				+ dijkstra.getDist(est_destino) + " min");

	}

}

class Dijkstra<V, E> {
	/* Valor para infinito */
	private static final Integer INFINITO = Integer.MAX_VALUE;
	/* Grafo de entrada */
	private Graph<ElementoDecorado<Estacion>, Tramo> grafo;
	/* Cola auxiliar con prioridad */
	private PriorityQueue<ElementoDecorado<Estacion>> Q;

	private Vertex<ElementoDecorado<Estacion>> buscarVertice(
			ElementoDecorado<Estacion> u_entry) {
		/*buscamos el vertice pero en el grafo de dikjstra*/
		Vertex<ElementoDecorado<Estacion>> source = null;

		Iterator<Vertex<ElementoDecorado<Estacion>>> iter_vertices = grafo
				.vertices().iterator();
		while (iter_vertices.hasNext()) {
			source = iter_vertices.next();

			if (source.element().equals(u_entry)) {
				break;
			} else {
				source = null;
			}
		}

		return source;
	}

	public Graph<ElementoDecorado<Estacion>, Tramo> devolverCamino(
			Vertex<ElementoDecorado<Estacion>> destino) {
		/*devolvemos el grafo con las estaciones que forman el camino mas corto*/
		
		Graph<ElementoDecorado<Estacion>, Tramo> camino = new AdjacencyListGraph<ElementoDecorado<Estacion>, Tramo>();
		Vertex<ElementoDecorado<Estacion>> aux = destino;
		Vertex<ElementoDecorado<Estacion>> inicio = null;
		Stack<Vertex<ElementoDecorado<Estacion>>> stack_estaciones = new Stack<Vertex<ElementoDecorado<Estacion>>>();

		/* Volcamos a pila todas las estaciones*/
		while (!aux.element().equals(aux.element().parent())) {
			stack_estaciones.push(aux);
			aux = buscarVertice(aux.element().parent());
		}
		stack_estaciones.push(aux);

		/*Insertamos los datos en un nuevo grafo.
		 Buscamos su antecesor en el recorrido.*/
		inicio = stack_estaciones.pop();
		camino.insertVertex(inicio.element());
		while (!stack_estaciones.isEmpty()) {
			aux = stack_estaciones.pop();
			camino.insertVertex(aux.element());
			camino.insertEdge(inicio, aux, aux.element().aristaParent());
			inicio = aux;
		}

		return camino;
	}

	public void dijkstraVisit(Vertex<ElementoDecorado<Estacion>> v) {
		/*Inicializamos los valores de los vertices para realizar el calculo*/
		for (Vertex<ElementoDecorado<Estacion>> u : grafo.vertices()) {
			int u_dist = 0;

			if (u == v) {
				u_dist = 0;
				u.element().setParent(v.element());
			} else {
				u.element().setParent(null);
				u_dist = INFINITO;
			}

			u.element().setVisitado(false);
			u.element().setAristaParent(null);
			u.element().setDistancia(u_dist);
			grafo.replace(u, u.element());
			Q.add(u.element());
		}

		while (!Q.isEmpty()) {

			ElementoDecorado<Estacion> u_entry = Q.poll();
			int u_dist = u_entry.distancia();

			if (u_dist == INFINITO) {
				continue;
			}

			/*Buscamos la estacion en el grafo para acceder a los adyacentes*/
			Vertex<ElementoDecorado<Estacion>> inicio = buscarVertice(u_entry);
			inicio.element().setVisitado(true);
			for (Edge<Tramo> e : grafo.incidentEdges(inicio)) {
				Vertex<ElementoDecorado<Estacion>> z = grafo
						.opposite(inicio, e);

				if (!z.element().visitado() && e.element() != null) {
					int e_weight = e.element().getduracion();
					int z_dist = z.element().distancia();
					int tiempo = u_dist + e_weight;

					if (tiempo < z_dist) {
						// Modificamos el tiempo en el extremo.
						z.element().setDistancia(tiempo);
						// Indicamos cual es el nodo del que proviene
						z.element().setParent(inicio.element());
						// Guardamos la arista de la que proviene
						z.element().setAristaParent(e.element());

						Vertex<ElementoDecorado<Estacion>> modificado = buscarVertice(z
								.element());

						// Modificamos la informacion en el grafo
						grafo.replace(modificado, z.element());

					}
				}
			}
			// Actualizamos la cola de los vertices.
			Q.clear();
			for (Vertex<ElementoDecorado<Estacion>> u : grafo.vertices()) {
				if (!u.element().visitado()) {
					Q.add(u.element());
				}
			}
		}
	}

	public void execute(Graph<ElementoDecorado<Estacion>, Tramo> g,
			Vertex<ElementoDecorado<Estacion>> s) {
		grafo = g;
		Q = new PriorityQueue<ElementoDecorado<Estacion>>();
		dijkstraVisit(s);
	}

	public int getDist(Vertex<ElementoDecorado<Estacion>> u) {
		Vertex<ElementoDecorado<Estacion>> aux = buscarVertice(u.element());
		return aux.element().distancia();
	}
}

class ElementoDecorado<T> implements Comparable<ElementoDecorado<T>> {
	// Se usa el patron Decorator para decorar el elemento con atributos
	// adicionales
	T elemento; // Elemento de datos
	boolean visitado = false; // Atributo de nodo visitado
	ElementoDecorado<T> parent = null; // Nodo desde el que se accedia a este
	int distance = 0; // Distancia (en vertices) desde el nodo original
	Tramo aristaParent = null; // Arista del nodo parent

	public ElementoDecorado(T element) {
		elemento = element;
	}

	public Tramo aristaParent() {
		return aristaParent;
	}
	
	public int compareTo(ElementoDecorado<T> arg0) {
		/*sobrescribimos el comparte de la cola de prioridad*/
		
		int ord = 0;

		if (this.distance < (arg0.distancia()))
			ord = -1;
		if (this.distance > (arg0.distancia()))
			ord = 1;

		return ord;

	}

	public int distancia() {
		return distance;
	}

	// Metodos de consulta
	public T elemento() {
		return elemento;
	}

	public boolean equals(ElementoDecorado<T> n) {
		return elemento.equals(n.elemento()); // elemento tiene que tener
												// sobrescrito equals()
	}

	public ElementoDecorado<T> parent() {
		return parent;
	}

	public void setAristaParent(Tramo arista) {
		aristaParent = arista;
	}

	public void setDistancia(int d) {
		distance = d;
	}

	// Metodos de actualización
	public void setParent(ElementoDecorado<T> u) {
		parent = u;
	}

	public void setVisitado(boolean t) {
		visitado = t;
	}

	public String toString() {
		return elemento.toString(); // elemento tiene que tener sobrescrito
									// toString()
	}

	public boolean visitado() {
		return visitado;
	}

}

class Estacion {

	private String nombre;
	public Estacion(String nombre) {
		this.nombre = nombre;
	}
	
	public boolean equals(Object obj) {
		boolean igual = true;

		Estacion other = (Estacion) obj;

		if (nombre == null) {
			if (other.nombre != null) {
				igual = false;
			}
		} else {
			if (!nombre.equals(other.nombre)) {
				igual = false;
			}
		}
		return igual;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public String toString() {
		return "Estacion [nombre=" + nombre + "]";
	}

}

class Tramo {
	private int duracion;
	private int linea;

	/**
	 * @param linea
	 * @param duracion
	 */
	public Tramo(int duracion, int linea) {
		this.duracion = duracion;
		this.linea = linea;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Tramo)) {
			return false;
		}
		Tramo other = (Tramo) obj;
		if (linea != other.linea) {
			return false;
		}
		if (duracion != other.duracion) {
			return false;
		}
		return true;
	}

	/**
	 * @return the duracion
	 */
	public int getduracion() {
		return duracion;
	}

	/**
	 * @return the linea
	 */
	public int getLinea() {
		return linea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + linea;
		result = prime * result + duracion;
		return result;
	}

	/**
	 * @param duracion
	 *            the duracion to set
	 */
	public void setduracion(int duracion) {
		this.duracion = duracion;
	}

	/**
	 * @param linea
	 *            the linea to set
	 */
	public void setLinea(int linea) {
		this.linea = linea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tramo [linea=" + linea + ", duracion=" + duracion + "]";
	}

}
