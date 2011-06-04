package edu.dat;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * Esta clase determina las propiedades de las filas de la primera
 * columna, que es la columna de autonumeracion.
 * @author Camilo Nova
 */
public class ModeloFilasTabla extends DefaultTableColumnModel {
	
	/**
	 * Representa si es la primera columna de la tabla.
	 */
	private boolean first = true;
	
	public void addColumn(TableColumn tc) {
		// Solo nos interesa la primera, el resto no importa
		if(first) {
			tc.setMaxWidth(25);
			super.addColumn(tc);
			first = false;
		}
	}
}
