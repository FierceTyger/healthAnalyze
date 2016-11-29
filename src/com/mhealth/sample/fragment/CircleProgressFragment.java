package com.mhealth.sample.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.view.ext.circleprogress.CircleProgress;
import android.view.ext.sidemenu.interfaces.ScreenShotable;
import android.widget.SeekBar;
import com.walkingposture.R;

/**
 * Created by cyw on 10/21/2015.
 */
public class CircleProgressFragment extends Fragment implements ScreenShotable ,View.OnClickListener {
    private CircleProgress mProgressView;
    private View mStartBtn;
    private View mStopBtn;
    private View mResetBtn;

    public CircleProgressFragment(){
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.circleprogress, container, false);
        mProgressView = (CircleProgress)rootView.findViewById(R.id.progress);
        mProgressView.startAnim();
        mStartBtn = rootView.findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(this);
        mStopBtn = rootView.findViewById(R.id.stop_btn);
        mStopBtn.setOnClickListener(this);
        mResetBtn = rootView.findViewById(R.id.reset_btn);
        mResetBtn.setOnClickListener(this);

        SeekBar mSeekBar = (SeekBar)rootView.findViewById(R.id.out_seek);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float factor = seekBar.getProgress() / 100f;
                mProgressView.setRadius(factor);
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == mStartBtn) {
            mProgressView.startAnim();
        } else if (v == mStopBtn) {
            mProgressView.stopAnim();
        } else if (v == mResetBtn) {
            mProgressView.reset();
        }
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
