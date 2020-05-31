public class City {
 
    private int x;
    private int y;
    private boolean isVisited = false ;
    
    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }
 
    public double distanceTo(City city) {
        int x = Math.abs(getX() - city.getX());
        int y = Math.abs(getY() - city.getY());
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

	public int getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public boolean isVisited() {
		return this.isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
 
}