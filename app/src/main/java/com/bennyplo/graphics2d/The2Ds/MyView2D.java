package com.bennyplo.graphics2d;
//
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import static com.bennyplo.graphics2d.MyMethods2D.*;

/**
 * Created by benlo on 09/05/2018.
 */

public class MyView2D extends View {
    private Paint mPaint;
    private Path mPath;


    public MyView2D(Context context) {
        super(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Add your drawing code here
        //drawRedRectangle(canvas);
        //drawGreenPolygon(canvas);
        //drawSolidPolygon(canvas);
        //drawGradientColor(canvas);
        //drawGradientSpecial(canvas);
        //demonstrateTranslation(canvas);
        int viewWidth = getResources().getDisplayMetrics().widthPixels;
        int viewHeight = getResources().getDisplayMetrics().heightPixels-70;
                //70 is the height of the bottom control bar
        demonstrateLineGraph(canvas, viewWidth, viewHeight);
    }
}
