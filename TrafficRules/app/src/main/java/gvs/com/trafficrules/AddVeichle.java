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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddVeichle extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    Button add_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_veichle, container, false);
        final EditText oname = ((EditText)view.findViewById(R.id.oname));
        final EditText vnum = ((EditText)view.findViewById(R.id.vid));
        final EditText phone = ((EditText)view.findViewById(R.id.phone));
        add_btn=(Button)view.findViewById(R.id.vreg_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name=oname.getText().toString();
                final String vnum1=vnum.getText().toString();
                final String phone1=phone.getText().toString();

                if(!TextUtils.isEmpty(name)){
                    Log.d("Inside","hellow");

                    final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Vehicle_Details");
                    ref.orderByChild("vehiclenum").equalTo(vnum1).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(getContext(), "Police already exists", Toast.LENGTH_SHORT).show();

                            }else {
                                String id = ref.push().getKey();
                                Vehicle_Model fdel=new Vehicle_Model(vnum1,id,name,phone1);
                                ref.child(id).setValue(fdel);
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
