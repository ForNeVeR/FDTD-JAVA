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

public class Component {
    
        //Attributes
	public int imat;
	public double xl, xu, yl, yu, zl, zu;
	public double time_appear;
	public double time_disappear;
	public double time;

	public Component(int imat, double xl, double xu, double yl, double yu,
			double zl, double zu, double time_appear, double time_disappear) {
		this.imat = imat;
		this.xl = xl;
		this.xu = xu;
		this.yl = yl;
		this.yu = yu;
		this.zl = zl;
		this.zu = zu;
		this.time_appear = time_appear;
		this.time_disappear = time_disappear;
	}

	public boolean exists(double time) {
		if ((time >= this.time_appear) && (time < this.time_disappear)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean in(FDTDVector position) {
		boolean temp;
		temp = false;
		if (position.x >= this.xl) {
			if (position.x < this.xu) {
				if (position.y >= this.yl) {
					if (position.y < this.yu) {
						if (position.z >= this.zl) {
							if (position.z < this.zu)
								temp = true;
						}
					}
				}
			}
		}
		return temp;
	}

	public boolean intime(FDTDVector position, double time) {
		return (in(position) && exists(time));

	}

}
