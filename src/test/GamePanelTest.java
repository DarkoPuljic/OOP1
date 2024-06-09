package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
        // Wait for the EDT to complete
        waitForEventDispatchThread();
    }

    // Helper method to wait for the EDT to complete
    private void waitForEventDispatchThread() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            // Signal that the EDT has completed
            latch.countDown();
        });
        // Wait for the latch to be counted down
        latch.await(2, TimeUnit.SECONDS);
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
        assertEquals(0, gamePanel.foodEaten);
    }

    @Test
    public void testInitialLength() {
        assertEquals(5, gamePanel.length);
    }

    @Test
    public void testRunningInitiallyFalse() {
        assertEquals(true, gamePanel.running);
    }
    
    @Test
    public void testPanelSize() {
        int actualWidth = gamePanel.getWidth();
        int actualHeight = gamePanel.getHeight();
        assertEquals(GamePanel.WIDTH, actualWidth);
        assertEquals(GamePanel.HEIGHT, actualHeight);
    }
    }
