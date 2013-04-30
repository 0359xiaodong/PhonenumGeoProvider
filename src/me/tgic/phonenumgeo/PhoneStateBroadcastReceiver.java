package me.tgic.phonenumgeo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created with IntelliJ IDEA.
 * User: tgic
 * Date: 5/1/13
 * Time: 3:21 AM
 */
public class PhoneStateBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new DrawLocationPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE);
    }
}
