
public class Estacion {

	private String nombre;
	/**
	 * @param nombre
	 */
	public Estacion(String nombre) {
		super();
		this.nombre = nombre;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean igual=true;
		
		Estacion other = (Estacion) obj;

		if (nombre == null) {
			if (other.nombre != null) {
				igual= false;
			}
		}else{	
			if (!nombre.equals(other.nombre)) {
				igual= false;
			}
		}
		return igual;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Estacion [nombre=" + nombre + "]";
	}
	
}
