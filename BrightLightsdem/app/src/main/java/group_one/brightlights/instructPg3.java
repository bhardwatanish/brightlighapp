package group_one.brightlights;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class instructPg3 extends AppCompatActivity  {

    private RelativeLayout myinstruction3=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructpg3);
//        Button Instruct1;
//        Instruct1 =(Button)findViewById(R.id.instruct1);
//        Instruct1.setOnClickListener(this);


        myinstruction3= (RelativeLayout) findViewById(R.id.id3);
        myinstruction3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getApplicationContext(), group_one.brightlights.LevelMenuClass.class);
                startActivity(intent);
                return true;

            }
        });
    }



}