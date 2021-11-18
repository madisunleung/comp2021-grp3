package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    Rectangle rec1 = new Rectangle("rec1",3,3,3, 3); // rectangle is a square
    Rectangle rec2 = new Rectangle("rec2",4.132135,5.212312316,8.98765456, 4.2345677);
    Rectangle rec3 = (Rectangle) rec1;
    Rectangle rec4 = new Rectangle("rec4",1.234,5.67,8.9, 9.123);

    @Test
    void getInfo() {
        // DOESN'T RETURN ANYTHING
        // USED IN LIST AND LIST ALL COMMANDS
        rec1.getInfo(1);
        rec2.getInfo(2);
        rec3.getInfo(3);
        rec4.getInfo(4);

    }

    @Test
    void move() {
        rec1.move(4, 5);
        assertEquals(7, rec1.getX());
        assertEquals(8, rec1.getY());

        rec3.move(4, 5);
        assertNotEquals(7, rec3.getX());
        assertNotEquals(8, rec3.getY());
        assertEquals(11, rec3.getX());
        assertEquals(13, rec3.getY());

        rec4.move(9.8765, 4.321);
        assertEquals(11.1105, rec4.getX());
        assertEquals(9.991, rec4.getY());

    }

    @Test
    void boundingbox() {
        assertArrayEquals(new double[] {3, 3, 3, 3}, rec1.boundingbox());
        assertArrayEquals(new double[] {4.132135,5.212312316, 8.98765456, 4.2345677}, rec2.boundingbox());
        assertArrayEquals(new double[] {3, 3, 3, 3}, rec3.boundingbox());
        assertArrayEquals(new double[] {1.234,5.67,8.9,9.123}, rec4.boundingbox());
    }
}