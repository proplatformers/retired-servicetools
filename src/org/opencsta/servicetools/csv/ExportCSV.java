/*
This file is part of Open CSTA.

    Open CSTA is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Open CSTA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Open CSTA.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opencsta.servicetools.csv;

import org.opencsta.servicetools.database.Database;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.log4j.Logger;

/**
 * 
 * @author chrismylonas
 */
public class ExportCSV {

	/**
     * 
     */
	private static Logger csvlogger = Logger.getLogger(ExportCSV.class);

	/**
     * 
     */
	private FileOutputStream fos;

	/**
     * 
     */
	private PrintStream ps;

	/**
     * 
     */
	private String filename;

	/**
     * 
     */
	public ExportCSV() {

	}

	/**
	 * @param _filename
	 */
	public ExportCSV(String _filename) {
		setFilename(_filename);
	}

	/**
     * 
     */
	public void openFile() {
		try {
			if (getFilename() == null) {
				throw new IOException("No filename set");
			}
			File file = new File(getFilename());
			fos = new FileOutputStream(file);
			ps = new PrintStream(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param headers
	 */
	public void writeHeaders(String[] headers) {
		String row = new String();
		for (int i = 0; i < headers.length; i++) {
			row += "\"" + headers[i] + "\"";
			if ((i + 1) == headers.length) {
				continue;
			}
			row += ",";
		}
		csvlogger
				.info("Writing Headers to file (" + getFilename() + "):" + row);
		ps.println(row);
	}

	/**
	 * @param rowData
	 */
	public void writeRow(String[] rowData) {
		String row = new String();
		for (int i = 0; i < rowData.length; i++) {
			row += "\"" + rowData[i] + "\"";
			if ((i + 1) == rowData.length) {
				continue;
			}
			row += ",";
		}
		csvlogger.info("Writing Row Data to file (" + getFilename() + "):"
				+ row);
		ps.println(row);
	}

	/**
     * 
     */
	public void closeFile() {
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

}
