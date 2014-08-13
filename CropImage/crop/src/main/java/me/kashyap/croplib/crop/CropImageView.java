package me.kashyap.croplib.crop;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created on 8/12/2014.
 */
public class CropImageView extends ImageView {

    private AspectRatio aspectRatio;
    private Drawable cropDrawable;
    private float optimumTouchSize;

    private RectF topRect = new RectF();
    private RectF leftRect = new RectF();
    private RectF rightRect = new RectF();
    private RectF bottomRect = new RectF();
    private Paint borderPaint = new Paint();
    private RectF actualImageRect;

    public CropImageView(Context context) {
        this(context, null);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        optimumTouchSize = convertDpToPixel(48, getContext());
        borderPaint.setColor(0xff999999);
        borderPaint.setStyle(Paint.Style.FILL);
    }

    public void setCropDrawable(Drawable cropDrawable) {
        this.cropDrawable = cropDrawable;
    }

    public void setCropDrawable(int cropDrawableId) {
        setCropDrawable(getResources().getDrawable(cropDrawableId));
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setActualImageRect();
        setBorderRects(w, h);
    }

    private void setBorderRects(int w, int h) {
        topRect.set(0, 0, w, 0);
        leftRect.set(0, 0, 0, h);
        rightRect.set(w, 0, w, h);
        bottomRect.set(0, h, w, h);
    }

    private void setActualImageRect() {
        Rect drawableBound = new Rect();
        getDrawable().copyBounds(drawableBound);
        actualImageRect = new RectF(drawableBound);
        getImageMatrix().mapRect(actualImageRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(cropDrawable != null) {
            cropDrawable.draw(canvas);
        }
        canvas.drawRect(leftRect, borderPaint);
        canvas.drawRect(topRect, borderPaint);
        canvas.drawRect(rightRect, borderPaint);
        canvas.drawRect(bottomRect, borderPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
