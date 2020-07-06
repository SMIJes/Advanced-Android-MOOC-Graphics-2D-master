package com.bennyplo.graphics2d;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import static com.bennyplo.graphics2d.MyMethods.*;

public class MyView extends View {
    //The vertices of the object to be drawn
    public static Coordinate[] drawObjectVertices;

    public MyView(Context context) {
        super(context, null);
        final View thisView = this;
        final MainActivity mainActivity =  new MainActivity();


        //initiateHorizontalMovementAnime(thisView, mainActivity);
        demonstrateGimblasLock(thisView, mainActivity);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //draw objects on the screen
        super.onDraw(canvas);
        DrawCube(canvas, drawObjectVertices, generateSamplePaints().get("RED"));//draw a cube onto the screen
    }







}