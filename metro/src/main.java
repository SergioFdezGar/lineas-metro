/**
 * 
 */
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

		
		Graph grafo=new AdjacencyListGraph();
		File f=new File("metro.dat");
		
		try {
			Scanner	datos = new Scanner(f);
			grafo=construyeGrafo(datos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected static Graph construyeGrafo(Scanner datos) {
		// TODO Auto-generated method stub
		Graph grafo= new AdjacencyListGraph();
		ElementoDecorado<Estacion> n1=null;
		ElementoDecorado<Estacion> n2= null;
//		
		
		//Declaración de objetos y variables
//		Iterator<Vertex<ElementoDecorado<Estacion>>> it;
		
		
		
		datos.useDelimiter("[\\;\\n]");
		
		while (datos.hasNext()){
			
			Estacion est_origen= new Estacion(datos.next());
			Estacion est_destino= new Estacion(datos.next());
			
			n1=new ElementoDecorado<Estacion>(est_origen);
			n2=new ElementoDecorado<Estacion>(est_destino);

			
			Vertex u= grafo.insertVertex(n1);
			Vertex v= grafo.insertVertex(n2);
			
			
			
			//Ahora debemos ver si existen los elementos en el grafo
			
			//Si no  se introducen ambos,
			
			//Si es sólo uno, se agrega el que no con la arista correspondiente
			
			//Si exiten los dos y no están conectado se crea la arista
			
			
			System.out.println(datos.next());
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
		
		
		
		return grafo;
	}
}