package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GamePanelTest {

    private GamePanel gamePanel;

    @BeforeEach
    public void setUp() throws InterruptedException {
        gamePanel = new GamePanel();
        JFrame frame = new JFrame();
        frame.add(gamePanel);
        frame.setVisible(true);
        waitForEventDispatchThread();
    }

    private void waitForEventDispatchThread() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS);
    }
    
    @Test
    public void testAddFood() {
        GamePanel gamePanel = new GamePanel();
        gamePanel.addFood();

        assertNotNull(gamePanel.jabukaX);
        assertNotNull(gamePanel.jabukaY);
    }
    

    @Test
    public void testConstructor() {
        assertNotNull(gamePanel);
    }

    @Test
    public void testInitialDirection() {
        assertEquals('D', gamePanel.direction);
    }

    @Test
    public void testInitialFoodEaten() {
        assertEquals(0, gamePanel.jabukaEaten);
    }

    @Test
    public void testInitialLength() {
        assertEquals(2, gamePanel.length);
    }

    @Test
    public void testRunningInitiallyFalse() {
        assertEquals(true, gamePanel.running);
    }
    
    @Test
    public void testPanelSize() {
        assertEquals(GamePanel.WIDTH, 750);
        assertEquals(GamePanel.HEIGHT, 750);
    }
    
    @Test
    public void testRestartGame() {
        GamePanel gamePanel = new GamePanel();
        gamePanel.running = false;
        gamePanel.length = 5;
        gamePanel.jabukaEaten = 10;
        gamePanel.direction = 'U';
        gamePanel.x[0] = 100;
        gamePanel.y[0] = 100;

        gamePanel.restartGame();

        assertTrue(gamePanel.running);
        assertEquals(2, gamePanel.length);
        assertEquals(0, gamePanel.jabukaEaten);
        assertEquals('D', gamePanel.direction);
        assertArrayEquals(new int[GamePanel.NUMBER_OF_UNITS], gamePanel.x);
        assertArrayEquals(new int[GamePanel.NUMBER_OF_UNITS], gamePanel.y);
    }
    }

