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

/* SPECIAL INSTRUCTION: The values of MaxNx, MaxNy, MaxNz set the maximum number of
 Yee cells in the x, y and z directions. Please note that the "size" of these array
 must be smal enough to fit in your computer memory (RAM) but they must also be big enough
 to for the problem as defined in the input file.
 */

public class FDTDArray {
        
        //Attributes
	public final static int MaxNx = 150;
	public final static int MaxNy = 30;
	public final static int MaxNz = 80;

	double[][][] grid = new double[MaxNx + 1][MaxNy + 1][MaxNz + 1];

	double[][] surfxy_0 = new double[MaxNx + 1][MaxNy + 1];
	double[][] surfxy_N = new double[MaxNx + 1][MaxNy + 1];
	double[][] surfxz_0 = new double[MaxNx + 1][MaxNz + 1];
	double[][] surfxz_N = new double[MaxNx + 1][MaxNz + 1];
	double[][] surfyz_0 = new double[MaxNy + 1][MaxNz + 1];
	double[][] surfyz_N = new double[MaxNy + 1][MaxNz + 1];
	double[][] surfxy_1 = new double[MaxNx + 1][MaxNy + 1];
	double[][] surfxy_Nm1 = new double[MaxNx + 1][MaxNy + 1];
	double[][] surfxz_1 = new double[MaxNx + 1][MaxNz + 1];
	double[][] surfxz_Nm1 = new double[MaxNx + 1][MaxNz + 1];
	double[][] surfyz_1 = new double[MaxNy + 1][MaxNz + 1];
	double[][] surfyz_Nm1 = new double[MaxNy + 1][MaxNz + 1];

	public FDTDArray() {

	}

	/**
	 * Makes sure that every value is reset to 0.0 in the double[][][]
	 */
	public void zero() {
		this.grid = new double[MaxNx + 1][MaxNy + 1][MaxNz + 1];
	}

	/**
	 * @param Nx
	 * @param Ny
	 * @param Nz
	 * @return
	 */
	public boolean dimensionsOK(int Nx, int Ny, int Nz) {
		boolean OK;
		OK = true;
		if (Nx >= MaxNx - 1) {
			System.out.println("Increase MaxNx to at least " + (Nx + 1)
					+ " in file Array.java");
			OK = false;
		}
		if (Ny >= MaxNy - 1) {
			System.out.println("Increase MaxNy to at least " + (Ny + 1)
					+ " in file Array.java");
			OK = false;
		}
		if (Nz >= MaxNz - 1) {
			System.out.println("Increase MaxNz to at least " + (Nz + 1)
					+ " in file Array.java");
			OK = false;
		}
		if (Nx < 10) {
			System.out.println("Warning: Nx is small: Nx = " + Nx);
			OK = false;
		}
		if (Ny < 10) {
			System.out.println("Warning: Ny is small: Ny = " + Ny);
			OK = false;
		}
		if (Nx < 10) {
			System.out.println("Warning: Nz is small: Nz = " + Nz);
			OK = false;
		}
		return OK;
	}

	public void copyGridtoSurf(int Nx, int Ny, int Nz) {
		int i, j, k;

		for (i = 0; i <= Nx; i++) {
			for (j = 0; j <= Ny; j++) {
				this.surfxy_0[i][j] = this.grid[i][j][0];
				this.surfxy_N[i][j] = this.grid[i][j][Nz];
				this.surfxy_1[i][j] = this.grid[i][j][1];
				this.surfxy_Nm1[i][j] = this.grid[i][j][Nz - 1];
			}
		}
		for (i = 0; i <= Nx; i++) {
			for (k = 0; k <= Nz; k++) {
				this.surfxz_0[i][k] = this.grid[i][0][k];
				this.surfxz_N[i][k] = this.grid[i][Ny][k];
				this.surfxz_1[i][k] = this.grid[i][1][k];
				this.surfxz_Nm1[i][k] = this.grid[i][Ny - 1][k];
			}
		}
		for (j = 0; j <= Ny; j++) {
			for (k = 0; k <= Nz; k++) {
				this.surfyz_0[j][k] = this.grid[0][j][k];
				this.surfyz_N[j][k] = this.grid[Nx][j][k];
				this.surfyz_1[j][k] = this.grid[1][j][k];
				this.surfyz_Nm1[j][k] = this.grid[Nx - 1][j][k];
			}
		}

	}

