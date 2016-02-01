package in.ashwanik.retroclient.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import in.ashwanik.retroclient.R;

/**
 * The type Transparent progress dialog.
 */
public class TransparentProgressDialog extends Dialog {

    private CircularProgressDrawable mDrawable;

    /**
     * Instantiates a new transparent progress dialog.
     *
     * @param context the context
     * @param color   the color
     */
    public TransparentProgressDialog(Context context, int color) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        mDrawable = new CircularProgressDrawable(color, 7);

        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageDrawable(mDrawable);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100, 100);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(lp);

        imageView.requestLayout();
        relativeLayout.addView(imageView);

        this.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        setContentView(relativeLayout, rlp);
    }

    /* (non-Javadoc)
     * @see android.app.Dialog#show()
     */
    @Override
    public void show() {
        super.show();
        mDrawable.start();
    }

}
