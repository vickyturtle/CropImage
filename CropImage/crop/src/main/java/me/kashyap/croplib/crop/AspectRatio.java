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

    /**
     * Set Aspect Ratio for {@link me.kashyap.croplib.crop.CropImageView}
     * set 0 for free cropping
     *
     * @param widthRatio  width ratio fro cropped image
     * @param heightRatio height ratio for cropped image
     */
    public AspectRatio(int widthRatio, int heightRatio) {
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
    }
}
