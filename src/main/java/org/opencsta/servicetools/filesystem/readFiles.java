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
package org.opencsta.servicetools.filesystem;

import java.io.File;
import java.io.IOException;

/**
 * @author chrismylonas
 * 
 */
public class readFiles {

	/**
	 * @param a
	 * @throws IOException
	 */
	public static void main(String[] a) throws IOException {
		showDir(1, new File("/dev/disk/by-uuid/"));
	}

	/**
	 * @param indent
	 * @param file
	 * @throws IOException
	 */
	static void showDir(int indent, File file) throws IOException {
		for (int i = 0; i < indent; i++)
			System.out.print('-');
		String abs = file.getAbsolutePath();
		String can = file.getCanonicalPath();
		if (abs.equalsIgnoreCase(can)) {
			;// not a link
		} else {
			System.out.println(abs + " links to " + can);
		}
		File pwd = new File(".");
		System.out.println("Current dir is: " + pwd.getAbsolutePath());
		File[] roots = pwd.listRoots();
		for (int i = 0; i < roots.length; i++) {
			System.out.println("Root is: " + roots[i].toString());
		}
		System.out.println(" Root dir is: " + pwd.listRoots());
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
				showDir(indent + 4, files[i]);
		}
	}
}