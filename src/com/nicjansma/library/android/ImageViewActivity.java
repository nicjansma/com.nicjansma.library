package com.nicjansma.library.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nicjansma.library.media.ImageUtils;


/**
 * A full-screen view of an image.
 *
 * @author Nic
 */
public class ImageViewActivity
    extends Activity
{
    //
    // Constants
    //
    /**
     * Android Intent.
     */
    public static final String INTENT = "com.nicjansma.library.action.IMAGEVIEW";

    /**
     * Activity bundle key for Resource ID.
     */
    private static final String BUNDLE_RESOURCE_ID = "resourceID";

    /**
     * Activity bundle key for Title.
     */
    private static final String BUNDLE_TITLE = "title";

    /**
     * Main image view.
     */
    private ImageView _imageView;

    /**
     * Attaches an ImageViewActivity to an ImageView.
     *
     * @param context Application context
     * @param imageView ImageView
     * @param resourceId Image resource ID
     * @param title Activity title
     * @param onClickRunnable Runnable to run when onClick is fired
     */
    public static void initializeImageView(
        final Context context,
        final ImageView imageView,
        final int resourceId,
        final String title,
        final Runnable onClickRunnable)
    {
        // set the image
        ImageUtils.setImageViewBitmapFromResource(context, imageView, resourceId);
        
        // add the onclick listener
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view)
            {
                if (onClickRunnable != null)
                {
                    onClickRunnable.run();
                }

                context.startActivity(ImageViewActivity.getIntent(resourceId, title));
            }
        });
    }

    /**
     * Gets an Intent for this Activity.
     *
     * @param resourceId Resource ID of the image
     * @param title Image title
     *
     * @return Intent to use
     */
    public static Intent getIntent(final int resourceId, final String title)
    {
        // create an intent
        Intent intent = new Intent(ImageViewActivity.INTENT);

        // store the specified resource ID
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_RESOURCE_ID, resourceId);
        bundle.putString(BUNDLE_TITLE, title);

        intent.putExtras(bundle);

        return intent;
    }

    @Override
    public final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // get the intended minifig
        Bundle bundle = this.getIntent().getExtras();

        if (bundle == null)
        {
            finish();
        }

        Context context = getApplicationContext();

        int resourceId = bundle.getInt(BUNDLE_RESOURCE_ID);
        String title = bundle.getString(BUNDLE_TITLE);

        _imageView = new ImageView(context);
        ImageUtils.setImageViewBitmapFromResource(context, _imageView, resourceId);
        setContentView(_imageView);

        // set the title
        setTitle(title);
    }

    @Override
    protected final void onDestroy()
    {
        super.onDestroy();

        ImageUtils.recycleImageViewBitmap(_imageView);
    }
}
