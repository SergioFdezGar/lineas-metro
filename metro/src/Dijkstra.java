import java.util.Iterator;
import java.util.PriorityQueue;

import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.Vertex;

public class Dijkstra<V, E> {
        /* Valor para infinito */
        private static final Integer INFINITO = Integer.MAX_VALUE;
        /* Grafo de entrada */
        private Graph<ElementoDecorado<Estacion>, Tramo> grafo;
        /* Cola auxiliar con prioridad */
        private PriorityQueue<ElementoDecorado<Estacion>> Q;

        public void execute(Graph<ElementoDecorado<Estacion>, Tramo> g, Vertex<ElementoDecorado<Estacion>>  s) {
                grafo = g;
                Q = new PriorityQueue<ElementoDecorado<Estacion>>();
                dijkstraVisit(s);
        }

        public int getDist(Vertex<ElementoDecorado<Estacion>>  u) {
                return (Integer) u.element().distancia();
        }

        public void dijkstraVisit(Vertex<ElementoDecorado<Estacion>>  v) {
        //Inicializamos los valores de los vertices para realizar el cálculo.
    	for (Vertex<ElementoDecorado<Estacion>> u : grafo.vertices()) {
            int u_dist;

            if (u == v) {
                    u_dist = 0;
                    u.element().setParent(v.element());
            } else {
                    u_dist = INFINITO;
            }
            
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
            
            //Buscamos la estación en el grafo para acceder a los adyacentes.
            Vertex<ElementoDecorado<Estacion>> inicio= buscarVertice(u_entry);
            inicio.element().setVisitado(true);
            for (Edge<Tramo> e : grafo.incidentEdges(inicio)) {
                Vertex<ElementoDecorado<Estacion>> z = grafo.opposite(inicio, e);

                if (!z.element().visitado()) {
                    int e_weight = e.element().getduracion();
                    int z_dist=z.element().distancia();
                    int tiempo=u_dist + e_weight;
                    
                    if (tiempo < z_dist) {
                    	//Modificamos el tiempo en el extremo.
                    	z.element().setDistancia(tiempo);
                    	//Indicamos cual es el nodo del que proviene
                    	z.element().setParent(inicio.element());
                    	
                    	Vertex<ElementoDecorado<Estacion>> modificado= buscarVertice(z.element());
                    	
                    	//Modificamos la informacion en el grafo
                    	grafo.replace(modificado, z.element());
                    	                    	
                    }
                	//Actualizamos la cola de los vertices.
                	Q.clear();
                	for (Vertex<ElementoDecorado<Estacion>> u : grafo.vertices()) {
                        if (!u.element().visitado()) {
                        	Q.add(u.element());
                        }
                    }
                }
            }
        }
    }

		private Vertex<ElementoDecorado<Estacion>> buscarVertice(
				ElementoDecorado<Estacion> u_entry) {
			Vertex<ElementoDecorado<Estacion>> source=null;
			
			Iterator <Vertex<ElementoDecorado<Estacion>>> iter_vertices= grafo.vertices().iterator();
			while(iter_vertices.hasNext()){
				source=iter_vertices.next();
				
				if(source.element().equals(u_entry)){
					break;
				}
				else{
					source=null;
				}
			}
			
			return source;
		}

		public String devolverCamino(Vertex<ElementoDecorado<Estacion>> destino){
			//Mientras que no lleguemos al vertice de inicio
			
			Vertex<ElementoDecorado<Estacion>> aux=destino;
			while(!aux.element().parent().equals(aux.element())){
				System.out.print(aux.element().elemento().getNombre() + " <-- ");
				aux= buscarVertice(aux.element().parent());	
			}
			return "\n Final";
		}
}


