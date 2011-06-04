package edu.dat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.gui.HojaCalculo;
import edu.log.builder.PanelHojaCalculo;

/**
 * Esta clase provee los metodos necesarios para <i>abrir</i> y
 * <i>guardar</i> en la aplicacion.
 * @author Camilo Nova
 */
public class SeleccionadorArchivos {
	
	/**
	 * Representa el archivo en el cual se trabaja actualmente.
	 */
	public File archivoActual;
	
	/**
	 * Este metodo carga en la tabla el archivo que recibe por
	 * parametro.
	 * @param archivo	Archivo a abrir
	 */
	public void abrirArchivo(File archivo) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
			((PanelHojaCalculo) HojaCalculo.getInstancia().constructor).setAllDataToModel((String[][]) ois.readObject());
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		archivoActual = archivo;
	}

	/**
	 * Este metodo almacena los datos de la tabla en el archivo
	 * pasado por parametro.
	 * @param archivo	Archivo a guardar
	 */
	public void guardarArchivo(File archivo) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
			String[][] datos = ((PanelHojaCalculo) HojaCalculo.getInstancia().constructor).getAllDataFromModel();
			oos.writeObject(datos);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		archivoActual = archivo;
	}
	
	/**
	 * Metodo para guardar cambios en el archivo actual.
	 */
	public void guardar() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoActual));
			String[][] datos = ((PanelHojaCalculo) HojaCalculo.getInstancia().constructor).getAllDataFromModel();
			oos.writeObject(datos);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
