package com.example.bsnotes.activities.helperClasses;

import android.app.Activity;
import android.content.Intent;


public class ActivityUtilities {

    private static ActivityUtilities sActivityUtilities = null;

    public static ActivityUtilities getInstance() {
        if (sActivityUtilities == null) {
            sActivityUtilities = new ActivityUtilities();
        }
        return sActivityUtilities;
    }

    public void invokeNewActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }

    public void invokeCustomUrlActivity(Activity activity, Class<?> tClass, String pageTitle, String pageUrl, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        intent.putExtra(AppConstant.BUNDLE_KEY_TITLE, pageTitle);
        intent.putExtra(AppConstant.BUNDLE_KEY_URL, pageUrl);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }

    public void invokeItemListActiviy(Activity activity, Class<?> tClass, String categoryId, String categoryTitle, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        intent.putExtra(AppConstant.BUNDLE_KEY_ID, categoryId);
        intent.putExtra(AppConstant.BUNDLE_KEY_TITLE, categoryTitle);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }

//
//    public void invokeDetailsActiviy(Activity activity, Class<?> tClass, Contents model, boolean shouldFinish) {
//        Intent intent = new Intent(activity, tClass);
//        intent.putExtra(AppConstant.BUNDLE_KEY_ITEM, model);
//        activity.startActivity(intent);
//        if (shouldFinish) {
//            activity.finish();
//        }
//    }

}
