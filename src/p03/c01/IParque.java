package src.p03.c01;

/**
 * Interfaz IParque - Proporciona el esqueleto para los métodos de entrada y salida de un parque. 
 * 
 * @author Irati Arraiza Urquiola - Eduardo Manuel Cabeza Lopez
 * @version 1.0
 * @since 1.0
 *
 */
public interface IParque {
	
	/**
	 * Simula la entrada a un parque. 
	 * @param puerta Puerta por la que se entra.
	 */
	public abstract void entrarAlParque(String puerta);

	/**
	 * Simula la salida de un parque.
	 * @param puerta Puerta por la que se sale. 
	 */
	public abstract void salirDelParque(String puerta);

}
