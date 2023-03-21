package src.p03.c01;

/**
 * Clase SistemaLanzador - Lanza los Thread correspondientes a cada proceso de
 * entrada/salida de cada puerta del parque.
 * 
 * @author Irati Arraiza Urquiola - Eduardo Manuel Cabeza Lopez
 * @version 1.0
 * @since 1.0
 * @see ActividadEntradaPuerta
 * @see ActividadSalidaPuerta
 * @see Parque
 */
public class SistemaLanzador {

	/**
	 * Constructor por defecto.
	 */
	public SistemaLanzador() {};
	
	/**
	 * Método principal main - Lanza 10 Threads, 5 de entrada y 5 de salida del
	 * parque.
	 * 
	 * Cada Thread de entrada o salida corresponde a una puerta del parque, teniendo
	 * en total 5 puertas, cada una con su proceso o actividad de entrada y salida.
	 * 
	 * @param args Argumentos de entrada en al ejecución
	 */
	public static void main(String[] args) {

		IParque parque = new Parque(); // Parque para el que se lanzan los hilos
		char letra_puerta = 'A'; // Letra inicial para asignar nombres a cada puerta
		final int NUMPUERTAS = 5; // Número de peurtas del parque

		// Mensaje que indica que el parque ha abierto
		System.out.println("Parque abierto:\n");

		// Bucle que se repite tantas veces como puertas tenga el parque
		for (int i = 0; i < NUMPUERTAS; i++) {

			String puerta = "" + ((char) (letra_puerta++));

			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread(entradas).start();

			// Creación de hilos de salida
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread(salidas).start();

		}

	}
}