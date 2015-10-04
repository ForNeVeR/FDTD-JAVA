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
 * Class PointCalculator perfoms all the Point calculations.
*/

public class PointCalculator{

    /**
     * Constructor for objects of class PointCalculator
     */
    public PointCalculator(){
        // Initialise instance variables
    }
    
    /**
     * A method to find the mid of two given Points
     * 
     * @param  i   The first Point
     * @param  q   The Second Point
     * @return     Point the middle point of given Points 
     */
    public Point mid(Point i, Point q){
        double x =(i.getXCoordinate() + q.getXCoordinate())/2;
        double y =(i.getYCoordinate() + q.getYCoordinate())/2;
        double z =(i.getZCoordinate() + q.getZCoordinate())/2;
        Point ansPoint = new Point(x, y, z);
        return ansPoint;
    }
    
    /**
     * The method for calculating the sum of two Points
     * 
     * @param  i   The first Point
     * @param  p   The Second Point
     * @return     Point the sum of given Points 
     */
    public Point add(Point i, Point p){
        double x = i.getXCoordinate() + p.getXCoordinate();
        double y = i.getYCoordinate() + p.getYCoordinate();
        double z = i.getZCoordinate() + p.getZCoordinate();
        i.setCoordinates(x, y, z);
        return i;
    }
    
    /**
     * The method for calculating the diffrence between two vactors
     * 
     * @return      a Point equivalent to the deference of two Points
     */
    public Point subtract(Point i, Point p){
        double x = i.getXCoordinate() - p.getXCoordinate();
        double y = i.getYCoordinate() - p.getYCoordinate();
        double z = i.getZCoordinate() - p.getZCoordinate();
        Point ansPoint = new Point(x, y, z);
        return ansPoint;
    }
    
    /**
     * The method for calculating the product of a Points by given factor
     * 
     * @param  i   a Point i to be multiplied by factor k
     * @param  k   a factor for multiplying the Point i by
     * @return      a Point equivalent to the product
     */
    public Point multiply(Point i, double k){
        double x = i.getXCoordinate()*k;
        double y = i.getYCoordinate()*k;
        double z = i.getZCoordinate()*k;
        Point ansPoint = new Point(x, y, z);
        return ansPoint;
    }
    
    /**
     * The method for calculating the product of two vactors
     * 
     * @param  i   a Point
     * @param  j   a Point
     * @return     a Point equivalent to the product of two Points
     */  
    public double dot(Point i, Point p){
        double x = i.getXCoordinate()*p.getXCoordinate();
        double y = i.getYCoordinate()*p.getYCoordinate();
        double z = i.getZCoordinate()*p.getZCoordinate();
        double ansDouble = x + y + z;
        return ansDouble;
    }
}
