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

public class XYGrid extends Grid implements Transferable{

    /**
     * Constructor for objects of class XYGrid
     */
    public XYGrid(int gridLength, int gridWidth, int gridDepth){
        super(gridLength,gridWidth,gridDepth);      
    }

    /**
     * Method to copy values from mainGrid to xyGrid.
     * 
     * @param aGrid the mainGrid from which values will be copied.
     * @param Nx an Integer giving the size of the mainGrid in 
     * the x direction.
     * @param Ny an Integer giving the size of the mainGrid in 
     * the y direction.
     * @param Nz an Integer giving the size of the mainGrid in 
     * the z direction.
     */
    public void copyFromMainGrid(double [][][] mainGrid, int Nx, int Ny, int Nz){
        for (int i=0; i<=Nx; i++){
            for (int j=0; j<=Ny; j++){
                this.getGrid()[i][j][0] = mainGrid[i][j][0];
                this.getGrid()[i][j][3] = mainGrid[i][j][Nz];
                this.getGrid()[i][j][1] = mainGrid[i][j][1];
                this.getGrid()[i][j][2] = mainGrid[i][j][Nz-1];
             }
        }
    }
    
    /**
     * Method to print the central mainGrid points when copying 
     * values from xyGrid to mainGrid
     * 
     * @param aGrid the mainGrid to which values will be copied.
     * @param Nx an Integer giving the size of the mainGrid in 
     * the x direction.
     * @param Ny an Integer giving the size of the mainGrid in 
     * the y direction.
     * @param Nz an Integer giving the size of the mainGrid in 
     * the z direction.
     */
    public void printCentralGridPoints(double [][][] mainGrid, int Nx, int Ny, int Nz){
        for (int i=1; i<=Nx-1; i++){
            for (int j=1; j<=Ny-1; j++){
                mainGrid[i][j][0]=this.getGrid()[i][j][0];
                mainGrid[i][j][Nz]=this.getGrid()[i][j][3];
                mainGrid[i][j][1]=this.getGrid()[i][j][1];
                mainGrid[i][j][Nz-1]=this.getGrid()[i][j][2];
            }
        }
    }
    
    /**
     * Method to print the edges when copying values from 
     * xyGrid to mainGrid.
     * 
     * @param aGrid the mainGrid from which values will be copied.
     * @param Nx an Integer giving the size of the mainGrid in 
     * the x direction.
     * @param Ny an Integer giving the size of the mainGrid in 
     * the y direction.
     * @param Nz an Integer giving the size of the mainGrid in 
     * the z direction.
     */
    public void printEdges(double[][][] mainGrid, double[][][] xzGrid, int Nx, int Ny, int Nz){
        for (int i=1; i<=Nx-1; i++){
            mainGrid[i][0][0]=(this.getGrid()[i][0][0] + xzGrid[i][0][0])/2.0;
			mainGrid[i][0][Nz]=(this.getGrid()[i][0][3] + xzGrid[i][Nz][0])/2.0;
			mainGrid[i][Ny][0]=(this.getGrid()[i][Ny][0] + xzGrid[i][0][3])/2.0;
			mainGrid[i][Ny][Nz]=(this.getGrid()[i][Ny][3] + xzGrid[i][Nz][3])/2.0;
          }
    }
    
    /**
     * Method to calculate the values to be stored in the 
     * xyGrid and then  copy the values to their appropiate 
     * location on the mainGrid.
     * 
     * @param aGrid the mainGrid from which values will be copied.
     * @param Nx an Integer giving the size of the mainGrid in 
     * the x direction.
     * @param Ny an Integer giving the size of the mainGrid in 
     * the y direction.
     * @param Nz an Integer giving the size of the mainGrid in 
     * the z direction.
     * @param coeff a double to calculate the values to be store 
     * on the mainGrid.
     */
    public void murl(double [][][] mainGrid, int Nx, int Ny, int Nz, double coeff){
        for (int Jx=0; Jx<=Nx; Jx++){
            for (int Jy=0; Jy<=Ny; Jy++){
                this.getGrid()[Jx][Jy][0] = this.getGrid()[Jx][Jy][1] + coeff*(mainGrid[Jx][Jy][1] - this.getGrid()[Jx][Jy][0]);
                this.getGrid()[Jx][Jy][3] = this.getGrid()[Jx][Jy][2] + coeff*(mainGrid[Jx][Jy][Nz-1] - this.getGrid()[Jx][Jy][3]);
          }
        }
    }
}

