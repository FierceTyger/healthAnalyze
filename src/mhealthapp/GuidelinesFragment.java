package mhealthapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ext.sidemenu.interfaces.ScreenShotable;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import systemManager.SystemManager;

public class GuidelinesFragment extends YouTubePlayerSupportFragment implements ScreenShotable {

    SystemManager sm;
    YouTubePlayerView youTubeView;
    String URL_VIDEO = "CaA-k1l0xa4";
    String KEY_DEVELOPER = "AIzaSyBIIs0u0NXhsZguv8nCNvSzUmflTt7K1Ek";
    static final int REQUEST_YOUTUBE_ACTIVITY = 1;

    public GuidelinesFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Intent guideIntent = new Intent(getActivity(), YoutubeActivity.class);
        startActivityForResult(guideIntent, REQUEST_YOUTUBE_ACTIVITY);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_YOUTUBE_ACTIVITY:
                if (resultCode == Activity.RESULT_CANCELED) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

                break;
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
