package edu.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.dat.SeleccionadorArchivos;
import edu.log.builder.BarraHerramientas;
import edu.log.builder.ConstructorAbstracto;
import edu.log.builder.MenuPrincipal;
import edu.log.builder.PanelHojaCalculo;

/**
 * Clase principal de la aplicacion, utiliza el patron <i>Builder</i>
 * para contruir secuencialmente la interfaz grafica.<p>
 * Implementa la interface <i>WindowListener</i> para manejar el evento
 * de cerrado de la aplicacion.
 * @author Camilo Nova
 */

public class HojaCalculo extends JFrame implements WindowListener {
	
	/**
	 * Representa la unica instancia de la clase, con lo cual
	 * garantizamos que solo exista una instancia en cada ejecucion
	 */
	private static HojaCalculo instancia;
	
	/**
	 * Representa una copia del MenuItem de salir, para el evento
	 * de cerrado de la aplicacion
	 */
	private JMenuItem salir;
	
	/**
	 * Representa la constante de la cantidad de filas de la tabla
	 */
	public final static int CANTIDAD_FILAS = 100;
	
	/**
	 * Representa la constante de la cantidad de columnas de la tabla
	 */
	public final static int CANTIDAD_COLUMNAS = 26;

	/**
	 * Representa la interface del patron Builder
	 */
	public ConstructorAbstracto constructor;

	/**
	 * Representa el seleccionador de archivos, para abrir y guardar
	 * en la aplicacion
	 */
	public SeleccionadorArchivos seleccionadorArchivos;
	
	/**
	 * Constructor de la clase, hace uso del patron builder para
	 * construir la interfaz grafica. Es privado para que solo sea
	 * instanciado mediante el llamado al metodo estatico <i>getInstancia()</i>
	 *
	 */
	private HojaCalculo() {
		seleccionadorArchivos = new SeleccionadorArchivos();
		
		// Construimos la parte grafica de la aplicacion
		constructor = new MenuPrincipal();
		salir = ((MenuPrincipal) constructor).salirItem;
		setJMenuBar((JMenuBar) constructor);
		
		constructor = new BarraHerramientas();
		add((Component) constructor, BorderLayout.NORTH);
		
		constructor = new PanelHojaCalculo();
		add((Component) constructor, BorderLayout.CENTER);
		// Terminamos...

		addWindowListener(this);
		setTitle("Hoja de Calculo - Ciencias de la Computacion I - Camilo Hernando Nova© 2005");
		setLocationByPlatform(true);
		setSize(800,600);
		setVisible(true);
	}
	
	/**
	 * Metodo que implementa el patron <i>Singleton</i> en la aplicacion
	 * @return	Instancia unica de la aplicacion.
	 */
	public static HojaCalculo getInstancia() {
		if(instancia == null)
			instancia = new HojaCalculo();
		return instancia;
	}
	
	/**
	 * Metodo principal de la clase, ejecuta la aplicacion.
	 * @param args	(Sin uso)
	 */
	public static void main(String[] args) {
		HojaCalculo.getInstancia();
	}

	/**
	 * Metodo que se ejecuta al intentar cerrar la aplicacion
	 */
	public void windowClosing(WindowEvent e) {
		salir.doClick();
	}
	public void windowOpened(WindowEvent e) {
	}
	public void windowClosed(WindowEvent e) {
	}
	public void windowIconified(WindowEvent e) {
	}
	public void windowDeiconified(WindowEvent e) {
	}
	public void windowActivated(WindowEvent e) {
	}
	public void windowDeactivated(WindowEvent e) {
	}
}
