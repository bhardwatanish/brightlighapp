package group_one.brightlights;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MakeLevelMenuClass extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_level_menu);
        Button easy, med, hard;
        easy=findViewById(R.id.easy);
        med=findViewById(R.id.med);
        hard=findViewById(R.id.hard);
        easy.setOnClickListener(this);
        med.setOnClickListener(this);
        hard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.easy:
                Intent i=new Intent(getApplicationContext(), group_one.brightlights.NineLightModeClass.class).putExtra("level",1);
                startActivity(i);
                break;
            case R.id.med:
                Intent j=new Intent(getApplicationContext(), group_one.brightlights.SixteenLightModeClass.class).putExtra("level",2);
                startActivity(j);
                break;
            case R.id.hard:
                Intent k=new Intent(getApplicationContext(), group_one.brightlights.TwentyFiveLights.class).putExtra("level",3);
                startActivity(k);
                break;
        }
    }
}
