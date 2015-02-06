package com.nicjansma.library.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * BitmapWorker task for decoding bitmaps.
 */
public final class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap>
{
    /**
     * Image view.
     */
    private final WeakReference<ImageView> _imageViewReference;

    /**
     * Application context.
     */
    private final Context _context;

    /**
     * Resource ID.
     */
    private final int _resourceId;

    /**
     * Task.
     *
     * @param context Application context
     * @param imageView ImageView
     * @param resourceId Resource ID
     */
    public BitmapWorkerTask(final Context context, final ImageView imageView, final int resourceId)
    {
        _context = context;
        _resourceId = resourceId;

        // Use a WeakReference to ensure the ImageView can be garbage collected
        _imageViewReference = new WeakReference<ImageView>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(final Integer... params)
    {
        final ImageView imageView = _imageViewReference.get();
        return ImageUtils.decodeImageViewBitmapFromResource(_context, imageView, _resourceId);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(final Bitmap bitmap)
    {
        if (_imageViewReference != null && bitmap != null)
        {
            final ImageView imageView = _imageViewReference.get();
            if (imageView != null)
            {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}