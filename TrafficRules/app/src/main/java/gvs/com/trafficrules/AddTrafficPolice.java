package gvs.com.trafficrules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AddTrafficPolice extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    Button add_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_traffic_police, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("Traffic_Police_Details");
        ArrayList<String> type = new ArrayList<String>();
        type.add("<--- Choose Gender --->");
        type.add("Male");
        type.add("Female");
        type.add("Other");
        final Spinner My_spinner1 = (Spinner)view.findViewById(R.id.uspiner);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, type);
        My_spinner1.setAdapter(adapter1);

        final EditText fname = ((EditText)view.findViewById(R.id.uname));
        final EditText fid = ((EditText)view.findViewById(R.id.uid));
        final EditText femail = ((EditText)view.findViewById(R.id.uemail));
        final EditText fphone = ((EditText)view.findViewById(R.id.uphone));
        final EditText faddress = ((EditText)view.findViewById(R.id.uaddress));
        final EditText pass = ((EditText)view.findViewById(R.id.pass));
        final EditText sname = ((EditText)view.findViewById(R.id.sname));

        final Spinner gender = (Spinner)view.findViewById(R.id.uspiner);
        add_btn=(Button)view.findViewById(R.id.sreg_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String gender1 = gender.getSelectedItem().toString();
                final String name=fname.getText().toString();
                final String rno=fid.getText().toString();
                final String email=femail.getText().toString();
                final String phone=fphone.getText().toString();
                final String address=faddress.getText().toString();
                final String password=pass.getText().toString();
                final String station=sname.getText().toString();
                if(!TextUtils.isEmpty(name)){
                    Log.d("Inside","hellow");

                    final DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Traffic_Police_Details");
                    ref.orderByChild("servicenum").equalTo(rno).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                ref.orderByChild("stationname").equalTo(station).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            Toast.makeText(getContext(), "Police already exists", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }else {
                                String id = ref.push().getKey();
                                TP_Model fdel=new TP_Model(rno,id,name,email,phone,address,password,gender1,station);
                                ref.child(id).setValue(fdel);
                                new SimpleMail().sendEmail(email, "UserName & Password", "Enter This UserName &  Password for Login Your Application"+"  "+"Password is :"+"  "+password+"   and UserName is:"+" "+rno);
                                Toast.makeText(getContext(), "Added Sucess", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getContext(),AdminHome.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else {
                    Toast.makeText(getContext(), "Please Fill All The Details", Toast.LENGTH_SHORT).show();



                }

            }
        });
        return view;
    }


}
