package gvs.com.trafficrules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CPLog extends AppCompatActivity {
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cplog);
    }
    public void reg(View view){
        final String cpname=((EditText)findViewById(R.id.cpname)).getText().toString();
        final String cemail=((EditText)findViewById(R.id.cemail)).getText().toString();
        final String cpaddress=((EditText)findViewById(R.id.cpaddress)).getText().toString();
        final String cpphone=((EditText)findViewById(R.id.cpphone)).getText().toString();
        final String cppass=((EditText)findViewById(R.id.cppass)).getText().toString();
        final String cpusername=((EditText)findViewById(R.id.cpusername)).getText().toString();
        databaseReference= FirebaseDatabase.getInstance().getReference("Public_Details");
        String id = databaseReference.push().getKey();
        Public_Model fdel=new Public_Model(id,cpname,cemail,cpphone,cpaddress,cppass,cpusername);
        databaseReference.child(id).setValue(fdel);
        Toast.makeText(CPLog.this, "Added Sucess", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(CPLog.this,CP_Log.class);
        startActivity(intent);

    }
}
