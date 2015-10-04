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

public class Mesh {
        
        //Attributes
        static public double xlength, ylength, zlength;
	static public double dl;
	static public double dt;
	static public double time;
	static public int Nx, Ny, Nz;
	static public int Nt;

        //Construntor of Mesh Objects
	public Mesh(double xlength, double ylength, double zlength, double dl,
			double time) {
		double eps = 0.00000000001;
		FDTDArray A = new FDTDArray();
		Mesh.xlength = xlength;
		Mesh.ylength = ylength;
		Mesh.zlength = zlength;
		Mesh.dl = dl;
		Mesh.Nx = (int) Math.round(xlength / dl);
		Mesh.Ny = (int) Math.round(ylength / dl);
		Mesh.Nz = (int) Math.round(zlength / dl);
		Mesh.dt = dl / PhysicalConstants.c0 / Math.sqrt(3.0) / 1.1;
		Mesh.Nt = (int) Math.round(time / dt);

		if (!A.dimensionsOK(Nx, Ny, Nz)) {
			System.out.println("Mesh is not OK");
		}
		if (Math.abs(Nx * dl - xlength) > eps) {
			System.out
					.println("x-dimension of domain must be compatable with cell size");
		}
		if (Math.abs(Ny * dl - ylength) > eps) {
			System.out
					.println("y-dimension of domain must be compatable with cell size");
		}
		if (Math.abs(Nz * dl - zlength) > eps) {
			System.out
					.println("z-dimension of domain must be compatable with cell size");
		}

	}
}

