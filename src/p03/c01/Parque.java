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
public class Parque implements IParque {

	/**
	 * Cantidad de personas actuales en el parque.
	 */
	private int contadorPersonasTotales; 
	/**
	 * Balance de entradas/salidas por una puerta del parque.
	 */
	private Hashtable<String, Integer> contadoresPersonasPuerta; 
	/**
	 * Aforo máximo del parque.
	 */
	private final int AFOROMAX = 50; 
	/**
	 * Cantidad mínima de personas en el parque. 
	 */
	private final int AFOROMIN = 0; 
	/**
	 * Tiempo medio.
	 */
	private double tmedio; 
	/**
	 * Tiempo inicial.
	 */
	private double tinicial; 
	/**
	 * Tiempo final.
	 */
	private double tfinal; 
	/**
	 * Total de entradas al parque.
	 */
	private int totalEntradas; 
	/**
	 * Total de salidas del parque.
	 */
	private int totalSalidas; 
	/**
	 * Total de entradas por puerta.
	 */
	private Hashtable<String, Integer> entradasPuertas;
	/**
	 * Total de salidas por puerta.
	 */
	private Hashtable<String, Integer> salidasPuertas;  

	/**
	 * Constructor de la clase.
	 * 
	 * Inicializa los atributos de contadores.
	 */
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		entradasPuertas = new Hashtable<String, Integer>();
		salidasPuertas = new Hashtable<String, Integer>();
		totalEntradas = 0;
		totalSalidas = 0;
	}

	/**
	 * Método sincronizado entrarAlParque - Simula una entrada por una puerta del parque. 
	 * 
	 * Se comprueban las precondiciones antes de entrar al parque, se actualizan los valores
	 * y se notifica a todos los hilos. 
	 * 
	 * @param puerta String con el nombre de la puerta por la que se entra al parque.
	 */
	@Override
	public synchronized void entrarAlParque(String puerta) {
		// Comprobar precondiciones
		comprobarAntesDeEntrar();
		// Se inicializa la entrada si no se ha utilizado esa puerta.
		inicializaEntradas(puerta);
		// Se obtiene el tiempo inicial.
		tinicial = getTiempo();
		// Actualiza el estado del parque ante una entrada.
		actualizaEntradas(puerta);
		// Imprimimos el estado del parque.
		imprimirInfo(puerta, "Entrada");
		// Se notifica a todos los hilos.
		notifyAll();
		// Se comprueba el invariante. 
		checkInvariante();

	}
	
	

	/**
	 * Método sincronizado salirDelParque - Simula una salida por una puerta del parque. 
	 * 
	 * Se comprueban las precondiciones antes de salir del parque, se actualizan los valores
	 * y se notifica a todos los hilos. 
	 * 
	 * @param puerta String con el nombre de la puerta por la que se sale del parque.
	 */ 
	 
	@Override
	public synchronized void salirDelParque(String puerta) {
		// Comprobar precondiciones
		comprobarAntesDeSalir();
		// Se inicializa la salida si no se ha utilizado esa puerta.
		inicializaSalidas(puerta);
		// Se obtiene el tiempo final.
		tfinal = getTiempo();
		// Se obtiene el tiempo medio.
		tmedio = getTiempoMedio(tfinal);
		// Actualiza el estado del parque ante una salida.
		actualizaSalidas(puerta);
		// Imprimimos el estado del parque.
		imprimirInfo(puerta, "Salida");
		// Se notifica a todos los hilos.
		notifyAll();
		// Se comprueba el invariante. 
		checkInvariante();
	}
	
	/**
	 * Método getPersonasActuales() - Obtiene el número de personas actuales en el parque.
	 * 
	 * @return contadorPersonasTotales Personas actuales en el parque. 
	 */
	private int getPersonasActuales() {
		return contadorPersonasTotales;
	}
	
	/**
	 * Método actualizaSalidas - Actualiza el estado del parque ante una salida. 
	 * 
	 * Se disminuye la cantidad de personas actuales y se incrementa el total de salidas del parque. 
	 * 
	 * @param puerta String con el nombre de la puerta por la que se sale del parque. 
	 */
	private void actualizaSalidas(String puerta) {
		//Disminuye en uno las personas actuales.
		contadorPersonasTotales--;
		//Aumenta en uno el total de salidas del parque.
		totalSalidas++;
		//Disminuye en uno el balance de entradas/salidas por la puerta. 
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);
		//Aumenta en uno el total de salidas por la puerta. 
		salidasPuertas.put(puerta, salidasPuertas.get(puerta) + 1);
	}
	
	/**
	 * Método actualizaEntradas - Actualiza el estado del parque ante una entrada. 
	 * 
	 * Se incrementa la cantidad de personas actuales y el total de entradas al parque. 
	 * 
	 * @param puerta String con el nombre de la puerta por la que se entra al parque. 
	 */
	private void actualizaEntradas(String puerta) {
		//Aumenta en uno las personas actuales.
		contadorPersonasTotales++;
		//Aumenta en uno el total de entradas del parque.
		totalEntradas++;
		//Aumenta en uno el balance de entradas/salidas por la puerta. 
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);
		//Aumenta en uno el total de entradas por la puerta. 
		entradasPuertas.put(puerta, entradasPuertas.get(puerta) + 1);
	}
	
	/**
	 * Método getTiempo - Obtiene la hora actual del sistema.
	 * 
	 * @return Hora actual del sistema.	 
	 */
	private double getTiempo(){
		return System.currentTimeMillis();		
	}
	
	/**
	 * Método getTiempoMedio - Calcula el tiempo medio de estancia en el parque.
	 * 
	 * @param tiempoFin Tiempo en el que se realizó la última salida del parque.
	 * @return Tiempo medio de estancia en el parque
	 */
	private double getTiempoMedio(double tiempoFin) {
		return (tmedio + (tiempoFin - tinicial)) / 2; // Devuelve el resultado de la media del tiempo de estancia.
	}

	/**
	 * Método inicializaEntradas - Inicializa los contadores de salida y/o entrada a 0 si estos valen null.
	 * 
	 * @param puerta String con el nombre de la puerta por la que se entra al parque
	 */
	private void inicializaEntradas(String puerta) {
				// Si no hay salidas por esa puerta, inicializamos
				if (contadoresPersonasPuerta.get(puerta) == null) {
					contadoresPersonasPuerta.put(puerta, 0);
					salidasPuertas.put(puerta, 0);					
				}
				// Si no hay entradas pro esa puerta inicializamos
				if (entradasPuertas.get(puerta) == null) {
					entradasPuertas.put(puerta, 0);
				}
	}
	
	/**
	 * Método inicializaSalidas - Inicializa los contadores de entrada y/o salida a 0 si estos valen null.
	 * 
	 * @param puerta String con el nombre de la puerta por la que se sale del parque
	 */
	private void inicializaSalidas(String puerta) {
				// Si no hay salidas por esa puerta, inicializamos
				if (contadoresPersonasPuerta.get(puerta) == null) {
					contadoresPersonasPuerta.put(puerta, 0);
					entradasPuertas.put(puerta, 0);					
				}
				// Si no hay salidas por esa puerta inicializamos	
				if (salidasPuertas.get(puerta) == null) {
					salidasPuertas.put(puerta, 0);
				}
	}
	
	
	/**
	 * Método imprimirInfo - Imprime la información del estado actual del parque,
	 * 
	 * Imprime el estado del parque en el momento actual. Se muestra la información del número total de personas
	 * en el parque, el total de entradas, el total de salidas y el tiempo medio de estancia en el parque.
	 * Imprime en formato tabla la información de las entradas, salidas y el balance total de entradas y salidas por
	 * cada puerta del parque.
	 * 
	 * 
	 * @param puerta Puerta que actualiza el estado del parque
	 * @param movimiento Tipo de movimiento (Entrada/Salida)
	 */
	private void imprimirInfo(String puerta, String movimiento) {
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + getPersonasActuales() );
		System.out.println("--> Total entradas: " + totalEntradas + "\t total salidas: " + totalSalidas + " \tTiempo medio de estancia: " + tmedio);
		System.out.println("");
		System.out.println("----> Puerta\tTotal entradas\tTotal salidas\tBalance");

		// Iteramos por todas las puertas e imprimimos sus entradas
		for (String p : contadoresPersonasPuerta.keySet()) {
			System.out.println("---->   " + p + "\t\t" + entradasPuertas.get(p) + "\t\t" + salidasPuertas.get(p) + "\t   " + contadoresPersonasPuerta.get(p));
			}
		System.out.println(" ");
	}

	/**
	 * Método sumaContadoresPuerta - Suma todos los contadores parciales de cada puerta.
	 * 
	 * Suma los valores del balance total de entradas y salidas para cada puerta. EL resultado debe coincidir con el
	 * número total de personas en el parque.
	 * 
	 * @return sumaContadoresPuerta Número total de personas en el parque
	 */
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0; // Inicializa en contador a 0
		Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements(); // Obtiene una enumeración con todos los contadores parciales de cada puerta
		
		// Mientras queden elementos en la enumeración sin sumar se repite el bloque while
		while (iterPuertas.hasMoreElements()) {
			sumaContadoresPuerta += iterPuertas.nextElement(); // Suma el siguiente elemento al total
		}
		return sumaContadoresPuerta; // Devuelve el resultado de la suma
	}

	/**
	 * Método chechInvariante - Comprueba los invariantes del objeto.
	 * 
	 * Comprueba los invariantes del objeto de clase Parque mediante aserciones.
	 * 
	 */
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == getPersonasActuales()
				: "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parque";
		assert getPersonasActuales() >= AFOROMIN
				: "INV: La suma de los contadores de las puertas no puede ser menor que cero.";
		assert getPersonasActuales() <= AFOROMAX
				: "INV: La suma de los contadores de las puertas no puede ser mayor que el aforo máximo del parque.";
	}

	/**
	 * Método comprobarAntesDeEntrar - Mantiene al hilo en espera mientras la condición de entrada no se cumpla.
	 */	 
	protected void comprobarAntesDeEntrar() {
		//Bucle while que se repite mientras la condición de entrada no se cumpla.
		while (!condicionEntrada()) {
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
	protected void comprobarAntesDeSalir() {
		
		//Bucle while que se repite mientras la condición de salida no se cumpla.
		while (!condicionSalida()) {
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
	 * Método condicionEntrada - Comprueba las precondiciones de entrada al parque.
	 *  
	 *  Si la cantidad de personas en el parque actualmente es menor que el aforo máximo
	 *  se cumple la condición de entrada y retornara True, de lo contrario retornará False.
	 *  
	 * @return True si la condición se cumple y False si no se cumple.
	 */
	private boolean condicionEntrada() {
		return getPersonasActuales() < AFOROMAX;
	}

	/**
	 *  Método condicionSalida - Comprueba las precondiciones de salida del parque.
	 *  
	 *  Si la cantidad de personas en el parque actualmente es mayor que el aforo mínimo
	 *  se cumple la condición de salida y retornara True, de lo contrario retornará False.
	 *  
	 * @return True si la condición se cumple y False si no se cumple.
	 */
	private boolean condicionSalida() {
		return getPersonasActuales() > AFOROMIN;
	}

}