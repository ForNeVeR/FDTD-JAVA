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
  FDTDpre requires the following class files to run: Component, EField, FDTDArrai, FDTDVector, 
   FileRead, Grid, HField, Material, Mesh, PhysicalConstants, Point, PointCalculator, Transferable,
   VectorField, XYGrid, XZGrid, YZGrid.
 */

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FDTDpre {
	public static void main(String[] args) throws IOException {

		int MaxNtout;
		String fileName = "experiment.dat";
		FileRead read= new FileRead(fileName);
                //ReadFromFile read = new ReadFromFile(fileName);

		// Total time of simulation
		double totalt;
                
		// The speed of electromagnetic radiation in the outer layer (Material
		// 1)
		double c1;

		// Variables in FDTD package
		int Nx, Ny, Nz;

		FDTDArray sigma = new FDTDArray();
		FDTDArray mu = new FDTDArray();
		FDTDArray epsilon = new FDTDArray();

		// Tolerance
		final double eps = 0.000001;
		final int ndlsplit = 3;

		String expname;
		final int MAXMAT = 20;

		// nmat, the actual number of distinct materials
		int nmat = 0;

		// material object
		Material[] material = new Material[MAXMAT + 1];

		// The counter through the distinct materials
		int imat;

		// Dimensions of the domain
		double xlength, ylength, zlength;

		// Cell size (Yee cell)
		double dl;

		// Set MAXCOMP, the maximum number of distinct materials
		final int MAXCOMP = 50;

		// Components
		Component[] component = new Component[MAXCOMP + 1];

		// ncomp, icomp the number, counter and label of the components
		int ncomp, icomp, jcomp;

		// Excitation
		FDTDVector[] E_excite = new FDTDVector[MAXCOMP + 1];

		boolean changemat;

		// Time
		double time;
		// time step
		double dt;
		// number of time steps in simulation
		int Nt;
		// the number of time steps that are output
		int Ntout;
		// the number of time steps jumped over
		int Ntjump;
		int n;
		double time_appear, time_disappear;

		// Output Control
		// Number of times that the electromagnetic data is saved
		int nsave;
		double sumcond, sumrel_perm, sumsusc;
		double avcond, avrel_perm, avsusc;
		double xlow, ylow, zlow, xx, yy, zz;
		double div;

		// General counters
		int i, j, k, ii, jj, kk;

		// Setting Excitation
		double sumEx, sumEy, sumEz;

		String s, monitor, stemfile, savefile;
		int itemp;
		double temp1, temp2;
		double coeff;

		// coordinates corresponding to E and H
		double[][] Ecube = new double[4][4], Hcube = new double[4][4];

		double[] Hold = new double[4], Hnew = new double[4], Eold = new double[4], Enew = new double[4];

		// material properties averaged in x,y,z directions
		double[] sigval = new double[4], rhoval = new double[4], muval = new double[4], epsval = new double[4];

		// counters
		int Jx, // Jx = 1..Nx
		Jy, // Jy = 1..Ny
		Jz; // Jz = 1..Nz

		// the voltage across contacts on the capacitor and the current
		double Vab, Iab;

		// position on Graphics screen
		int IX, IZ, IXP, IZP, JXP, JZP;

		FDTDVector point = new FDTDVector();
		double xl, xu, yl, yu, zl, zu;

		double Exval, Eyval, Ezval, Val;

		double current;
		double dtomu, temp, con, con1, con2;

		String name;
		double conductivity, susceptibility, rel_permittivity;
		boolean appeared;

		Mesh M;

		PhysicalConstants Const = new PhysicalConstants();

		double epsilon0 = Const.epsilon0;
		double mu0 = Const.mu0;
		double c0 = Const.c0;

		// Voltage Probes
		final int maxpd = 20;
		FDTDVector startpoint[] = new FDTDVector[maxpd + 1];
		FDTDVector endpoint[] = new FDTDVector[maxpd + 1];
		String pdfile[] = new String[maxpd + 1];

		time = 0.0;

		// Set up file names for output
		itemp = fileName.indexOf('.');
		stemfile = fileName.substring(0, itemp);
		// Monitor file: Check this file if there are errors
		monitor = stemfile + ".mon";
		// Output file
		savefile = stemfile + ".out";

		// setup the streams
		File outFileX = new File("x.out");
		FileOutputStream outFileStreamX = new FileOutputStream(outFileX);
		DataOutputStream outDataStreamX = new DataOutputStream(outFileStreamX);

		File outFileY = new File("y.out");
		FileOutputStream outFileStreamY = new FileOutputStream(outFileY);
		DataOutputStream outDataStreamY = new DataOutputStream(outFileStreamY);

		File outFileZ = new File("z.out");
		FileOutputStream outFileStreamZ = new FileOutputStream(outFileZ);
		DataOutputStream outDataStreamZ = new DataOutputStream(outFileStreamZ);

		System.out.println("Reading Input File");
		// Get experiment name
		expname = read.getString(5);

		// Get material properties
		// Get number of materials and validate
		nmat = read.getInt(10, 1);
		if (nmat > MAXMAT) {
			System.out.println("Number of Materials is too Large (>MAXMAT) ");
		}
		if (nmat < 1) {
			System.out.println("There must be at least one material");
		}
		// Get material properties
		for (imat = 1; imat <= nmat; imat++) {
			name = read.getString(12 + 3 * imat);
			conductivity = read.getDouble(13 + 3 * imat, 1);
			rel_permittivity = read.getDouble(13 + 3 * imat, 2);
			susceptibility = read.getDouble(13 + 3 * imat, 3);
			material[imat] = new Material(name, conductivity, rel_permittivity,
					susceptibility);
		}

		// Get domain and mesh
		// Get size of Yee cell
		dl = read.getDouble(22 + 3 * nmat, 1);
		// Validate dl
		if (dl <= 0.0) {
			System.out.println("The cell size must be positive");
			System.out.println("Input terminated");
		}
		// Get domain dimensions
		// Get xlength and validate
		xlength = read.getDouble(17 + 3 * nmat, 1);
		if (xlength < dl) {
			System.out.println("x-dimension is less than the cell size");
			System.out.println("Input terminated");
		}
		ylength = read.getDouble(17 + 3 * nmat, 2);
		if (ylength < dl) {
			System.out.println("y-dimension is less than the cell size");
			System.out.println("Input terminated");
		}
		zlength = read.getDouble(17 + 3 * nmat, 3);
		if (zlength < dl) {
			System.out.println("z-dimension is less than the cell size");
			System.out.println("Input terminated");
		}

		ncomp = read.getInt(29 + 3 * nmat, 1);
		if (ncomp > MAXCOMP) {
			System.out
					.println("Number of material components is too Large (>MAXMAT) ");
		}
		if (ncomp < 0) {
			System.out
					.println("There must be at least zero material components");
		}

		// Set time
		// Set totalt
		totalt = read.getDouble(35 + 3 * nmat + 2 * ncomp, 1);
		if (totalt <= 0.0) {
			System.out.println("Total simulation time must be positive");
		}

		for (icomp = 1; icomp <= ncomp; icomp++) {
                        imat = read.getInt(32 + 3 * nmat + 2 * icomp, 1);
			xl = read.getDouble(32 + 3 * nmat + 2 * icomp, 2);
			xu = read.getDouble(32 + 3 * nmat + 2 * icomp, 3);
			yl = read.getDouble(32 + 3 * nmat + 2 * icomp, 4);
			yu = read.getDouble(32 + 3 * nmat + 2 * icomp, 5);
			zl = read.getDouble(32 + 3 * nmat + 2 * icomp, 6);
			zu = read.getDouble(32 + 3 * nmat + 2 * icomp, 7);
			time_appear = read.getDouble(45 + 3 * nmat + 3 * ncomp + icomp, 2);
			time_disappear = read.getDouble(45 + 3 * nmat + 3 * ncomp + icomp,
					3);
			component[icomp] = new Component(imat, xl, xu, yl, yu, zl, zu,
					time_appear, time_disappear);
		}

		// Set Excitation
		for (icomp = 1; icomp <= ncomp; icomp++) {
			double x, y, z;
			x = read.getDouble(41 + 3 * nmat + 2 * ncomp + icomp, 2);
			y = read.getDouble(41 + 3 * nmat + 2 * ncomp + icomp, 3);
			z = read.getDouble(41 + 3 * nmat + 2 * ncomp + icomp, 4);

			E_excite[icomp] = new FDTDVector(x, y, z);
		}

		M = new Mesh(xlength, ylength, zlength, dl, totalt);

		HField H = new HField();

		EField E = new EField();

		// Set Nx, Ny and Nz and check that mesh is OK
		Nx = M.Nx;
		Ny = M.Ny;
		Nz = M.Nz;
		dt = M.dt;
		Nt = M.Nt;

		// Set MaxNtout
		MaxNtout = read.getInt(54 + 3 * nmat + 4 * ncomp, 1);
		System.out.println("MaxNtout = " + MaxNtout);
		Ntjump = (Nt - 1) / MaxNtout + 1;
		Ntout = Nt / Ntjump;

		System.out.println("Nt = " + Nt);
		System.out.println("Ntout = " + Ntout);

		// Set the speed of electromagnetic radiation in the outer field
		c1 = c0
				/ Math.sqrt((1.0 + material[1].susceptibility)
						* material[1].rel_perm);

		// Send information to the monitoring file *.mon
		System.out.println("Sending information to the monitoring file "
				+ monitor);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				monitor, false)));
		out.print("Monitoring file for ");
		out.println(read.getFileName());
		out.println("Nt=");
		out.println(Nt);
		out.println("dt=  value           E  (exponent)");
		temp2 = Math.log(dt) / Math.log(10.0);
		itemp = (int) temp2 - 1;
		temp1 = dt / Math.pow(10.0, itemp);
		out.print(temp1);
		out.print("  ");
		out.println(itemp);
		out.println("Nx=");
		out.println(Nx);
		out.println("Ny");
		out.println(Ny);
		out.println("Nz=");
		out.println(Nz);
		out.println("Experiment name: " + expname);
		out.print("Number of Materials ");
		out.println(nmat);
		for (imat = 1; imat <= nmat; imat++) {
			out.println(material[imat].name);
			out.print(": cond=");
			out.print(material[imat].conductivity);
			out.print("   rel_perm=");
			out.print(material[imat].rel_perm);
			out.print("   susc=");
			out.println(material[imat].susceptibility);
		}
		out.print("Dimensions: ");
		out.print(xlength);
		out.print("   ");
		out.print(ylength);
		out.print("   ");
		out.println(zlength);
		out.print("Yee Cell Size:");
		out.println(dl);

		out.print("Number of material components:");
		out.println(ncomp);

		for (icomp = 1; icomp <= ncomp; icomp++) {
			out.print("Material no ");
			out.print(component[icomp].imat);
			out.print(" (");
			out.println(material[component[icomp].imat].name + ") ");
			out.print("   ");
			out.print(component[icomp].xl);
			out.print("   ");
			out.print(component[icomp].xu);
			out.print("   ");
			out.print(component[icomp].yl);
			out.print("   ");
			out.print(component[icomp].yu);
			out.print("   ");
			out.print(component[icomp].zl);
			out.print("   ");
			out.print(component[icomp].zu);

			out.println();
		}

		out.println();
		out.println("Excitation");
		for (icomp = 1; icomp <= ncomp; icomp++) {
			out.print(icomp);
			out.print("  ");
			out.print(E_excite[icomp].x + " " + E_excite[icomp].y + " "
					+ E_excite[icomp].z);
			out.println();
		}
		out.println();
		out.println("Times of appearance and disappearance");
		for (icomp = 1; icomp <= ncomp; icomp++) {
			out.print(icomp);
			out.print("  ");
			out.print(component[icomp].time_appear);
			out.print("  ");
			out.println(component[icomp].time_disappear);
		}
		out.println("MaxNtout = " + MaxNtout);

		int npd;

		npd = read.getInt(58 + 3 * nmat + 4 * ncomp, 1);

		out.close();

		// Open output files
		PrintWriter pds = new PrintWriter(new BufferedWriter(new FileWriter(
				"pds.out", false)));

		System.out.println("Initialise E,H data structures to zero");

		outDataStreamX.writeInt(Ntout);
		outDataStreamX.writeInt(Ny);
		outDataStreamX.writeInt(Nz);

		outDataStreamY.writeInt(Ntout);
		outDataStreamY.writeInt(Nx);
		outDataStreamY.writeInt(Nz);

		outDataStreamZ.writeInt(Ntout);
		outDataStreamZ.writeInt(Nx);
		outDataStreamZ.writeInt(Ny);

		// Loop (n) through the time steps
		for (n = 1; n <= Ntout * Ntjump + 1; n++) { // compute H from E

			System.out.println(n);

			time = (n - 0.5) * dt;

			changemat = false;
			for (icomp = 1; icomp <= ncomp; icomp++) {
				if (!component[icomp].exists(time - dt) == component[icomp]
						.exists(time)) {
					changemat = true;
				}
			}
			if (changemat) {
				System.out
						.println("Setting Up Material Property Data Structures");
				div = dl / (ndlsplit + 1);
				System.out.println("Material changes");
				for (i = 1; i <= Nx - 1; i++) {
					xlow = i * dl + div / 2;
					for (j = 1; j <= Ny - 1; j++) {
						ylow = j * dl + div / 2;
						for (k = 1; k <= Nz - 1; k++) {
							zlow = k * dl + div / 2;
							sumcond = 0.0;
							sumrel_perm = 0.0;
							for (ii = 0; ii <= ndlsplit - 1; ii++) {
								point.x = xlow + ii * div;
								for (jj = 0; jj <= ndlsplit - 1; jj++) {
									point.y = ylow + jj * div;
									for (kk = 0; kk <= ndlsplit - 1; kk++) {
										point.z = zlow + kk * div;
										imat = 1;
										for (icomp = 1; icomp <= ncomp; icomp++)
											if (component[icomp].intime(point,
													time))
												imat = component[icomp].imat;
										sumcond = sumcond
												+ material[imat].conductivity;
										sumrel_perm = sumrel_perm
												+ material[imat].rel_perm;
									}
								}
							}
							avcond = sumcond / (ndlsplit * ndlsplit * ndlsplit);
							sigma.grid[i][j][k] = avcond;
							avrel_perm = sumrel_perm
									/ (ndlsplit * ndlsplit * ndlsplit);
							epsilon.grid[i][j][k] = avrel_perm * epsilon0;
						}
					}
				}

				// Set mu,rho
				for (i = 1; i <= Nx - 1; i++) {
					xlow = i * dl + div / 2 - dl / 2;
					for (j = 1; j <= Ny - 1; j++) {
						ylow = j * dl + div / 2 - dl / 2;
						for (k = 1; k <= Nz - 1; k++) {
							zlow = k * dl + div / 2 - dl / 2;
							sumsusc = 0.0;
							for (ii = 0; ii <= ndlsplit - 1; ii++) {
								point.x = xlow + ii * div;
								for (jj = 0; jj <= ndlsplit - 1; jj++) {
									point.y = ylow + jj * div;
									for (kk = 0; kk <= ndlsplit - 1; kk++) {
										point.z = zlow + kk * div;
										imat = 1;
										for (icomp = 1; icomp <= ncomp; icomp++)
											if (component[icomp].intime(point,
													time))
												imat = component[icomp].imat;
										sumsusc = sumsusc
												+ material[imat].susceptibility;
									}
								}
							}
							avsusc = sumsusc / (ndlsplit * ndlsplit * ndlsplit);
							mu.grid[i][j][k] = mu0 * (1.0 + avsusc);
						}
					}
				}
			}
			System.out.println("Computing H");

			for (Jx = 1; Jx <= Nx - 2; Jx++) {
				for (Jy = 1; Jy <= Ny - 2; Jy++) {
					for (Jz = 1; Jz <= Nz - 2; Jz++) {
						Hold[1] = H.x.grid[Jx][Jy][Jz];
						Hold[2] = H.y.grid[Jx][Jy][Jz];
						Hold[3] = H.z.grid[Jx][Jy][Jz];
						muval[1] = (mu.grid[Jx][Jy][Jz] + mu.grid[Jx + 1][Jy][Jz]) / 2.0;
						muval[2] = (mu.grid[Jx][Jy][Jz] + mu.grid[Jx][Jy + 1][Jz]) / 2.0;
						muval[3] = (mu.grid[Jx][Jy][Jz] + mu.grid[Jx][Jy][Jz + 1]) / 2.0;

						rhoval[1] = 1.0e-20;
						rhoval[2] = 1.0e-20;
						rhoval[3] = 1.0e-20;

						Ecube[1][2] = (E.x.grid[Jx - 1][Jy][Jz] - E.x.grid[Jx - 1][Jy - 1][Jz])
								/ dl;
						Ecube[1][3] = (E.x.grid[Jx - 1][Jy][Jz] - E.x.grid[Jx - 1][Jy][Jz - 1])
								/ dl;
						Ecube[2][1] = (E.y.grid[Jx][Jy - 1][Jz] - E.y.grid[Jx - 1][Jy - 1][Jz])
								/ dl;
						Ecube[2][3] = (E.y.grid[Jx][Jy - 1][Jz] - E.y.grid[Jx][Jy - 1][Jz - 1])
								/ dl;
						Ecube[3][1] = (E.z.grid[Jx][Jy][Jz - 1] - E.z.grid[Jx - 1][Jy][Jz - 1])
								/ dl;
						Ecube[3][2] = (E.z.grid[Jx][Jy][Jz - 1] - E.z.grid[Jx][Jy - 1][Jz - 1])
								/ dl;
						Ecube[1][1] = 0.0;
						Ecube[2][2] = 0.0;
						Ecube[3][3] = 0.0;

						// equation 3.26a
						dtomu = dt / muval[1];
						temp = rhoval[1] * dtomu / 2.0;
						con = 1.0 + temp;
						con1 = (1.0 - temp) / con;
						con2 = dtomu / con;
						Hnew[1] = con1 * Hold[1] + con2
								* (Ecube[2][3] - Ecube[3][2]);

						// equation 3.26b
						dtomu = dt / muval[2];
						temp = rhoval[2] * dtomu / 2.0;
						con = 1.0 + temp;
						con1 = (1.0 - temp) / con;
						con2 = dtomu / con;
						Hnew[2] = con1 * Hold[2] + con2
								* (Ecube[3][1] - Ecube[1][3]);

						// equation 3.26c
						dtomu = dt / muval[3];
						temp = rhoval[3] * dtomu / 2.0;
						con = 1.0 + temp;
						con1 = (1.0 - temp) / con;
						con2 = dtomu / con;
						Hnew[3] = con1 * Hold[3] + con2
								* (Ecube[1][2] - Ecube[2][1]);

						// renewH( Hold, Ecube, dt, rhoval, muval, Hnew );
						H.x.grid[Jx][Jy][Jz] = Hnew[1];
						H.y.grid[Jx][Jy][Jz] = Hnew[2];
						H.z.grid[Jx][Jy][Jz] = Hnew[3];
					}
				}
			}

			// First Order Mur Correction
			System.out.println("Mur Correction in H");
			coeff = (c1 * dt - dl) / (c1 * dt + dl);
			H.x.Mur1(Nx, Ny, Nz, coeff);
			H.y.Mur1(Nx, Ny, Nz, coeff);
			H.z.Mur1(Nx, Ny, Nz, coeff);

			// excitation
			System.out.println("Apply Excitation");
			for (i = 1; i <= Nx - 1; i++) {
				for (j = 1; j <= Ny - 1; j++) {
					for (k = 1; k <= Nz - 1; k++) {
						jcomp = 0;
						point.x = (i + 1) * dl;
						point.y = j * dl + dl / 2;
						point.z = k * dl + dl / 2;
						for (icomp = 1; icomp <= ncomp; icomp++) {
							if (Math.abs(E_excite[icomp].x) >= eps) {
								if (component[icomp].intime(point, time))
									jcomp = icomp;
							}
						}
						if (jcomp != 0)
							E.x.grid[i][j][k] = E_excite[jcomp].x;
						jcomp = 0;
						point.x = i * dl + dl / 2;
						point.y = j * dl + dl;
						point.z = k * dl + dl / 2;
						for (icomp = 1; icomp <= ncomp; icomp++) {
							if (Math.abs(E_excite[icomp].y) >= eps) {
								if (component[icomp].intime(point, time))
									jcomp = icomp;
							}
						}
						if (jcomp != 0)
							E.y.grid[i][j][k] = E_excite[jcomp].y;
						jcomp = 0;
						point.x = i * dl + dl / 2;
						point.y = j * dl + dl / 2;
						point.z = k * dl + dl;
						for (icomp = 1; icomp <= ncomp; icomp++) {
							if (Math.abs(E_excite[icomp].z) >= eps) {
								if (component[icomp].intime(point, time))
									jcomp = icomp;
							}
						}
						if (jcomp != 0)
							E.z.grid[i][j][k] = E_excite[jcomp].z;
					}
				}
			}

			pds.print(time + " ");
			for (int ipd = 1; ipd <= npd; ipd++) {
				FDTDVector start = new FDTDVector();
				FDTDVector end = new FDTDVector();
				float pd;
				int line = 60 + 3 * nmat + 4 * ncomp + ipd * 2;
				start.set(read.getDouble(line, 1), read.getDouble(line, 2),
						read.getDouble(line, 3));
				end.set(read.getDouble(line, 4), read.getDouble(line, 5),
						read.getDouble(line, 6));
				pd = (float) E.getPD(start, end);
				pds.print(pd + " ");
				if (ipd == 1) {
					FDTDVector middle = new FDTDVector();
					start.mid(end);
					System.out.println(E.getValue(start).y);
				}
			}
			pds.println();

			// compute E from H
			System.out.println("Computing E");
			for (Jx = 1; Jx <= (Nx - 1); Jx++) {
				for (Jy = 1; Jy <= (Ny - 1); Jy++) {
					for (Jz = 1; Jz <= (Nz - 1); Jz++) {
						Eold[1] = E.x.grid[Jx][Jy][Jz];
						Eold[2] = E.y.grid[Jx][Jy][Jz];
						Eold[3] = E.z.grid[Jx][Jy][Jz];

						sigval[1] = (sigma.grid[Jx][Jy][Jz] + sigma.grid[Jx + 1][Jy][Jz]) / 2.0;
						sigval[2] = (sigma.grid[Jx][Jy][Jz] + sigma.grid[Jx][Jy + 1][Jz]) / 2.0;
						sigval[3] = (sigma.grid[Jx][Jy][Jz] + sigma.grid[Jx][Jy][Jz + 1]) / 2.0;
						epsval[1] = (epsilon.grid[Jx][Jy][Jz] + epsilon.grid[Jx + 1][Jy][Jz]) / 2.0;
						epsval[2] = (epsilon.grid[Jx][Jy][Jz] + epsilon.grid[Jx][Jy + 1][Jz]) / 2.0;
						epsval[3] = (epsilon.grid[Jx][Jy][Jz] + epsilon.grid[Jx][Jy][Jz + 1]) / 2.0;
						Hcube[1][2] = (H.x.grid[Jx][Jy + 1][Jz + 1] - H.x.grid[Jx][Jy][Jz + 1])
								/ dl;
						Hcube[1][3] = (H.x.grid[Jx][Jy + 1][Jz + 1] - H.x.grid[Jx][Jy + 1][Jz])
								/ dl;
						Hcube[2][1] = (H.y.grid[Jx + 1][Jy][Jz + 1] - H.y.grid[Jx][Jy][Jz + 1])
								/ dl;
						Hcube[2][3] = (H.y.grid[Jx + 1][Jy][Jz + 1] - H.y.grid[Jx + 1][Jy][Jz])
								/ dl;
						Hcube[3][1] = (H.z.grid[Jx + 1][Jy + 1][Jz] - H.z.grid[Jx][Jy + 1][Jz])
								/ dl;
						Hcube[3][2] = (H.z.grid[Jx + 1][Jy + 1][Jz] - H.z.grid[Jx + 1][Jy][Jz])
								/ dl;
						Hcube[1][1] = 0.0;
						Hcube[2][2] = 0.0;
						Hcube[3][3] = 0.0;

						double dtoeps;

						// equation 3.27a
						dtoeps = dt / epsval[1];
						temp = sigval[1] * dtoeps / 2.0;
						con = 1.0 + temp;
						con1 = (1.0 - temp) / con;
						con2 = dtoeps / con;
						Enew[1] = con1 * Eold[1] - con2
								* (Hcube[2][3] - Hcube[3][2]);

						// equation 3.27b
						dtoeps = dt / epsval[2];
						temp = sigval[2] * dtoeps / 2.0;
						con = 1.0 + temp;
						con1 = (1.0 - temp) / con;
						con2 = dtoeps / con;
						Enew[2] = con1 * Eold[2] - con2
								* (Hcube[3][1] - Hcube[1][3]);

						// equation 3.27c
						dtoeps = dt / epsval[3];
						temp = sigval[3] * dtoeps / 2.0;
						con = 1.0 + temp;
						con1 = (1.0 - temp) / con;
						con2 = dtoeps / con;
						Enew[3] = con1 * Eold[3] - con2
								* (Hcube[1][2] - Hcube[2][1]);

						// renewE( Eold, Hcube, dt, sigval, epsval, Enew );
						E.x.grid[Jx][Jy][Jz] = Enew[1];
						E.y.grid[Jx][Jy][Jz] = Enew[2];
						E.z.grid[Jx][Jy][Jz] = Enew[3];

					}
				}
			}

			for (icomp = 1; icomp <= ncomp; icomp++) {
				appeared = component[icomp].exists(time);
				System.out.print(appeared);
			}
			System.out.println();

			coeff = (c1 * dt - dl) / (c1 * dt + dl);
			E.x.Mur1(Nx, Ny, Nz, coeff);
			E.y.Mur1(Nx, Ny, Nz, coeff);
			E.z.Mur1(Nx, Ny, Nz, coeff);

			// number of planar records
			int Nrec;

			// plot E-field strength on x-plane
			// E-Field Strength = sqrt ( (E.x*E.x) + (E.y*E.y) + (E.z*E.z) )
			if (n % Ntjump == 0) {
				Jx = Nx / 2;
				for (Jy = 1; Jy <= Ny - 2; Jy++) {
					for (Jz = 1; Jz <= Nz - 2; Jz++) {
						Exval = (E.x.grid[Jx][Jy][Jz] + E.x.grid[Jx - 1][Jy][Jz]) / 2;
						Eyval = (E.y.grid[Jx][Jy][Jz] + E.y.grid[Jx][Jy - 1][Jz]) / 2;
						Ezval = (E.z.grid[Jx][Jy][Jz] + E.z.grid[Jx][Jy][Jz - 1]) / 2;
						Val = Math.sqrt(Exval * Exval + Eyval * Eyval + Ezval
								* Ezval);
						outDataStreamX.writeFloat((float) Val);
					}
				}

				Jy = Ny / 2;
				// plot E-field strength on y-plane
				for (Jx = 1; Jx <= Nx - 2; Jx++) {
					for (Jz = 1; Jz <= Nz - 2; Jz++) {
						Exval = (E.x.grid[Jx][Jy][Jz] + E.x.grid[Jx - 1][Jy][Jz]) / 2;
						Eyval = (E.y.grid[Jx][Jy][Jz] + E.y.grid[Jx][Jy - 1][Jz]) / 2;
						Ezval = (E.z.grid[Jx][Jy][Jz] + E.z.grid[Jx][Jy][Jz - 1]) / 2;
						Val = Math.sqrt(Exval * Exval + Eyval * Eyval + Ezval
								* Ezval);
						outDataStreamY.writeFloat((float) Val);
					}
				}

				Jz = Nz / 2;
				// plot E-field strength on z-plane
				for (Jx = 1; Jx <= Nx - 2; Jx++) {
					for (Jy = 1; Jy <= Ny - 2; Jy++) {
						Exval = (E.x.grid[Jx][Jy][Jz] + E.x.grid[Jx - 1][Jy][Jz]) / 2;
						Eyval = (E.y.grid[Jx][Jy][Jz] + E.y.grid[Jx][Jy - 1][Jz]) / 2;
						Ezval = (E.z.grid[Jx][Jy][Jz] + E.z.grid[Jx][Jy][Jz - 1]) / 2;
						Val = Math.sqrt(Exval * Exval + Eyval * Eyval + Ezval
								* Ezval);
						outDataStreamZ.writeFloat((float) Val);
					}
				}

			}
		}

		pds.close();

		// output done, so close the streams
		outDataStreamX.close();
		outDataStreamY.close();
		outDataStreamZ.close();

	}

}

