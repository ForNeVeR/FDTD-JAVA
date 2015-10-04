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

public class Material
{
    //Attributes
    public String name;                                                 //Name
    public double conductivity, rel_perm, susceptibility;               //Conductivity, Relative Permittivity, Susceptibility
    
    
    public Material(String name, double conductivity, double rel_perm, double susceptibility)   //Constructor of Objects of Material
    {
		this.name=name;                         //Set this object's variable called name to the value of the argument name
		this.conductivity=conductivity;         //Set this object's variable called conductivity to the value of the argument conductivity
		this.rel_perm=rel_perm;                 //Set this object's variable called rel_perm to the value of the argument rel_perm
		this.susceptibility=susceptibility;     //Set this object's variable called susceptibility to the value of the argument susceptibility
    }
}

