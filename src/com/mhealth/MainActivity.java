package com.mhealth;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ext.*;
import android.widget.Button;
import android.widget.TextView;
import com.walkingposture.R;
import communicationManager.CommunicationManager;
import communicationManager.dataStructure.ObjectData;
import communicationManager.dataStructure.ObjectData.SensorType;
import dataprocessingManager.DataProcessingManager;
import dataprocessingManager.featuresExtraction.FeaturesExtraction;
import holocircularprogressbar.HoloCircularProgressBar;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * The Class MainActivity.
 *
 * @author Pascal Welsch
 * @since 05.03.2013
 */
public class MainActivity extends Activity implements ColorPicker.OnColorChangedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    protected boolean mAnimationHasEnded = false;

    public CommunicationManager cm;
    public DataProcessingManager dpm;
    ArrayList<SensorType> sensors;
    ArrayList<SensorType> sensors1;
    Pair<ArrayList<SensorType>, String> pair;
    ArrayList<Pair<ArrayList<SensorType>, String>> sensorsAndDevices;
    private  String devicename="Mobile Device";
    public String addressess;
    Instances ins;






    private ColorPicker picker;
    private SVBar svBar;
    private OpacityBar opacityBar;
    private Button button;
    private TextView text;



    /**
     * The Switch button.
     */


    private HoloCircularProgressBar mHoloCircularProgressBar;

    private ObjectAnimator mProgressBarAnimator;
    private SatelliteMenu menu;
    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLight);

        if (getIntent() != null) {
            final Bundle extras = getIntent().getExtras();
            if (extras != null) {
                final int theme = extras.getInt("theme");
                if (theme != 0) {
                    setTheme(R.style.AppThemeLight);
                }
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holocircularprogress_activity_home);
        cm = CommunicationManager.getInstance();
        cm.CreateStorage(getApplicationContext());
        cm.addDeviceMobile(getApplicationContext(), devicename);
        sensors = new ArrayList<SensorType>();
        sensors.add(SensorType.ACCELEROMETER);
        cm.setEnabledSensors(devicename, sensors);
        cm.setStoreData(devicename, true);
        addressess = "";




            dpm = DataProcessingManager.getInstance();
            dpm.createAndSetStorage(getApplicationContext());
            sensors1 = new ArrayList<SensorType>();
            sensors1.add(SensorType.ACCELEROMETER_X);
            sensors1.add(SensorType.ACCELEROMETER_Y);
            sensors1.add(SensorType.ACCELEROMETER_Z);
            sensorsAndDevices = new ArrayList<Pair<ArrayList<SensorType>, String>>();
            pair = new Pair(sensors1, devicename);
            sensorsAndDevices.add(pair);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_X, FeaturesExtraction.FeatureType.MINIMUM);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_X, FeaturesExtraction.FeatureType.MAXIMUM);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_X, FeaturesExtraction.FeatureType.MEAN);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_X, FeaturesExtraction.FeatureType.STANDARD_DEVIATION);

            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_Y, FeaturesExtraction.FeatureType.MINIMUM);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_Y, FeaturesExtraction.FeatureType.MAXIMUM);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_Y, FeaturesExtraction.FeatureType.MEAN);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_Y, FeaturesExtraction.FeatureType.STANDARD_DEVIATION);

            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_Z, FeaturesExtraction.FeatureType.MINIMUM);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_Z, FeaturesExtraction.FeatureType.MAXIMUM);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_Z, FeaturesExtraction.FeatureType.MEAN);
            dpm.addFeature(devicename, ObjectData.SensorType.ACCELEROMETER_Z, FeaturesExtraction.FeatureType.STANDARD_DEVIATION);


        File fileArff = new File(Environment.getExternalStorageDirectory()+"/ActivityDetector/accelerationdata.arff");
        if(!fileArff.exists())
            copyArffToSdCard(getApplicationContext());
        dpm.readFile(Environment.getExternalStorageDirectory(), "/ActivityDetector/accelerationdata.arff");
        dpm.setTrainInstances();


        try{

            InputStream is = getAssets().open("logistic.model");
            ObjectInputStream ois = new ObjectInputStream(is);
            Logistic cls = (Logistic) ois.readObject();
            Log.d("mobile", cls.toString());
            dpm.loadModel(cls);
            ois.close();
        }
        catch (Exception e) {
            Log.d("mobile",e.getMessage());
            // TODO: handle exception
        }

            menu = (SatelliteMenu) findViewById(R.id.menu);
            mHoloCircularProgressBar = (HoloCircularProgressBar) findViewById(
                    R.id.holoCircularProgressBar);

            menu.setMainImage(R.drawable.ic_launcher);
            menu.setSatelliteDistance(250);
            menu.setTotalSpacingDegree(90);
            menu.setCloseItemsOnClick(true);
            menu.setExpandDuration(500);
            List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
            // items.add(new SatelliteMenuItem(4, R.drawable.ic_1));
            items.add(new SatelliteMenuItem(5, R.drawable.ic_3));
            items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
            items.add(new SatelliteMenuItem(3, R.drawable.ic_5));
            items.add(new SatelliteMenuItem(2, R.drawable.ic_6));
            items.add(new SatelliteMenuItem(1, R.drawable.ic_2));
