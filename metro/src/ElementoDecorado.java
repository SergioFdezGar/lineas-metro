public class ElementoDecorado <T>{
  // Se usa el patrón Decorator para decorar el elemento con atributos
  // adicionales
  T elemento;                  // Elemento de datos
  boolean visitado=false;      // Atributo de nodo visitado
  ElementoDecorado<T> parent=null; // Nodo desde el que se accedió a éste
  int distance=0;              // Distancia (en vértices) desde el nodo original
  T aristaParent=null;      //Arista del nodo parent

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
  public T aristaParent(){
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
  public void setAristaParent(T arista){
    aristaParent=arista;
  }  
  
  public boolean equals (ElementoDecorado n){
    return elemento.equals(n.elemento()); //elemento tiene que tener sobrescrito equals()
  }
  public String toString(){
    return elemento.toString();    //elemento tiene que tener sobrescrito toString()
  }
}