	public void copySurftoGrid(int Nx, int Ny, int Nz) {
		int i, j, k;

		// Central grid points
		// System.out.println("Central grid points");
		for (i = 1; i <= Nx - 1; i++) {
			for (j = 1; j <= Ny - 1; j++) {
				this.grid[i][j][0] = this.surfxy_0[i][j];
				this.grid[i][j][Nz] = this.surfxy_N[i][j];
				this.grid[i][j][1] = this.surfxy_1[i][j];
				this.grid[i][j][Nz - 1] = this.surfxy_Nm1[i][j];
			}
		}
		for (i = 1; i <= Nx - 1; i++) {
			for (k = 1; k <= Nz - 1; k++) {
				this.grid[i][0][k] = this.surfxz_0[i][k];
				this.grid[i][Ny][k] = this.surfxz_N[i][k];
				this.grid[i][1][k] = this.surfxz_1[i][k];
				this.grid[i][Ny - 1][k] = this.surfxz_Nm1[i][k];
			}
		}
		for (j = 1; j <= Ny - 1; j++) {
			for (k = 1; k <= Nz - 1; k++) {
				this.grid[0][j][k] = this.surfyz_0[j][k];
				this.grid[Nx][j][k] = this.surfyz_N[j][k];
				this.grid[1][j][k] = this.surfyz_1[j][k];
				this.grid[Nx - 1][j][k] = this.surfyz_Nm1[j][k];
			}
		}

		// Edges
		// System.out.println("Edges");

		for (i = 1; i <= Nx - 1; i++) {
			this.grid[i][0][0] = (this.surfxy_0[i][0] + this.surfxz_0[i][0]) / 2.0;
			this.grid[i][0][Nz] = (this.surfxy_N[i][0] + this.surfxz_0[i][Nz]) / 2.0;
			this.grid[i][Ny][0] = (this.surfxy_0[i][Ny] + this.surfxz_N[i][0]) / 2.0;
			this.grid[i][Ny][Nz] = (this.surfxy_N[i][Ny] + this.surfxz_N[i][Nz]) / 2.0;
		}
		for (j = 1; j <= Ny - 1; j++) {
			this.grid[0][j][0] = (this.surfxy_0[0][j] + this.surfyz_0[j][0]) / 2.0;
			this.grid[0][j][Nz] = (this.surfxy_N[0][j] + this.surfyz_0[j][Nz]) / 2.0;
			this.grid[Nx][j][0] = (this.surfxy_0[Nx][j] + this.surfyz_N[j][0]) / 2.0;
			this.grid[Nx][j][Nz] = (this.surfxy_N[Nx][j] + this.surfyz_N[j][Nz]) / 2.0;
		}
		for (k = 1; k <= Nz - 1; k++) {
			this.grid[0][0][k] = (this.surfxz_0[0][k] + this.surfyz_0[0][k]) / 2.0;
			this.grid[Nx][0][k] = (this.surfxz_0[Nx][k] + this.surfyz_N[0][k]) / 2.0;
			this.grid[0][Ny][k] = (this.surfxz_N[0][k] + this.surfyz_0[Ny][k]) / 2.0;
			this.grid[Nx][Ny][k] = (this.surfxz_N[Nx][k] + this.surfyz_N[Ny][k]) / 2.0;
		}

		// Corners
		// System.out.println("Corners");

		this.grid[0][0][0] = (this.surfxy_0[0][0] + this.surfxz_0[0][0] + this.surfyz_0[0][0]) / 3.0;
		this.grid[0][0][Nz] = (this.surfxy_N[0][0] + this.surfxz_0[0][Nz] + this.surfyz_0[0][Nz]) / 3.0;
		this.grid[0][Ny][0] = (this.surfxy_0[0][Ny] + this.surfxz_N[0][0] + this.surfyz_0[Ny][0]) / 3.0;
		this.grid[0][Ny][Nz] = (this.surfxy_N[0][Ny] + this.surfxz_N[0][Nz] + this.surfyz_0[Ny][Nz]) / 3.0;
		this.grid[Nx][0][0] = (this.surfxy_0[Nx][0] + this.surfxz_0[Nx][0] + this.surfyz_N[0][0]) / 3.0;
		this.grid[Nx][0][Nz] = (this.surfxy_N[Nx][0] + this.surfxz_0[Nx][Nz] + this.surfyz_N[0][Nz]) / 3.0;
		this.grid[Nx][Ny][0] = (this.surfxy_0[Nx][Ny] + this.surfxz_N[Nx][0] + this.surfyz_N[Ny][0]) / 3.0;
		this.grid[Nx][Ny][Nz] = (this.surfxy_N[Nx][Ny] + this.surfxz_N[Nx][Nz] + this.surfyz_N[Ny][Nz]) / 3.0;

	}

	public void Mur1(int Nx, int Ny, int Nz, double coeff) {
		int Jx, Jy, Jz;
		// this.CopyGridtoSurf(Nx,Ny,Nz);
		for (Jx = 0; Jx <= Nx; Jx++) {
			for (Jy = 0; Jy <= Ny; Jy++) {
				this.surfxy_0[Jx][Jy] = this.surfxy_1[Jx][Jy] + coeff
						* (this.grid[Jx][Jy][1] - this.surfxy_0[Jx][Jy]);
				this.surfxy_N[Jx][Jy] = this.surfxy_Nm1[Jx][Jy] + coeff
						* (this.grid[Jx][Jy][Nz - 1] - this.surfxy_N[Jx][Jy]);

			}
		}
		for (Jy = 0; Jy <= Ny; Jy++) {
			for (Jz = 0; Jz <= Nz; Jz++) {
				this.surfyz_0[Jy][Jz] = this.surfyz_1[Jy][Jz] + coeff
						* (this.grid[1][Jy][Jz] - this.surfyz_0[Jy][Jz]);
				this.surfyz_N[Jy][Jz] = this.surfyz_Nm1[Jy][Jz] + coeff
						* (this.grid[Nx - 1][Jy][Jz] - this.surfyz_N[Jy][Jz]);
			}
		}
		for (Jx = 0; Jx <= Nx; Jx++) {
			for (Jz = 0; Jz <= Nz; Jz++) {
				this.surfxz_0[Jx][Jz] = this.surfxz_1[Jx][Jz] + coeff
						* (this.grid[Jx][1][Jz] - this.surfxz_0[Jx][Jz]);
				this.surfxz_N[Jx][Jz] = this.surfxz_Nm1[Jx][Jz] + coeff
						* (this.grid[Jx][Ny - 1][Jz] - this.surfxz_N[Jx][Jz]);
			}
		}
		// System.out.println(Nx+" "+Ny+" "+Nz);

		this.copySurftoGrid(Nx, Ny, Nz);
	}

	public double[][][] getGrid() {
		return grid;
	}
}
