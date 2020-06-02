import java.util.Enumeration;
import java.util.Vector;

public class DivideAndConquer {
	private Route optimum = new Route();
	private int n = 6;
	private boolean isWide;
	private Vector<City> part1 = new Vector<City>();
	private Vector<City> part2 = new Vector<City>();
	
	public DivideAndConquer( Vector<City> cities) {
		runAlgorithm(cities);
	}
	
	private Route runAlgorithm ( Vector<City> cities ) {
		//System.out.println("***Main Algorithm Called"); 
		if (cities.size() <= n) {
			return exhaustive(cities);
		}
		else {
			Split(cities);
			Vector<City> p1 = new Vector<> (part1);
			Vector<City> p2 = new Vector<> (part2);
			return Merge (runAlgorithm(p1),runAlgorithm(p2));
		}
		
	}
	
	private Route exhaustive(Vector<City> cities) {
		//System.out.println("Exhaustive Started"); 
		Route smallTour = new Route ();
		int[] indexes = new int [cities.size()];
		for (int j = 0; j < cities.size(); j++) {
	      smallTour.addToTour(cities.elementAt(j));
	    }
		int i = 1;
		double minLength = smallTour.length();
		Route smallest = new Route(smallTour);
		while (i < cities.size()) {   
			if (indexes[i] < i) {
				smallTour.Swap( i ,i % 2 == 0 ?  0: indexes[i]);
				if(minLength >= smallTour.length()) {
					minLength = smallTour.length();
					smallest.setTour(smallTour.getTour());
				}
				indexes[i]++;
				i = 1;
			}
			else {
				indexes[i] = 0;
				i++;
			}
		}
		//System.out.println("Exhaustive Ended");
		return smallest;
	}

	private void Split ( Vector<City> cities ) {
		//System.out.println("Split Started");
		int mid = middle (cities);
		int index = 0;
		Vector<City> larger = new Vector<City>();
		Vector<City> lefted = cities;
		Enumeration<City> en = cities.elements();
		if (isWide) {
			while(en.hasMoreElements()) {
		        City currCity = en.nextElement();
		        if ( mid>currCity.getX() ) {
		        	lefted.removeElementAt(index);
		        	larger.add(currCity);
		        }
		        index++;
			}
	    }
		else {
			while(en.hasMoreElements()) {
		        City currCity = en.nextElement();
		        if ( mid>currCity.getY() ) {
		        	lefted.removeElementAt(index);
		        	larger.add(currCity);
		        }
		        index++;
			}
		}
		part1 = larger;
		part2 = lefted;
		//System.out.println("Split Ended");
	}
	
	private Route Merge (Route route, Route route2 ) {
		//System.out.println("**Merge Started");
		double tourLength = route.CalculateTogether(route2); 
		double minTour = tourLength;
		Route first = new Route(route);
		Route second = new Route(route2);
		for (int i = 0; i < route.getTour().size(); i++) {	
			for (int j = 0; j < route2.getTour().size(); j++) {
				route2.Rotate();
				tourLength = route.CalculateTogether(route2); 
				if (minTour > tourLength) {
					minTour = tourLength;
					first.setTour(route.getTour());
					second.setTour(route2.getTour());
				}
			}
			route.Rotate();
		}
		first.Merge(second);
		optimum.setTour(first.getTour());
		//System.out.println("**Merge Ended");
		return first;
	}
	private int middle (Vector <City> cities) {
		//System.out.println("--Middle Started");
		int Xmax = 0,Ymax =0;
		int Xmin =cities.firstElement().getX();
		int Ymin =cities.firstElement().getY();
		Enumeration<City> en = cities.elements();
	    while(en.hasMoreElements()) {
	        City currCity = en.nextElement();
	        int x = currCity.getX();
	        int y = currCity.getY();
	        Xmax = (x > Xmax) ? x : Xmax;
	        Ymax = (y > Ymax) ? y : Ymax;
	        Xmin = (x < Xmin) ? x : Xmin;
	        Ymin = (y < Ymin) ? y : Ymin;
		}
	    if ((Ymax-Ymin) > (Xmax-Xmin)) {
	    	isWide=false;
	    	return (Ymin+((Ymax-Ymin)/2));
	    }
	    else {
	    	isWide=true;
	    	return (Xmin+((Xmax-Xmin)/2));
	    }
	}

	public Route Result() {
		return optimum;
	}
}
