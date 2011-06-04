package edu.gui;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.log.builder.PanelHojaCalculo;

/**
 * Esta clase representa el dialogo de eliminar filas o columnas en la tabla.
 * Extiende de <i>JDialog</i> e implementa <i>KeyListener</i>.
 * @author Camilo Nova
 */
public class DialogoEliminar extends JDialog implements KeyListener {

	/**
	 * Representa la cantidad de filas
	 */
	private JTextField cantidadFilas = new JTextField(5);
	
	/**
	 * Representa la cantidad de columnas
	 */
	private JTextField cantidadColumnas = new JTextField(5);
	
	/**
	 * Representa el boton de aceptar
	 */
	private JButton aceptarBtn = new JButton("Aceptar");
	
	/**
	 * Representa el boton de cancelar
	 */
	private JButton cancelarBtn = new JButton("Cancelar");
	
	/**
	 * Representa la seleccion de filas
	 */
	private JRadioButton filasRadioBtn = new JRadioButton("Filas", true);
	
	/**
	 * Representa la seleccion de columnas
	 */
	private JRadioButton columnasRadioBtn = new JRadioButton("Columnas");
	
	/**
	 * Representa el grupo de selecciones
	 */
	private ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * Constructor de la clase, genera el dialogo donde se puede seleccionar
	 * si se quieren eliminar filas o columnas y cuantas.<p>
	 * En cada caso, decide que accion tomar para cada opcion.
	 *
	 */
	public DialogoEliminar() {
		cantidadFilas.addKeyListener(this);
		cantidadColumnas.addKeyListener(this);
		filasRadioBtn.addKeyListener(this);
		columnasRadioBtn.addKeyListener(this);
		buttonGroup.add(filasRadioBtn);
		buttonGroup.add(columnasRadioBtn);
		
		add(filasRadioBtn);
		add(cantidadFilas);
		add(columnasRadioBtn);
		add(cantidadColumnas);
		
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(filasRadioBtn.isSelected() && Integer.parseInt(cantidadFilas.getText()) > 0) {
						((PanelHojaCalculo)HojaCalculo.getInstancia().constructor).eliminarFilas(Integer.parseInt(cantidadFilas.getText()));
					}
					else if(columnasRadioBtn.isSelected() && Integer.parseInt(cantidadColumnas.getText()) > 0) {
						((PanelHojaCalculo)HojaCalculo.getInstancia().constructor).eliminarColumnas(Integer.parseInt(cantidadColumnas.getText()));
					}
					else
						System.err.println("Debe ser un numero mayor que cero");
				} catch (NumberFormatException e1) {
					System.err.println("Debe digitar un numero!!!");
				}
				dispose();
			}
		});
		add(aceptarBtn);
		
		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(cancelarBtn);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3,2));
		setSize(220,110);
		setTitle("Eliminar...");
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-125, Toolkit.getDefaultToolkit().getScreenSize().height/2-125);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setVisible(true);
	}
	
	/**
	 * Listener de teclado para el dialogo de peticion de datos.
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			aceptarBtn.doClick();
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			cancelarBtn.doClick();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}
}