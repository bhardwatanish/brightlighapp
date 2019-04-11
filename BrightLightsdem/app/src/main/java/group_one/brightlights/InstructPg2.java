package group_one.brightlights;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class InstructPg2 extends AppCompatActivity {



    private RelativeLayout myinstruction2=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructpg2);
//        Button Instruct1;
//        Instruct1 =(Button)findViewById(R.id.instruct1);
//        Instruct1.setOnClickListener(this);


        myinstruction2= (RelativeLayout) findViewById(R.id.ID2);
        myinstruction2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getApplicationContext(), group_one.brightlights.instructPg3.class);
                startActivity(intent);
                return true;

            }
        });
    }




}