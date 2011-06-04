package edu.log.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import edu.dat.FiltroArchivo;
import edu.gui.DialogoEliminar;
import edu.gui.DialogoInsertar;
import edu.gui.HojaCalculo;

/**
 * Esta clase genera el menu principal de la aplicacion.
 * @author Camilo Nova
 */

public class MenuPrincipal extends JMenuBar implements ConstructorAbstracto {
	
	private JMenu archivoMenu = new JMenu("Archivo");
	private JMenu edicionMenu = new JMenu("Edicion");
	private JMenu ayudaMenu = new JMenu("Ayuda");
	
	private JMenuItem nuevoItem = new JMenuItem("Nuevo");
	private JMenuItem abrirItem = new JMenuItem("Abrir");
	private JMenuItem guardarItem = new JMenuItem("Guardar");
	public JMenuItem salirItem = new JMenuItem("Salir");
	
	private JMenuItem insertarItem = new JMenuItem("Insertar");
	private JMenuItem eliminarItem = new JMenuItem("Eliminar");
	
	private JMenuItem creditosItem = new JMenuItem("Creditos");
	
	/**
	 * Constructor de la clase. Genera el menu y le da funcionalidad
	 * a todos los items del menu.
	 *
	 */
	public MenuPrincipal() {
		archivoMenu.setMnemonic('a');
		edicionMenu.setMnemonic('e');
		ayudaMenu.setMnemonic('y');
		
		/*
		 * Construccion del menu archivo
		 */
		nuevoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HojaCalculo.getInstancia().seleccionadorArchivos.archivoActual = null;
				((PanelHojaCalculo) HojaCalculo.getInstancia().constructor).setAllDataToModel(new String[HojaCalculo.CANTIDAD_FILAS][HojaCalculo.CANTIDAD_COLUMNAS]);
			}
		});
		nuevoItem.setMnemonic('n');
		nuevoItem.setToolTipText("Crear una Tabla Vacia");
		archivoMenu.add(nuevoItem);
		
		archivoMenu.addSeparator();
		
		abrirItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.addChoosableFileFilter(new FiltroArchivo());
				int option = chooser.showOpenDialog(HojaCalculo.getInstancia());
				if (option == JFileChooser.APPROVE_OPTION) {
					HojaCalculo.getInstancia().seleccionadorArchivos.abrirArchivo(chooser.getSelectedFile());
				}
			}
		});
		abrirItem.setMnemonic('a');
		abrirItem.setToolTipText("Abrir un archivo");
		archivoMenu.add(abrirItem);
		
		guardarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (HojaCalculo.getInstancia().seleccionadorArchivos.archivoActual != null) {
					HojaCalculo.getInstancia().seleccionadorArchivos.guardar();
				}
				else {
					JFileChooser chooser = new JFileChooser();
					chooser.setMultiSelectionEnabled(false);
					chooser.addChoosableFileFilter(new FiltroArchivo());
					int option = chooser.showSaveDialog(HojaCalculo.getInstancia());
					if (option == JFileChooser.APPROVE_OPTION) {
						HojaCalculo.getInstancia().seleccionadorArchivos.guardarArchivo(chooser.getSelectedFile());
					}
				}
			}
		});
		guardarItem.setMnemonic('g');
		guardarItem.setToolTipText("Guardar el archivo actual");
		archivoMenu.add(guardarItem);
		
		archivoMenu.addSeparator();
		
		salirItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (HojaCalculo.getInstancia().seleccionadorArchivos.archivoActual == null) {
					int option = JOptionPane.showConfirmDialog(HojaCalculo.getInstancia(), "El archivo no ha sido guardado. Desea guardarlo ahora", "Guardar...", JOptionPane.YES_NO_CANCEL_OPTION);
					if (option == JOptionPane.YES_OPTION)
						guardarItem.doClick();
					if (option == JOptionPane.NO_OPTION)
						System.exit(0);
				}
				else
					System.exit(0);
			}
		});
		salirItem.setMnemonic('s');
		salirItem.setToolTipText("Salir del programa");
		archivoMenu.add(salirItem);
		
		/*
		 * Construccion del menu edicion
		 */	
		insertarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogoInsertar();
			}
		});
		insertarItem.setMnemonic('i');
		insertarItem.setToolTipText("Insertar Filas o Columnas");
		edicionMenu.add(insertarItem);
		
		eliminarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogoEliminar();
			}
		});
		eliminarItem.setMnemonic('e');
		eliminarItem.setToolTipText("Eliminar Filas o Columnas");
		edicionMenu.add(eliminarItem);
		
		/*
		 * Construccion del menu ayuda
		 */
		creditosItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(HojaCalculo.getInstancia(), "\"Hoja Calculo\" Desarrollado por:\n" +
						"Camilo Hernando Nova Martinez\n" +
						"codigo 20022020090\n" +
						"Ingenieria de Sistemas\n" +
						"Ciencias de la Computacion I\n" +
						"Universidad Distrital Fransisco Jose de Caldas");
			}
		});
		creditosItem.setMnemonic('c');
		creditosItem.setToolTipText("Mostrar los creditos de la aplicacion");
		ayudaMenu.add(creditosItem);
		
		add(archivoMenu);
		add(edicionMenu);
		add(ayudaMenu);	
	}
}