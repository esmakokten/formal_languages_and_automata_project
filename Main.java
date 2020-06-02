import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
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
        for (int i = 0; i < DistanceTo.length; i++) {
        	for (int j = i; j < DistanceTo[i].length; j++) {
        		DistanceTo[i][j] = cities.elementAt(i).distanceTo(cities.elementAt(j));
        	}
        }
       
        /* Print Distance to Array 
         * for (int i = 0; i < DistanceTo.length; i++) {
        	for (int j = 0; j < DistanceTo[i].length; j++) {
        		System.out.printf("%f-",DistanceTo[i][j] );
        	}
        	System.out.println("" ); 
        }*/

        System.out.println("Program Started" ); 
        
        DivideAndConquer myAlg = new DivideAndConquer( cities);
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
        XYChart chart = new XYChartBuilder().width(800).height(500).title("Shortest Routes").xAxisTitle("X").yAxisTitle("Y").build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(8);
        chart.addSeries("Divide&Conquer", ShortestForDC.getxData() , ShortestForDC.getyData());
       // chart.addSeries("Absolute Solution", absoluteSol.getxData() , absoluteSol.getyData());
        //"Absolute Solution" "Divide&Conquer"
        chart.addInfoContent("Divide&conquer algorithm shortest tour length = " + ShortestForDC.length());
        chart.addInfoContent("Shortest tour length = " + absoluteSol.length());
        chart.getStyler().setInfoPanelVisible(true);
      
        chart.getStyler().setInfoPanelBorderColor(new Color(255, 255, 255));
        new SwingWrapper(chart).displayChart();
        
       
    }
}
