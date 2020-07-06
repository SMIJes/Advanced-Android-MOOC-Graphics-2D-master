package com.bennyplo.graphics2d;
import static com.bennyplo.graphics2d.MyView.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import static java.lang.Math.*;

public class MyMethods {

    //**********************************************************************************************
    //Draw Functions functions
    public static void DrawLinePairs(Canvas canvas, Coordinate[] vertices, int start, int end,
                                     Paint paint)    {//draw a line connecting 2 points
        //canvas - canvas of the view
        //points - array of points
        //start - index of the starting point
        //end - index of the ending point
        //paint - the paint of the line
        canvas.drawLine((int)vertices[start].x,(int)vertices[start].y,(int)vertices[end].x,(int)vertices[end].y,paint);
    }

    public static void DrawCube(Canvas canvas, Coordinate[] coordinates, Paint paint)    {//draw a cube on the screen
        DrawLinePairs(canvas, coordinates, 0, 1, paint);
        DrawLinePairs(canvas, coordinates, 1, 3, paint);
        DrawLinePairs(canvas, coordinates, 3, 2, paint);
        DrawLinePairs(canvas, coordinates, 2, 0, paint);
        DrawLinePairs(canvas, coordinates, 4, 5, paint);
        DrawLinePairs(canvas, coordinates, 5, 7, paint);
        DrawLinePairs(canvas, coordinates, 7, 6, paint);
        DrawLinePairs(canvas, coordinates, 6, 4, paint);
        DrawLinePairs(canvas, coordinates, 0, 4, paint);
        DrawLinePairs(canvas, coordinates, 1, 5, paint);
        DrawLinePairs(canvas, coordinates, 2, 6, paint);
        DrawLinePairs(canvas, coordinates, 3, 7, paint);
    }

    public static void give3DAppearance(View thisview, Coordinate[] cube_vertices) {
        cube_vertices = translate(cube_vertices, 2, 2, 2);
        cube_vertices = scale(cube_vertices, 40, 40, 40);
        cube_vertices = rotate(cube_vertices, 45, 'y');
        cube_vertices = rotate(cube_vertices, 45, 'z');
        drawObjectVertices = cube_vertices;
        thisview.invalidate();
    }


    //**********************************************************************************************
    //matrix and transformation functions
    public static double []GetIdentityMatrix()    {//return an 4x4 identity matrix
        double []matrix=new double[16];
        matrix[0]=1;matrix[1]=0;matrix[2]=0;matrix[3]=0;
        matrix[4]=0;matrix[5]=1;matrix[6]=0;matrix[7]=0;
        matrix[8]=0;matrix[9]=0;matrix[10]=1;matrix[11]=0;
        matrix[12]=0;matrix[13]=0;matrix[14]=0;matrix[15]=1;
        return matrix;
    }

    public static Coordinate Transformation(Coordinate vertex,double []matrix)    {
        //affine transformation with homogeneous coordinates
        //i.e. a vector (vertex) multiply with the transformation matrix
        // vertex - vector in 3D
        // matrix - transformation matrix
        Coordinate result=new Coordinate();
        result.x=matrix[0]*vertex.x+matrix[1]*vertex.y+matrix[2]*vertex.z+matrix[3];
        result.y=matrix[4]*vertex.x+matrix[5]*vertex.y+matrix[6]*vertex.z+matrix[7];
        result.z=matrix[8]*vertex.x+matrix[9]*vertex.y+matrix[10]*vertex.z+matrix[11];
        result.w=matrix[12]*vertex.x+matrix[13]*vertex.y+matrix[14]*vertex.z+matrix[15];
        return result;
    }

    public static Coordinate[]Transformation(Coordinate []vertices,double []matrix)
    {
        Coordinate[] result=new Coordinate[vertices.length];
        for (int i=0;i<vertices.length;i++)
        {
            result[i]=Transformation(vertices[i],matrix);
            result[i].Normalise();
        }
        return result;
    }


    //**********************************************************************************************
    //Affine transformation functions
    public static Coordinate []translate(Coordinate []vertices,double tx,double ty,double tz)    {
        double []matrix=GetIdentityMatrix();
        matrix[3]=tx;
        matrix[7]=ty;
        matrix[11]=tz;
        return Transformation(vertices,matrix);
    }

    public static Coordinate[]rotate(Coordinate []vertices,double angle, char axis)    {
        double []matrix=GetIdentityMatrix();
        switch(axis) {
            case 'x' :matrix[5]=cos(angle);matrix[6]=-sin(angle);
                matrix[9]=sin(angle); matrix[10]=cos(angle);break;
            case 'y':matrix[0]=cos(angle);matrix[2]=sin(angle);
                matrix[8]=-sin(angle);matrix[9]=cos(angle);break;
            case 'z' :matrix[0]=cos(angle);matrix[1]=-sin(angle);
                matrix[4]=sin(angle);matrix[5]=cos(angle);break;
        }
        return Transformation(vertices,matrix);
    }

    public static Coordinate[]scale(Coordinate []vertices,double sx,double sy,double sz)    {
        double []matrix=GetIdentityMatrix();
        matrix[0]=sx;
        matrix[5]=sy;
        matrix[10]=sz;
        return Transformation(vertices,matrix);
    }

