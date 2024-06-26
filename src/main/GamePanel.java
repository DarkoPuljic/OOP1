package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	public static final int WIDTH = 750;
	public static final int HEIGHT = 750;
	public static final int UNIT_SIZE = 20;
	public static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	public static final int SPEED_INCREASE_FACTOR= 2;

	// koordinate tela zmije
	public int x[] = new int[NUMBER_OF_UNITS];
	public int y[] = new int[NUMBER_OF_UNITS];
	
	// initial length of the snake
	public int length = 2;
	public int jabukaEaten;
	public int jabukaX;
	public int jabukaY;
	public char direction = 'D';
	public boolean running = false;
	Random random;
	Timer timer;
	
	public GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		this.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_ENTER && !running) {
	                restartGame();
	            }
	        }
	    });
		play();
	}
	public void play() {
		addFood();
		running = true;
		
		timer = new Timer(90, this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
	}
	
	public void move() {
		for (int i = length; i > 0; i--) {
			// pomera zmiju za jedno polje u zeljenom pravcu
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		if (direction == 'L') {
			x[0] = x[0] - UNIT_SIZE;
		} else if (direction == 'R') {
			x[0] = x[0] + UNIT_SIZE;
		} else if (direction == 'U') {
			y[0] = y[0] - UNIT_SIZE;
		} else {
			y[0] = y[0] + UNIT_SIZE;
		}	
	}
	
	public void checkFood() {
		if(x[0] == jabukaX && y[0] == jabukaY) {
			length++;
			jabukaEaten++;
			addFood();
			timer.setDelay(timer.getDelay() - SPEED_INCREASE_FACTOR);
		}
	}
	
	public void draw(Graphics graphics) {
		
		if (running) {
			graphics.setColor(new Color(255, 0, 0));
			graphics.fillOval(jabukaX, jabukaY, UNIT_SIZE, UNIT_SIZE);
			
			graphics.setColor(Color.white);
			graphics.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
			
			for (int i = 1; i < length; i++) {
				graphics.setColor(new Color(40, 200, 150));
				graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			
			graphics.setColor(Color.white);
			graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
			FontMetrics metrics = getFontMetrics(graphics.getFont());
			graphics.drawString("Score: " + jabukaEaten, (WIDTH - metrics.stringWidth("Score: " + jabukaEaten)) / 2, graphics.getFont().getSize());
		
		} else {
			gameOver(graphics);
		}
	}
	
	public void addFood() {
		jabukaX = random.nextInt((int)(WIDTH / UNIT_SIZE))*UNIT_SIZE;
		jabukaY= random.nextInt((int)(HEIGHT / UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void checkHit() {
		// hitac u telo
		for (int i = length; i > 0; i--) {
			if (x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}
		
		// hitac u zid
		if (x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics graphics) {
		graphics.setColor(Color.red);
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 3);
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
		metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Score: " + jabukaEaten, (WIDTH - metrics.stringWidth("Score: " + jabukaEaten)) / 2, graphics.getFont().getSize());
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 30));
		metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Press enter to restart", (WIDTH - metrics.stringWidth("Press Enter to restart")) / 2, HEIGHT / 2);
		}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (running) {
			move();
			checkFood();
			checkHit();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';
					}
					break;
					
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';
					}
					break;
					
				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';
					}
					break;
					
				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';
					}
					break;		
			}
		}
	}
	public void restartGame() {
        running = true;
        length = 2;
        jabukaEaten = 0;
        direction = 'D';
        x = new int[NUMBER_OF_UNITS];
        y = new int[NUMBER_OF_UNITS];
        play();
    }
}