//        items.add(new SatelliteMenuItem(5, R.drawable.sat_item));
            menu.addItems(items);

            menu.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {

                public void eventOccured(int id) {
                    switch (id) {
                        case 1:
                            if(cm.isStreaming(devicename))
                                Log.d("mobile","streaming");
                            else
                                cm.startStreaming(devicename);
                            animate(mHoloCircularProgressBar, new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationCancel(final Animator animation) {
                                    animation.end();
                                }

                                @Override
                                public void onAnimationEnd(final Animator animation) {
                                    if (!mAnimationHasEnded) {
                                        animate(mHoloCircularProgressBar, this);
                                        switchColor();
                                    } else {
                                        mAnimationHasEnded = false;
                                    }
                                }

                                @Override
                                public void onAnimationRepeat(final Animator animation) {
                                }

                                @Override
                                public void onAnimationStart(final Animator animation) {
                                }
                            });

                            break;
                        case 2:
                            break;
                        case 3:
                            if(!cm.isStreaming(devicename))
                                Log.d("mobile","stop");
                            else
                            cm.stopStreaming(devicename);
                            mAnimationHasEnded = true;
                            mProgressBarAnimator.cancel();
                            break;
                        case 4:
                            String result = "";
                            String nameMetaTable = cm.getDevice(devicename).getMetadataTableName();
                            dpm.retrieveInformationByLastID(nameMetaTable, sensorsAndDevices);
                            dpm.feature_extraction(DataProcessingManager.DataType.Raw, false, false);
                            for (Double d : dpm.featuresVector) {
                                Log.d("featuresVector", d.toString());
                                result = result + "," + d;
                            }
                            ins=dpm.featureVectorToInstances(dpm.featuresVector);
                            Log.d("mobile", ins.firstInstance().toString());

                            double label [] = new double[2];
                            label=dpm.classifyInstanceToFM(ins.firstInstance());
                            text.setText("your featuresVector data :"+" "+label[0]+"  "+label[1]);
                            text.setTextColor(picker.getColor());
                            picker.setOldCenterColor(picker.getColor());
                            break;
                        case 5:
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, text.getText());
                            sendIntent.setType("text/plain");
                            startActivity(Intent.createChooser(sendIntent, "Share with"));
                            break;

                    }
                    Log.d("sat", "Clicked on " + id);
                }
            });


            picker = (ColorPicker) findViewById(R.id.picker);
            svBar = (SVBar) findViewById(R.id.svbar);
            opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
            //button = (Button) findViewById(R.id.button1);
            text = (TextView) findViewById(R.id.textView1);

            picker.addSVBar(svBar);
            picker.addOpacityBar(opacityBar);
            picker.setOnColorChangedListener(this);


        }

    @Override
    public void onColorChanged(int color) {
        //gives the color when it's changed.
    }


    /**
     * generates random colors for the ProgressBar
     */
    protected void switchColor() {
        Random r = new Random();
        int randomColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));

        mHoloCircularProgressBar.setProgressColor(randomColor);


        randomColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));

        mHoloCircularProgressBar.setProgressBackgroundColor(randomColor);

    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.circular_progress_bar_sample, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_switch_theme:
                switchTheme();
                break;

            default:
                Log.w(TAG, "couldn't map a click action for " + item);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Switch theme.
     */
    public void switchTheme() {

        final Intent intent = getIntent();
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final int theme = extras.getInt("theme", -1);
            if (theme == R.style.AppThemeLight) {
                getIntent().removeExtra("theme");
            } else {
                intent.putExtra("theme", R.style.AppThemeLight);
            }
        } else {
            intent.putExtra("theme", R.style.AppThemeLight);
        }
        finish();
        startActivity(intent);
    }

    /**
     * Animate.
     *
     * @param progressBar the progress bar
     * @param listener    the listener
     */
    private void animate(final HoloCircularProgressBar progressBar,
            final Animator.AnimatorListener listener) {
        final float progress = (float) (Math.random() * 2);
        int duration = 3000;
        animate(progressBar, listener, progress, duration);
    }
    private void animate(final HoloCircularProgressBar progressBar, final Animator.AnimatorListener listener,
            final float progress, final int duration) {

        mProgressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
        mProgressBarAnimator.setDuration(duration);

        mProgressBarAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });
        if (listener != null) {
            mProgressBarAnimator.addListener(listener);
        }
        mProgressBarAnimator.reverse();
        mProgressBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressBar.setProgress((Float) animation.getAnimatedValue());
            }
        });
        progressBar.setMarkerProgress(progress);
        mProgressBarAnimator.start();
    }

    public void copyArffToSdCard(Context ctx) {

        InputStream is = ctx.getResources().openRawResource(R.raw.accelerationdata);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192);

        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsolutePath() +"/ActivityDetector");
        directory.mkdirs();
        File file = new File(directory, "accelerationdata.arff");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        try {
            String text;
            while (true) {
                text = br.readLine();
                if (text == null)
                    break;
                text += "\n";
                osw.write(text);
            }
            isr.close();
            is.close();
            br.close();
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