    public static Coordinate[]share(Coordinate []vertices,double hx,double hy)    {
        double []matrix=GetIdentityMatrix();
        matrix[2]=hx;
        matrix[6]=hy;
        return Transformation(vertices,matrix);
    }

    //**********************************************************************************************
    //Quaternion transformation functions
    public static double[] generateUnitQuaternionQ(double angle, char axis){
        double[] q = {0,0,0,0} ;
        q[0]= cos(angle/2);
        switch(axis) {
            case 'x' :q[1]=sin(angle/2)*1; break;
            case 'y' :q[2]=sin(angle/2)*1; break;
            case 'z' :q[3]=sin(angle/2)*1; break;
        }
        return q;
    }

    public static double[] generateUnitMatrixM(double angle, char axis){
        double[] q= generateUnitQuaternionQ(angle, axis);
        double w=q[0]; double x=q[1]; double y=q[2]; double z=q[3];
        double[] M = new double[4*4];
        M[0] =pow(w,2)+pow(x,2)-pow(y,2)-pow(z,2);
        M[1] =2*x*y-2*w*z;
        M[2] =2*x*z+2*w*y;
        M[4] =2*x*y+2*w*z;
        M[5] =pow(w,2)+pow(y,2)-pow(x,2)-pow(z,2);
        M[6] =2*y*z-2*w*x ;
        M[8] =2*x*z-2*w*y ;
        M[9] =2*y*z+2*w*x;
        M[10] =pow(w,2)+pow(z,2)-pow(x,2)-pow(y,2);;
        M[3] =0; M[7] =0; M[11] =0;
        M[12] =0; M[13] =0; M[14] =0; M[15] =1;
        return M;
    }

    public static Coordinate[] rotateByQuaternion(Coordinate[] coordinates, double angle,
                                                  char axis){
        double[] M = generateUnitMatrixM(angle, axis);

        return MyMethods.Transformation(coordinates,M);
    }


    //**********************************************************************************************
    //Generator functions
    public  static Hashtable<String, Paint> generateSamplePaints()    {

        Hashtable<String, Paint> my_paints = new Hashtable<>();

        Paint redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setStyle(Paint.Style.FILL_AND_STROKE);//Stroke
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(2);
        my_paints.put("RED", redPaint);

        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(5);
        my_paints.put("BLACK", blackPaint);

        return my_paints;
    }

    public  static Hashtable<String, Coordinate[]> generateCoordinates()    {

        Hashtable<String, Coordinate[]> my_cordinates = new Hashtable<>();

        Coordinate[] cube_vertices = new Coordinate[8];
        cube_vertices[0] = new Coordinate(-1, -1, -1, 1);
        cube_vertices[1] = new Coordinate(-1, -1, 1, 1);
        cube_vertices[2] = new Coordinate(-1, 1, -1, 1);
        cube_vertices[3] = new Coordinate(-1, 1, 1, 1);
        cube_vertices[4] = new Coordinate(1, -1, -1, 1);
        cube_vertices[5] = new Coordinate(1, -1, 1, 1);
        cube_vertices[6] = new Coordinate(1, 1, -1, 1);
        cube_vertices[7] = new Coordinate(1, 1, 1, 1);
        my_cordinates.put("CUBE", cube_vertices);

        return my_cordinates;
    }


    //**********************************************************************************************
    //Animation functions
    public static void initiateHorizontalMovementAnime(final View view,
                                                       final MainActivity mainActivity)    {
        drawObjectVertices = generateCoordinates().get("CUBE");
        give3DAppearance(view, drawObjectVertices);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            float position_x=0f;
            boolean dir=true;
            /////////
            //Coordinate body_axis=new Coordinate(0.3f,1,0,0);
            @Override
            public void run() {
                if (position_x+200>=view.getWidth() && dir)
                    dir=false;
                else if (!dir && position_x<=0)
                    dir=true;
                if (dir)
                {
                    drawObjectVertices = translate(drawObjectVertices, 1f,0,0);
                    position_x += 1f;
                }
                else {
                    drawObjectVertices = translate(drawObjectVertices, -1f,0,0);
                    position_x -= 1f;
                }
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        view.invalidate();//update the view
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task,100,2);
    }

    public static void demonstrateGimblasLock(final View view,
                                                       final MainActivity mainActivity)    {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            double angle  = 45;

            @Override
            public void run() {
                drawObjectVertices = generateCoordinates().get("CUBE");
                drawObjectVertices = translate(drawObjectVertices,2,2,2);
                drawObjectVertices = scale(drawObjectVertices,40,40,40);
                drawObjectVertices = rotate(drawObjectVertices,angle,'x');
                drawObjectVertices = rotate(drawObjectVertices,90,'y');
                drawObjectVertices = rotate(drawObjectVertices,25,'z');
                drawObjectVertices = translate(drawObjectVertices,400,400,0);

                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });

                angle+=0.1;
                if(angle>=360) angle=0;
            }
        };
        timer.scheduleAtFixedRate(task,100,100);
    }

}
