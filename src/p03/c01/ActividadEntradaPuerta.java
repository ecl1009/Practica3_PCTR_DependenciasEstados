package src.p03.c01;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Clase ActividadEntradaPuerta - Simula las entradas del parque.
 * 
 * Clase que implementa la actividad de entrada a un parque. La clase será
 * creada por un objeto Thread y simula las entradas al parque. Simula una
 * puerta de entrada al parque y realiza varias entradas desde esa puerta.
 * 
 * @author Irati Arraiza Urquiola - Eduardo Manuel Cabeza Lopez.
 * @version 1.0
 * @since 1.0
 *
 */
public class ActividadEntradaPuerta implements Runnable {

	/**
	 * Número de entradas por la puerta
	 */
	private static final int NUMENTRADAS = 20;

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
	 * Inicializa los atributos puerta y parque con los pámetros pasados al
	 * constructor.
	 * 
	 * @param puerta String con el nombre de la puerta por la que se simulan las
	 *               entradas.
	 * @param parque Objeto de clase Parque que implementa la interfaz IParque.
	 */
	public ActividadEntradaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}

	/**
	 * Método run - Método que ejecuta el hilo al ser creado. Simula NUMENTRADAS
	 * entradas al parque.
	 */
	@Override
	public void run() {
		// Se ejecuta el bucle hasta alcanzar el número máximo de entradas establecido
		// en NUMENTRADAS
		for (int i = 0; i < NUMENTRADAS; i++) {
			// Bloque try que se intenta ejecutar
			try {
				// Invoca al método entrarAlParque pasandole la puerta como parámetro
				parque.entrarAlParque(puerta);
				// Duerme al hilo un tiempo aleatorio entre 0 y 5 segundos.
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5) * 1000);

				// Captura la excepción InterruptedException cuando se interrumpe el hilo
			} catch (InterruptedException e) {
				// Al capturar la excepción se imprime un mensaje informando de la excepción
				// capturada
				System.out.println("Entrada interrumpida");
				System.out.println(e.toString());
				return; // Finaliza la ejecución del método
			}

		}
	}

}