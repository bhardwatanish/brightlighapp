package group_one.brightlights;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.support.constraint.Constraints.TAG;


public  class GamesAdapter extends
        RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private List<String> mGameIdList;
    private int game_type;
    public GamesAdapter(List<String> GameIds)
    {
        mGameIdList = GameIds;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView gameIdTextView;
        public Button playButton;

        public ViewHolder(View itemView) {
            super(itemView);

            gameIdTextView = (TextView) itemView.findViewById(R.id.game_id);
            playButton = (Button) itemView.findViewById(R.id.play_button);

            playButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {

                    TextView gameIdTextView = (TextView) ((View)v.getParent()).findViewById(R.id.game_id);
                    final String gameId = gameIdTextView.getText().toString();

                    System.out.println("Game ID is: " + gameId);

                    FirebaseDatabase.getInstance().getReference("games").child(gameId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Load the actual values from the DB into our board array
                            //Log.d(TAG, "got new game state");

                            boolean end = false;

                            int row = 3, col = 3;

                            while (!end) {

                                String key = row + "_" + col;

                                DataSnapshot ref = dataSnapshot.child(key);

                                if (ref.exists()) {
                                          row++;
                                          col++;
                                } else {
                                    game_type = row;
                                    start_game(game_type,v,gameId);
                                    end = true;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

               }
            });
        }
    }

    void start_game(int game_size,View v,String gameId){

        System.out.println("Size of game is: " + game_size);

        if(game_size == 3){
            Intent l=new Intent(v.getContext().getApplicationContext(), group_one.brightlights.NineLightModeClass.class).putExtra("level",1).putExtra("gameId", gameId);
            v.getContext().startActivity(l);
        }else if(game_size == 4){
            Intent l=new Intent(v.getContext().getApplicationContext(), group_one.brightlights.SixteenLightModeClass.class).putExtra("level",1).putExtra("gameId", gameId);
            v.getContext().startActivity(l);
        }else if(game_size == 5){
            Intent l=new Intent(v.getContext().getApplicationContext(), group_one.brightlights.TwentyFiveLights.class).putExtra("level",1).putExtra("gameId", gameId);
            v.getContext().startActivity(l);
        }else {

        }
    }
    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View gameView = inflater.inflate(R.layout.game_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(gameView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GamesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        String gameId = mGameIdList.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.gameIdTextView;
        textView.setText(gameId);
        Button button = viewHolder.playButton;
        //button.setText(gameId.isOnline() ? "Message" : "Offline");
        //button.setEnabled(gameId.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mGameIdList.size();
    }
}



