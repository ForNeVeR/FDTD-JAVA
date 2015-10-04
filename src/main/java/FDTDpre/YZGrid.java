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

public class YZGrid extends Grid implements Transferable{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class YZGrid
     */
    public YZGrid(int gridLength, int gridWidth, int gridDepth){
        super(gridLength,gridWidth,gridDepth);      
    }

    /**
     * Method to copy values from grid to yzGrid.
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
        for (int j=0; j<=Ny; j++){
            for (int k=0; k<=Nz; k++){
                this.getGrid()[j][k][0] = mainGrid[0][j][k];
                this.getGrid()[j][k][3] = mainGrid[Nx][j][k];
                this.getGrid()[j][k][1] = mainGrid[1][j][k];
                this.getGrid()[j][k][3] = mainGrid[Nx-1][j][k];
            }
        }
    }
    
    /**
     * Method to print the central grid points when copying 
     * values from yzGrid to grid
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
        for (int j=1; j<=Ny-1; j++){
            for (int k=1; k<=Nz-1; k++){
                mainGrid[0][j][k]=this.getGrid()[j][k][0];
                mainGrid[Nx][j][k]=this.getGrid()[j][k][3];
                mainGrid[1][j][k]=this.getGrid()[j][k][1];
                mainGrid[Nx-1][j][k]=this.getGrid()[j][k][2];
            }
        }
    }
    
    /**
     * Method to print the edges when copying values from 
     * yzGrid to grid.
     * 
     * @param aGrid the grid from which values will be copied.
     * @param Nx an Integer giving the size of the grid in 
     * the x direction.
     * @param Ny an Integer giving the size of the grid in 
     * the y direction.
     * @param Nz an Integer giving the size of the grid in 
     * the z direction.
     */
    public void printEdges(double[][][] mainGrid, double[][][] xyGrid, int Nx, int Ny, int Nz){
        for (int j=1; j<=Ny-1; j++){
            mainGrid[0][j][0]=(xyGrid[0][j][0]+this.getGrid()[j][0][0])/2.0;
            mainGrid[0][j][Nz]=(xyGrid[0][j][3]+this.getGrid()[j][Nz][0])/2.0;
            mainGrid[Nx][j][0]=(xyGrid[Nx][j][0]+this.getGrid()[j][0][3])/2.0;
            mainGrid[Nx][j][Nz]=(xyGrid[Nx][j][3]+this.getGrid()[j][Nz][3])/2.0;
        }
    }
    
    /**
     * Method to calculate the values to be stored in the yzGrid
     * and then copy the values to their appropiate location on 
     * the grid.
     * 
     * @param aGrid the grid from which values will be copied.
     * @param Nx an Integer giving the size of the grid in the 
     * x direction.
     * @param Ny an Integer giving the size of the grid in the 
     * y direction.
     * @param Nz an Integer giving the size of the grid in the 
     * z direction.
     * @param coeff a double to calculate the values to be store 
     * on the grid.
     */
    public void murl(double [][][] mainGrid, int Nx, int Ny, int Nz, double coeff){
        for (int Jy=0; Jy<=Ny; Jy++){ 
            for (int Jz=0; Jz<=Nz; Jz++){
                this.getGrid()[Jy][Jz][0]=this.getGrid()[Jy][Jz][1]+   coeff*(mainGrid[1][Jy][Jz]-   this.getGrid()[Jy][Jz][0]);
                this.getGrid()[Jy][Jz][3]=this.getGrid()[Jy][Jz][2]+ coeff*(mainGrid[Nx-1][Jy][Jz]-this.getGrid()[Jy][Jz][3]);
            }
        }
    }
}


