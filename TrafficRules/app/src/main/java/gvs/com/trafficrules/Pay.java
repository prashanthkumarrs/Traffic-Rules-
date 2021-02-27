package gvs.com.trafficrules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pay extends AppCompatActivity {
    String cvehiclenum;
    String cownername;
    String cphone;
    String ccmptitle;
    String ccmpdesc;
    String cchalana;
    String id;
    String handleby;
    String station;
    String opt;
    String count;
    String date;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        sharedPreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Intent intent=getIntent();

        cvehiclenum=intent.getStringExtra("cvehiclenum");
        cownername=intent.getStringExtra("cownername");
        cphone=intent.getStringExtra("cphone");
        ccmptitle=intent.getStringExtra("ccmptitle");
        ccmpdesc=intent.getStringExtra("ccmpdesc");
        cchalana=intent.getStringExtra("cchalana");
        id=intent.getStringExtra("id");
        handleby=intent.getStringExtra("handleby");
        station=intent.getStringExtra("station");
        opt=intent.getStringExtra("opt");
        count=intent.getStringExtra("count");
        date=intent.getStringExtra("date");


    }
    public void pay(View view){
        final String actnum=((EditText)findViewById(R.id.actnum)).getText().toString();
        final String smount=((EditText)findViewById(R.id.amount)).getText().toString();
        final String cvvnuom=((EditText)findViewById(R.id.cvvnuom)).getText().toString();

        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Chalana_Details");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Chalana_Model cdel=new Chalana_Model(cvehiclenum,id,cownername,cphone,ccmptitle,ccmpdesc,cchalana,"Payed",station,handleby,opt,count,date);
                ref.child(id).setValue(cdel);
                Intent intent=new Intent(Pay.this,VPHome.class);
                startActivity(intent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
