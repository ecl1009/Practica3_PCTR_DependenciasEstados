package src.p03.c01;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
public class ActividadSalidaPuerta implements Runnable{

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
	 * Inicializa los atributos puerta y parque con los parámetros pasados al constructor.
	 * 
	 * @param puerta String con el nombre de la puerta por la que se simulan las salidas.
	 * @param parque Objeto de clase Parque que implementa la interfaz IParque.
	 */
	public ActividadSalidaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}
	
	/**
	 * Método run - Método que ejecuta el hilo al ser creado.
	 * Simula NUMSALIDAS salidas del parque.
	 */
	@Override
	public void run() {
		// Se ejecuta el bucle hasta alcanzar el número máximo de salidas establecido en NUMSALIDAS
		for (int i = 0; i < NUMSALIDAS; i++) {
			// Bloque try que se intenta ejecutar
			try {
				//Invoca al método salirDelParque pasandole la puerta como parámetro
				parque.salirDelParque(puerta);
				//Duerme al hilo un tiempo aleatorio entre 0 y 5 segundos
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5) * 1000);
				
				//Captura la excepción InterruptedException cuando se interrumpe el hilo
			} catch (InterruptedException e) {
				// Al capturar la excepción se imprime un mensaje informando de la excepción capturada
				System.out.println("Salida interrumpida");
				System.out.println(e.toString());
				return; // Finaliza la ejecución del método
			}

		}
	}
}
