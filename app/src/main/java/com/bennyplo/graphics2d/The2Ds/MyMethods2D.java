package com.bennyplo.graphics2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;

import java.util.Hashtable;
import java.util.Objects;

public class MyMethods2D {

    public static void drawRedRectangle(Canvas canvas){
        Paint paint = new Paint();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);//stroke only no fill
        paint.setColor(0xffff0000);//color red
        paint.setStrokeWidth(5);

        canvas.drawRect(10,30,200,200,paint);
    }

    public static void drawGreenPolygon(Canvas canvas){
        Paint paint = new Paint();
        //alpha, red, blue, green color channels
        paint.setARGB(255,0,255,0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        Path path = new Path();
        path= new Path();
        path.moveTo(10,10);
        path.lineTo(300,10);
        path.lineTo(300,300);
        path.lineTo(10,300);
        path.lineTo(10,10);

        canvas.drawPath(path, paint);
    }

    public static void drawSolidPolygon(Canvas canvas)  {
        Path path= new Path();
        path.moveTo(20,700);
        path.lineTo(100,800);
        path.lineTo(200,700);
        path.lineTo(300,800);
        path.lineTo(380,700);
        path.lineTo(300,600);
        path.lineTo(200,650);
        path.lineTo(100,600);
        path.lineTo(20,700);
        path.close();

        Paint fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setARGB(255,255,0,0);

        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStrokeWidth(10 );

        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, borderPaint);


    }

    public static void drawGradientColor(Canvas canvas){
        Path path= new Path();
        path.moveTo(20,700);
        path.lineTo(100,800);
        path.lineTo(200,700);
        path.lineTo(300,800);
        path.lineTo(380,700);
        path.lineTo(300,600);
        path.lineTo(200,650);
        path.lineTo(100,600);
        path.lineTo(20,700);
        path.close();

        LinearGradient linear = new LinearGradient(20,700,380,700, Color.BLUE,
                Color.RED, Shader.TileMode.MIRROR);

        Paint gradientPaint = new Paint();
        gradientPaint.setStyle(Paint.Style.FILL);
        gradientPaint.setShader(linear);

        canvas.drawPath(path, gradientPaint);
    }

    public static void drawGradientSpecial(Canvas canvas){
        Path path= new Path();
        path.moveTo(20,1000);
        path.lineTo(100,1100);
        path.lineTo(200,1000);
        path.lineTo(300,1100);
        path.lineTo(380,1000);
        path.lineTo(300,900);
        path.lineTo(200,950);
        path.lineTo(100,900);
        path.lineTo(20,1000);
        path.close();

        LinearGradient linear = new LinearGradient(0,0,4,4, Color.BLUE,
                Color.RED, Shader.TileMode.MIRROR);

        Paint gradientPaint = new Paint();
        gradientPaint.setStyle(Paint.Style.FILL);
        gradientPaint.setShader(linear);

        canvas.drawPath(path, gradientPaint);
    }

    public static  Point[] translate(Point[] vertices, int px, int py){
        double[][] matrix = new double[3][3];
        matrix[0][0]=1; matrix[0][1]=0; matrix[0][2]=px;
        matrix[1][0]=0; matrix[1][1]=1; matrix[1][2]=py;
        matrix[2][0]=0; matrix[2][1]=0; matrix[2][2]=1;
        return  AffineTransformation(vertices, matrix);
    }

    public  static  Point[] scale(Point[] vertices, double cx, double dy){
        double[][] matrix = new double[3][3];
        matrix[0][0]=cx; matrix[0][1]=0; matrix[0][2]=0;
        matrix[1][0]=0; matrix[1][1]=dy; matrix[1][2]=0;
        matrix[2][0]=0; matrix[2][1]=0; matrix[2][2]=1;
        return  AffineTransformation(vertices, matrix);

    }

    private static Point[] AffineTransformation(Point[] vertices, double[][] matrix) {
        Point[] result = new Point[vertices.length];
        for(int i=0; i<vertices.length; i++){
            int x = (int) (matrix[0][0]*vertices[i].x + matrix[0][1]*vertices[i].y + matrix[0][2]);
            int y = (int) (matrix[1][0]*vertices[i].x + matrix[1][1]*vertices[i].y + matrix[1][2]);
            result[i]= new Point(x,y);
        }
            return result;
    }

    public static Point[] generateSamplePoint(){
        Point[] points= new Point[5];
        points[0]= new Point(50,300);
        points[1]= new Point(150,400);
        points[2]= new Point(180, 340);
        points[3]= new Point(240, 420);
        points[4]= new Point(300, 200);
        return points;
    }

    public  static Hashtable<String, Paint> generateSamplePaints(){

        Hashtable<String, Paint> my_paints = new Hashtable<>();

        Paint redPaint = new Paint();
        redPaint.setARGB(255,255,0,0);
        redPaint.setStrokeWidth(5);
        redPaint.setStyle(Paint.Style.STROKE);
        my_paints.put("RED", redPaint);

        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(5);
        my_paints.put("BLACK", blackPaint);

        return my_paints;
    }

    public  static  Path generatePath(Point[] newPoints){
        Path myLines = new Path();
        myLines.moveTo(newPoints[0].x, newPoints[0].y);
        for (int i=1; i<newPoints.length; i++)
            myLines.lineTo(newPoints[i].x, newPoints[i].y);
        myLines.close();
        return myLines;
    }

    public static void demonstrateTranslation(Canvas canvas){
        canvas.drawPath(generatePath(generateSamplePoint()), Objects.requireNonNull(generateSamplePaints().get("RED")));
        Point[] newPoints = translate(generateSamplePoint(), 20,40);
        canvas.drawPath(generatePath(newPoints), Objects.requireNonNull(generateSamplePaints().get("BLACK")));

    }

    public static void demonstrateLineGraph(Canvas canvas, int viewWidth, int viewHeight){
        int[] plotData = {11,29,10,20,12,5,31,24,21,13};

        Point[] points = new Point[plotData.length];
        int minValue= 999999;
        int maxValue=-999999;
        for (int i = 0; i<plotData.length; i++){
            points[i]= new Point(i, plotData[i]);
            minValue = Math.min(minValue, plotData[i]);
            maxValue = Math.max(maxValue, plotData[i]);}

        points = translate(points, 0, -minValue);
        double yscale = viewHeight/(double)(maxValue-minValue);
        double xscale = viewWidth/(double)(points.length-1);
        points = scale(points, xscale, yscale);

        Path path = new Path();
        path.moveTo(points[0].x, points[0].y);
        for (int i=1; i<points.length;i++)
            path.lineTo(points[i].x, points[i].y);

        canvas.drawPath(path, Objects.requireNonNull(generateSamplePaints().get("RED")));

    }



}


