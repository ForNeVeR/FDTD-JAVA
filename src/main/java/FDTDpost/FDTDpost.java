package FDTDpost;

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

  Copyright 2008- Stephen Kirkup et al
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
  The main author can be contacted on stephen@kirkup.info
  FDTDpost is the post-processor. It does not require any other classes to run it.
*/

import java.io.*;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.*;
import java.awt.event.*;
class FDTDpost extends Frame
{
  //Attributes  
  final int MaxNi=1000;
  final int MaxNj=1000;
  int i, j, n, Nt, Ni, Nj;
  float MaxE, MinE;
  double dxPlot, dyPlot, dt;
  int idxPlot,idyPlot;
  char c;
  float temp;

  float[][] E = new float[MaxNi][MaxNj];


  public static void main(String arg[])
  {

	  new FDTDpost();
  }

  FDTDpost()
  { super("FDTDpost");

        System.out.println("Plane x = Plane y-z");                      
	System.out.println("Plane y = Plane x-z");
	System.out.println("Plane z = Plane x-y");
	System.out.println("Which plane (x, y, or z) ?");

    try
	{
	  c=(char)(System.in.read());
	  System.in.close();
	}
	catch (IOException e)

	{
	  System.out.println("Wrong input.");
	}

	addWindowListener(
		new WindowAdapter() { public void windowClosing(WindowEvent e)
		                                    { System.exit(0);  }
	                                      } );
	setSize(1000,800);
    show();
  }

  public void paint(Graphics g)
  { try
	{ //setup file and stream

	      File inFile = new File(c + ".out");
	      FileInputStream inFileStream = new FileInputStream(inFile);
	      DataInputStream inDataStream = new DataInputStream(inFileStream);

	      Nt=inDataStream.readInt();
	      Ni=inDataStream.readInt();
	      Nj=inDataStream.readInt();
          temp = inDataStream.readFloat();
	  	  MaxE=temp;
	  	  MinE=temp;

		System.out.println("Nt = "+Nt+" Ni = "+Ni+" Nj = "+Nj);
       for (n=1; n<=Nt/2; n++)
       {
	     for ( i = 1; i <= (Ni-2); i++ )
	  	{ for ( j = 1; j <= (Nj-2); j++ )
	      {
		    temp = inDataStream.readFloat();
		    if (i>=10&&i<=Ni-10&&j>=10&&j<=Nj-10)
		    {
	  	    MaxE=Math.max(temp,MaxE);
	  	    MinE=Math.min(temp,MinE);
		}
		}
	   }
	}

	inDataStream.close();
	}
	catch(IOException e)
	{
	System.err.println(e);
	}


	 try
	{

      File inFile = new File(c + ".out");
      FileInputStream inFileStream = new FileInputStream(inFile);
      DataInputStream inDataStream = new DataInputStream(inFileStream);

      Nt=inDataStream.readInt();
      if (Nt<=0) System.out.println("Nt must be positive");

      Ni=inDataStream.readInt();
      if (Ni>MaxNi)
        {
         System.out.println("Ni input is greater then MaxNi; increase MaxNi to ");
         System.out.print(Ni);
         System.out.print(" and rerun");
	    }

      Nj=inDataStream.readInt();
      if (Nj>MaxNj)
        {
         System.out.print("Nj input is greater then MaxNj; increase MaxNj to ");
         System.out.print(Nj);
         System.out.print(" and rerun");
	    }


	dxPlot = 800.0/(double)(Ni-2);
	dyPlot = 600.0/(double)(Nj-2);
	dxPlot = Math.min(dyPlot,dxPlot);
	dyPlot = dxPlot;
	idxPlot=(int)(dxPlot+1.0);
	idyPlot=(int)(dyPlot+1.0);

	// draw frame
	g.setColor( new Color ( 0, 0, 0 ) );
	for ( i = 0; i <= 5; i++ )
	{ g.drawRect( 44+i,
	  	          99+i,
	  	          12+(int)(dxPlot*(double)(Ni-2))-i*2,
	  	          12+(int)(dyPlot*(double)(Nj-2))-i*2 );
	}

	for ( n=1; n<=Nt; n++ )
	{
	  g.setColor( new Color ( 255, 255, 255 ) );
	  g.drawString( "k = " + (n-1), 50, (int)(dyPlot*(float)Nj)+120 );
	  g.setColor( new Color ( 0, 0, 0 ) );
	  g.drawString( "k = " + n, 50, (int)(dyPlot*(float)Nj)+120 );

	   for ( i = 1; i <= (Ni-2); i++ )
	  	{ for ( j = 1; j <= (Nj-2); j++ )
	  	  { E[i][j] = inDataStream.readFloat();
	  	  }
	  	}



    float temp1=511.0f/MinE;
    float temp2=511.0f/MaxE;
    double temp;

	for ( j = 1; j <= (Nj-2); j++)
	  { for ( i = 1; i <= (Ni-2); i++)
	  	{ if (E[i][j] < 0)
	  	  {
	  	    if ( E[i][j] <= (MinE/2) )
	  	    { temp=(MinE-E[i][j])*temp1;
	  	      temp=Math.min(temp,255.0);
	  	      temp=Math.max(temp,0.0);
	  	      g.setColor( new Color ( 0, (int)temp, 255 ) );
	  	    }

	  	    else
	  	    {   temp=((MinE/2)-E[i][j])*temp1;
	  	        temp=Math.min(temp,255.0);
	  	        temp=Math.max(temp,0.0);
				g.setColor( new Color ( (int)temp, 255, 255 ) );
	  	    }
	  	  }

	  	  else
	  	  {
	  	    if (E[i][j] < (MaxE/2))
	  	    {   temp=((MaxE/2)-E[i][j])*temp2;
	  	        temp=Math.min(temp,255.0);
	  	        temp=Math.max(temp,0.0);
				g.setColor( new Color ( 255, (int)temp, 255 ) );
	  	    }

	  	    else
	  	    {
	  	        temp=(MaxE-E[i][j])*temp2;
	  	        temp=Math.min(temp,255.0);
	  	        temp=Math.max(temp,0.0);
	  	        g.setColor( new Color ( 255, 0, (int)temp ));
	  	    }
	  	  }



			g.fillRect( 50+(int)((double)(i-1)*dxPlot),
	  	              105+(int)((double)((Nj-2)-(j))*dyPlot),
	  	              idxPlot,
	  	              idyPlot);
	  	}
	  }
    }

      inDataStream.close();
    }
    catch(IOException e)
    { System.err.println(e);
    }


  }
}
