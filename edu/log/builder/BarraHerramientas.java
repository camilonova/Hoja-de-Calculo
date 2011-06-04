package edu.log.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import edu.gui.DialogoEliminar;
import edu.gui.DialogoInsertar;

/**
 * Esta clase construye una barra de herramientas para la aplicacion
 * @author Camilo Nova
 */

public class BarraHerramientas extends JToolBar implements ConstructorAbstracto {
	
	/**
	 * Representa el boton de insertar filas o columnas
	 */
	private JButton insertarBoton = new JButton("Insertar");
	
	/**
	 * Representa el boton de eliminar filas o columnas
	 */
	private JButton eliminarBoton = new JButton("Eliminar");
	
	/**
	 * Constructor de la clase. Genera la barra de herramientas
	 * y le da utilidad a sus botones.
	 *
	 */
	public BarraHerramientas() {
		insertarBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogoInsertar();
			}
		});
		insertarBoton.setToolTipText("Insertar Filas o Columnas");
		
		eliminarBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogoEliminar();
			}
		});
		eliminarBoton.setToolTipText("Eliminar la Seleccion Actual");
		
		add(insertarBoton);
		add(eliminarBoton);
	}
}
