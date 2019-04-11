package group_one.brightlights;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelMenuClass extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_menu);
        Button l1,l2,l3,multiBut,instruction;
        l1=(Button) findViewById(R.id.l1);
        l2=(Button) findViewById(R.id.l2);
        l3=(Button) findViewById(R.id.l3);
        multiBut = (Button) findViewById(R.id.multiBut);
        instruction=(Button)findViewById(R.id.instruction);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        multiBut.setOnClickListener(this);
        instruction.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.l1:
                Intent i=new Intent(getApplicationContext(), group_one.brightlights.NineLightModeClass.class).putExtra("level",1);
                startActivity(i);
                break;
            case R.id.l2:
                Intent j=new Intent(getApplicationContext(), group_one.brightlights.SixteenLightModeClass.class).putExtra("level",1);
                startActivity(j);
                break;
            case R.id.l3:
                Intent k=new Intent(getApplicationContext(), group_one.brightlights.TwentyFiveLights.class).putExtra("level",3);
                startActivity(k);
                break;
            case R.id.multiBut:
                Intent m=new Intent(getApplicationContext(), group_one.brightlights.LogInClass.class);
                startActivity(m);
                break;
            case R.id.instruction:
                Intent n=new Intent(getApplicationContext(), group_one.brightlights.instructPg.class);
                startActivity(n);
                break;
        }
    }
}
