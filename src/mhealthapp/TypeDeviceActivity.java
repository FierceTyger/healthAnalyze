package mhealthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.walkingposture.R;

public class TypeDeviceActivity extends Activity {

    private EditText txtNameDevice;
    private String nameDevice;
    private Button mobile;
    private Button shimmer;

    static final int DEVICE_MOBILE = 1;
    static final int DEVICE_SHIMMER = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.type_device);
        // Set result CANCELED incase the user backs out
        setResult(Activity.RESULT_CANCELED);

        txtNameDevice = (EditText) findViewById(R.id.txt_name_device);

        mobile = (Button) findViewById(R.id.button_mobile);
        mobile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                nameDevice = txtNameDevice.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(ConnectFragment.TYPE_DEVICE, DEVICE_MOBILE);
                intent.putExtra(ConnectFragment.NAME_DEVICE, nameDevice);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        shimmer = (Button) findViewById(R.id.button_shimmer);
        shimmer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                nameDevice = txtNameDevice.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(ConnectFragment.TYPE_DEVICE, DEVICE_SHIMMER);
                intent.putExtra(ConnectFragment.NAME_DEVICE, nameDevice);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

}
