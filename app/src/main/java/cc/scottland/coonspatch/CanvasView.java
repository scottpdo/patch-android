package cc.scottland.coonspatch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cc.scottland.coonspatch.geometry.Bezier;
import cc.scottland.coonspatch.geometry.Point;
import cc.scottland.coonspatch.geometry.Surface;

/**
 * Created by scottdonaldson on 7/13/17.
 */

public class CanvasView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    int bg = Color.BLACK;
    int d = 10; // number of divisions in surface

    Surface surface = new Surface();
    Camera camera = new Camera(
      new Point(0.5f, 0.5f, -2), new Point()
    );

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        // draw background
        paint.setColor(bg);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        drawBoundaryCurves(canvas, paint);
        drawInteriorCurves(canvas, paint);
    }

    private void init() {
        invalidate();
        requestLayout();
    }

    private float f(Point pt) {
        return pt.getX() / pt.getZ();
    }

    private float g(Point pt) {
        return pt.getY() / pt.getZ();
    }

    private Point transform(Point pt, Canvas canvas) {

        Point p = pt.clone();
        Point loc = camera.getLocation();

        p.moveX(-loc.getX());
        p.moveY(-loc.getY());
        p.moveZ(-loc.getZ());

        int dim = Math.min(canvas.getWidth(), canvas.getHeight());

        float x = canvas.getWidth() / 2 + dim * f(p);
        float y = canvas.getHeight() / 2 - dim * g(p);

        return new Point(x, y, 0);
    }

    private void drawLine(Point start, Point end, Canvas canvas, Paint paint, int strokeWidth) {

        start = transform(start, canvas);
        end = transform(end, canvas);

        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.WHITE);

        canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);

    }

    private void drawBezier(Bezier crv, Canvas canvas, Paint paint, int strokeWidth) {

        Point p0 = transform(crv.getP0(), canvas);
        Point p3 = transform(crv.getP3(), canvas);

        List<Point> pts = new ArrayList<Point>();

        pts.add(p0);
        for (int i = 1; i < d; i++) {
            float t = (float) i;
            t /= d;
            pts.add(transform(crv.evaluate(t), canvas));
        }
        pts.add(p3);

        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.WHITE);

        for (int i = 0; i < pts.size() - 1; i++) {
            Point a = pts.get(i);
            Point b = pts.get(i + 1);
            canvas.drawLine(a.getX(), a.getY(), b.getX(), b.getY(), paint);
        }
    }

    private void drawBoundaryCurves(Canvas canvas, Paint paint) {
        drawBezier(surface.getU0(), canvas, paint, 4);
        drawBezier(surface.getU1(), canvas, paint, 4);
        drawBezier(surface.getV0(), canvas, paint, 4);
        drawBezier(surface.getV1(), canvas, paint, 4);
    }

    private void drawInteriorCurves(Canvas canvas, Paint paint) {

        float step = 1.f / d;

        for (float u = 0; u < 1; u += step) {
            for (float v = 0; v < 1; v += step) {

                Point o = surface.patch(u, v);
                Point p = surface.patch(Utils.clamp(u + step, 0, 1), v);
                Point q = surface.patch(u, Utils.clamp(v + step, 0, 1));

                drawLine(o, p, canvas, paint, 2);
                drawLine(o, q, canvas, paint, 2);
            }
        }
    }

    public void onTouchStart(MotionEvent e) {
    }

    public void onTouchEnd(MotionEvent e) {
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_5:
            default:
                return super.onKeyUp(keyCode, event);
        }
    }
}