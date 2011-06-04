package edu.dat;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.gui.HojaCalculo;
import edu.log.DemultiplexorExpresion;
import edu.log.DemutiplexorFormula;

/**
 * Esta clase representa el modelo de la tabla, con lo cual se convierte
 * en el patron a seguir de la tabla, si se quisiera modificar alguna
 * caracteristica de la tabla, se debe hacer desde aqui.
 * @author Camilo Nova
 */
public class ModeloTabla extends AbstractTableModel {

	/**
	 * Representa los nombres de las columnas de la tabla
	 */
	private Vector encabezados = new Vector(); 
	
	/**
	 * Representa los datos de la tabla
	 */
	private String datos[][] = new String[HojaCalculo.CANTIDAD_FILAS][HojaCalculo.CANTIDAD_COLUMNAS+1];
	
	/**
	 * Representa la clase que resuelve la expresion
	 */
	private DemultiplexorExpresion demultiplexorExpresion;
	
	/**
	 * Representa la clase que resuelve la formula
	 */
	private DemutiplexorFormula demultiplexorFormula;
	
	/**
	 * Constructor de la clase, les da nombres a las columnas
	 * de la tabla.<p>
	 * <b>La etiquetacion de las columnas con letras, solamente
	 * son validas hasta el valor de 26</b>.
	 */
	public ModeloTabla() {
		char letra = 'A';
		encabezados.add(Character.toString('#'));
		for(int i=0; i < HojaCalculo.CANTIDAD_COLUMNAS; i++) {
			encabezados.add(Character.toString(letra));
			letra++;
		}
		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++) {
			datos[i][0] = Integer.toString(i);
		}
		fireTableStructureChanged();
	}

	/**
	 * Metodo que retorna la cantidad de filas.
	 */
	public int getRowCount() {
		return datos.length;
	}

	/**
	 * Metodo que retorna la cantidad de columnas.
	 */
	public int getColumnCount() {
		return encabezados.size();
	}
	
	/**
	 * Metodo que retorna el nombre de las columnas.
	 */
	public String getColumnName(int column) {
		return (String) encabezados.elementAt(column);
	}

	/**
	 * Metodo que retorna el objeto situado en los parametros.
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return Integer.toString(rowIndex+1);
		else
			return datos[rowIndex][columnIndex];
	}
	
	/**
	 * Metodo que determina si una celda es editable o no.
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// No queremos que sean editables las celdas de autonumeracion
		if (columnIndex == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Metodo que actualiza la informacion de una celda.<p>
	 * Ademas determina el tipo de dato que fue introducido y hace
	 * la operacion correspondiente al tipo de dato.
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// Verificamos que no sea nulo ni que sea de tamaño cero
		if(aValue != null && ((String) aValue).length() != 0) {
			if(((String) aValue).startsWith("=")) {
				// Formula
				demultiplexorFormula = new DemutiplexorFormula(this, (String) aValue);
				datos[rowIndex][columnIndex] = String.valueOf(demultiplexorFormula.getValor());
			}
			else if((Character.isDigit(((String) aValue).charAt(0)))) {
				// Operacion
				demultiplexorExpresion = new DemultiplexorExpresion((String) aValue);
				datos[rowIndex][columnIndex] = String.valueOf(demultiplexorExpresion.getValor());
			}
			else if((Character.isLetter(((String) aValue).charAt(0)))) {
				// Texto
				datos[rowIndex][columnIndex] = (String) aValue;
			}
		}
		else	// Celda Vacia
			datos[rowIndex][columnIndex] = null;

		fireTableDataChanged();
	}
	
	/**
	 * Metodo que cambia todos los datos de la tabla por los
	 * recibidos como parametro, este metodo fue creado con el fin
	 * de hacer compatible la tabla con <i>Abrir</i>.
	 * @param str	String[][] con todos los nuevos datos
	 */
	public void setAllData(String[][] str) {
		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS+1; j++)
				datos[i][j] = str[i][j];
		fireTableDataChanged();
	}
	
	/**
	 * Metodo que retorna todos los datos de la tabla, este 
	 * metodo fue creado con el fin de hacer compatible la 
	 * tabla con <i>Guardar</i>.
	 * @return String[][] con todos los datos de la tabla
	 */
	public String[][] getAllData() {
		return datos;
	}
}
