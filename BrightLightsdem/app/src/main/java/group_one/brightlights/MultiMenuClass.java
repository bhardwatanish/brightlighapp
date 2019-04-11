package group_one.brightlights;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MultiMenuClass extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_menu);
        Button make_level, show_levels;
        make_level =(Button) findViewById(R.id.make_level);
        show_levels = (Button) findViewById(R.id.show_levels);
        make_level.setOnClickListener(this);
        show_levels.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.make_level:
                Intent l=new Intent(getApplicationContext(), group_one.brightlights.MakeLevelMenuClass.class);
                startActivity(l);
                break;
            case R.id.show_levels:
                Intent m=new Intent(getApplicationContext(), group_one.brightlights.MultiPlayerActivity.class);
                startActivity(m);
                break;
        }
    }
}
