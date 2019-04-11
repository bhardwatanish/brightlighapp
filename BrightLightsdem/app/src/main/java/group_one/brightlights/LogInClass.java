package group_one.brightlights;



import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import group_one.brightlights.Model.User;          //User. Java file, has class User
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInClass extends AppCompatActivity {

    EditText NewUser,NewPassword;
    EditText User,Password;

    Button SignUpBTN, SignInBTN;
   // private Button testButton;

    FirebaseDatabase database;
    DatabaseReference users,moves;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_layout);

        //Test Button
//        testButton = (Button) findViewById(R.id.testactivity);
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openTestActivity();
//            }
//        });

        //Firebase
        database =FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        User=(EditText)findViewById(R.id.user_name);
        Password=(EditText)findViewById(R.id.password);

        SignInBTN=(Button)findViewById(R.id.button_sign_in);
        SignUpBTN=(Button)findViewById(R.id.button_sign_up);

        SignUpBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showSignUpDialog();

            }
        });
        SignInBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(User.getText().toString(), Password.getText().toString());
            }
        });



    }

    private void signIn(final String user, final String pwd) {

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists()){
                    if(!user.isEmpty()){
                        User login = dataSnapshot.child(user).getValue(User.class);

                        if(login.getPasswordR().equals(pwd)){

                            Intent l=new Intent(getApplicationContext(), group_one.brightlights.MultiMenuClass.class);
                            startActivity(l);
                            Toast.makeText(LogInClass.this, "Good Login", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(LogInClass.this, "Wrong Pass bro", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(LogInClass.this, "Please enter your username",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                    Toast.makeText(LogInClass.this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void    showSignUpDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogInClass.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Please create a username and password");
        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up_layout,null);
        NewUser = (EditText)sign_up_layout.findViewById(R.id.new_user_name);
        NewPassword = (EditText)sign_up_layout.findViewById(R.id.new_password);
        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                final User user = new User(NewUser.getText().toString(),
                        NewPassword.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUserNameR()).exists())
                            Toast.makeText(LogInClass.this, "User already exists", Toast.LENGTH_SHORT).show();
                        else
                        {
                            users.child(user.getUserNameR())
                                    .setValue(user);
                            Toast.makeText(LogInClass.this, "User registered",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();


            }
        });
        alertDialog.show();
    }

//
//    //open Network Testing
//    public void openTestActivity(){
//        Intent gototest = new Intent(this, group_one.brightlights.DatabaseTestClass.class);
//        startActivity(gototest);
//    }


}
