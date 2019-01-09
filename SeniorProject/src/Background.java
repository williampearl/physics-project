import processing.core.*;
import java.io.*;
import java.util.*;
public class Background extends PApplet{
	private ArrayList<PVector> stars;
	private PApplet parent;
	private int[] starColors = {color(158, 177, 255), color(223, 229, 255)};
	public Background(PApplet par, int numStars) {
		parent = par;
		stars  = new ArrayList<PVector>();
		for(int i=0; i<numStars; i++) 
			stars.add(new PVector((float)Math.random()*par.width, (float)Math.random()*par.height, starColors[(int)(Math.random()*starColors.length)]));
	}
	
	public void draw() {
		parent.pushMatrix();
		
		for(PVector st : stars) {
			parent.pushMatrix();
			parent.translate(st.x, st.y);
			
			int color = Math.random()<0.01f?(int)(st.z*0.8f):(int)st.z;
			parent.noStroke();
			parent.fill(color);
			parent.ellipse(0, 0, 1, 1);
			parent.popMatrix();
		}
		parent.popMatrix();
	}
}
