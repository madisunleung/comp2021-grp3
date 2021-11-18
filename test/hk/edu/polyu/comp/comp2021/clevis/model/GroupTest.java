package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    Shape squr1 = new Square("squr1", 3, 3, 3);
    Shape cir1 = new Circle("cir1", 3, 3, 3);
    Shape rec1 = new Rectangle("rec1", 3, 3, 5, 7);
    Shape line1 = new Line("line1", 3, 3, 5, 7);
    Shape[] g = {squr1, cir1,rec1,line1};
    Group grp = new Group("x", g);

    @Test
    void getInfo() {
        grp.getInfo(1);
    }

    @Test
    void boundingbox() {
        double[] res = grp.boundingbox();
    }

    @Test
    void delete() {
        Shape.addShape(grp);
        assertFalse(Shape.delete("x"));
        assertTrue(Shape.delete("bruh"));
    }

    @Test
    void move() {
        grp.getInfo(1);
        grp.move(10,20);
        grp.getInfo(2);
    }

    @Test
    void ungroup() {
        Shape.addShape(squr1);
        Shape.addShape(cir1);
        Shape.addShape(line1);
        Shape.addShape(rec1);
        Shape.addShape(grp);
        Shape.List();
        grp.ungroup();
        Shape.List();
    }
}