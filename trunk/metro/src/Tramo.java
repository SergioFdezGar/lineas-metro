
public class Tramo {
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
	
	/**
	 * @return the linea
	 */
	public int getLinea() {
		return linea;
	}
	/**
	 * @param linea the linea to set
	 */
	public void setLinea(int linea) {
		this.linea = linea;
	}
	/**
	 * @return the duracion
	 */
	public int getduracion() {
		return duracion;
	}
	/**
	 * @param duracion the duracion to set
	 */
	public void setduracion(int duracion) {
		this.duracion = duracion;
	}
	/* (non-Javadoc)
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
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tramo [linea=" + linea + ", duracion=" + duracion + "]";
	}
	
	
	
}
