package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.Application;
import org.junit.jupiter.api.*;

import java.io.IOException;


/**
 * Please be notified that, CLI will return true, as long as command "quit" is not the input
 * The methods inside CLI are also called, they return false for valid input, true for invalid inputs
 * But those methods are not tested here for the sake of encapsulation, they are made to be private methods in clevis
 * Thus we can only access them using public method CLI here
 */
import static org.junit.jupiter.api.Assertions.*;
public class ClevisTest {

    Clevis clevis = new Clevis();

    public ClevisTest() throws IOException {
    }


    @Test
    void CLICmdRecognizer(){
        assertTrue(clevis.CLI("invalid command example"));      //asserting true, since CLI returns true as long as the command is not quit, indicating to continue
        assertTrue(clevis.CLI("rectangle ImARec 0 0 0 0"));
        assertTrue(clevis.CLI("listAll"));
        assertFalse(clevis.CLI("quit"));
    }

    @Test
    void TestRecConstruction(){
        assertTrue(clevis.CLI("rectangle a 0 0 0 0"));  //Testing via CLI
        assertTrue(clevis.CLI("rectangle a 1 1 1 1"));  //Invalid message should show, named used, action is aborted
        assertTrue(clevis.CLI("rectangle aa 1.2 3.4 5.6 7.8"));  //Testing via CLI
    }

    @Test
    void TestLineConstruction(){
        assertTrue(clevis.CLI("line b 0 0 0 0"));
        assertTrue(clevis.CLI("line b 1 1 0 1"));     //Invalid message should show, named used, action is aborted
        assertTrue(clevis.CLI("line bb 19.2 49.1 -10.5 -60.123415"));
    }
    @Test
    void TestCirConstruction(){
        assertTrue(clevis.CLI("circle c 1 1 0"));
        assertTrue(clevis.CLI("circle c 0 0 0 "));      //Invalid message should show, named used, action is aborted
        assertTrue(clevis.CLI("circle cc 10.123141 9.1231515 10"));
    }

    @Test
    void TestSquConstruction(){
        assertTrue(clevis.CLI("square d 0 0 1"));
        assertTrue(clevis.CLI("square d 0 0 5"));       //Invalid message should show, named used, action is aborted
        assertTrue(clevis.CLI("square dd 20.15151 90 29"));
    }

    @Test
    void TestGrpConstruction(){
        clevis.CLI("square x1 0 0 1");
        clevis.CLI("square x2 20.15151 90 29");
        clevis.CLI("square y1 20.15151 90 29");
        assertTrue(clevis.CLI("group x x1 x2"));
        assertTrue(clevis.CLI("group x x1 x2"));      //Invalid message should show, named used, action is aborted
        assertTrue(clevis.CLI("group y y1 x1"));      //a shape is already grouped, action is aborted
        clevis.CLI("listAll");
        clevis.CLI("delete x");
    }

    @Test
    void TestUngrp(){

        clevis.CLI("square un1 0 0 1");
        clevis.CLI("square un2 20.15151 90 29");
        clevis.CLI("group un un1 un2");
        clevis.CLI("listAll");
        assertTrue(clevis.CLI("ungroup dontexist"));
        assertTrue(clevis.CLI("ungroup un1"));
        assertTrue(clevis.CLI("ungroup un"));
        clevis.CLI("listAll");              //now x should not be there
    }


    @Test
    void TestDel() {
        clevis.CLI("listAll");
        assertTrue(clevis.CLI("delete Haha"));     //Haha isn't there, should be invalid
        assertTrue(clevis.CLI("delete aa"));
        clevis.CLI("listAll");          //aa should be gone
    }

    @Test
    void TestBoundingBox(){
        clevis.CLI("rectangle bon1 5 4 10 15");
        clevis.CLI("line bon2 6.1 7.9 10 2");
        clevis.CLI("circle bon3 50 100 9 ");
        clevis.CLI("square bon4 24 96 5 ");
        assertTrue(clevis.CLI("boundingbox bon1"));           //should just be the x,y,w,h of rectangle m1
        assertTrue(clevis.CLI("boundingbox bon2"));           //should be 6.1, 7.9, 3.9, 5.9
        clevis.CLI("group bon m1 m2 m3 m4");
        assertTrue(clevis.CLI("boundingbox dontexist"));           //invalid
        assertTrue(clevis.CLI("boundingbox bon2"));           //invalid
        assertTrue(clevis.CLI("boundingbox bon"));       //should be 5, 109 , 52 ,120
        clevis.CLI("delete bon");
    }

    @Test
    void TestMove(){
        clevis.CLI("rectangle mo1 0 0 5 10");
        clevis.CLI("circle mo2 0 0 5");
        assertTrue(clevis.CLI("move mo1 10 20"));
        clevis.CLI("group moveTest mo1 mo2");
        assertTrue(clevis.CLI("move moveTest 5 5"));
        assertTrue(clevis.CLI("move mo2 2 2"));
        clevis.CLI("delete moveTest");
    }

    @Test
    void TestList(){
        clevis.CLI("rectangle m1 5 4 10 15");
        clevis.CLI("line m2 6.1 7.9 10 2");
        clevis.CLI("circle m3 50 100 9 ");
        clevis.CLI("square m4 24 96 5 ");
        assertTrue(clevis.CLI("list m1"));
        clevis.CLI("group ImAGrp m1 m2 m3 m4");
        assertTrue(clevis.CLI("list ImAGrp"));
        assertTrue(clevis.CLI("list m1"));  //should be invalid, group component
        clevis.CLI("delete ImAGrp");
        clevis.CLI("listAll");
    }

    @Test
    void TestUndoRedo(){
        clevis.CLI("rectangle m1 5 4 10 15");
        clevis.CLI("line m2 6.1 7.9 10 2");
        clevis.CLI("circle m3 50 100 9 ");
        clevis.CLI("square m4 24 96 5 ");
        assertTrue(clevis.CLI("undo"));
        clevis.CLI("listAll");          //m4 is gone
        assertTrue(clevis.CLI("redo"));
        clevis.CLI("listAll");          //m4 is back
        clevis.CLI("delete m2");
        clevis.CLI("listAll");          //m2 deleted
        clevis.CLI("undo");
        clevis.CLI("listAll");          //m2 deletion undone
        clevis.CLI("group ImAGrp m1 m2 m3 m4");
        clevis.CLI("move ImAGrp 10 20");
        clevis.CLI("listAll");
        assertTrue(clevis.CLI("undo"));
        assertTrue(clevis.CLI("undo"));
        clevis.CLI("listAll");          //all shapes moved back and ungrouped
        clevis.CLI("redo");
        clevis.CLI("delete ImAGrp");
    }

    @Test
    void testNameNotUsed(){
        assertTrue(clevis.nameNotUsed("bruh"));
        clevis.CLI("rectangle m1 5 4 10 15");
        clevis.CLI("line m2 6.1 7.9 10 2");
        clevis.CLI("circle m3 50 100 9 ");
        clevis.CLI("square m4 24 96 5 ");
        assertFalse(clevis.nameNotUsed("m1"));
        assertFalse(clevis.nameNotUsed("m4"));
        clevis.CLI("group ImAGrp m1 m2 m3 m4");
        assertFalse(clevis.nameNotUsed("m4"));
        clevis.CLI("delete ImAGrp");
    }

    @Test
    void TestWriteLogs() throws IOException {
        clevis.cmds.add("quit");            //please be noted all lines should be added by program itself, this one is made to test out
        clevis.writeLogs("log.html","log.txt");
    }


}