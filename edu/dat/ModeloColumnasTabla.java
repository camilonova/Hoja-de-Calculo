package edu.dat;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * Esta clase determina el modelo de las columnas en la tabla, son
 * de un tipo especial ya que deseamos determinar un tamaño minimo
 * para las columnas, ademas de tener la capacidad de darle un aspecto
 * mas personalizado a las columnas en una tabla.
 * @author Camilo Nova
 */
public class ModeloColumnasTabla extends DefaultTableColumnModel {
	
	/**
	 * Esta variable ayuda a saltar la primera columna de la tabla
	 * que como veremos es la columna de la autonumeracion.
	 */
	private boolean primera = true;
	
	public void addColumn(TableColumn tc) {
		if (primera)
			primera = false;
		else {
			tc.setMinWidth(150);
			super.addColumn(tc);
		}
	}
}
