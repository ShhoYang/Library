package com.yl.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yl.library.R;

/**
 * @author Yang Haoshi
 */

public class BottomDialog extends Dialog {

    public BottomDialog(Context context, int layoutId) {
        super(context, R.style.BottomDialog);
        setContentView(layoutId);
        createDialog();
    }

    public BottomDialog(Context context, View view) {
        super(context, R.style.BottomDialog);
        setContentView(view);
        createDialog();
    }

    private void createDialog() {
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialogAnimation);

        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        //按比例设置
        /*WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        params.width = (int) (display.getWidth() * 0.6);
        params.height = (int) (display.getHeight() * 0.7);
        window.setAttributes(params);*/
    }
}
