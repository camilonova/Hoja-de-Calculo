package edu.dat;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Esta clase representa el filtro para la seleccion de archivos
 * en los dialogos de <b>abrir</b> y de <b>guardar</b>.<p>
 * Extiende de <i>FileFilter</i> e implementa los metodos necesarios
 * para hacer un filtrado unico de un tipo de archivo, en este caso
 * el tipo de archivo es seleccionado con la extension <i>.cam</i>.
 * @author Camilo Nova
 */
public class FiltroArchivo extends FileFilter {

	public boolean accept(File f) {
		if(f.isDirectory())
			return true;
		if (f.getName().endsWith(".cam"))
			return true;
		return false;
	}

	public String getDescription() {
		return "\"*.cam\" Archivo de Hoja de Calculo - Proyecto Ciencias";
	}
}
