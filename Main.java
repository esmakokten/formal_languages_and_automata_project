import java.awt.BorderLayout;
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
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Main{
	
	public static Vector<City> cities = new Vector<City>(1);
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
        //// new cities added to the cities vector
        
        /// iterate print the vector
        Enumeration<City> en = cities.elements();
        System.out.println("\nElements are:");
        while(en.hasMoreElements()) {
        	City currCity = en.nextElement();
           //System.out.println(currCity.getX() + "," + currCity.getY());
		}
        
        //Simetrik hepsini hesaplamaya gerek yok
        for (int i = 0; i < DistanceTo.length; i++) {
        	for (int j = 0; j < DistanceTo[i].length; j++) {
        		DistanceTo[i][j] = cities.elementAt(i).distanceTo(cities.elementAt(j));
        	}
        }
       
        for (int i = 0; i < DistanceTo.length; i++) {
        	for (int j = 0; j < DistanceTo[i].length; j++) {
        		System.out.printf("%f-",DistanceTo[i][j] );
        	}
        	System.out.println("" ); 
        }


        
        
        /*
         * DRAW ile alakali
         * int [] xData = new int [cities.size()];
        int [] yData = new int [cities.size()];
       
        cities.forEach(city -> xData[cities.indexOf(city)]= city.getX() );
        cities.forEach(city -> yData[cities.indexOf(city)]= city.getY() );  
        //cities.elementAt(index);
        // 
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DrawRoute Draw = new DrawRoute();
        Draw.setPreferredSize(new Dimension(320, 200));
        testFrame.getContentPane().add(Draw, BorderLayout.CENTER);
        Draw.addLine(0, 10, 10, 0);
        testFrame.pack();
        testFrame.setVisible(true);
        //
        // Create Chart
        XYChart chart = new XYChartBuilder().width(600).height(500).title("City Map").xAxisTitle("X").yAxisTitle("Y").build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(8);
        chart.addSeries("City", xData , yData);
       // chart.getStyler().setPlotGridHorizontalLinesVisible(true);
        
        
        new SwingWrapper(chart).displayChart();
        */
       
    }
}
