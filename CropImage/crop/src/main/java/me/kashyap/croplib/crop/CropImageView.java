package me.kashyap.croplib.crop;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private Rect cropRect = new Rect();

    private float lastTouchX;
    private float lastTouchY;
    private int anchorPoint;

    public CropImageView(Context context) {
        this(context, null);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        optimumTouchSize = convertDpToPixel(48, getContext());
        borderPaint.setColor(0x99999999);
        borderPaint.setStyle(Paint.Style.FILL);
    }

    public void setCropDrawable(Drawable cropDrawable) {
        this.cropDrawable = cropDrawable;
        setActualImageRect();
    }

    public void setCropDrawable(int cropDrawableId) {
        setCropDrawable(getResources().getDrawable(cropDrawableId));
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("cropImageView", "image drawable null :" + (getDrawable() == null));
        setActualImageRect();
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
        setCropRect();
    }

    private void setCropRect() {
        cropRect.set((int) leftRect.right, (int) topRect.bottom, (int) rightRect.left, (int) bottomRect.top);
        cropDrawable.setBounds(cropRect);
    }

    private void updateBorderRects() {
        leftRect.right = cropRect.left;
        topRect.bottom = cropRect.top;
        rightRect.left = cropRect.right;
        bottomRect.top = cropRect.bottom;

        topRect.left = bottomRect.left = cropRect.left;
        topRect.right = bottomRect.right = cropRect.right;
        cropDrawable.setBounds(cropRect);
    }

    private void setActualImageRect() {
        Rect drawableBound = new Rect();
        getDrawable().copyBounds(drawableBound);
        actualImageRect = new RectF(drawableBound);
        getImageMatrix().mapRect(actualImageRect);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        setActualImageRect();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setActualImageRect();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setActualImageRect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (cropDrawable != null) {
            cropDrawable.draw(canvas);
        }
        canvas.drawRect(leftRect, borderPaint);
        canvas.drawRect(topRect, borderPaint);
        canvas.drawRect(rightRect, borderPaint);
        canvas.drawRect(bottomRect, borderPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                calculateAnchorPoint();
                return true;

            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - lastTouchX;
                float dy = event.getY() - lastTouchY;
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                onActionMove(dx, dy);
                break;


        }
        return super.onTouchEvent(event);
    }

    private void onActionMove(float dx, float dy) {
        switch (anchorPoint) {
            case AnchorPoint.NONE:
//                if(dx + cropRect.left > leftRect.left) {
                    cropRect.left += dx;
//                } else if (dx + cropRect.right < rightRect.right) {
                    cropRect.right += dx;
//                }

//                if(dy + cropRect.top > topRect.top) {
                    cropRect.top += dy;
//                } else if(dy + cropRect.bottom < bottomRect.bottom){
                    cropRect.bottom += dy;
//                }
                updateBorderRects();
                invalidate();
                break;
            case AnchorPoint.LEFT:
                break;
            case AnchorPoint.TOP:
                break;
            case AnchorPoint.BOTTOM:
                break;
            case AnchorPoint.RIGHT:
                break;
            case AnchorPoint.TOP | AnchorPoint.LEFT:
                break;
            case AnchorPoint.TOP | AnchorPoint.RIGHT:
                break;
            case AnchorPoint.BOTTOM | AnchorPoint.LEFT:
                break;
            case AnchorPoint.BOTTOM | AnchorPoint.RIGHT:

                break;

        }
    }

    private void calculateAnchorPoint() {
        anchorPoint = AnchorPoint.NONE;
        if (Math.abs(cropRect.left - lastTouchX) < optimumTouchSize)
            anchorPoint = anchorPoint | AnchorPoint.LEFT;
        else if (Math.abs(cropRect.right - lastTouchX) < optimumTouchSize)
            anchorPoint = anchorPoint | AnchorPoint.RIGHT;
        else if (Math.abs(cropRect.top - lastTouchY) < optimumTouchSize)
            anchorPoint = anchorPoint | AnchorPoint.TOP;
        else if (Math.abs(cropRect.bottom - lastTouchY) < optimumTouchSize)
            anchorPoint = anchorPoint | AnchorPoint.BOTTOM;

    }
}
