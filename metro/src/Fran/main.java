package Fran;




import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
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

		Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> grafo = new AdjacencyListGraph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>>();
		File f = new File("metro.dat");

		try {
			Scanner datos = new Scanner(f);
			grafo = construyeGrafo(datos);
			System.out.println("Listado de estaciones y adyacentes");
			//mostrarGrafo(grafo);
			dijkstra(grafo);
			// System.out.print(grafo.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void dijkstra(Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> grafo) {
		Scanner leer =new Scanner (System.in);
		Vertex<ElementoDecorado<Estacion>> est1=null;
		Vertex<ElementoDecorado<Estacion>> est2=null;
		System.out.print("Introduce el nombre de la estacion de origen\n");
		est1=obtenerestacion(grafo,leer.nextLine());
		if(est1==null){
			System.out.print("ERROR");
		}
		System.out.print("Introduce el nombre de la estacion de destino\n");
		est2=obtenerestacion(grafo,leer.nextLine());
		if(est2==null){
			System.out.print("ERROR");
		}
		
		
		boolean es=false;
		ArrayList <Vertex<ElementoDecorado<Estacion>>> informacion=new ArrayList<Vertex<ElementoDecorado<Estacion>>> ();
		informacion.add(est1);
		Vertex<ElementoDecorado<Estacion>> est3=est1;
		Vertex<ElementoDecorado<Estacion>> est4=null;
		while(es==false){

			est4=obtenercaminos(grafo,est3,informacion);
			if(est2.element().elemento.getNombre().equals(est4.element().elemento.getNombre())){
				es=true;
				informacion.add(est4);
			}
			else{
				informacion.add(est4);
				est3=est4;
			}
		}
		
		
		for(int i=0; i<informacion.size();i++){
			System.out.print(informacion.get(i).element().elemento.getNombre());
		}
	
		
	}


	private static Vertex<ElementoDecorado<Estacion>> obtenerestacion(Graph<ElementoDecorado<Estacion>, 
		ElementoDecorado<Tramo>> grafo,String nombre){
		
		Vertex<ElementoDecorado<Estacion>> estacion=null;
		Iterator <Vertex<ElementoDecorado<Estacion>>> iter= grafo.vertices().iterator();
		boolean es=false;
		while(iter.hasNext()&&es==false){
			Vertex<ElementoDecorado<Estacion>> v_est1= iter.next();
			if(nombre.equals(v_est1.element().elemento.getNombre())){
				estacion=v_est1;
				es=true;
				
			}
		}
		return estacion;
		
	}
	private static Vertex<ElementoDecorado<Estacion>> obtenercaminos(Graph<ElementoDecorado<Estacion>, 
		ElementoDecorado<Tramo>> grafo, Vertex<ElementoDecorado<Estacion>> estacion, ArrayList<Vertex<ElementoDecorado<Estacion>>> informacion){
		PriorityQueue <Integer>list_aux = new PriorityQueue ();
		Iterator <Edge<ElementoDecorado<Tramo>>> iter_edges= grafo.incidentEdges(estacion).iterator();
		/*LLENAMOS LA COLA CON EL PESO DE LAS ARISTAS ARISTAS*/
		while(iter_edges.hasNext()){
			list_aux.add(iter_edges.next().element().elemento.getduracion());
		}
		
		/*COMO LA CABEZA DE LA COLA CONTIENE LA DURACI�N DE LA ARISTA CON MENORO PESO*/
		boolean fin=false;
		iter_edges= grafo.incidentEdges(estacion).iterator();
		Edge<ElementoDecorado<Tramo>> aux=null;
		Edge<ElementoDecorado<Tramo>> aux1=null;
		while(iter_edges.hasNext()&&fin==false){
			aux1=iter_edges.next();
			if(list_aux.peek()==aux1.element().elemento.getduracion()){
				aux=aux1;
				fin=true;
			}
	
		}
		fin=false;
		Vertex<ElementoDecorado<Estacion>> ver_aux=grafo.opposite(estacion, aux);
		/*Esto es para la estacion esta repetida */
		for(int i=0;i<informacion.size()&&fin==false;i++){
			if(informacion.get(i).element().elemento.getNombre().equals(ver_aux.element().elemento.getNombre())){
				list_aux.poll();
				aux=repetido(list_aux,(iter_edges= grafo.incidentEdges(estacion).iterator()));
				fin=true;
			}
		}
		return grafo.opposite(estacion, aux);
	}
	
	private static Edge<ElementoDecorado<Tramo>> repetido(PriorityQueue <Integer>list_aux,Iterator <Edge<ElementoDecorado<Tramo>>> iter_edges){
		/*Si esta repetido como hemos quitado su arista de la cola cogemos la siguiente */
		Edge<ElementoDecorado<Tramo>> aux=null;
		Edge<ElementoDecorado<Tramo>> aux1=null;
		boolean fin=false;
		while(iter_edges.hasNext()&&fin==false){
			aux1=iter_edges.next();
			if(list_aux.peek()==aux1.element().elemento.getduracion()){
				aux=aux1;
				fin=true;
			}
	
		}
		return aux;
	}

	private static void mostrarGrafo(
			Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> grafo) {
		// TODO Auto-generated method stub
		Iterator <Vertex<ElementoDecorado<Estacion>>> iter= grafo.vertices().iterator();
		
		while(iter.hasNext()){
			//Vamos pasando de estación en estacion y mostramos los vértices adyacentes
			//Con la correspondiente arista.
			
			//Recogemos el vétice
			Vertex<ElementoDecorado<Estacion>> v_est= iter.next();
			System.out.print(v_est.element().elemento.getNombre());
			System.out.println("\nCorrespondencias con:");
			Iterator <Edge<ElementoDecorado<Tramo>>> iter_edges= grafo.incidentEdges(v_est).iterator();
			
			while(iter_edges.hasNext()){
				Edge<ElementoDecorado<Tramo>> ed_est= iter_edges.next();
				ElementoDecorado<Tramo> elemento_tramo= ed_est.element();
				//Hay que mostrar el nombre de la estacion destino teniendo el cuenta la arista (tramo)
				System.out.println("Estacion -> " + grafo.opposite(v_est, ed_est).element().elemento.getNombre());
				System.out.println("\t --> Linea:" + elemento_tramo.elemento().getLinea());
				System.out.println("\t --> Duracion:" + elemento_tramo.elemento().getduracion());
				
				
			}
			
		}
		
	}

	private static Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> construyeGrafo(
			Scanner datos) {
		// TODO Auto-generated method stub
		Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> grafo = new AdjacencyListGraph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>>();

		ElementoDecorado<Estacion> n1 = null;
		ElementoDecorado<Estacion> n2 = null;
		ElementoDecorado<Tramo> tramo = null;

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

			Tramo camino = new Tramo(linea, duracion);
			// Tramo camino= new Tramo(datos.nextInt(), datos.nextInt());
			// Creamos el elementoDecorado para crear la arista
			tramo = new ElementoDecorado<Tramo>(camino);

			// Objeto Vertex auxiliar.

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
			//System.out.println("\tNumero vertices: " + grafo.numVertices());
			pasada++;
		}

		return grafo;
	}

}