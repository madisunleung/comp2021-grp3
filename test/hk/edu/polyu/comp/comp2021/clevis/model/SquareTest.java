package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {
    Square squr1 = new Square("squr1",3,3,3);
    Square squr2 = new Square("squr2",4.132135,5.212312316,8.98765456);
    Square squr3 = (Square) squr1;
    Square squr4 = new Square("squr4",1.234,5.67,8.9);

    @Test
    void getInfo() {
        // DOESN'T RETURN ANYTHING
        // USED IN LIST AND LIST ALL COMMANDS
        squr1.getInfo(1);
        squr2.getInfo(2);
        squr3.getInfo(3);
        squr4.getInfo(4);
    }

    @Test
    void move() {
        squr1.move(4, 5);
        assertEquals(7, squr1.x);
        assertEquals(8, squr1.y);

        squr3.move(4, 5);
        assertNotEquals(7, squr3.x);
        assertNotEquals(8, squr3.y);
        assertEquals(11, squr3.x);
        assertEquals(13, squr3.y);

        squr4.move(9.8765, 4.321);
        assertEquals(11.1105, squr4.x);
        assertEquals(9.991, squr4.y);
    }

    @Test
    void boundingbox() {
        assertArrayEquals(new double[] {3, 3, 3, 3}, squr1.boundingbox());
        assertArrayEquals(new double[] {4.132135,5.212312316, 8.98765456, 8.98765456}, squr2.boundingbox());
        assertArrayEquals(new double[] {3, 3, 3, 3}, squr3.boundingbox());
        assertArrayEquals(new double[] {1.234,5.67,8.9,8.9}, squr4.boundingbox());
    }
}