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

public class EField extends VectorField {
	Mesh M;

	public EField() {
		super();
	}

	public FDTDVector getValue(FDTDVector point) {
		FDTDVector Eval = new FDTDVector();
		int i, j, k;
		double dl = M.dl;
		double xrem, yrem, zrem;
		if (point.x < 0.0 || point.x > this.M.xlength) {
			System.out.println("point out of domain");
			// ERROR();
		}
		if (point.y < 0.0 || point.y > this.M.ylength) {
			System.out.println("point out of domain");
			// ERROR();
		}
		if (point.z < 0.0 || point.z > this.M.zlength) {
			System.out.println("point out of domain");
			// ERROR();
		}

		i = (int) (point.x / dl - 1.0);
		j = (int) (point.y / dl - 0.5);
		k = (int) (point.z / dl - 0.5);
		xrem = (point.x - dl) % dl;
		yrem = (point.y - dl / 2) % dl;
		zrem = (point.z - dl / 2) % dl;

		Eval.x = super.x.grid[i][j][k] + xrem
				* (super.x.grid[i + 1][j][k] - super.x.grid[i][j][k]) / M.dl;
		i = (int) (point.x / dl - 0.5);
		j = (int) (point.y / dl - 1.0);
		k = (int) (point.z / dl - 0.5);

		xrem = (point.x - dl / 2) % dl;
		yrem = (point.y - dl) % dl;
		zrem = (point.z - dl / 2) % dl;
		Eval.y = super.y.grid[i][j][k] + yrem
				* (super.y.grid[i][j + 1][k] - super.y.grid[i][j][k]) / M.dl;
		i = (int) (point.x / dl - 0.5);
		j = (int) (point.y / dl - 0.5);
		k = (int) (point.z / dl - 1.0);

		xrem = (point.x - dl / 2) % dl;
		yrem = (point.y - dl / 2) % dl;
		zrem = (point.z - dl) % dl;
		Eval.z = super.z.grid[i][j][k] + zrem
				* (super.z.grid[i][j][k + 1] - super.z.grid[i][j][k]) / M.dl;
		return Eval;
	}

	public double getPD(FDTDVector p, FDTDVector q) {
		FDTDVector dir = q;
		dir.subtract(p);
		double length;
		FDTDVector Evalue;
		length = dir.magnitude();
		int numberofsteps = (int) (length / M.dl) + 4;
		double steplength = length / numberofsteps;
		FDTDVector pd = new FDTDVector();
		FDTDVector point = new FDTDVector();
		point = p;
		Evalue = this.getValue(point);
		pd.add(Evalue);
		FDTDVector step = dir;
		step.multiply(1.0 / ((numberofsteps)));
		for (int i = 1; i <= numberofsteps - 1; i++) {
			point.add(step);
			Evalue = this.getValue(point);
			pd.add(Evalue);
			pd.add(Evalue);
		}
		point.add(step);
		pd.add(this.getValue(point));
		double result = pd.dot(step) / 2.0;
		return result;
	}

	private boolean OK(int i, int j, int k) {
		boolean ok = false;
		if (i >= 0 && i <= M.Nx && j >= 0 && j <= M.Ny && k >= 0 && k <= M.Nz)
			ok = true;
		return ok;
	}

	private void pause() {
		float a, b;
		a = 0.0f;
		b = 1.0f;
		long i;
		for (i = 1; i <= 1000000000; i++) {
			a = a + b;
			a = a - b;
		}
	}
}

