package group_one.brightlights;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TwentyFiveLights extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[5][5];
    private TextView textViewPlayer1;
    private int[][] color= new int[5][5];
    private int[][] clicked = new int[5][5];
    private TextView scores;
    private int count = 0;
    private String gameId = " ";
    private static final String TAG = "ColorCoding";
    boolean multiplayer = false;
    private int[][] NonMuiplayerReset = new int[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase.getInstance().getReference().child("games")
                .child(gameId)
                .setValue(null);

        FirebaseDatabase.getInstance().getReference().child("games")
                .child(gameId)
                .child("RESET")
                .setValue(System.currentTimeMillis());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.twenty_five_light_mode);

      //  textViewPlayer1 = findViewById(R.id.text1);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
                setarray(i, j, 0);
                setboardcolor(i,j);
            }
        }

        setlevel();
        scores = findViewById(R.id.score);
        Button reset;
        reset=(Button) findViewById(R.id.reset);
        reset.setOnClickListener(this);
        Button ss;
        ss=(Button) findViewById(R.id.ss);
        ss.setOnClickListener(this);


        gameId = getIntent().getExtras().getString("gameId");
        if (gameId == null) {
            Random rand = new Random();
            gameId = "" + (rand.nextInt(8999999) + 1000000);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    NonMuiplayerReset[i][j] = color[i][j];
                }
            }
        }else{
            System.out.println("Multiplayer");
            multiplayer = true;
            setGameId(gameId);
        }
    }

    @Override
    public void onClick(View v) {
        //if (((Button) v).getText().toString().equals("x")){
        switch(v.getId()){
            case R.id.button_00:
                swap(0,0);
                break;
            case R.id.button_01:
                swap(0,1);
                break;
            case R.id.button_02:
                swap(0,2);
                break;
            case R.id.button_03:
                swap(0,3);
                break;
            case R.id.button_04:
                swap(0,4);
                break;
            case R.id.button_10:
                swap(1,0);
                break;
            case R.id.button_11:
                swap(1,1);
                break;
            case R.id.button_12:
                swap(1,2);
                break;
            case R.id.button_13:
                swap(1,3);
                break;
            case R.id.button_14:
                swap(1,4);
                break;
            case R.id.button_20:
                swap(2,0);
                break;
            case R.id.button_21:
                swap(2,1);
                break;
            case R.id.button_22:
                swap(2,2);
                break;
            case R.id.button_23:
                swap(2,3);
                break;
            case R.id.button_24:
                swap(2,4);
                break;
            case R.id.button_30:
                swap(3,0);
                break;
            case R.id.button_31:
                swap(3,1);
                break;
            case R.id.button_32:
                swap(3,2);
                break;
            case R.id.button_33:
                swap(3,3);
                break;
            case R.id.button_34:
                swap(3,4);
                break;
            case R.id.button_40:
                swap(4,0);
                break;
            case R.id.button_41:
                swap(4,1);
                break;
            case R.id.button_42:
                swap(4,2);
                break;
            case R.id.button_43:
                swap(4,3);
                break;
            case R.id.button_44:
                swap(4,4);
                break;
            case R.id.reset:
                resetarray();
                count = -1;
                break;
            case R.id.ss:
                if(!checkforwin()) {
                    int move = do_move();

                    count += 2;

                    int x = move / 5;
                    int y = move - (x * 5);
                    swap(x, y);
                }
                break;
        }
        count++;
        scores.setText("MOVES: "+ count);
        FirebaseDatabase.getInstance().getReference().child("games")
                .child(gameId)
                .child("MOVES")
                .setValue(count);

    }

    private void saveboardstate(int col, int row) {
        FirebaseDatabase.getInstance().getReference().child("games")
                .child(gameId)
                .child(col + "_" + row)
                .setValue("" + color[col][row]);
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

    private void setarray(int i, int j, int value) {
//        Random rand = new Random();
        //color[i][j]= rand.nextInt(2) + 0;
        color[i][j] = value;
        setboardcolor(i, j);
    }

    private void resetarray() {
        System.out.println("reseting array");

        if(!multiplayer) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    setarray(i, j, 0);

                    if(!multiplayer) {
                        setarray(i,j,NonMuiplayerReset[i][j]);
                        saveboardstate(i, j);
                    }
                }
            }
        }else{
            setGameId(gameId);
        }
        count = 0;
    }

    private void swap(int i,int j){
        if(i-1>=0){
            if (color[i-1][j]==1){
                color[i-1][j]=0;
            }
            else{
                color[i-1][j]=1;
            }
        }
        if(i+1<=4){
            if (color[i+1][j]==1){
                color[i+1][j]=0;
            }
            else{
                color[i+1][j]=1;
            }
        }
        if(j-1>=0){
            if (color[i][j-1]==1){
                color[i][j-1]=0;
            }
            else{
                color[i][j-1]=1;
            }
        }
        if(j+1<=4){
            if (color[i][j+1]==1){
                color[i][j+1]=0;
            }
            else{
                color[i][j+1]=1;
            }
        }
        if (color[i][j]==1){
            color[i][j]=0;
        }
        else{
            color[i][j]=1;
        }

        for (int k = 0; k < 5; k++) {
            for (int l = 0; l < 5; l++) {

                setboardcolor(k,l);
                if(!multiplayer) {
                    saveboardstate(k, l);
                }
            }
        }

    }

    private static int Reduction_steps[][]= {
            {2,1,0},
            {6,1,0},
            {3,2,1},
            {6,2,0},
            {7,2,0},
            {4,3,0},
            {6,3,0},
            {7,3,0},
            {8,3,0},
            {5,4,0},
            {6,4,0},
            {7,4,0},
            {9,4,0},
            {6,5,1},
            {7,5,0},
            {9,5,0},
            {10,5,0},
            {7,6,0},
            {8,6,0},
            {9,6,0},
            {11,6,0},
            {8,7,0},
            {9,7,0},
            {10,7,0},
            {11,7,0},
            {12,7,0},
            {9,8,1},
            {11,8,0},
            {12,8,0},
            {13,8,0},
            {10,9,0},
            {11,9,0},
            {13,9,0},
            {14,9,0},
            {11,10,1},
            {13,10,0},
            {15,10,0},
            {14,11,0},
            {15,11,0},
            {16,11,0},
            {13,12,0},
            {14,12,0},
            {17,12,0},
            {16,13,1},
            {17,13,0},
            {18,13,0},
            {17,14,1},
            {19,14,0},
            {18,15,1},
            {19,15,0},
            {20,15,0},
            {18,16,0},
            {19,16,0},
            {20,16,0},
            {21,16,0},
            {18,17,0},
            {20,17,0},
            {21,17,0},
            {22,17,0},
            {21,18,0},
            {23,18,0},
            {20,19,1},
            {22,19,0},
            {23,19,0},
            {21,20,0},
            {22,20,0},
            {23,21,0},
            {23,22,1},
            {22,23,0},
            {21,23,0},
            {20,23,0},
            {19,23,0},
            {15,23,0},
            {20,22,0},
            {14,22,0},
            {19,21,0},
            {15,21,0},
            {14,21,0},
            {13,21,0},
            {19,20,0},
            {18,20,0},
            {18,19,0},
            {17,19,0},
            {15,19,0},
            {16,18,0},
            {15,18,0},
            {14,18,0},
            {16,17,0},
            {14,17,0},
            {13,17,0},
            {12,17,0},
            {15,16,0},
            {13,16,0},
            {10,16,0},
            {14,15,0},
            {13,15,0},
            {11,15,0},
            {12,14,0},
            {10,14,0},
            {8,14,0},
            {12,13,0},
            {11,13,0},
            {10,13,0},
            {9,13,0},
            {9,12,0},
            {8,12,0},
            {7,12,0},
            {10,11,0},
            {9,11,0},
            {7,11,0},
            {5,11,0},
            {8,10,0},
            {7,10,0},
            {6,10,0},
            {8,9,0},
            {7,9,0},
            {6,9,0},
            {5,9,0},
            {4,9,0},
            {7,8,0},
            {5,8,0},
            {2,8,0},
            {6,7,0},
            {5,7,0},
            {4,7,0},
            {3,7,0},
            {4,6,0},
            {3,6,0},
            {1,6,0},
            {4,5,0},
            {2,4,0},
            {2,3,0},
            {1,2,0}
    };

    private int do_move(){

        int Array_len = Reduction_steps.length;
        int Light_len = 25;
        int Loop_size = 5;
        int lights_up_on[] = new int[Light_len];
        int lights_up_off[] = new int[Light_len];
        int numb_move_lights_off = 0;
        int numb_move_lights_on = 0;
        int temp1,temp2;

        //Initializing the lights on and off arrays
        //So it will calculate shortest distance both to fully off and fully on lights
        for(int x=0;x<Loop_size;x++) {
            for(int y=0;y<Loop_size;y++) {

                lights_up_on[(x*Loop_size)+y] = color[x][y];

                if(color[x][y] == 0){
                    lights_up_off[(x*Loop_size)+y] = 1;
                }else{
                    lights_up_off[(x*Loop_size)+y] = 0;
                }
            }
        }

        for(int y = 0; y < Array_len ; y++)
        {
            if(Reduction_steps[y][2] == 0)
            {
                //Lights on
                lights_up_on[(Reduction_steps[y][0] - 1)] = lights_up_on[(Reduction_steps[y][0] - 1)] - lights_up_on[(Reduction_steps[y][1] - 1)];
                if(lights_up_on[(Reduction_steps[y][0] - 1)] == -1) {
                    lights_up_on[(Reduction_steps[y][0] - 1)] = 1;
                }

                //Lights off
                lights_up_off[(Reduction_steps[y][0] - 1)] = lights_up_off[(Reduction_steps[y][0] - 1)] - lights_up_off[(Reduction_steps[y][1] - 1)];
                if(lights_up_off[(Reduction_steps[y][0] - 1)] == -1) {
                    lights_up_off[(Reduction_steps[y][0] - 1)] = 1;
                }

            }
            else
            {
                //Lights on
                temp1 = lights_up_on[(Reduction_steps[y][0] - 1)];
                lights_up_on[(Reduction_steps[y][0] - 1)] = lights_up_on[(Reduction_steps[y][1] - 1)];
                lights_up_on[(Reduction_steps[y][1] - 1)] = temp1;

                //Light off
                temp2 = lights_up_off[(Reduction_steps[y][0] - 1)];
                lights_up_off[(Reduction_steps[y][0] - 1)] = lights_up_off[(Reduction_steps[y][1] - 1)];
                lights_up_off[(Reduction_steps[y][1] - 1)] = temp2;

            }
        }


        //Checking which path shorter
        for(int x=0;x<Light_len;x++) {
            if(lights_up_on[x] == 1) {
                numb_move_lights_on++;
            }
            if(lights_up_off[x] == 1) {
                numb_move_lights_off++;
            }
        }

        //If light off takes less amount of moves it will do that if not it will go with lights on
        //Lights off is default if the amount of moves is equal
        for(int x=0;x<Light_len;x++) {
            if(lights_up_on[x] == 1 && numb_move_lights_on < numb_move_lights_off) {
                return x;
            }
            if(lights_up_off[x] == 1 && numb_move_lights_on >= numb_move_lights_off) {
                return x;
            }
        }
        //Returns a one if an error has occurred
        return -1;
    }

    private boolean checkforwin(){
        int flag;
        flag=color[0][0];
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(flag!=color[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    private  void setlevel(){
        int k= (int) getIntent().getIntExtra("level",0);
        //int k=Integer.parseInt(s);
        Random rand = new Random();
        for(int l=0;l<k*2;l++){
            int i= rand.nextInt(5) + 0;
            int j= rand.nextInt(5) + 0;
            swap(i,j);}

    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
        Log.d(TAG, "setGameId: " + gameId);

        final int size = 5;

        if(!multiplayer) {
            FirebaseDatabase.getInstance().getReference("games").child(gameId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Load the actual values from the DB into our board array
                    Log.d(TAG, "got new game state");

                    for (int row = 0; row < size; row++)
                        for (int col = 0; col < size; col++) {
                            String key = row + "_" + col;

                            DataSnapshot ref = dataSnapshot.child(key);

                            if (ref.exists()) {
                                setarray(row, col, Integer.parseInt(ref.getValue(String.class)));
                            } else {
                                setarray(row, col, 0);
                            }

                            setboardcolor(row, col);
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            FirebaseDatabase.getInstance().getReference("games").child(gameId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Load the actual values from the DB into our board array
                    Log.d(TAG, "got new game state");

                    for (int row = 0; row < size; row++)
                        for (int col = 0; col < size; col++) {
                            String key = row + "_" + col;

                            DataSnapshot ref = dataSnapshot.child(key);

                            if (ref.exists()) {
                                setarray(row, col, Integer.parseInt(ref.getValue(String.class)));
                            } else {
                                setarray(row, col, 0);
                            }

                            setboardcolor(row, col);
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

}
