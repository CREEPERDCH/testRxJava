package com.thaifintech.testrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "dxq";

    @BindView(R.id.tvShow)
    TextView mTvShow;
    @BindView(R.id.btnChange)
    Button mBtnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnChange)
    public void onViewClicked() {
        changeView();
    }

    private void changeView() {
        //被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello Android !");
                e.onComplete();
            }
        });
        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
                mTvShow.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "change charsequence failed !", Toast
                        .LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "change charsequence successful !", Toast
                        .LENGTH_SHORT).show();
            }
        };
        //订阅
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
