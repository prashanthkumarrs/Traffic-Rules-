package gvs.com.trafficrules;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewChalanaDetails extends Fragment {
    ListView club_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    List<Chalana_Model> detailsList;
    EditText search;
    Button btn_search;
    String key;
    String servicenum;
    String cphone;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_chalana_details, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Chalana_Details");
        servicenum=sharedPreferences.getString("tservicenum","");


        club_list=(ListView)view.findViewById(R.id.vchlist);
        databaseReference.orderByChild("handleby").equalTo(servicenum).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    detailsList=new ArrayList<Chalana_Model>();
                    for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        Chalana_Model clubdel=childSnapshot.getValue(Chalana_Model.class);
                        detailsList.add(clubdel);
                    }
                   CustomAdoptor customAdoptor= new CustomAdoptor();
                    club_list.setAdapter(customAdoptor);
                }else{


                }

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
            view = getActivity().getLayoutInflater().inflate(R.layout.view_chalana_list,null);
            TextView cname=(TextView)view.findViewById(R.id.chname1);
            TextView cdesc=(TextView)view.findViewById(R.id.chvdesc);
            TextView  cdate=(TextView)view.findViewById(R.id.chfine1);
            TextView  chvname=(TextView)view.findViewById(R.id.chvname);
            TextView  chvoname=(TextView)view.findViewById(R.id.chvoname);
            TextView  chvphone=(TextView)view.findViewById(R.id.chvphone);
            TextView  cstatus=(TextView)view.findViewById(R.id.chvstatus);
            TextView  chdate=(TextView)view.findViewById(R.id.cdate);
            float daysBetween = 0;
            TextView  cdays=(TextView)view.findViewById(R.id.cdays);
            Date c = Calendar.getInstance().getTime();

            System.out.println("Current time => " + c);
            Log.d("dateis", String.valueOf(c));

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c);
            String  sundate=detailsList.get(i).getDate();
            try {
                Date dateBefore = df.parse(sundate);
                Date dateAfter = df.parse(formattedDate);
                long difference = dateAfter.getTime() - dateBefore.getTime();
                daysBetween = (difference / (1000*60*60*24));
                /* You can also convert the milliseconds to days using this method
                 * float daysBetween =
                 *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
                 */
                System.out.println("Number of Days between dates: "+daysBetween);
                Log.d("numbe of days is", String.valueOf(daysBetween));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int days= (int) daysBetween;
            String days1=String.valueOf(days);
            cname.setText(detailsList.get(i).getCcmptitle());
            cdesc.setText(detailsList.get(i).getCcmpdesc());
            cdate.setText(detailsList.get(i).getCchalana());
            chvname.setText(detailsList.get(i).getCvehiclenum());
            chvoname.setText(detailsList.get(i).getCownername());
            chvphone.setText(detailsList.get(i).getCphone());
            cstatus.setText(detailsList.get(i).getStatus());

            chdate.setText(detailsList.get(i).getDate());
           if(days<=3){
               int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE);
               if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)
                       != PackageManager.PERMISSION_GRANTED) {
                   ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.SEND_SMS},
                           MY_PERMISSIONS_REQUEST_SEND_SMS);
               } else if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                   ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
               } else{
                   SmsManager sms = SmsManager.getDefault();

                   Log.d("hellow","sravani2");
                   String msg="YOur Vehicle Chalana Details:"+"COMPLAINT TITLE:"+" " +detailsList.get(i).getCcmptitle()+" "+ " ON:"+" "+cdate+" "+" Fine is:"+detailsList.get(i).getCchalana();
                   sms.sendTextMessage(detailsList.get(i).getCphone(), null,msg, sentPI, deliveredPI);


               }

           }else{
               databaseReference2= FirebaseDatabase.getInstance().getReference().child("Vehicle_Details");
               databaseReference2.orderByChild("vehiclenum").equalTo(detailsList.get(i).getCvehiclenum()).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       if(dataSnapshot.exists()){
                           for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                            String   key1 = childSnapshot.getKey();
                               databaseReference2.child(key1).removeValue();

                           }
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });



           }



            return view;
        }
    }

}
