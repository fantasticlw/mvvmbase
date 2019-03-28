package cn.ruicz.basecore.http;

import android.app.Activity;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import cn.ruicz.basecore.utils.MaterialDialogUtils;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import cn.ruicz.basecore.utils.MaterialDialogUtils;

/**
 * Created by CLW on 2016/7/8.
 * 带弹窗的订阅者
 */
public class DialogObserver<T> extends SimpleObserver<T> {
    protected MaterialDialog mWaitDialog;

    public DialogObserver(Activity activity, Consumer<T> onNext) {
        super(activity, onNext);
        initialize(activity);
    }

    public DialogObserver(Activity activity, Consumer<T> onNext, Action onCompleted) {
        super(activity, onNext, onCompleted);
        initialize(activity);
    }

    public DialogObserver(Activity activity, Consumer<T> onNext, Consumer onError) {
        super(activity, onNext, onError);
        initialize(activity);
    }

    public DialogObserver(Activity activity, Consumer<T> onNext,Action onComplete,Consumer<Throwable> onError ) {
        super(activity, onNext,onComplete ,onError );
        initialize(activity);
    }

    public DialogObserver(Activity activity) {
        super(activity);
        initialize(activity);
    }

    private void initialize(Context context) {
//        mWaitDialog = DialogManager.getWaitDialog(context, "请稍候");
        mWaitDialog = MaterialDialogUtils.showIndeterminateProgressDialog(context, "请稍后", false).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((Activity)getContext()).runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (!mWaitDialog.isShowing()) mWaitDialog.show();
                    }
                }
        );
    }


    @Override
    public void onComplete() {
        super.onComplete();
        ((Activity)getContext()).runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (mWaitDialog.isShowing()) mWaitDialog.dismiss();
                    }
                }
        );
    }
}
