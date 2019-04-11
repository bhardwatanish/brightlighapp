package group_one.brightlights;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Random;
public class DatabaseTestClass extends AppCompatActivity implements View.OnClickListener {


    private Button[][] buttons = new Button[3][3];
    private TextView textViewPlayer1;
    private int[][] color = new int[3][3];
    private int[][] clicked = new int[3][3];
    private String gameId;
    private static final String TAG = "ColorCoding";
    FirebaseDatabase database;
    DatabaseReference games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_test_class);
        database =FirebaseDatabase.getInstance();
        games = database.getReference("games");
        FirebaseDatabase.getInstance().getReference().child("games")
                .child(gameId)
                .setValue(null);

       /* FirebaseDatabase.getInstance().getReference().child("games")
                .child(gameId)
                .child("reset")
                .setValue(System.currentTimeMillis());
*/


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
                setarray(i, j);
                setboardcolor(i, j);
            }
        }
        setlevel();
        Button reset;
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(this);
        Button ss;
        ss = (Button) findViewById(R.id.ss);
        ss.setOnClickListener(this);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clicked[i][j] = 0;
            }
        }
        clicked[1][1] = 1;

    }

    @Override
    public void onClick(View v) {

        //if (((Button) v).getText().toString().equals("x")){
        switch (v.getId()) {
            case R.id.button_00:
                swap(0, 0);
                break;
            case R.id.button_01:
                swap(0, 1);
                break;
            case R.id.button_02:
                swap(0, 2);
                break;
            case R.id.button_10:
                swap(1, 0);
                break;
            case R.id.button_12:
                swap(1, 2);
                break;
            case R.id.button_11:
                swap(1, 1);
                break;
            case R.id.button_20:
                swap(2, 0);
                break;
            case R.id.button_21:
                swap(2, 1);
                break;
            case R.id.button_22:
                swap(2, 2);
                break;
            case R.id.reset:
                resetarray();
            case R.id.ss:
                int move = do_move(0, 44);
                swap(1, 1);
                Toast toast = Toast.makeText(getApplicationContext(),
                        move,
                        Toast.LENGTH_SHORT);

                toast.show();
        }

    }

    private void setboardcolor(int i, int j) {
        // Random rand = new Random();
        // color[i][j]= rand.nextInt(2) + 0;
        if (color[i][j] == 1) {
            buttons[i][j].setBackgroundColor(Color.parseColor("red"));
            buttons[i][j].setTextColor(Color.parseColor("red"));
            buttons[i][j].setText("x");

        } else {
            buttons[i][j].setBackgroundColor(Color.parseColor("#a4c639"));
            buttons[i][j].setTextColor(Color.parseColor("#a4c639"));
            buttons[i][j].setText("0");
        }

    }

    private void setarray(int i, int j) {
//        Random rand = new Random();
        //color[i][j]= rand.nextInt(2) + 0;
        color[i][j] = 1;
        setboardcolor(i, j);
    }

    private void resetarray() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                setarray(i, j);
            }
        }
        setlevel();
    }

    private void swap(int i, int j) {
        if (i - 1 >= 0) {
            if (color[i - 1][j] == 1) {
                color[i - 1][j] = 0;
            } else {
                color[i - 1][j] = 1;
            }
        }
        if (i + 1 <= 2) {
            if (color[i + 1][j] == 1) {
                color[i + 1][j] = 0;
            } else {
                color[i + 1][j] = 1;
            }
        }
        if (j - 1 >= 0) {
            if (color[i][j - 1] == 1) {
                color[i][j - 1] = 0;
            } else {
                color[i][j - 1] = 1;
            }
        }
        if (j + 1 <= 2) {
            if (color[i][j + 1] == 1) {
                color[i][j + 1] = 0;
            } else {
                color[i][j + 1] = 1;
            }
        }
        if (color[i][j] == 1) {
            color[i][j] = 0;
        } else {
            color[i][j] = 1;
        }

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {

                setboardcolor(k, l);
            }
        }

    }

    private int do_move(int min_rounds, int last_move) {
        int best_move = 0;
        if (checkforwin()) {
            return min_rounds;
        } else if (min_rounds >= 1) {
            return 100;
        } else {
            int minscore = 100;
            //int best_move;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (clicked[i][j] != 1) {


                        swap(i, j);
                        clicked[i][j] = 1;
                        int k = do_move(min_rounds++, i * 10 + j);
                        if (minscore >= k) {
                            minscore = k;
                            best_move = i * 10 + j;

                        }
                        swap(i, j);
                        clicked[i][j] = 0;

                    }
                }
            }
        }

        int best_move1 = best_move;
        return best_move1;
    }

    private boolean checkforwin() {
        int flag;
        flag = color[0][0];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (flag != color[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setlevel() {
        int k = (int) getIntent().getIntExtra("level", 0);
        //int k=Integer.parseInt(s);
        Random rand = new Random();
        for (int l = 0; l < k; l++) {
            int i = rand.nextInt(3);
            int j = rand.nextInt(3);
            swap(i, j);
        }

    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
        Log.d(TAG, "setGameId: " + gameId);
        FirebaseDatabase.getInstance().getReference().child("games")
                .child(gameId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getValue() == null) {
                            return;
                        }
                        String key = dataSnapshot.getKey();
                        if (!key.equals("reset")) {
                            int row = Integer.parseInt(key.substring(0, 1));
                            int col = Integer.parseInt(key.substring(2, 3));
                            Integer shape = dataSnapshot.getValue(Integer.class);


                        }
                    }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getValue() == null) {
                            return;
                        }
                        if (dataSnapshot.getKey().equals("reset")) {
                            resetarray();

                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }
}