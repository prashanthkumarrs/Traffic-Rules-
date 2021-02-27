package gvs.com.trafficrules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ConformComplaints extends Fragment {
    ListView club_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    List<Complaint_Model> detailsList;
    TextView isdate;
    String srno;
    String sbranch;
    String semail;
    String id;
    String skey;
    TextView bname;
    TextView sdate;
    String sname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_conform_complaints, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        club_list=(ListView)view.findViewById(R.id.conf_list);
        sname=sharedPreferences.getString("sname","");

        databaseReference= FirebaseDatabase.getInstance().getReference("Complaint_Details");
        databaseReference.orderByChild("psname").equalTo(sname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailsList=new ArrayList<Complaint_Model>();

                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    Complaint_Model clubdel=childSnapshot.getValue(Complaint_Model.class);
                    detailsList.add(clubdel);
                }
              CustomAdoptor customAdoptor= new CustomAdoptor();
                club_list.setAdapter(customAdoptor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
            view = getActivity().getLayoutInflater().inflate(R.layout.conform_list,null);
            TextView  cname=(TextView)view.findViewById(R.id.cname1);
            TextView cdesc=(TextView)view.findViewById(R.id.cdesc1);
            TextView cby=(TextView)view.findViewById(R.id.cby1);
            TextView  cdate=(TextView)view.findViewById(R.id.cdate1);
            TextView cstatus=(TextView)view.findViewById(R.id.cstatus1);
            Button videobtn=(Button)view.findViewById(R.id.video_btn);

            Button cnf_btn=(Button)view.findViewById(R.id.cnf_btn);
            Button igr_btn=(Button)view.findViewById(R.id.igr_btn);
            cname.setText(detailsList.get(i).getCmptitle());
            cdesc.setText(detailsList.get(i).getCmpdesc());
            cby.setText(detailsList.get(i).getCmp_by());
            cdate.setText(detailsList.get(i).getCmp_date());
            cstatus.setText(detailsList.get(i).getCmp_status());
            igr_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Complaint_Model del = new Complaint_Model(detailsList.get(i).getCmptitle(),detailsList.get(i).getId(),detailsList.get(i).getCmpdesc(),detailsList.get(i).getCmpic(),detailsList.get(i).getCmp_by(),detailsList.get(i).getCmp_date(),"Fake Complaint",detailsList.get(i).getPsname());
                    databaseReference.child(detailsList.get(i).getId()).setValue(del);

                }
            });
            videobtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getContext(),View_Video.class);
                    intent.putExtra("videourl", detailsList.get(i).getCmpic());
                    startActivity(intent);

                }
            });

            cnf_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getContext(),Conform_Comp.class);
                    intent.putExtra("cname", detailsList.get(i).getCmptitle());
                    intent.putExtra("cdesc",  detailsList.get(i).getCmpdesc());
                    intent.putExtra("cby", detailsList.get(i).getCmp_by());
                    intent.putExtra("url",   detailsList.get(i).getCmpic());
                    intent.putExtra("cstatus",detailsList.get(i).getCmp_status());
                    intent.putExtra("cdate", detailsList.get(i).getCmp_date());
                    intent.putExtra("id",  detailsList.get(i).getId());
                    intent.putExtra("psname",  detailsList.get(i).getPsname());
                    startActivity(intent);
                }
            });




            return view;
        }
    }

}
