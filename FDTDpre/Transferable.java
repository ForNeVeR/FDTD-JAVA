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

/**
 * Interface Transferable enable's the transfer of value 
 * from one grid object to another.
 * 
 * @author Dr Stephen Kirkup, Goodchild Ndou and Irfan Mulla
 * @version (August 2008)
*/

public interface Transferable{
    /**
     * Method to copy values from grid.
     * 
     * @param aGrid the grid from which values will be copied.
     * @param Nx an Integer giving the size of the grid in 
     * the x direction.
     * @param Ny an Integer giving the size of the grid in 
     * the y direction.
     * @param Nz an Integer giving the size of the grid in 
     * the z direction.
     */
    public abstract void copyFromMainGrid(double[][][] aGrid, int Nx, int Ny, int Nz);
    
    /**
     * Method to print the central grid points when copying 
     * values to grid.
     * 
     * @param aGrid the grid to which values will be copied.
     * @param Nx an Integer giving the size of the grid in 
     * the x direction.
     * @param Ny an Integer giving the size of the grid in 
     * the y direction.
     * @param Nz an Integer giving the size of the grid in 
     * the z direction.
     */
    public abstract void printCentralGridPoints(double[][][] mainGrid, int Nx, int Ny, int Nz);
    
    /**
     * Method to print the edges when copying values to grid.
     * 
     * @param aGrid the grid from which values will be copied.
     * @param aPlane the surface to which the values will be 
     * coppied.
     * @param Nx an Integer giving the size of the grid in 
     * the x direction.
     * @param Ny an Integer giving the size of the grid in 
     * the y direction.
     * @param Nz an Integer giving the size of the grid in 
     * the z direction.
     */
    public abstract void printEdges(double[][][] mainGrid, double [][][] aGrid, int Nx, int Ny, int Nz);
    
    /**
     * Method to calculate the values to be stored in the xy, xz or yz Grids and then
     * copy the values to their appropiate location on the grid.
     * 
     * @param aGrid the grid from which values will be copied.
     * @param Nx an Integer giving the size of the grid in 
     * the x direction.
     * @param Ny an Integer giving the size of the grid in 
     * the y direction.
     * @param Nz an Integer giving the size of the grid in 
     * the z direction.
     * @param coeff a double to calculate the values to be 
     * store on the grid.
     */
    public abstract void murl(double [][][] mainGrid, int Nx, int Ny, int Nz, double coeff);
}

