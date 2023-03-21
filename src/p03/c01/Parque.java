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
	public void entrarAlParque(String puerta){		
		
		// Comprobar precondiciones
		comprobarAntesDeEntrar();
		
		// Si no hay salidas ni entradas por esa puerta, inicializamos
        if (contadoresPersonasPuerta.get(puerta) == null) {
            contadoresPersonasPuerta.put(puerta, 0);
            entradasPuertas.put(puerta, 0);
            salidasPuertas.put(puerta, 0);
           
        }
        // Se obtiene el tiempo inicial.
        tinicial = System.currentTimeMillis();
        //Aumenta en uno las personas actuales.
      	contadorPersonasTotales++;
      	//Aumenta en uno el total de entradas del parque.
      	totalEntradas++;
      	//Aumenta en uno el balance de entradas/salidas por la puerta. 
      	contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);
      	//Aumenta en uno el total de entradas por la puerta. 
      	entradasPuertas.put(puerta, entradasPuertas.get(puerta) + 1);
		// Imprimimos el estado del parque
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
		
		// Si no hay salidas ni entradas por esa puerta, inicializamos
        if (contadoresPersonasPuerta.get(puerta) == null) {
            contadoresPersonasPuerta.put(puerta, 0);
            entradasPuertas.put(puerta, 0);
            salidasPuertas.put(puerta, 0);
           
        }
        // Se obtiene el tiempo final.
     	tfinal = System.currentTimeMillis();
     	// Se obtiene el tiempo medio.
     	tmedio = (tmedio +(tfinal - tinicial))/2;
     	//Disminuye en uno las personas actuales.
     	contadorPersonasTotales--;
     	//Aumenta en uno el total de salidas del parque.
     	totalSalidas++;
     	//Disminuye en uno el balance de entradas/salidas por la puerta. 
     	contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);
     	//Aumenta en uno el total de salidas por la puerta. 
     	salidasPuertas.put(puerta, salidasPuertas.get(puerta) + 1);
				
		
		// Imprimimos el estado del parque.
		imprimirInfo(puerta, "Salida");
		// Se notifica a todos los hilos.
		notifyAll();
		// Se comprueba el invariante. 
		checkInvariante();
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
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales );
		System.out.println("--> Total entradas: " + totalEntradas + "\t total salidas: " + totalSalidas + " \tTiempo medio de estancia: " + tmedio);
		System.out.println("");
		System.out.println("----> Puerta\tTotal entradas\tTotal salidas\tBalance");

		// Iteramos por todas las puertas e imprimimos sus entradas
		for (String p : contadoresPersonasPuerta.keySet()) {
			System.out.println("---->   " + p + "\t\t" + entradasPuertas.get(p) + "\t\t" + salidasPuertas.get(p) + "\t   " + contadoresPersonasPuerta.get(p));
			//System.out.println("----> " + p + " entradas: " + entradasPuertas.get(p) + ", salidas: "
					//+ salidasPuertas.get(p));
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
