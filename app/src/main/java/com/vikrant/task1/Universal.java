package com.vikrant.task1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

public class Universal {
    public static String userID = null;
    public static String userName = null;

    public void OpenGivenActivity(Context context, Class nextClass) {
        Intent intent = new Intent(context, nextClass);
        context.startActivity(intent);
    }

    public void OpenGivenActivity(Context context, Class nextClass, Boolean finish) {
        Intent intent = new Intent(context, nextClass);
        context.startActivity(intent);
        if (finish) {
            ((Activity) context).finish();
        }
    }
    public Dialog SetLoadingBar(Context callingActivity) {
        Dialog loadingDialog;
        loadingDialog = new Dialog(callingActivity);
        loadingDialog.setContentView(R.layout.g1_progressbar);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.show();

        return loadingDialog;
    }
}
