
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

public class VialotorLog extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vialotor_log);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }
    public void vLog(View view){
        final String email=((EditText)findViewById(R.id.vphone)).getText().toString();
        final String password=((EditText)findViewById(R.id.otp)).getText().toString();
        if (email.length() <= 1 || password.length() <= 1 ) {
            Toast.makeText(VialotorLog.this, "All Fields Should be More then 3 Characters", Toast.LENGTH_SHORT).show();

        } else {

            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Chalana_Details");
            final DatabaseReference ref2= FirebaseDatabase.getInstance().getReference().child("Chalana_Details");
            ref.orderByChild("cphone").equalTo(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        ref2.orderByChild("opt").equalTo(password).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                        String key = childSnapshot.getKey();
                                        String cvehiclenum = childSnapshot.child("cvehiclenum").getValue().toString();
                                        String cphone = childSnapshot.child("cphone").getValue().toString();
                                        String otp = childSnapshot.child("opt").getValue().toString();
                                        String cchalana = childSnapshot.child("cchalana").getValue().toString();


                                        Log.d("name is", key);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("cvehiclenum", cvehiclenum);
                                        editor.putString("tkey", key);
                                        editor.putString("cphone", cphone);
                                        editor.putString("otp", otp);
                                        editor.putString("cchalana", cchalana);

                                        editor.commit();
                                        Log.d("keyis", key);
                                        Intent intent = new Intent(VialotorLog.this, VPHome.class);
                                        startActivity(intent);
                                    }

                                }else{
                                    Toast.makeText(VialotorLog.this, "Failed To Login", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }else{
                        Toast.makeText(VialotorLog.this, "Failed To Login", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
}
