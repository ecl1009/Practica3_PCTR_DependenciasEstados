package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Clase Parque - Implementa un parque en el que se tiene un aforo máximo dado por AFOROMAX y un aforo mínimo dado por AFOROMIN.
 * 
 * Se controlan las entradas y salidas del parque por cada una de las puertas y se obtiene el tiempo medio de estancia. 
 * Cada puerta de entrada y salida se simula de forma concurrente con varios hilos. Cada hilo se corresponde con una puerta de entrada o salida.
 * 
 * @author Irati Arraiza Urquiola - Eduardo Manuel Cabeza Lopez
 * @version 1.0
 * @since 1.0
 * @see IParque
 *
 */
public class Parque implements IParque{


	/**
	 * Cantidad de personas actuales en el parque.
	 */ 
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	/**
	 * Aforo máximo del parque.
	 */
	private final int AFOROMAX = 50; 
	/**
	 * Cantidad mínima de personas en el parque. 
	 */
	private final int AFOROMIN = 0; 
	
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
	}


	@Override
	public void entrarAlParque(String puerta){		// TODO
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		// TODO
				
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		// TODO
		
		
		// TODO
		
	}
	
	// 
	// TODO Método salirDelParque
	//
	
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	/**
	 * Método chechInvariante - Comprueba los invariantes del objeto.
	 * 
	 * Comprueba los invariantes del objeto de clase Parque mediante aserciones.
	 * 
	 */
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert contadorPersonasTotales >= AFOROMIN
				: "INV: La suma de los contadores de las puertas no puede ser menor que cero.";
		assert contadorPersonasTotales <= AFOROMAX
				: "INV: La suma de los contadores de las puertas no puede ser mayor que el aforo máximo del parque.";	
	}

	/**
	 * Método comprobarAntesDeEntrar - Mantiene al hilo en espera mientras la condición de entrada no se cumpla.
	 *
	 */
	protected void comprobarAntesDeEntrar(){
		//Bucle while que se repite mientras la condición de entrada no se cumpla.
		while (!(contadorPersonasTotales < AFOROMAX)) {
			//Bloque try que intenta ejecutar
			try {
				wait(); //Mantiene al hilo dormido hasta recibir una notificación que lo despierte
			// Captura y trata la excepción InterruptedException
			} catch (InterruptedException e) {
				System.out.println("Hilo interrumpido"); // Mensaje mostrado en caso de que el hilo sea interrumpido.
			}
		}
	}

	/**
	 * Método comprobarAntesDeSalir - Mantiene al hilo en espera mientras la condición de salida no se cumpla.
	 * 
	 */
	protected void comprobarAntesDeSalir(){
		//Bucle while que se repite mientras la condición de salida no se cumpla.
		while (!(contadorPersonasTotales > AFOROMIN)) {
			//Bloque try que intenta ejecutar
			try {
				wait(); //Mantiene al hilo dormido hasta recibir una notificación que lo despierte
			// Captura y trata la excepción InterruptedException
			} catch (InterruptedException e) {
				System.out.println("Hilo interrumpido"); // Mensaje mostrado en caso de que el hilo sea interrumpido.
			}
		}
	}


}
