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

public class XZGrid extends Grid implements Transferable{
    /**
     * Constructor for objects of class XZGrid
     */
    public XZGrid(int gridLength, int gridWidth, int gridDepth){
        super(gridLength,gridWidth,gridDepth);      
    }

    /**
     * Method to copy values from grid to xzGrid.
     * 
     * @param aGrid the grid from which values will be copied.
     * @param Nx an Integer giving the size of the grid in 
     * the x direction.
     * @param Ny an Integer giving the size of the grid in 
     * the y direction.
     * @param Nz an Integer giving the size of the grid in 
     * the z direction.
     */
    public void copyFromMainGrid(double [][][] mainGrid, int Nx, int Ny, int Nz){
        for (int i=0; i<=Nx; i++){
            for (int k=0; k<=Nz; k++){
                this.getGrid()[i][k][0]=mainGrid[i][0][k];
                this.getGrid()[i][k][3]=mainGrid[i][Ny][k];
                this.getGrid()[i][k][1]=mainGrid[i][1][k];
                this.getGrid()[i][k][2]=mainGrid[i][Ny-1][k];
            }
        }
    }
    
    /**
     * Method to print the central grid points when copying 
     * values from xzGrid to grid
     * 
     * @param aGrid the grid to which values will be copied.
     * @param Nx an Integer giving the size of the grid in 
     * the x direction.
     * @param Ny an Integer giving the size of the grid in 
     * the y direction.
     * @param Nz an Integer giving the size of the grid in 
     * the z direction.
     */
    public void printCentralGridPoints(double [][][] mainGrid, int Nx, int Ny, int Nz){
        for (int i=1; i<=Nx-1; i++){
            for (int k=1; k<=Nz-1; k++){
                mainGrid[i][0][k]=this.getGrid()[i][k][0];
                mainGrid[i][Ny][k]=this.getGrid()[i][k][3];
                mainGrid[i][1][k]=this.getGrid()[i][k][1];
                mainGrid[i][Ny-1][k]=this.getGrid()[i][k][2];
          }
        }
    }
    
    /**
     * Method to print the edges when copying values from 
     * xzGrid to grid.
     * 
     * @param aGrid the grid from which values will be copied.
     * @param Nx an Integer giving the size of the grid in 
     * the x direction.
     * @param Ny an Integer giving the size of the grid in 
     * the y direction.
     * @param Nz an Integer giving the size of the grid in 
     * the z direction.
     */
    public void printEdges(double[][][] mainGrid, double[][][] yzGrid, int Nx, int Ny, int Nz){
        for (int k=1; k<=Nz-1; k++){
            mainGrid[0][0][k] = (this.getGrid()[0][k][0] + yzGrid[0][k][0])/2.0;
            mainGrid[Nx][0][k] = (this.getGrid()[Nx][k][0] + yzGrid[0][k][3])/2.0;
            mainGrid[0][Ny][k] = (this.getGrid()[0][k][3] + yzGrid[Ny][k][0])/2.0;
            mainGrid[Nx][Ny][k] = (this.getGrid()[Nx][k][3] + yzGrid[Ny][k][3])/2.0;
        }
    }
    
    /**
     * Method to calculate the values to be stored in the 
     * xzGrid and then copy the values to their appropiate 
     * location on the grid.
     * 
     * @param aGrid the grid from which values will be copied.
     * @param Nx an Integer giving the size of the grid in 
     * the x direction.
     * @param Ny an Integer giving the size of the grid in 
     * the y direction.
     * @param Nz an Integer giving the size of the grid in 
     * the z direction.
     * @param coeff a double to calculate the values to be 
     * store on the xzGrid.
     */
    public void murl(double [][][] mainGrid, int Nx, int Ny, int Nz, double coeff){
        for (int Jx=0; Jx<=Nx; Jx++){ 
            for (int Jz=0; Jz<=Nz; Jz++){
                this.getGrid()[Jx][Jz][0]=this.getGrid()[Jx][Jz][1]+   coeff*(mainGrid[Jx][1][Jz]-   this.getGrid()[Jx][Jz][0]);
                this.getGrid()[Jx][Jz][3]=this.getGrid()[Jx][Jz][2]+ coeff*(mainGrid[Jx][Ny-1][Jz]-this.getGrid()[Jx][Jz][3]);
            }
        }
    }
}

