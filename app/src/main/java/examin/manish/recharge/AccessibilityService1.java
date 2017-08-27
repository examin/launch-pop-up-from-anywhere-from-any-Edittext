package examin.manish.recharge;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class AccessibilityService1 extends android.accessibilityservice.AccessibilityService {

    static final String TAG = "RecorderService";

    private String getEventType(AccessibilityEvent event) {
        switch (event.getEventType()) {
           case AccessibilityEvent.TYPE_VIEW_SELECTED:
                return "TYPE_View_Selected";
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                return "TYPE_VIEW_TEXT_CHANGED";
        }
        return "default";
    }

    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(!getEventType(event).equals("default")) {
            Log.v(TAG, String.format(
                    "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                    getEventType(event), event.getClassName(), event.getPackageName(),
                    event.getEventTime(), getEventText(event)));
            if(getEventText(event).equals("Rs@50")||getEventText(event).equals("@100")||getEventText(event).equals("@50")||getEventText(event).equals("Rs @20")||getEventText(event).equals("Rs @10")||getEventText(event).equals("Rs @100")||getEventText(event).equals("Rs @1000"))
            {
                Intent dialogIntent = new Intent(this, RechargeActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.v(TAG, "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.v(TAG, "onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }

}
