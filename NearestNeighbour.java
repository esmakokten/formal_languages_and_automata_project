import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class NearestNeighbour {
	private static int [][] Edges = new int [1128][2];
    private static int [] isVisited = new int [48];
    private static double[][] Distances = new double [48][48];
    private Route [] routes = new Route [48];
    private static Vector<City> cities = new Vector<City>(48);
    
    public NearestNeighbour(double [][] DistanceTo,  Vector<City> citiess) {
        for (int i = 0; i < DistanceTo.length; i++) {
            for (int j = 0; j < DistanceTo[i].length; j++) {
                Distances[i][j]=DistanceTo[i][j];
            }
        }
        cities= citiess; 
        makeRoute(0);
    }
    
    private void makeRoute(int initialCity) {
    	routes[initialCity]=new Route();
    	routes[initialCity].addToTour(cities.elementAt(initialCity));
    	cities.elementAt(initialCity).setVisited(true);
    	int first = getNearest(initialCity);
		while(cities.elementAt(first).isVisited()||first==-1) {
			first = getNearest(initialCity);
		}
		routes[initialCity].addToTour(cities.elementAt(first));
		cities.elementAt(first).setVisited(true);
    	while (routes[initialCity].getTour().size()<48) {
    		int next = getNearest(first);
    		System.out.println(next); 
    		if(!(next==-1)) {
	    		if (!cities.elementAt(next).isVisited()) {
	    			System.out.println("neden buradayim" ); 
		    		routes[initialCity].addToTour(cities.elementAt(next));
		    		cities.elementAt(next).setVisited(true);
		    		first = next;
	    		}
    		}
    	}
    }
    public Route Result() {
    	return routes[0];
    }

    public static int getNearest(int city) {
	    int nearest=-1;
	    double min=99999;
	      for(int j=0;j<Distances[0].length;j++) {
	           if(min>=Distances[city][j] && Distances[city][j]>0 && city!=j) {
	               min=Distances[city][j];
	               nearest = j;
	           }
	     }
	     if (!(nearest==-1)) {
	    	 Distances[city][nearest]=-1;
	     }
	    return nearest;
	} 
}

	

