package Fran;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import net.datastructures.AdaptablePriorityQueue;
import net.datastructures.DefaultComparator;
import net.datastructures.Edge;
import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.Entry;
import net.datastructures.Graph;
import net.datastructures.HeapAdaptablePriorityQueue;
import net.datastructures.InvalidKeyException;
import net.datastructures.Vertex;

public class Dkjstra {
	/* Valor para infinito */
	private static final Integer INFINITO = Integer.MAX_VALUE;
	private Object ENTRY = new Object();
	private static ArrayList <Vertex<ElementoDecorado<Estacion>>> informacion;
	private static int distacia;
	
	public <V> void calcular(Graph<ElementoDecorado<Estacion>, ElementoDecorado<Tramo>> grafo,Vertex<ElementoDecorado<Estacion>> estacion){
		DefaultComparator dc = new DefaultComparator();
		AdaptablePriorityQueue <Integer,Vertex<ElementoDecorado<Estacion>>> Q ;
		Q=new HeapAdaptablePriorityQueue<Integer,Vertex<ElementoDecorado<Estacion>>>(dc);
		Iterator<Vertex<ElementoDecorado<Estacion>>> iter = grafo.vertices().iterator();
		int u_dist;
		while(iter.hasNext()){
			Vertex<ElementoDecorado<Estacion>> aux=iter.next();
			if(estacion.element().elemento.getNombre().equals(aux.element().elemento.getNombre())){
				u_dist=0;
			}
			else{
				u_dist=INFINITO;
			}
			aux.element().distance=u_dist;
			Entry<Integer, Vertex<ElementoDecorado<Estacion>>> u_entry = Q.insert(u_dist, aux);
			aux.put(ENTRY, u_entry);
		}
		boolean fin=false;
		while(!Q.isEmpty()&&fin==false){
			Entry<Integer, Vertex<ElementoDecorado<Estacion>>> u_entry = Q.min();
			Vertex<ElementoDecorado<Estacion>> u = u_entry.getValue();
			informacion.add(u);
			Vertex<ElementoDecorado<Estacion>> min=Q.min().getValue();
			Q.removeMin();
			Iterator <Edge<ElementoDecorado<Tramo>>> iter_edges= grafo.incidentEdges(min).iterator();
			int i=0;
			while(iter_edges.hasNext()&&fin==false){
				Edge<ElementoDecorado<Tramo>> camino= iter_edges.next();
				Vertex<ElementoDecorado<Estacion>>union= grafo.opposite(min, camino);
				Entry<Integer, Vertex<ElementoDecorado<Estacion>>> union_entry = (Entry<Integer, Vertex<ElementoDecorado<Estacion>>>)union.get(ENTRY);
				if (union_entry != null) {
					int e_weight = (Integer) camino.element().elemento.getduracion();
					int z_dist = union_entry.getKey();

					if (union_entry.getKey() + e_weight < z_dist) {
						Q.replaceKey(union_entry, union_entry.getKey() + e_weight);

					}
				}
				
			}
		
			
			
			
		}
		
		/*System.out.print(informacion.size());*/
	}

	
}
