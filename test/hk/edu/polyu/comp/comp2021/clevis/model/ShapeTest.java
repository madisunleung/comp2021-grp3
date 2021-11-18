package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShapeTest {

    // Change the parameters later..
    Shape squr1 = new Square("squr1", 3, 3, 3);
    Shape squr2 = new Square("squr2", 4.132135, 5.212312316, 8.98765456);
    Shape cir1 = new Circle("cir1", 3, 3, 3);
    Shape cir2 = new Circle("cir1", 5, 5, 5);
    Shape rec1 = new Rectangle("rec1", 3, 3, 5, 7);
    Shape rec2 = new Rectangle("rec2", 6, 6, 8, 9);
    Shape line1 = new Line("line1", 3, 3, 5, 7);
    Shape line2 = new Line("line1", 3, 3, 5, 7);
    Shape[] g = {squr1, cir1, rec1, line1};
    Group grp = new Group("x", g);


    @Test
    void testAddShape() {
        Shape.addShape(squr1);
        Shape.addShape(squr2);
        Shape.addShape(cir1);
        Shape.addShape(cir2);
        Shape.addShape(rec1);
        Shape.addShape(rec2);
        Shape.addShape(line1);
        Shape.addShape(line2);
    }

    @Test
    void testGetName() {
        assertNotNull(squr1.getName()); //
        assertNotEquals(" ", squr1.getName());
        assertEquals("squr1", (new Square("squr1", 3, 3, 3)).getName());
    }

    @Test
    void testGetInfo() {
        squr1.getInfo(1);
        squr2.getInfo(2);
        grp.getInfo(1);
        //it just prints out the information of the Shape in the method of getInfo.
    }


    @Test
    void testBoundingbox() {
        double[] res = squr1.boundingbox();
        for (int i = 0; i < 4; i++) {
            assertEquals(3, res[i]);
        }
        res = grp.boundingbox();
        assertEquals(0, res[0]);
        assertEquals(7, res[1]);
        assertEquals(8, res[2]);
        assertEquals(11, res[3]);
    }

    @Test
    void ungroup() {
        Shape.addShape(squr1);
        Shape.addShape(cir1);
        Shape.addShape(rec1);
        Shape.addShape(line1);
        Shape.addShape(grp);
        Shape.List();
        grp.ungroup();
        Shape.List();         //The group should not be there now
    }

    @Test
    void regroup() {
        Shape.addShape(squr1);
        Shape.addShape(cir1);
        Shape.addShape(rec1);
        Shape.addShape(line1);
        Shape.addShape(grp);
        Shape.List();
        grp.ungroup();
        Shape.List();
        grp.regroup();
        Shape.List();         //Lost and found

    }

    @Test
    void findAShape() {
        Shape.addShape(grp);
        assertEquals(grp, Shape.findAShape("x"));
    }

    @Test
    void belongToGroup() {
        Shape.addShape(squr1);
        Shape.addShape(cir1);
        Shape.addShape(rec1);
        Shape.addShape(line1);
        Shape.addShape(grp);
        assertEquals(grp, squr1.belongToGroup());
    }

    @Test
    void testDelete() {
        Shape.addShape(squr1);
        Shape.addShape(cir1);
        Shape.addShape(rec1);
        Shape.addShape(line1);
        Shape.addShape(grp);
        assertTrue(Shape.delete("squr1"));
        assertFalse(Shape.delete("x"));
    }

    @Test
    void undelete() {
        Shape.addShape(squr2);
        Shape.addShape(cir2);
        Shape.addShape(rec2);
        Shape.addShape(line2);
        assertFalse(Shape.delete("squr2"));

    }

    @Test
    void listTest() {
        Shape.addShape(squr1);
        Shape.addShape(cir1);
        Shape.addShape(rec1);
        Shape.addShape(line1);
        Shape.addShape(grp);
        Shape.List();         //Should see a group containing all shapes
    }

    @Test
    void listFromHead() {
        Shape.addShape(squr1);
        Shape.addShape(cir1);
        Shape.addShape(rec1);
        Shape.addShape(line1);
        Shape.addShape(grp);
        Shape.ListFromHead();         //The method to facilitate our undo,redo requirements
        grp.ungroup();
        Shape.delete("squr1");
        Shape.List();             //Now the group and squr1 is removed from the list as you view it normally
        Shape.ListFromHead();         //But as you do this, all the shapes are still there in their correct orders.
    }

    @Test
    void saveUndo() {

    }

    @Test
    void saveMove() {
    }

    @Test
    void popUndo() {
    }

    @Test
    void popMove() {
    }

    @Test
    void clearRedo() {
    }

    @Test
    void saveRedo() {
    }

    @Test
    void savereMove() {
    }
}
