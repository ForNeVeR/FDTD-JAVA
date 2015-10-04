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
 * class Point describes the location of a point in space.
 * 
 * @author (Goodchild Ndou MBCS) 
 * @version (Sept 2006)
 */

public class Point{
    // instance variables - replace the example below with your own
    private double xCoordinate;
    private double yCoordinate;
    private double zCoordinate;

    /**
     * Constructor for objects of class Point
     */
    public Point(){
        // initialise instance variables
        xCoordinate = 0;
        yCoordinate = 0;
        zCoordinate = 0;
    }
    
    /**
     * Constructor for objects of class Point whic initialises the 
     * coordinates to the given values x, y, and z
     */
    public Point(double x, double y, double z){
        // initialise instance variables
        xCoordinate = x;
        yCoordinate = y;
        zCoordinate = z;
    }
    
    /**
     * Method to set the value stored in the instance variables
     * xCoordinate, yCoordinate and zCoordinate. 
     */
    public void setCoordinates(double x, double y, double z){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.zCoordinate = z;
    }
    
    /**
     * Method to set the value stored in the instance variable
     * xCoordinate 
     */
    public void setXCoordinate(double x){
        this.xCoordinate = x;
    }
    
    /**
     * Method to set the value stored in the instance variable
     * yCoordinate 
     */
    public void setYCoordinate(double y){
        this.yCoordinate = y;
    }
    
    /**
     * Method to set the value stored in the instance variable
     * xCoordinate 
     */
    public void setZCoordinate(double z){
        this.zCoordinate = z;
    }

    /**
     * Method to get the value stored in the instance variable
     * xCoordinate 
     * 
     * @return     value stored in the instance variable, xCoordinate
     */
    public double getXCoordinate(){
        return xCoordinate;
    }
    
    /**
     * Method to get the value stored in the instance variable
     * yCoordinate 
     * 
     * @return     value stored in the instance variable, yCoordinate
     */
    public double getYCoordinate(){
        return yCoordinate;
    }
    
    /**
     * Method to get the value stored in the instance variable
     * zCoordinate 
     * 
     * @return     value stored in the instance variable, zCoordinate
     */
    public double getZCoordinate(){
        return zCoordinate;
    }
    
    /**
     * Method to get the magnitude of the Vector.
     * 
     * @returns a double which is the value calculated to be 
     * the magnitude of the Vector
     */
    public double magnitude(){
        double xMagnitude = this.getXCoordinate(); 
        double yMagnitude = this.getYCoordinate();
        double zMagnitude = this.getZCoordinate();
        return (Math.sqrt(xMagnitude * xMagnitude + yMagnitude * yMagnitude + zMagnitude * zMagnitude));
    }
}

