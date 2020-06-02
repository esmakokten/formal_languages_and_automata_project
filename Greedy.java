import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Greedy{
	private static int [][] Edges = new int [1128][2];
	// {[1,3],[2,6],[]} /// A:[A,B] ,B:[B,A,F] ,C:[,A,C,T]
    private Dictionary <Integer,ArrayList<Integer>> Segments = new Hashtable<Integer, ArrayList<Integer>>(); 
    private static int [] isVisited = new int [48];
    private static double[][] Distances = new double [48][48];
    public Greedy(double [][] DistanceTo) {
        for (int i = 0; i < DistanceTo.length; i++) {
            for (int j = i+1; j < DistanceTo[i].length; j++) {
                Distances[i][j]=DistanceTo[i][j];
            }
        }
        setSegments();
        sortEdges();
        selectEdge();
        createCircle();
        for (int i = 0; i < Segments.get(0).size(); i++) {
        	System.out.println( Segments.get(0).get(i)); 
        }
    }
    public ArrayList<Integer> Result() {
    	return Segments.get(0);
    }

    public void setSegments() {
    	for (int i = 0; i < 48; i++) {
    		ArrayList<Integer> s = new ArrayList<Integer>();
    		s.add(i);
    		Segments.put(i, s);
    	}
    }
    private void sortEdges() {
    	for (int i = 0; i < Edges.length; i++) {
    		Edges[i]=getEdge();
    	}
    }
    private void createCircle () {
    	ArrayList<Integer> s = new ArrayList<Integer>();
    	boolean isFirst=true;
    	Enumeration<ArrayList<Integer>> en = Segments.elements();
    	ArrayList<Integer> firstSeg = en.nextElement();
    	ArrayList<Integer> nextSeg;
    	while (en.hasMoreElements()) {
    		isFirst=true;
    		nextSeg = en.nextElement();
    		for (int i = 0; i < s.size(); i++) {
				if (s.get(i)==nextSeg.get(0)||s.get(i)==nextSeg.get(nextSeg.size()-1)) {
					isFirst=false;
				}
    		}
    		if(isFirst) {
    			s.add(nextSeg.get(0));
    			s.add(nextSeg.get(nextSeg.size()-1));
    			firstSeg.addAll(nextSeg);
    		}
    		
    	}
    	Segments.put(0, firstSeg);
    }

    private void selectEdge() {
    	for (int i = 0; i < Edges.length; i++) {
    		int [] edge = Edges[i];
    		System.out.println( edge[0] +" "+ edge[1]); 
	    	ArrayList<Integer> segment1 = Segments.get(edge[0]);
	    	ArrayList<Integer> segment2 = Segments.get(edge[1]);
	    	if (segment1.get(0)==edge[0]) {
	    		if (segment2.get(0)==edge[1]) {
	    			Collections.reverse(Segments.get(edge[0]));
	    			Segments.get(edge[0]).addAll(Segments.get(edge[1]));
	    			Segments.put(edge[1], Segments.get(edge[0]));
	    			for (int j = 0; j < Segments.get(edge[0]).size(); j++) {
	    				Segments.put(Segments.get(edge[0]).get(j), Segments.get(edge[0]));
	    			}
	    			
	    		}
	    		else if (segment2.get(segment2.size()-1)==edge[1]) {
	    			Segments.get(edge[1]).addAll(Segments.get(edge[0]));
	    			Segments.put(edge[0], Segments.get(edge[1]));
	    			for (int j = 0; j < Segments.get(edge[1]).size(); j++) {
	    				Segments.put(Segments.get(edge[1]).get(j), Segments.get(edge[1]));
	    			}
	    		}
	    	}
	    	else if (segment1.get(segment1.size()-1)==edge[0]) {
	    		if (segment2.get(0)==edge[1]) {
	    			Segments.get(edge[0]).addAll(Segments.get(edge[1]));
	    			Segments.put(edge[1], Segments.get(edge[0]));
	    			for (int j = 0; j < Segments.get(edge[0]).size(); j++) {
	    				Segments.put(Segments.get(edge[0]).get(j), Segments.get(edge[0]));
	    			}
	    		}
	    		else if (segment2.get(segment2.size()-1)==edge[1]) {
	    			Collections.reverse(Segments.get(edge[1]));
	    			Segments.get(edge[0]).addAll(Segments.get(edge[1]));
	    			Segments.put(edge[1], Segments.get(edge[0]));
	    			for (int j = 0; j < Segments.get(edge[1]).size(); j++) {
	    				Segments.put(Segments.get(edge[1]).get(j), Segments.get(edge[1]));
	    			}
	    		}
	    	}	  
    	}
    }
    
    public static int[] getEdge() {
	    int[] twins = new int[2];
	    double min=99999;
	    for(int i=0;i<Distances.length;i++) {
	          for(int j=i+1;j<Distances[i].length;j++) {
	               if(min>=Distances[i][j] && Distances[i][j]>0) {
	                   min=Distances[i][j];
	                   twins[0] = i;
	                   twins[1] = j;
	               }
	          }
        }
	    Distances[twins[0]][twins[1]]=-1;
	    return twins;
	} 
}