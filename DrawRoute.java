import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JComponent;

public class DrawRoute extends JComponent {
	public int height = 670;
	private static class Line{
	    final double x1; 
	    final double y1;
	    final double x2;
	    final double y2;   
	    final Color color;

	    public Line(double x1, double y1, double x2, double y2, Color color) {
	        this.x1 = x1/10;
	        this.y1 = y1/10;
	        this.x2 = x2/10;
	        this.y2 = y2/10;
	        this.color = color;
	    }               
	}

	private final LinkedList<Line> lines = new LinkedList<Line>();

	public void addLine(double x1, double x2, double x3, double x4) {
	    addLine(x1, x2, x3, x4, Color.black);
	}

	public void addLine(double x1, double x2, double x3, double x4, Color color) {
	    lines.add(new Line(x1,x2,x3,x4, color));        
	    repaint();
	}

  public void clearLines() {
	    lines.clear();
	    repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    for (Line line : lines) {
	        g.setColor(line.color);
	        g.drawLine((int)(line.x1), height-(int)line.y1, (int)line.x2, height-(int)line.y2);
	    }
	}
	
}
