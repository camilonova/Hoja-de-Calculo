package edu.log;

import java.util.StringTokenizer;

import javax.swing.table.AbstractTableModel;

/**
 * Esta clase convierte el valor de una formula entrada a una expresion matematica para
 * que sea evaluada por la clase <i>DemultiplexorExpresion</i>.<p>
 * La forma en la que se reconoce de que se trata de una formula es porque comienza con
 * el simbolo "=", entonces esta clase convierte el valor de una celda al valor numerico
 * para calcular el valor de la expresion.
 * @author Camilo Nova
 */
public class DemutiplexorFormula {
	
	/**
	 * Representa la cadena en donde se arma la expresion matematica
	 */
	private String cadena;
	
	/**
	 * Representa la fila a la que hace referencia la entrada
	 */
	private String fila;
	
	/**
	 * Representa la columna a la que hace referencia la entrada
	 */
	private String columna;
	
	/**
	 * Representa el Tokenizer que usamos para separar la expresion en Tokens
	 */
	private StringTokenizer strToken;
	
	/**
	 * Representa el dato de la celda a la cual hace referencia <i>fila</i> y <i>columna</i>.
	 */
	private String datoCelda;
	
	/**
	 * Representa si el token representa una celda, si es una celda el valor es true
	 */
	private boolean isCelda = false;
	
	/**
	 * Representa el Demultiplexor de la expresion matematica.
	 */
	private DemultiplexorExpresion demultiplexorExpresion;
	
	
	/**
	 * Constructor de la clase, recibe como parametros la tabla en la cual
	 * buscar los valores y la cadena a convertir.<p>
	 * La responsabilidad de la clase es la de convertir una espresion de tipo
	 * "=A3*B5", al tipo "(valor de A3)*(valor de B5)" y enviarla a <i>DemultiplexorExpresion</i>
	 * a que la resuelva.
	 * @param tabla		Tabla de la cual recuperar los datos
	 * @param cad		Cadena a convertir
	 */
	public DemutiplexorFormula(AbstractTableModel tabla, String cad) {
		strToken = new StringTokenizer(cad, "=+*-/^() ", true);
		cadena = new String();
		fila = new String();
		columna = new String();

		while(strToken.hasMoreTokens()) {
			String g = strToken.nextToken();
			// Nos saltamos los siguientes tokens
			if(!g.equals("=") && !g.equals(" ")) {
				char a = 'A';
				for(int i=0; i < 26; i++) {
					if(g.startsWith(Character.toString(a))) {
						isCelda = true;
						StringTokenizer str = new StringTokenizer(g, Character.toString(a), true);
						if(str.countTokens() == 2) {
							// Validamos que solamente existan 2 Tokens
							columna = str.nextToken();
							fila = str.nextToken();

							try {
								datoCelda = (String) tabla.getValueAt(Integer.valueOf(fila).intValue()-1, validarColumna(columna));
							} catch (NumberFormatException e) {
								System.err.println("Celda no valida");	
								return;
							}
							
							if(datoCelda == null) {
								System.err.println("La Celda " + columna + fila + " no contiene ningun valor");
								return;
							}
							cadena += datoCelda;
						}
						else {
							System.err.println("Celda no valida");	
							return;
						}
					}
					a++;
				}
				if(!isCelda)
					cadena += g;
				
				// Volvemos a la condicion inicial
				isCelda = false;
			}
		}
		demultiplexorExpresion = new DemultiplexorExpresion(cadena);
	}
	
	/**
	 * Metodo que retorna el valor de la expresion, luego de ser
	 * calculado por <i>DemultiplexorExpresion</i>. Retorna cero
	 * si ocurre algun error en la evaluacion de la cadena.
	 * @return	Valor numerico de la expresion
	 */
	public double getValor() {
		// Si ocurre un error en la evaluacion de la expresion
		if(demultiplexorExpresion == null)
			return 0;
		else
			return demultiplexorExpresion.getValor();
	}
	
	/**
	 * Metodo que convierte el valor del parametro s a su equivalente
	 * en entero para la ubicacion en la tabla
	 * @param s		Cadena con el nombre de la columna
	 * @return		Entero que representa la posicion de la columna
	 */
	private int validarColumna(String s) {
		char c = 'A';
		
		for(int i=0; i < 26; i++) {
			if(s.charAt(0) == c)
				return i+1;
			c++;
		}
		return 0;
	}
}
