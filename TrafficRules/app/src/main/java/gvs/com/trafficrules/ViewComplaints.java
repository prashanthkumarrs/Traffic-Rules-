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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewComplaints extends Fragment {

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
        View view= inflater.inflate(R.layout.fragment_view_complaints, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        club_list=(ListView)view.findViewById(R.id.cmp_list);
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
            view = getActivity().getLayoutInflater().inflate(R.layout.complaint_list,null);
          TextView  cname=(TextView)view.findViewById(R.id.cname);
            TextView cdesc=(TextView)view.findViewById(R.id.cdesc);
            TextView cby=(TextView)view.findViewById(R.id.cby);
            TextView  cdate=(TextView)view.findViewById(R.id.cdate);
            TextView cstatus=(TextView)view.findViewById(R.id.cstatus);



            cname.setText(detailsList.get(i).getCmptitle());
            cdesc.setText(detailsList.get(i).getCmpdesc());
            cby.setText(detailsList.get(i).getCmp_by());
            cdate.setText(detailsList.get(i).getCmp_date());
            cstatus.setText(detailsList.get(i).getCmp_status());





            return view;
        }
    }
}
