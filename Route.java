import java.util.Enumeration;
import java.util.Vector;
import java.util.Collections;

public class Route {
  private Vector <City> Tour = new Vector <City> ();
  private double length;
  private double segmentLength;
  private int[] xData;
  private int[] yData;
  
  public Route() {
  }
  public Route(Route that) {
	  this.Tour=that.Tour;
  }

  public Vector <City> getTour() {
	  return Tour;
  }
  public void setTour(Vector <City> newTour) {
	  Tour = newTour;
  }

  public void addToTour(City city) {
	  Tour.addElement(city);
  }
  
  public double length(){
	  calculateLength();
	  return length;
  }
  public double segmentLength(){
	  calculateSegmentLength();
	  return segmentLength;
  }
  public void calculateLength() {
	  double total =0;
	  Enumeration<City> en = Tour.elements();
	  City currCity = en.nextElement();
	  City nextCity;
      while(en.hasMoreElements()) {
      	if(en.hasMoreElements()) {
      		nextCity = en.nextElement();
      		total += currCity.distanceTo(nextCity);
      		currCity = nextCity;
      	}
      	total += Tour.lastElement().distanceTo(Tour.firstElement());
      }
      this.length = total;
  }
  
  public void calculateSegmentLength() {
	  double total =0;
	  Enumeration<City> en = Tour.elements();
	  City currCity = en.nextElement();
	  City nextCity;
      while(en.hasMoreElements()) {
      	if(en.hasMoreElements()) {
      		nextCity = en.nextElement();
      		total += currCity.distanceTo(nextCity);
      		currCity = nextCity;
      	}
      }
      this.segmentLength = total;
  }
  
  public void Swap(int i, int j) {
	  Collections.swap(Tour, i, j);;
  }
  
  public void Rotate() {
	  Collections.rotate(Tour, 1);
  }
  
  public double CalculateTogether(Route route2) {
	  double totalLength = this.segmentLength() + route2.segmentLength();
      totalLength += this.Tour.lastElement().distanceTo(route2.Tour.firstElement());
      totalLength += this.Tour.firstElement().distanceTo(route2.Tour.lastElement());
	  return totalLength;
  } 
  public void Merge(Route route2) {
	  Enumeration<City> en = route2.Tour.elements();
      while(en.hasMoreElements()) {
    	  Tour.add(en.nextElement());
      }
  } 
  
  /*
   * It was a trial for drawing graph
  public void Draw (DrawRoute allroutes) {
	  for (int j = 0; j < Tour.size()-1; j++) {
		  City currCity = Tour.elementAt(j);
		  City nextCity = Tour.elementAt(j+1);
		  allroutes.addLine(currCity.getX(), currCity.getY(), nextCity.getX(), nextCity.getY());
      }
	  allroutes.addLine(Tour.lastElement().getX(),Tour.lastElement().getY(), Tour.firstElement().getX(), Tour.firstElement().getY());
  }
  */

 public int[] getxData(){return xData;}
 public int[] getyData(){return yData;}
 public void setxyData(){
	int [] xData = new int [Tour.size()+1];
    int [] yData = new int [Tour.size()+1];
    Tour.forEach(city -> xData[Tour.indexOf(city)]= city.getX() );
    Tour.forEach(city -> yData[Tour.indexOf(city)]= city.getY() );
    xData[Tour.size()]= Tour.firstElement().getX();
    yData[Tour.size()]= Tour.firstElement().getY();
    this.xData = xData;
    this.yData = yData;
 }

}
