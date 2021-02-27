package gvs.com.trafficrules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment {
    ListView club_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    List<Chalana_Model> detailsList;
    EditText search;
    Button btn_search;
    String key;
    String cvehiclenum;
    String cphone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Chalana_Details");

        key=sharedPreferences.getString("tkey","");
        cvehiclenum=sharedPreferences.getString("cvehiclenum","");
        cphone=sharedPreferences.getString("cphone","");
        club_list=(ListView)view.findViewById(R.id.chlist);
     final EditText search1=((EditText)view.findViewById(R.id.vsearch));

        btn_search=(Button)view.findViewById(R.id.search_btn);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=search1.getText().toString();
                Log.d("hii","am inside");
                databaseReference.orderByChild("ccmptitle").equalTo(name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                    databaseReference.orderByChild("cphone").equalTo(cphone).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                databaseReference.orderByChild("cvehiclenum").equalTo(cvehiclenum).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            Log.d("hii","am inside2");

                                            detailsList=new ArrayList<Chalana_Model>();
                                            for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                                Chalana_Model clubdel=childSnapshot.getValue(Chalana_Model.class);
                                                detailsList.add(clubdel);
                                            }
                                            CustomAdoptor customAdoptor= new CustomAdoptor();
                                            club_list.setAdapter(customAdoptor);
                                        }else{
                                            Toast.makeText(getContext(), "Search With Correct Complaint Name", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }else{
                                Toast.makeText(getContext(), "Search With Correct Complaint Name", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    }else {
                            Toast.makeText(getContext(), "Search With Correct Complaint Name", Toast.LENGTH_SHORT).show();

                        }


                  }
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


        return view;
    }


    class CustomAdoptor extends BaseAdapter {

        @Override
        public int getCount() {
            return detailsList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getActivity().getLayoutInflater().inflate(R.layout.chalana_list,null);
            TextView  cname=(TextView)view.findViewById(R.id.chname);
            TextView cdesc=(TextView)view.findViewById(R.id.chdesc);
            TextView cby=(TextView)view.findViewById(R.id.chdate);
            TextView  cdate=(TextView)view.findViewById(R.id.chfine);
            TextView  cstatus=(TextView)view.findViewById(R.id.chstatus);

            Button cnf_btn=(Button)view.findViewById(R.id.ch_btn);
            cname.setText(detailsList.get(i).getCcmptitle());
            cdesc.setText(detailsList.get(i).getCcmpdesc());
            cdate.setText(detailsList.get(i).getCchalana());
            cstatus.setText(detailsList.get(i).getStatus());


            cnf_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getContext(),Pay.class);
                    intent.putExtra("cvehiclenum", detailsList.get(i).getCvehiclenum());
                    intent.putExtra("cownername",  detailsList.get(i).getCownername());
                    intent.putExtra("cphone", detailsList.get(i).getCphone());
                    intent.putExtra("ccmptitle",   detailsList.get(i).getCcmptitle());
                    intent.putExtra("ccmpdesc",detailsList.get(i).getCcmpdesc());
                    intent.putExtra("cchalana", detailsList.get(i).getCchalana());
                    intent.putExtra("id",  detailsList.get(i).getId());
                    intent.putExtra("handleby",  detailsList.get(i).getHandleby());
                    intent.putExtra("station",  detailsList.get(i).getStation());
                    intent.putExtra("opt",  detailsList.get(i).getOpt());
                    intent.putExtra("count",  detailsList.get(i).getComp_count());
                    intent.putExtra("date",  detailsList.get(i).getDate());

                    startActivity(intent);



                }
            });
            return view;
        }
    }
}
