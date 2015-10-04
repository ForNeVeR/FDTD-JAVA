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

public class FDTDVector {
    
        //Attributes
	public double x;
	public double y;
	public double z;

	public FDTDVector() {
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
	}

	public FDTDVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public FDTDVector mid(FDTDVector q) {
		FDTDVector middle = new FDTDVector();
		middle.x = (this.x + q.x) / 2;
		middle.y = (this.y + q.y) / 2;
		middle.z = (this.z + q.z) / 2;
		return middle;
	}

	public double magnitude() {
		return (Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z));
	}

	public void add(FDTDVector p) {
		this.x = this.x + p.x;
		this.y = this.y + p.y;
		this.z = this.z + p.z;
	}

	public void subtract(FDTDVector p) {
		this.x = this.x - p.x;
		this.y = this.y - p.y;
		this.z = this.z - p.z;
	}

	public void multiply(double k) {
		this.x = this.x * k;
		this.y = this.y * k;
		this.z = this.z * k;
	}

	public double dot(FDTDVector p) {
		double temp;
		temp = this.x * p.x + this.y * p.y + this.z * p.z;
		return temp;
	}

}

