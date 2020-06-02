import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JFrame;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.InfoPanelPosition;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Main{
	
	public static Vector<City> cities = new Vector<City>();
    public static double DistanceTo [] [] = new double [48][48] ;
	
	public static void main(String[] args) throws Exception{
        File file = new File("C:\\Users\\z00442AS\\Desktop\\SCHOOL\\FormalLanguages\\att48_xy.txt");
        Scanner sc = new Scanner(file);
        while(sc.hasNext()){
            int x = sc.nextInt();
            int y = sc.nextInt();
            cities.add(new City(x,y));
        }
        sc.close();
    
        Route absoluteSol = new Route();
        File sol = new File("C:\\Users\\z00442AS\\Desktop\\SCHOOL\\FormalLanguages\\att48_s.txt");
        Scanner scc = new Scanner(sol);
        while(scc.hasNext()){
            int nextCityIndex = scc.nextInt();
            absoluteSol.addToTour(cities.elementAt(nextCityIndex-1));
        }
        scc.close();
        absoluteSol.setxyData();
        System.out.printf("Shortest route length is = %f \n", absoluteSol.length());
        /* 
         * Print the vector of cities
        Enumeration<City> en = cities.elements();
        System.out.println("\nElements are:");
        while(en.hasMoreElements()) {
        	City currCity = en.nextElement();
           System.out.println(currCity.getX() + "," + currCity.getY());
		}*/
        
        // It is symmetric matrix so no need to calculate the lower half
        int count=0;
        for (int i = 0; i < DistanceTo.length; i++) {
        	for (int j = 0; j < DistanceTo[i].length; j++) {
        		DistanceTo[i][j] = cities.elementAt(i).distanceTo(cities.elementAt(j));
        		count++;
        	}
        }
         /*Print Distance to Array 
          for (int i = 0; i < DistanceTo.length; i++) {
        	for (int j = 0; j < DistanceTo[i].length; j++) {
        		//System.out.printf("%f-",DistanceTo[i][j] );
        	}
        	System.out.println("" ); 
        }*/
       System.out.println("Nearest Neighbour Started"); 
       long nstartTime = System.nanoTime();
       NearestNeighbour myNearest = new NearestNeighbour(DistanceTo, cities);  
       long nendTime   = System.nanoTime();
       long ntotalTime = nendTime - nstartTime;
       Route ShortestForNearest = myNearest.Result();
       ShortestForNearest.setxyData();
       System.out.printf("Nearest Neighbour finished the route length is = %f \n", ShortestForNearest.length()); 
       
        Route Greedy = new Route();
        System.out.println("Greedy Started"); 
        long gstartTime = System.nanoTime();
        Greedy z = new Greedy(DistanceTo);
        ArrayList<Integer> GreedyRoute = z.Result();
        long gendTime   = System.nanoTime();
        long gtotalTime = gendTime - gstartTime;
        for (int i = 0; i < GreedyRoute.size(); i++) { 		      
            Greedy.addToTour(cities.elementAt(GreedyRoute.get(i)));
        }  
        Greedy.setxyData();
        System.out.printf("Greedy finished the route length is = %f \n", Greedy.length());
       
      
        System.out.println("Divide&Conquer Started"); 
        long dstartTime = System.nanoTime();
        DivideAndConquer myAlg = new DivideAndConquer(cities);  
        long dendTime   = System.nanoTime();
        long dtotalTime = dendTime - dstartTime;
        Route ShortestForDC = myAlg.Result();
        ShortestForDC.setxyData();
        System.out.printf("Divide&Conquer finished the route length is = %f \n", ShortestForDC.length()); 
        
        
        /*
         * The first attempt to draw
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DrawRoute Draw = new DrawRoute();
        Draw.setPreferredSize(new Dimension(1080,670));
        testFrame.getContentPane().add(Draw, BorderLayout.CENTER);
        ShortestForDC.Draw(Draw);
        //Draw.addLine(0, 0, 900, 900);
        testFrame.pack();
        testFrame.setVisible(true);
         *
         */
       
        
        //cities.forEach(city -> xData[cities.indexOf(city)]= city.getX() );
        //cities.forEach(city -> yData[cities.indexOf(city)]= city.getY() );  
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(500).title("Travelling Salesman Problem").xAxisTitle("X").yAxisTitle("Y").build();

        XYChart chart1 = new XYChartBuilder().width(800).height(500).title("Greedy").xAxisTitle("X").yAxisTitle("Y").build();
        XYChart chart2 = new XYChartBuilder().width(800).height(500).title("Nearest Neighbour").xAxisTitle("X").yAxisTitle("Y").build();
        XYChart chart3 = new XYChartBuilder().width(800).height(500).title("Divide&Conquer").xAxisTitle("X").yAxisTitle("Y").build();
        XYChart chart4 = new XYChartBuilder().width(800).height(500).title("ShortestTour").xAxisTitle("X").yAxisTitle("Y").build();
        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(8);
        
        chart.addSeries("Divide&Conquer", ShortestForDC.getxData() , ShortestForDC.getyData());
        chart.addSeries("Greedy", Greedy.getxData() , Greedy.getyData());
        chart.addSeries("Nearest Neighbour", ShortestForNearest.getxData() , ShortestForNearest.getyData());
        chart.addSeries("Absolute Solution", absoluteSol.getxData() , absoluteSol.getyData());
        chart.addInfoContent("Shortest tour length = " + absoluteSol.length());
        
        chart3.addSeries("Divide&Conquer", ShortestForDC.getxData() , ShortestForDC.getyData());
        chart1.addSeries("Greedy", Greedy.getxData() , Greedy.getyData());
        chart2.addSeries("Nearest Neighbour", ShortestForNearest.getxData() , ShortestForNearest.getyData());
        chart4.addSeries("Absolute Solution", absoluteSol.getxData() , absoluteSol.getyData());
        chart4.addInfoContent("Shortest tour length = " + absoluteSol.length());
        //"Absolute Solution" "Divide&Conquer"
        chart.addInfoContent("Divide&conquer algorithm shortest tour length = " + ShortestForDC.length());
        chart.addInfoContent("Divide&conquer execution time = " + dtotalTime/1000 + " miliseconds.");
        chart.addInfoContent("Greedy algorithm shortest tour length = " + Greedy.length());
        chart.addInfoContent("Greedy Algorithm execution time = " + gtotalTime/1000 + " miliseconds.");
        chart.addInfoContent("Nearest Neighbour shortest tour length = " + ShortestForNearest.length());
        chart.addInfoContent("Nearest Neighbour execution time = " + ntotalTime/1000 + " miliseconds.");
        chart.addInfoContent("Shortest tour length = " + absoluteSol.length());
        
        
        chart3.addInfoContent("Divide&conquer algorithm shortest tour length = " + ShortestForDC.length());
        chart3.addInfoContent("Divide&conquer execution time = " + dtotalTime/1000 + " miliseconds.");
        chart1.addInfoContent("Greedy algorithm shortest tour length = " + Greedy.length());
        chart1.addInfoContent("Greedy Algorithm execution time = " + gtotalTime/1000 + " miliseconds.");
        chart2.addInfoContent("Nearest Neighbour shortest tour length = " + ShortestForNearest.length());
        chart2.addInfoContent("Nearest Neighbour execution time = " + ntotalTime/1000 + " miliseconds.");
      
        chart.getStyler().setInfoPanelVisible(true);
        chart1.getStyler().setInfoPanelVisible(true);
        chart2.getStyler().setInfoPanelVisible(true);
        chart3.getStyler().setInfoPanelVisible(true);
        chart4.getStyler().setInfoPanelVisible(true);
        chart.getStyler().setInfoPanelBorderColor(new Color(255, 255, 255));
        chart1.getStyler().setInfoPanelBorderColor(new Color(255, 255, 255));
        chart2.getStyler().setInfoPanelBorderColor(new Color(255, 255, 255));
        chart3.getStyler().setInfoPanelBorderColor(new Color(255, 255, 255));
        chart4.getStyler().setInfoPanelBorderColor(new Color(255, 255, 255));
        new SwingWrapper(chart).displayChart();
        new SwingWrapper(chart1).displayChart();
        new SwingWrapper(chart2).displayChart();
        new SwingWrapper(chart3).displayChart();
        new SwingWrapper(chart4).displayChart();
        
    }
}
