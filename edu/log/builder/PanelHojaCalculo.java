package edu.log.builder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import edu.dat.ModeloColumnasTabla;
import edu.dat.ModeloFilasTabla;
import edu.dat.ModeloTabla;
import edu.gui.HojaCalculo;

/**
 * Esta clase representa el area en donde se muestra la tabla.<p>
 * Ademas provee metodos para el manejo externo de la tabla, tales
 * como insertar o eiminar filas o columnas, etc.
 * @author Camilo Nova
 */

public class PanelHojaCalculo extends JPanel implements ConstructorAbstracto {
	
	/**
	 * Representa el modelo de la tabla
	 */
	private ModeloTabla modelo;
	
	/**
	 * Representa el modelo de las columnas de la tabla
	 */
	private ModeloColumnasTabla columnasTabla;
	
	/**
	 * Representa el modelo de las filas de la tabla
	 */
	private ModeloFilasTabla filasTabla;
	
	/**
	 * Representa la tabla principal
	 */
	public  JTable tabla;
	
	/**
	 * Representa la tabla auxiliar
	 */
	private JTable tablaAux;
	
	/**
	 * Representa el area de vista de la tabla
	 */
	private JViewport viewPort;
	
	/**
	 * Representa el ScrollPane que permite el movimiento por la tabla
	 */
	private JScrollPane scrollPane;
	
	/**
	 * Representa la fila seleccionada actualmente
	 */
	private int filaSeleccionada = -1;
	
	/**
	 * Representa la columna seleccionada actualmente
	 */
	private int columnaSeleccionada = -1;
	
	/**
	 * Representa el copia de los datos de la tabla
	 */
	private String dataBackup[][] = new String[HojaCalculo.CANTIDAD_FILAS][HojaCalculo.CANTIDAD_COLUMNAS+1];
	
	
	/**
	 * Constructor de la clase, crea la tabla y de da todas las propiedades
	 * para mostrarla en la aplicacion.
	 *
	 */
	public PanelHojaCalculo() {
		modelo = new ModeloTabla();
		columnasTabla = new ModeloColumnasTabla();
		tabla = new JTable(modelo, columnasTabla);
		tablaAux = new JTable(modelo, filasTabla);
	
		tabla.createDefaultColumnsFromModel();
		tablaAux.createDefaultColumnsFromModel();
		
		tabla.setColumnSelectionAllowed(true);
		tabla.setRowSelectionAllowed(true);
		
		tablaAux.setSelectionModel(tabla.getSelectionModel());
		tablaAux.setMaximumSize(new Dimension(40, 10000));
		tablaAux.setBackground(new Color(238,238,238));
		// Se puede pasar a true si se quiere seleccionar las filas
		tablaAux.setEnabled(false);
		tablaAux.setColumnSelectionAllowed(false);
		tablaAux.setCellSelectionEnabled(false);
		
		viewPort = new JViewport();
		viewPort.setView(tablaAux);
		viewPort.setPreferredSize(tablaAux.getMaximumSize());
		
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaAux.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
		scrollPane = new JScrollPane(tabla);
		scrollPane.setRowHeader(viewPort);
		scrollPane.setPreferredSize(new Dimension(790,500));
		add(scrollPane);
	}
	
	/**
	 * Metodo que actualiza todos los datos de la tabla, por el enviado como parametro
	 * @param dat	Datos a actualizar en la tabla
	 */
	public void setAllDataToModel(String[][] dat) {
		modelo.setAllData(dat);
	}
	
	/**
	 * Metodo que retorna los datos de la tabla.
	 * @return		Datos de la tabla
	 */
	public String[][] getAllDataFromModel() {
		return modelo.getAllData();
	}

	/**
	 * Metodo que inserta las filas pasadas por parametro a la tabla
	 * en la posicion actual.
	 * @param cantidad		Cantidad de filas a insertar
	 */
	public void insertarFilas(int cantidad) {
		filaSeleccionada = tabla.getSelectedRow();
		
		for(int i=0; i <= filaSeleccionada; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS; j++)
				dataBackup[i][j] = (String) tabla.getValueAt(i,j);

		for(int i=filaSeleccionada+1; i <= filaSeleccionada+cantidad; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS; j++)
				dataBackup[i][j] = null;

		for(int i=filaSeleccionada+cantidad+1; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS; j++)
				dataBackup[i][j] = (String) tabla.getValueAt(i-cantidad,j);

		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS; j++) {
				tabla.setValueAt(dataBackup[i][j], i, j);
				dataBackup[i][j] = null;
			}
		
		modelo.fireTableStructureChanged();
		tabla.setRowSelectionInterval(filaSeleccionada+1, filaSeleccionada+cantidad);
	}
	
	/**
	 * Metodo que inserta las columnas pasadas por parametro a la tabla
	 * en la posicion actual.
	 * @param cantidad		Cantidad de columnas a insertar
	 */
	public void insertarColumnas(int cantidad) {
		columnaSeleccionada = tabla.getSelectedColumn();

		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=0; j <= columnaSeleccionada; j++)
				dataBackup[i][j] = (String) tabla.getValueAt(i,j);

		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=columnaSeleccionada+1; j <= columnaSeleccionada+cantidad; j++)
				dataBackup[i][j] = null;

		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=columnaSeleccionada+cantidad+1; j < HojaCalculo.CANTIDAD_COLUMNAS; j++)
				dataBackup[i][j] = (String) tabla.getValueAt(i,j-cantidad);

		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS; j++) {
				tabla.setValueAt(dataBackup[i][j], i, j);
				dataBackup[i][j] = null;
			}
		
		modelo.fireTableStructureChanged();
		tabla.setColumnSelectionInterval(columnaSeleccionada+1, columnaSeleccionada+cantidad);
	}
	
	/**
	 * Metodo que elimina las filas pasadas por parametro a la tabla
	 * en la posicion actual, hacia la derecha.
	 * @param cantidad		Cantidad de filas a eliminar
	 */
	public void eliminarFilas(int cantidad) {
		filaSeleccionada = tabla.getSelectedRow();
		
		for(int i=0; i < filaSeleccionada; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS; j++)
				dataBackup[i][j] = (String) tabla.getValueAt(i,j);

		for(int i=filaSeleccionada; i < HojaCalculo.CANTIDAD_FILAS-cantidad; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS; j++)
				dataBackup[i][j] = (String) tabla.getValueAt(i+cantidad,j);

		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS; j++)
				tabla.setValueAt(dataBackup[i][j], i, j);

		dataBackup = null;
		modelo.fireTableStructureChanged();
	}
	
	/**
	 * Metodo que elimina las columnas pasadas por parametro a la tabla
	 * en la posicion actual, hacia abajo.
	 * @param cantidad		Cantidad de columnas a eliminar
	 */
	public void eliminarColumnas(int cantidad) {
		columnaSeleccionada = tabla.getSelectedColumn();

		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=0; j < columnaSeleccionada; j++)
				dataBackup[i][j] = (String) tabla.getValueAt(i,j);

		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=columnaSeleccionada; j < HojaCalculo.CANTIDAD_COLUMNAS-cantidad; j++)
				dataBackup[i][j] = (String) tabla.getValueAt(i,j+cantidad);

		for(int i=0; i < HojaCalculo.CANTIDAD_FILAS; i++)
			for(int j=0; j < HojaCalculo.CANTIDAD_COLUMNAS; j++)
				tabla.setValueAt(dataBackup[i][j], i, j);
	
		dataBackup = null;
		modelo.fireTableStructureChanged();
	}
}