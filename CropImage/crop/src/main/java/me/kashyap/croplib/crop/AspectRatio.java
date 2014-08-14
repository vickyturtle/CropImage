package me.kashyap.croplib.crop;

/**
 * Created on 8/12/2014.
 */

/**
 * Class for maintaining aspect ratio when dragging or resizing crop area
 */
public class AspectRatio {

    private int widthRatio;
    private int heightRatio;
    float heightMultiplier;
    float widthMultiplier;

    /**
     * Set Aspect Ratio for {@link me.kashyap.croplib.crop.CropImageView}
     * set 0 for free cropping
     *
     * @param widthRatio  width ratio for cropped image
     * @param heightRatio height ratio for cropped image
     */
    public AspectRatio(int widthRatio, int heightRatio) {
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
        heightMultiplier = (float) heightRatio / widthRatio;
        widthMultiplier = (float) widthRatio / heightRatio;
    }

    public int getHeightRatio() {
        return heightRatio;
    }

    public int getWidthRatio() {
        return widthRatio;
    }
}
