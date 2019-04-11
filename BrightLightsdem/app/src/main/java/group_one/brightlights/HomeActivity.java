package group_one.brightlights;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class HomeActivity extends AppCompatActivity {

   // MediaPlayer background;
    private RelativeLayout logo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        background = MediaPlayer.create(this, R.raw.background);
//        background.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        logo= (RelativeLayout) findViewById(R.id.bl);
        logo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getApplicationContext(), group_one.brightlights.LevelMenuClass.class);
                startActivity(intent);
                return true;

            }
        });


//
//        background.stop();

    }


}
// Changes to make get to open the home activity- on touch go to lvel menu