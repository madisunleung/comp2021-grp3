package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {
    Line l1 = new Line("line1",0,0,0,0);
    Line l2 = new Line("line2",0, 5, 7 ,0);

    @Test
    void getInfo() {
        l1.getInfo(1);
        l2.getInfo(2);
    }


    @Test
    void boundingbox() {
        double[] res = l2.boundingbox();
        assertEquals(0,res[0]);
        assertEquals(5,res[1]);
        assertEquals(7,res[2]);
        assertEquals(5,res[3]);
    }
}