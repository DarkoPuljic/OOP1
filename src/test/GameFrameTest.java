package test;

import org.junit.jupiter.api.Test;

import main.GameFrame;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameFrameTest {
	
	@Test
    public void testGameFrame() {
        GameFrame gameFrame = new GameFrame();
        assertNotNull(gameFrame);
        
	}
}