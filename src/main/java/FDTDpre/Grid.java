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

public class Grid    {
    
    //Attributes
    private int gridLength;
    private int gridWidth;
    private int gridDepth;
    private double[][][] grid;
    
    /**
     * Constructor of Grid objects. Creates the grid of size
     * [gridLength][gridWidth][gridDepth] and initialises the 
     * all the values to zero.
     * 
     * @param gridLength the length of the grid.
     * @param gridWidth the width of the grid.
     * @param gridDepth the depth of the grid.
     */
    public Grid(int gridLength, int gridWidth, int gridDepth){
        // initialise instance variables
        this.setGridLength(gridLength);
        this.setGridWidth(gridWidth);
        this.setGridDepth(gridDepth);
        this.setGrid(new double[gridLength][gridWidth][gridDepth]);
    }
    
    /**
     * Method to set the grid length
     * 
     * @param gridLength the length of the grid.
     */
    public void setGridLength(int gridLength){
        this.gridLength = gridLength;
    }
    
    /**
     * Method to set the grid width
     * 
     * @param gridWidth the width of the grid.
     */
    public void setGridWidth(int gridWidth){
        this.gridWidth = gridWidth;
    }
    
    /**
     * Method to set the grid depth
     * 
     * @param gridDepth the depth of the grid.
     */
    public void setGridDepth(int gridDepth){
        this.gridDepth = gridDepth;
    }
    
    /**
     * Method to set the grid.
     * 
     * @param aGrid a three dimesional double to be set as the grid.
     */
    public void setGrid(double[][][] aGrid){
        this.grid = aGrid;
    }
    
    /**
     * Method to get the grid length
     * 
     * @return gridLength the length of the grid.
     */
    public int getGridLength(){
        return gridLength;
    }
    
    /**
     * Method to get the grid Width
     * 
     * @return gridWidth the width of the grid.
     */
    public int getGridWidth(){
        return gridWidth;
    }
    
    /**
     * Method to get the grid depth
     * 
     * @return gridDepth the depth of the grid.
     */
    public int getGridDepth(){
        return gridDepth;
    }
    
    /**
     * Method to get the grid
     * 
     * @return aGrid a three dimesional double stored as the grid.
     */
    public double[][][] getGrid(){
        return grid;
    }
    
    /**
     * Method to set all values to zero on the grid
     * 
     */
    public void zeroGrid(){
        for (int i = 0; i < gridLength; i++){
            for (int j = 0; j < gridWidth; j++){
                for (int k = 0; k < gridDepth; k++){
                    this.grid[i][j][k] = 0.0;
                }
            }
        }
    }
}


