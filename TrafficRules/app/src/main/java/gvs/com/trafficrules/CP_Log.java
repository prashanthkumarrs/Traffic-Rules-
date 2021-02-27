package gvs.com.trafficrules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CP_Log extends AppCompatActivity {
TextView cp_reg;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_cp__log);
        cp_reg=(TextView)findViewById(R.id.cr_reg);
        cp_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CP_Log.this,CPLog.class);
                startActivity(intent);
            }
        });


    }

    public void cpLog(View view){
        final String email=((EditText)findViewById(R.id.cpusername)).getText().toString();
        final String password=((EditText)findViewById(R.id.cppassword)).getText().toString();
        if (email.length() <= 1 || password.length() <= 1 ) {
            Toast.makeText(CP_Log.this, "All Fields Should be More then 3 Characters", Toast.LENGTH_SHORT).show();

        } else {

            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Public_Details");
            final DatabaseReference ref2= FirebaseDatabase.getInstance().getReference().child("Public_Details");
            ref.orderByChild("username").equalTo(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        ref2.orderByChild("password").equalTo(password).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                        String key = childSnapshot.getKey();
                                        String tame = childSnapshot.child("name").getValue().toString();
                                        String temail = childSnapshot.child("email").getValue().toString();
                                        String tservicenum = childSnapshot.child("username").getValue().toString();
                                        String taddress = childSnapshot.child("address").getValue().toString();
                                        String tphone = childSnapshot.child("phone").getValue().toString();

                                        Log.d("name is", key);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("cpname", tame);
                                        editor.putString("cpkey", key);
                                        editor.putString("cpemail", temail);
                                        editor.putString("cpusername", tservicenum);
                                        editor.putString("cpaddress", taddress);
                                        editor.putString("cpphone", tphone);

                                        editor.commit();
                                        Log.d("keyis", key);
                                        Intent intent = new Intent(CP_Log.this, CPHome.class);
                                        startActivity(intent);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }else{
                        Toast.makeText(CP_Log.this, "Failed To Login", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }

}
