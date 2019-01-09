import java.util.*;
import gui.*;
import processing.core.*;
public class Runner extends PApplet{
	public static void main(String[] args) {
		PApplet.main("Runner");
	}
	
	public void settings() {
		size(1024, 700);
	}
	
	Background bg;
	GameSystem game;
	
	float zoom = 1f;
	PVector cameraCenter;
	ArrayList<Button> buttons;
	public void setup() {
		frameRate(60);
		game = new GameSystem(this, 20);
		bg = new Background(this, 100);
		buttons = new ArrayList<Button>();
		Button bx;
		bx = new Button(this, new PVector(3*width/4, 7*height/8),
							new PVector(width/4, height/8), "Restart");
		bx.setClickMethod(game::restart);
		
		//pause button
		Button pause = new Button(this, new PVector(0, height-20),
						new PVector(20, 20), "||");
		pause.setClickMethod((n)->game.setTickSpeed(0));
		Button  fast = new Button(this, new PVector(30, height-20),
				new PVector(20, 20), ">");
		fast.setClickMethod((n)->game.setTickSpeed(5));
		Button  fFast = new Button(this, new PVector(60, height-20),
				new PVector(20, 20), ">>");
		fFast.setClickMethod((n)->game.setTickSpeed(500));
		buttons.add(pause);
		buttons.add(bx);
		buttons.add(fast);
		buttons.add(fFast);
	}
	
	public void draw() {
		background(0);
		
		bg.draw();
		game.tick();
		drawButtons();
	}
	public void drawButtons() {
		for(Button b : buttons)
			b.draw();
	}
	public boolean checkButtons(PVector mouse) {
		boolean ch = false;
		for(int i=0; i<buttons.size()&&!ch; i++) {
			ch = buttons.get(i).tryClick(mouse);
		}
		return ch;
	}
	public void mousePressed() {
		if(!checkButtons(new PVector(mouseX, mouseY))) {
			Asteroid a = game.generateRandomAsteroid();
			a.setPosition(new PVector(mouseX, mouseY));
			game.getParticles().add(a);
		}
	}
	public void mouseReleased() {
		for(Button b : buttons)
			b.unClick();
	}
	public void keyPressed() {
		switch(key) {
		case '=':
			game.setTickSpeed(game.getTickSpeed()+1);
			break;
		case '+':
			game.setTickSpeed(game.getTickSpeed()+10);
			break;
		case '-':
			game.setTickSpeed(game.getTickSpeed()-1);
			break;
		case '_':
			game.setTickSpeed(game.getTickSpeed()-10);
			break;	
		}
	}
}
