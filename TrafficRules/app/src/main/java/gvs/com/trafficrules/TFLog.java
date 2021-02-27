package gvs.com.trafficrules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TFLog extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tflog);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }
    public void tfLog(View view){
        final String email=((EditText)findViewById(R.id.uid)).getText().toString();
        final String password=((EditText)findViewById(R.id.upass)).getText().toString();
        if (email.length() <= 1 || password.length() <= 1 ) {
            Toast.makeText(TFLog.this, "All Fields Should be More then 3 Characters", Toast.LENGTH_SHORT).show();

        } else {

            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Traffic_Police_Details");
            final DatabaseReference ref2= FirebaseDatabase.getInstance().getReference().child("Traffic_Police_Details");
            ref.orderByChild("servicenum").equalTo(email).addValueEventListener(new ValueEventListener() {
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
                                        String tservicenum = childSnapshot.child("servicenum").getValue().toString();
                                        String taddress = childSnapshot.child("address").getValue().toString();
                                        String tphone = childSnapshot.child("phone").getValue().toString();
                                        String tgender = childSnapshot.child("gender").getValue().toString();
                                        String sname= childSnapshot.child("stationname").getValue().toString();

                                        Log.d("name is", key);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("tame", tame);
                                        editor.putString("tkey", key);
                                        editor.putString("temail", temail);
                                        editor.putString("tservicenum", tservicenum);
                                        editor.putString("taddress", taddress);
                                        editor.putString("tphone", tphone);
                                        editor.putString("tgender", tgender);
                                        editor.putString("sname", sname);
                                        editor.commit();
                                        Log.d("keyis", key);
                                        Intent intent = new Intent(TFLog.this, TPHome.class);
                                        startActivity(intent);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }else{
                        Toast.makeText(TFLog.this, "Failed To Login", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
}
