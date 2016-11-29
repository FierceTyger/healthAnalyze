package com.mhealth;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.mhealth.sample.*;
import com.walkingposture.R;

public class WelcomeActivity extends Activity {
    /** Called when the activity is first created. */

	private Handler mHandler;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
       
        initView();
    }
    
    
    public void initView()
    {
    	mHandler = new Handler();
    	mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				goLoginActivity();
			}
    	}, 3000);
    }
    /**
     * cx
     * 修改主题跳转对象
     * */
    /*
    public void goLoginActivity()
    {
    	Intent intent = new Intent();
    	intent.setClass(this, MainActivity.class);
    	startActivity(intent);
    	finish();
    }*/
    public void goLoginActivity()
    {
        Intent intent = new Intent();
        intent.setClass(this, com.mhealth.sample.MainActivity.class);
        startActivity(intent);
        finish();
    }
}