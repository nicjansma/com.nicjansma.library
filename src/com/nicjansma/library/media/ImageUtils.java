package com.nicjansma.library.media;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * Image utilities.
 */
public abstract class ImageUtils
{
    //
    // Constants
    //
    /**
     * Temp storage to use in the BitmapFactory.
     */
    private static final int BITMAP_TEMP_STORAGE = 32 * 1024;

    //
    // Public functions
    //
    /**
     * Recycle's an ImageView Bitmap.
     *
     * In SDK 3, since we aren't using a BitmapFactory, we don't recycle.
     *
     * @param imageView ImageView
     */
    public static void recycleImageViewBitmap(
        final ImageView imageView)
    {
        if (imageView != null)
        {
            BitmapDrawable bd = (BitmapDrawable) imageView.getDrawable();
            Bitmap bmp = bd.getBitmap();
            bmp.recycle();
        }
    }

    /**
     * Sets an ImageView's Bitmap from a Resource ID.
     *
     * @param context Application context.
     * @param imageView ImageView
     * @param resourceId Resource ID
     */
    public static void setImageViewBitmapFromResource(
        final Context context,
        final ImageView imageView,
        final int resourceId)
    {
        // decode resource
        Bitmap bitmap = decodeImageViewBitmapFromResource(context, imageView, resourceId);
        imageView.setImageBitmap(bitmap);
        bitmap = null;
    }

    /**
     * Sets an ImageView's Bitmap from a Resource ID.
     *
     * @param context Application context.
     * @param imageView ImageView
     * @param resourceId Resource ID
     * @param reqWidth Required width
     * @param reqHeight Required height
     */
    public static void setSampledImageViewBitmapFromResource(
        final Context context,
        final ImageView imageView,
        final int resourceId,
        final int reqWidth,
        final int reqHeight)
    {
        // decode resource
        Bitmap bitmap = decodeSampledBitmapFromResource(context.getResources(), resourceId, reqWidth, reqHeight);
        imageView.setImageBitmap(bitmap);
        bitmap = null;
    }

    /**
     * Decode an ImageView's Bitmap from a Resource ID.
     *
     * @param context Application context.
     * @param imageView ImageView
     * @param resourceId Resource ID
     *
     * @return Bitmap
     */
    public static Bitmap decodeImageViewBitmapFromResource(
        final Context context,
        final ImageView imageView,
        final int resourceId)
    {
        // setup options
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = false;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inTempStorage = new byte[BITMAP_TEMP_STORAGE];

        // decode resource
        return BitmapFactory.decodeResource(context.getResources(), resourceId, opts);
    }

    /**
     * Calculates the inSampleSize for a bitmap.
     *
     * Per http://developer.android.com/training/displaying-bitmaps/load-bitmap.html#load-bitmap
     *
     * @param options BitmapFactory options
     * @param reqWidth Required width
     * @param reqHeight Required height
     *
     * @return Sample size (inSampleSize)
     */
    public static int calculateInSampleSize(
            final BitmapFactory.Options options, final int reqWidth, final int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * Decodes the sampled bitmap from a resource.
     *
     * Per http://developer.android.com/training/displaying-bitmaps/load-bitmap.html#load-bitmap
     *
     * @param res Resource
     * @param resId Resource ID
     * @param reqWidth Required width
     * @param reqHeight Required height
     *
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(final Resources res, final int resId,
            final int reqWidth, final int reqHeight)
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
