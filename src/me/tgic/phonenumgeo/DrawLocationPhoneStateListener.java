package me.tgic.phonenumgeo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: tgic
 * Date: 5/1/13
 * Time: 2:40 AM
 */
public class DrawLocationPhoneStateListener extends PhoneStateListener {

    private TextView textView;
    private final Context context;
    private WindowManager windowManager;

    public DrawLocationPhoneStateListener(Context context) {
        this.context = context;
    }


    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:

                windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();


                // Copy from toast
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                params.format = PixelFormat.TRANSLUCENT;
                params.windowAnimations = android.R.style.Animation_Toast;
                params.type = WindowManager.LayoutParams.TYPE_TOAST;

                // steal from toast
                Toast toast = new Toast(context);
                params.gravity = toast.getGravity() ;
                params.x = toast.getXOffset();
                params.y = toast.getYOffset();

                textView = new TextView(context.getApplicationContext());
                String location = FilePhonenumDataLoader.getInstance(context).searchGeocode(incomingNumber);

                if (location == null) {
                    location = "Unknown Location";
                }

                textView.setText(location);
                textView.setBackground(context.getResources().getDrawable(android.R.drawable.toast_frame));


                windowManager.addView(textView, params);

                break;
            case TelephonyManager.CALL_STATE_IDLE:

                if (windowManager != null) {
                    windowManager.removeView(textView);
                }

                break;
            default:
                break;
        }
    }
}
