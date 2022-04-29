import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int screen_width = 500;
	static final int screen_height = 500;
	static final int unit_size = 25;
	static final int game_units = (screen_width*screen_height)/unit_size;
	static final int delay = 75;
	static final int x[] = new int[game_units];
	static final int y[]= new int[game_units];
	int bodyParts = 6;
	int applesEaten;
	int applesX;
	int applesY;
	boolean running = false;
	char direction = 'R';
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(screen_width, screen_height));
		this.setBackground(Color.white);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
	}
	public void startGame() {
		newapple();
		running = true;
		timer = new Timer(delay, this);
		timer.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	public void draw(Graphics g) {
		if(running) {
		for (int i = 0; i <screen_height/unit_size;i++) {
			g.drawLine(i*unit_size, 0, i*unit_size, screen_height);
			g.drawLine(0, i*unit_size, screen_height, i*unit_size);
		}
		g.setColor(Color.red);
		g.fillOval(applesX, applesY, unit_size, unit_size);
		
		for(int i =0; i < bodyParts; i++) {
			if(i == 0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], unit_size, unit_size);
			}
			else {
				g.setColor(Color.green);
			}
		}
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (screen_width - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
	}
		else {
			gameOver(g);
		}
	}
	public void newapple() {
		applesX = random.nextInt((int)(screen_width/unit_size))*unit_size;
		applesY = random.nextInt((int)(screen_width/unit_size))*unit_size;
	}
	public void move() {
		for(int i =bodyParts; i >0; i++) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U' :
			y[0] = y[0] - unit_size;
			break;
		case 'D' :
			y[0] = y[0] + unit_size;
			break;
		case 'L' :
			x[0] = x[0] - unit_size;
			break;
		case 'R' :
			x[0] = x[0] + unit_size;
			break;
		}
		
	}
	public void checkApple() {
		if((x[0] == applesX) && (y[0] == applesY)) {
			bodyParts++;
			applesEaten++;
			newapple();
		}
	}
	public void checkCollision() {
		for (int i =bodyParts; i>0;i++) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		if(x[0] < 0) {
			running = false;
		}
		if(x[0] > screen_width) {
			running = false;
		}
		if(y[0] < 0) {
			running = false;
		}
		if(y[0] > screen_height) {
			running = false;
		}
		if(!running) {
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.blue);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (screen_width - metrics.stringWidth("Game Over"))/2, screen_height);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction ='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction ='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction ='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction ='D';
				}
				break;
			}
		}
	}
}
