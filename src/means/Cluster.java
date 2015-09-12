package means;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Cluster extends Ellipse2D.Double{
	
	ArrayList<double[]> list = new ArrayList<double[]>();
	double [] position;
	ArrayList<double[]> positionHistory = new ArrayList<double[]>();
	
	public Cluster(){}
	
	public Cluster(double[] position){
		this.position =  position;
	}
	public double[] getPosition(){
		return position;
	}
	public void addPoints(double[] points){
		list.add(points);
	}

	public void clearCluster(){
		list = new ArrayList<double[]>();
	}
	public boolean changePosition(){
		double x=0;
		double y=0;
		
		double sum1=0;
		double sum2=0;
		for(int i=0;i<list.size();i++){
			sum1 += (list.get(i))[0];
			sum2 += (list.get(i))[1];
		}
		double[] position1 = new double[2];
		position1[0] =  sum1/list.size();
		position1[1] = sum2/list.size();
//		double[] xy = {sum1/list.size(),sum2/list.size()};
		boolean flag = position1[0]-position[0]!=0 || position1[1]-position[1]!=0;
		position = position1;
		return flag;
	}
	
	
	public void printPoints(){

		for(int i=0;i<list.size();i++)
		System.out.print(" "+list.get(i)[0]+","+list.get(i)[1]);
		
		System.out.println();

	}
	
	
	@Override
	public String toString(){
		return ""+position[0]+","+position[1];
	}
	
	
}
