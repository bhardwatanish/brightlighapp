package group_one.brightlights;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import group_one.brightlights.GamesAdapter;
import group_one.brightlights.R;

public class MultiPlayerActivity extends AppCompatActivity {

    List<String> Games = new LinkedList<String>();
    GamesAdapter adapter = new GamesAdapter(Games);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mp_recycler_view);

        RecyclerView rvGames = (RecyclerView) findViewById(R.id.rvGames);

        rvGames.setAdapter(adapter);
        rvGames.setLayoutManager(new LinearLayoutManager(this));

        setupFirebase();
    }

    private void setupFirebase()
    {
        FirebaseDatabase.getInstance().getReference("games").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String gameId = dataSnapshot.getKey();
                if (!Games.contains(gameId))
                    Games.add(gameId);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String gameId = dataSnapshot.getKey();
                if (!Games.contains(gameId))
                    Games.add(gameId);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String gameId = dataSnapshot.getKey();
                if (Games.contains(gameId))
                    Games.remove(gameId);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
