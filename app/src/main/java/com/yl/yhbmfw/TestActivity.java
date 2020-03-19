package com.yl.yhbmfw;

import android.util.Log;

import com.yl.library.base.activity.BaseActivity;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author Yang Shihao
 * @date 2018/1/7
 */

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initMVP() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    int i = 0;
    private Observable mObservable;

    @OnClick(R.id.btn)
    public void onViewClicked() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Log.d(TAG, "subscribe: " + Thread.currentThread().getName());
                        i++;
                        if (i % 2 == 0) {
                            e.onNext("" + i);
                        } else {
                            e.onError(new RuntimeException("" + i));
                        }
                    }
                }.start();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String s) throws Exception {
                                   Log.d(TAG, "String: " + s);
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d(TAG, "Throwable: " + throwable.getMessage());
                            }
                        });
    }
}
