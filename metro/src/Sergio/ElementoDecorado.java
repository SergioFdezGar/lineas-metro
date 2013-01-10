package Sergio;
public class ElementoDecorado <T> implements Comparable<ElementoDecorado<T>>{
  // Se usa el patrón Decorator para decorar el elemento con atributos
  // adicionales
  T elemento;                  // Elemento de datos
  boolean visitado=false;      // Atributo de nodo visitado
  ElementoDecorado<T> parent=null; // Nodo desde el que se accedió a éste
  int distance=0;              // Distancia (en vértices) desde el nodo original
  Tramo aristaParent=null;      //Arista del nodo parent

  public ElementoDecorado (T element){
    elemento=element;
  }

  //Métodos de consulta
  public T elemento(){
    return elemento;
  }
  public boolean visitado(){
    return visitado;
  }
  public ElementoDecorado<T> parent(){
    return parent;
  }
  public int distancia(){
    return distance;
  }
  public Tramo aristaParent(){
    return aristaParent;
  }

  // Métodos de actualización
  public void setParent(ElementoDecorado<T> u){
    parent=u;
  }
  public void setDistancia(int d){
    distance=d;
  }

  public void setVisitado(boolean t){
    visitado=t;
  }
  public void setAristaParent(Tramo arista){
    aristaParent=arista;
  }  
  
  public boolean equals (ElementoDecorado<T> n){
    return elemento.equals(n.elemento()); //elemento tiene que tener sobrescrito equals()
  }
  public String toString(){
    return elemento.toString();    //elemento tiene que tener sobrescrito toString()
  }

/* (non-Javadoc)
 * @see java.lang.Comparable#compareTo(java.lang.Object)
 */
@Override
public int compareTo(ElementoDecorado<T> arg0) {
	int ord = 0;

	if (this.distance<(arg0.distancia()))
		ord = -1;
	if (this.distance>(arg0.distancia()))
		ord = 1;

	return ord;

}

  
  
  

}
