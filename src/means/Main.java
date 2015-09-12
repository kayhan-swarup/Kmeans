package means;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JPanel implements ActionListener{
	
	public static Cluster c1 = new Cluster();
	public static Cluster c2=new Cluster();
	public static ArrayList<double[]> pointList = new ArrayList<double[]>();	
	public ArrayList <Cluster> clusters = new ArrayList<Cluster>();
	public static double[][] points = {
		{1.1,0.5},
		{2.5,3.4},
		{0.4,2.1},
		{0.3,0.3},
		{0.1,1.0},
		{2.7,7.3},
		{8.2,2.8},
		{10.2,7.3},
		{2.1,1.7},
		{3.5,2.4},
		{1.7,8.2}
};
	
	public Main(){
		setBounds(0,0,800,800);
		init();
		repaint();
		
	}
	

	
//	public static double[][]points = {
//		{3,5},
//		{6,8},
//		{2,7},
//		{-2,4},
//		{1,9}
//	};
//	

	public static double getDistance(double[] p,double[] q){
		double distance = 0;
		
		distance = Math.pow((p[0]-q[0]), 2)+Math.pow(p[1]-p[1], 2);
		
		return distance;
	}
	
	Graphics2D graphics;
	@Override
	public void paint(Graphics g){
		super.paint(g);
		graphics = (Graphics2D)g;
		drawPoints(graphics);
		
		
	}
	public void drawPoints(Graphics2D g){
		g.setColor(Color.red);
		g.fill(new Ellipse2D.Double(getX(c1.position[0]),getY(c1.position[1]),30,30));
		g.setColor(Color.red);
		g.fill(new Ellipse2D.Double(getX(c2.position[0]),getY(c2.position[1]),30,30));
		g.setColor(Color.BLACK);
		for(int i=0;i<pointList.size();i++){
			Ellipse2D.Double point = new Ellipse2D.Double(getX(pointList.get(i)[0]),getY(pointList.get(i)[1]),4,4);
			g.fill(point);
			
		}
		
		repaint();
//		System.out.println(getX(c1.position[0])+","+getX(c1.position[1])+", c2: "+getX(c2.position[0])+","+getX(c2.position[1]));
	}

	JTextField xText;
	JTextField yText;
	
	public JPanel getInputLayout(boolean x){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		if(x){
		
		panel.add(new JLabel("X= "));
		xText = new JTextField(4);
		panel.add(xText);
		
		
		}
		else{
		yText = new JTextField(4);
		
		
		panel.add(new JLabel("Y= "));
		panel.add(yText);}
		return panel;
	}
	
	public JFrame frame;
	
	public void init(){
		if(frame==null)
		frame = new JFrame();
		
		frame.setLayout(null);
		frame.setBounds(0,0,900,800);
		JPanel buttonLayout = new JPanel(new FlowLayout());
		buttonLayout.setBounds(800,0,100,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		JButton simulate = new JButton("Simulate");
		JButton clearAll = new JButton("Clear All");
		JButton addButton = new JButton("ADD");
		buttonLayout.add(simulate);buttonLayout.add(clearAll);
		buttonLayout.setBackground(Color.GRAY);
		buttonLayout.add(getInputLayout(true));
		buttonLayout.add(getInputLayout(false));
		buttonLayout.add(addButton);
		
		addButton.setActionCommand("ADD");
		simulate.setActionCommand("SIMULATE");
		simulate.addActionListener(this);
		clearAll.setActionCommand("CLEAR");
		clearAll.addActionListener(this);
		addButton.addActionListener(this);
		
		
		frame.add(buttonLayout);
		frame.show();
	}
	
	
	
	public double getX(double x){
		x = x*20;
		return (double)400+x;
		
	}
	public double getY(double y){
		y = y*20;
		return (double)400+y;
		
	}
	
	public static void simulate(){
		double maxDis = 0;
		int p=-1;
		int q=-1;
		for(int i=0;i<pointList.size();i++){
			for(int j=0;j<pointList.size();j++){
				if(j!=i)
				{
					if(getDistance(pointList.get(i),pointList.get(j))>maxDis){
						maxDis = getDistance(pointList.get(i),pointList.get(j));
						p=i;q=j;
					}
				}
			}
		}
		System.out.println(pointList.get(p)[0]+","+pointList.get(p)[1]+"   "+pointList.get(p)[0]+","+pointList.get(p)[1]);
//		
		c1 = new Cluster(pointList.get(p));
		c2 = new Cluster(pointList.get(q));
		
//		c1 = new Cluster(new double[]{(double)new Random().nextDouble(),new Random().nextDouble()});
//		c2 = new Cluster(new double[]{(double)new Random().nextDouble(),new Random().nextDouble()});
		
		boolean change = true;
		while(change){
			c1.clearCluster();c2.clearCluster();
			for(int i=0;i<pointList.size();i++){
				if(getDistance(c1.position, pointList.get(i))<getDistance(c2.position, pointList.get(i))){
					c1.addPoints(pointList.get(i));
					
				}
				else{
					c2.addPoints(pointList.get(i));
				}
				
			}
			System.out.print("Points belong to C1:");
			c1.printPoints();
			System.out.print("Points belong to C2:");
			c2.printPoints();
			
			change = c1.changePosition() || c2.changePosition();
			System.out.println("C1: "+c1 + "    C2: "+c2);
			
		}
		
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		pointList = new ArrayList<double[]>();
		for(int i=0;i<points.length;i++){
			for(int j=0;j<points[i].length;j++){
				
				pointList.add(points[i]);
			}
		}
		
		
		simulate();
		new Main();

		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("ADD")){
			double[] pos = new double[2];
			if(!xText.getText().equals("")&&!yText.getText().equals("")){
				try{
					pos[0] = Double.parseDouble(xText.getText());
					pos[1] = Double.parseDouble(yText.getText());
				}catch(Exception e){
					e.printStackTrace();
				}
				pointList.add(pos);
				repaint();
			}
		}
		if(arg0.getActionCommand().equals("SIMULATE")){
			if(pointList.size()>0){
			simulate();
			repaint();}
		}
		if(arg0.getActionCommand().equals("CLEAR")){
			pointList=new ArrayList<double[]>();
			
		}
	}

}
