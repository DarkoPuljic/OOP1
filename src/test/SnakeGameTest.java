package test;

import org.junit.jupiter.api.Test;

import main.GameFrame;
import main.SnakeGame;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SnakeGameTest {

    @Test
    public void testMain() {
        SnakeGame.main(new String[0]);
        assertNotNull(GameFrame.class);
    }
}