package FDTDpre;

/* 3D FDTD PACKAGE in Java by Stephen Kirkup
  ==========================================
  MARK 2. Released August 2008	
  (MARK 1 by Stephen Kirkup was released January 2005)
  (MARK 2 : Design revised by Stephen Kirkup, Goodchild Ndou and Irfan Mulla
            and user manual by Stephen Kirkup, Goodchild Hdou, Irfan Mulla and Javad Yazdani)
  User manual and codes (or their successors) and any upgrades or information are to be 
   available from the websites below
  FDTD-OS project
  Java codes


  Copyright 2008 Stephen Kirkup et al
  John Tyndall Nuclear Research Institute
  School of Computing Engineering and Physical Sciences
  University of Central Lancashire - Westlakes Campus
  Samuel Lindow Building
  West Lakes Science and Technology Park
  Whitehaven
  Cumbria CA24 3JY
  United Kingdom

  www.kirkup.info/opensource

  Issued under the GNU General Public License 2007, see gpl.txt
  Available as open source
  FDTD-OS : http://groups.google.co.uk/group/fdtd-os
            www.kirkup.info/opensource
            www.east-lancashire-research.org.uk
  The main author can be contacted on stephen@kirkup.info
  This class is required for FDTDpre.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

public class FileRead {
	private static String file;

	public FileRead(String fileName) {
		file = fileName;
	}

	public static String getFileName() {
		return file;
	}

	public static String getString(int irow) {
		int i;
		String s = "";
		// Trap exceptions
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			for (i = 1; i <= irow + 1; i++)
				s = in.readLine();
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found:  " + file);
		} catch (IOException e) {
			System.out.println("IOException: the stack trace is:");
		}
		return s;
	}

	public static double getDouble(int irow, int jcol) {
		int i, j;
		String s = "";
		double val = 0.0;

		// Trap exceptions
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			StreamTokenizer st = new StreamTokenizer(in);
			for (i = 1; i <= irow; i++)
				s = in.readLine();
			for (j = 1; j <= jcol; j++)
				st.nextToken();
			val = st.nval;
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found:  " + file);
		} catch (IOException e) {
			System.out.println("IOException: the stack trace is:");
		}
		return val;
	}

	public static int getInt(int irow, int jcol) {
		double val;
		int ival;
		val = getDouble(irow, jcol);
		ival = (int) val;
		return ival;
	}

	public static boolean getBoolean(int irow, int jcol) {
		int ival;
		boolean x;
		ival = getInt(irow, jcol);
		x = ival == 1;
		return x;
	}

}
