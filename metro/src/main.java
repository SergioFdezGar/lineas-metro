

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import net.datastructures.AdjacencyListGraph;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.InvalidPositionException;
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

		
		Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> grafo=new AdjacencyListGraph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>>();
		File f=new File("metro.dat");
		
		try {
			Scanner	datos = new Scanner(f);
			//grafo=construyeGrafo(datos);
			grafo=leerMostrar(datos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> leerMostrar(Scanner datos) {
		// TODO Auto-generated method stub
		Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> grafo= new AdjacencyListGraph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>>();
		
		ElementoDecorado<Estacion> n1=null;
		ElementoDecorado<Estacion> n2= null;
		ElementoDecorado<Tramo>tramo=null;
		
		//Declaración de objetos y variables
		Iterator<Vertex<ElementoDecorado<Estacion>>> iter=null;
		
		
		//Lectura del archivo de texto
		datos.useDelimiter("[\\;\\n]"); //Delimitadores ';' y salto de linea
		
		int pasada=1;
		while (datos.hasNext()){
			System.out.println("\tPasada # "+ pasada);
			iter=grafo.vertices().iterator();
			System.out.println("\tNumero vertices: " + grafo.numVertices());
			
			
			//Creamos las estaciones de origen y destino
			Estacion est_origen= new Estacion(datos.next());
			Estacion est_destino= new Estacion(datos.next());
			//Creamos el elementoDecorado para crear los vertices
			n1=new ElementoDecorado<Estacion>(est_origen);
			n2=new ElementoDecorado<Estacion>(est_destino);
			
			//Creamos el tramo entre las estaciones de origen y destino
			datos.next();
			datos.next();
			Tramo camino= new Tramo(0, 0);
			//Tramo camino= new Tramo(datos.nextInt(), datos.nextInt());
			//Creamos el elementoDecorado para crear la arista
			tramo=new ElementoDecorado<Tramo>(camino);
			
			
			//Objeto Vertex auxiliar.
			
			Vertex<ElementoDecorado<Estacion>> u=null;
			Vertex<ElementoDecorado<Estacion>> v=null;
			Vertex<ElementoDecorado<Estacion>> aux= null;
			boolean n1_existe=false;
			boolean n2_existe=false;
			
			//Parte en la que vemos si coinciden los vertices
			while (iter.hasNext()){
				
				//Comprobamos si ya existen los vértices
				aux= iter.next();
				
				//Si la estación origen existe.
				if(aux.element().equals(n1)){
					System.out.println("Estacion Origen "+ aux.element().toString() + " repetida.");
					u=aux;
					n1_existe=true;
				}
				
				if(aux.element().equals(n2)){
					
					System.out.println("Estacion Destino "+ aux.element().toString() + " repetida.");
					v=aux;
					n2_existe=true;
				}
			}
			
			//Creamos los vertices para poder agregarlos al grafo
			if(!n1_existe){
				u= grafo.insertVertex(n1);
			}
			
			if(!n2_existe){
				v= grafo.insertVertex(n2);
			}
			
			
			if(!grafo.areAdjacent(u, v)){
				grafo.insertEdge(u, v, tramo);
			}else{
				System.out.println("El vertice ya existe");
			}
			
			pasada++;
		}
		
		return grafo;
	}


	protected static Graph<ElementoDecorado<Estacion>, String> construyeGrafo(Scanner datos) {
		// TODO Auto-generated method stub
		Graph<ElementoDecorado<Estacion>, String> grafo= new AdjacencyListGraph<ElementoDecorado<Estacion>, String>();
		ElementoDecorado<Estacion> n1=null;
		ElementoDecorado<Estacion> n2= null;	
		
		//Declaración de objetos y variables
		Iterator<Vertex<ElementoDecorado<Estacion>>> iter=null;
		
		
		
		datos.useDelimiter("[\\;\\n]");
		
		while (datos.hasNext()){
			
			iter=grafo.vertices().iterator();
			
			Estacion est_origen= new Estacion(datos.next());
			Estacion est_destino= new Estacion(datos.next());
			
			n1=new ElementoDecorado<Estacion>(est_origen);
			n2=new ElementoDecorado<Estacion>(est_destino);

			
			//Comprobaciones para ver si esxiten los vértices.
			
			
			Vertex<ElementoDecorado<Estacion>> u= grafo.insertVertex(n1);
			Vertex<ElementoDecorado<Estacion>> v= grafo.insertVertex(n2);
			
			Vertex<ElementoDecorado<Estacion>> aux= grafo.insertVertex(null);
			
			
			
			//Parte en la que vemos si coinciden los vertices
			while (iter.hasNext()){
				//Comprobamos si ya existen los vértices
				aux= iter.next();
				
				//Si la estación origen existe.
				if(!u.equals(aux)){
				}else{
					System.out.println("Estacion origen "+ est_origen.getNombre() + " repetida.");
					u=aux;
					continue;
				}
				
				if(!v.equals(aux)){
				}else{
					System.out.println("Estacion destino "+ est_destino.getNombre() + " repetida.");
					v=aux;
					continue;
				}
			}
			
			
			
			if(!grafo.areAdjacent(u, v)){
				grafo.insertEdge(u, v, datos.next());
			}else{
				System.out.println("El vertice ya existe");
			}
		
			
			
		
				
			//Ahora debemos ver si existen los elementos en el grafo
			
			//Si no  se introducen ambos,
			
			//Si es sólo uno, se agrega el que no con la arista correspondiente
			
			//Si exiten los dos y no están conectado se crea la arista
			
			
			
			System.out.println(datos.next());
//			System.out.println(n);
//			n++;
//			

//			
//			System.out.println(n);
//			
//			if(!grafo.areAdjacent(u, v)){
//				grafo.insertEdge(u, v, null);
//			}
			
			
		}
		System.out.println("Listado de vertices");
		Vertex<ElementoDecorado<Estacion>> lectura= grafo.insertVertex(null);
		while (iter.hasNext()){
			lectura=iter.next();
			System.out.println(lectura.toString());
		}
		
		return grafo;
	}
}