package src.p03.c01;

/**
 * Clase ActividadSalidaPuerta - Simula las salidas del parque.
 * 
 * Clase que implementa la actividad de salida de un parque.
 * La clase será creada por un objeto Thread y simula las salidas del parque.
 * Simula una puerta de salida del parque y realiza varias salidas desde esa puerta.
 * 
 * @author Irati Arraiza Urquiola - Eduardo Manuel Cabeza Lopez.
 * @version 1.0  
 * @since 1.0
 *
 */
public class ActividadSalidaPuerta{

	/**
	 * Número de salidas por la puerta
	 */
	private static final int NUMSALIDAS = 20;
	
	/**
	 * Nombre de la puerta
	 */
	private String puerta;
	
	/**
	 * Parque al que pertenece la puerta
	 */
	private IParque parque;
	
	/**
	 * Constructor de la clase.
	 *
	 * Inicializa los atributos puerta y parque con los pámetros pasados al constructor.
	 * 
	 * @param puerta String con el nombre de la puerta por la que se simulan las salidas.
	 * @param parque Objeto de clase Parque que implementa la interfaz IParque.
	 */
	public ActividadSalidaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}
}
