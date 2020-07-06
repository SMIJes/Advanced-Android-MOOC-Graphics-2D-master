package com.bennyplo.graphics2d;

import org.junit.Test;

import static com.bennyplo.graphics2d.MyMethods.generateCoordinates;
import static com.bennyplo.graphics2d.MyMethods.*;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {


        Coordinate[] c = {new Coordinate(3,4,2,0)};
        for (int i = 0; i <c.length ; i++) {
            System.out.println(rotate(c, 45, 'y')[i].x);
            System.out.println(rotateByQuaternion(c, 45, 'y')[i].x);
            System.out.println(rotate(c, 45, 'y')[i].y);
            System.out.println(rotateByQuaternion(c, 45, 'y')[i].y);
            System.out.println(rotate(c, 45, 'y')[i].z);
            System.out.println(rotateByQuaternion(c, 45, 'y')[i].z);
            System.out.println(rotate(c, 45, 'y')[i].w);
            System.out.println(rotateByQuaternion(c, 45, 'y')[i].w);
            System.out.println("");
        }

    }
}