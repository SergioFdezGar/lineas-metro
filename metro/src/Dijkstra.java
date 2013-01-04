import net.datastructures.AdaptablePriorityQueue;
import net.datastructures.DefaultComparator;
import net.datastructures.Edge;
import net.datastructures.Entry;
import net.datastructures.Graph;
import net.datastructures.HeapAdaptablePriorityQueue;
import net.datastructures.Vertex;

public class Dijkstra<V, E> {
	/* Valor para infinito */
	private static final Integer INFINITO = Integer.MAX_VALUE;
	/* Grafo de entrada */
	private Graph<V, E> grafo;
	/* Clave DECORADA PARA EL PESO DE LA ARISTA */
	private Object WEIGHT;
	/* Clave decorada para las distancias entre vertices */
	private Object DIST = new Object();
	/* Clave decorada para las entradas en la cola con proiridad */
	private Object ENTRY = new Object();
	/* Cola auxiliar con prioridad */
	private AdaptablePriorityQueue<Integer, Vertex<V>> Q;

	public void execute(Graph<V, E> g, Vertex<V>  s, Object w) {
		grafo = g;
		WEIGHT = w;
		System.out.println("Valor de WEIGHT:"+ WEIGHT);
		DefaultComparator dc = new DefaultComparator();
		Q = new HeapAdaptablePriorityQueue<Integer, Vertex<V>>(dc);
		dijkstraVisit(s);
	}

	public int getDist(Vertex<Estacion>  u) {
		return (Integer) u.get(DIST);
	}

	public void dijkstraVisit(Vertex<V>  v) {
		for (Vertex<V> u : grafo.vertices()) {
			int u_dist;

			if (u == v) {
				u_dist = 0;
			} else {
				u_dist = INFINITO;
			}

			Entry<Integer, Vertex<V>> u_entry = Q.insert(u_dist, u);
			u.put(ENTRY, u_entry);
		}

		while (!Q.isEmpty()) {

			Entry<Integer, Vertex<V>> u_entry = Q.min();
			Vertex<V> u = u_entry.getValue();
			int u_dist = u_entry.getKey();
			Q.remove(u_entry);
			u.put(DIST, u_dist);
			u.remove(ENTRY);

			if (u_dist == INFINITO) {
				continue;
			}

			for (Edge<E> e : grafo.incidentEdges(u)) {
				Vertex<V> z = grafo.opposite(u, e);
				Entry<Integer, Vertex<V>> z_entry = (Entry<Integer, Vertex<V>>)z.get(ENTRY);

				if (z_entry != null) {
					int e_weight = (Integer) e.get(WEIGHT);
					int z_dist = z_entry.getKey();

					if (u_dist + e_weight < z_dist) {
						Q.replaceKey(z_entry, u_dist + e_weight);

					}
				}
			}
		}
	}

}
