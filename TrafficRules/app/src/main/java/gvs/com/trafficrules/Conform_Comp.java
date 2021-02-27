package gvs.com.trafficrules;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Conform_Comp extends AppCompatActivity {
    String bname;
    String cdesc;
    String cby;
    String url;
    String cstatus;
    String cdate;
    String id;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;
    String imgname;
    String imgurl;
    EditText cmpptitle;
    EditText cmppdesc;
    EditText imagview;
    String key;
    String ownername;
    EditText expdate;
    EditText chalana;
    ImageView imageView;
    Spinner vnum;
    Button sub_btn;
     String username;
     String stationname;
    String comp_count;
    int OTP;
 String phone;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform__comp);
        sharedPreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username=sharedPreferences.getString("tservicenum","");
        stationname=sharedPreferences.getString("sname","");
        Intent intent=getIntent();
        imageView = findViewById(R.id.imageView);
        sub_btn=(Button)findViewById(R.id.btn_conform);
        bname=intent.getStringExtra("cname");
        cdesc=intent.getStringExtra("cdesc");
        cby=intent.getStringExtra("cby");
        url=intent.getStringExtra("url");
        cstatus=intent.getStringExtra("cstatus");
        cdate=intent.getStringExtra("cdate");
        stationname=intent.getStringExtra("psname");
        id=intent.getStringExtra("id");
        chalana=(EditText)findViewById(R.id.chalana);

        cmppdesc=(EditText)findViewById(R.id.campp_desc);
        cmpptitle=(EditText)findViewById(R.id.cmpp_title);
        cmpptitle.setText(bname);
        cmppdesc.setText(cdesc);
        loadImageFromUrl(url);
        databaseReference= FirebaseDatabase.getInstance().getReference("Vehicle_Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> fdel=new ArrayList<>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                    String areaName = areaSnapshot.child("vehiclenum").getValue(String.class);
                    fdel.add(areaName);}
                Spinner areaSpinner = (Spinner)findViewById(R.id.cmp_too);
                final String[] areas = fdel.toArray(new String[fdel.size()]);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(Conform_Comp.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

sub_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        vnum=(Spinner)findViewById(R.id.cmp_too);
        final String vnum1 = vnum.getSelectedItem().toString();
        final String chalana1=chalana.getText().toString();

        final DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("Complaint_Details");
        Complaint_Model del = new Complaint_Model(bname,id,cdesc,url,cby,cdate," Complaint Accepted",stationname);
        databaseReference2.child(id).setValue(del);

        DatabaseReference databaseReference3=FirebaseDatabase.getInstance().getReference("Vehicle_Details");
        databaseReference3.orderByChild("vehiclenum").equalTo(vnum1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                        phone = areaSnapshot.child("phone").getValue(String.class);

                        ownername=areaSnapshot.child("owner_name").getValue(String.class);
                    }}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



                        Random pass=new Random(87654);
                        final int OTP=pass.nextInt();
                        Log.d("otp is", String.valueOf(OTP));
                        final String pass1=String.valueOf(OTP);
                        final DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Chalana_Details");;
                        ref.orderByChild("cvehiclenum").equalTo(vnum1).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                               if(dataSnapshot.exists()) {
                                   for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                       key = childSnapshot.getKey();
                                       comp_count = childSnapshot.child("comp_count").getValue().toString();
                                   }
                                   int count = Integer.parseInt(comp_count);
                                   Log.d("value is", String.valueOf(count));
                                   if (count <= 10) {
                                       Date c = Calendar.getInstance().getTime();
                                       SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                       String curdate = df.format(c);
                                   Chalana_Model cdel = new Chalana_Model(vnum1, key, ownername, phone, bname, cdesc, chalana1, "ChalanaGenerated", username,stationname,  pass1, String.valueOf(count + 1),curdate);
                                   ref.child(key).setValue(cdel);
                                   int permissionCheck = ContextCompat.checkSelfPermission(Conform_Comp.this, Manifest.permission.READ_PHONE_STATE);
                                   if (ContextCompat.checkSelfPermission(Conform_Comp.this, Manifest.permission.SEND_SMS)
                                           != PackageManager.PERMISSION_GRANTED) {
                                       ActivityCompat.requestPermissions((Activity) Conform_Comp.this, new String[]{Manifest.permission.SEND_SMS},
                                               MY_PERMISSIONS_REQUEST_SEND_SMS);
                                   } else if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                       ActivityCompat.requestPermissions((Activity) Conform_Comp.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                                   } else {
                                       SmsManager sms = SmsManager.getDefault();

                                       Log.d("hellow", "sravani2");
                                       String msg = "YOur Vehicle Chalana Details:" + "COMPLAINT TITLE:" + " " + bname + " " + " ON:" + " " + cdate + " " + " Fine is:" + chalana1 + " " + " Login With your PHONENUMBER AND THIS PASSWORD" + " " + OTP;
                                       sms.sendTextMessage(phone, null, msg, sentPI, deliveredPI);


                                   }
                               }else {

                                    databaseReference.orderByChild("vehiclenum").equalTo(vnum1).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                                            String   key1 = childSnapshot.getKey();
                                            databaseReference.child(key1).removeValue();
                                        }}

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                       ref.child(key).removeValue();
                                   }



                               } else{
                                   Date c = Calendar.getInstance().getTime();
                                   SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                   String curdate = df.format(c);
                                   String id1 = ref.push().getKey();
                                   Chalana_Model cdel=new Chalana_Model(vnum1,id1,ownername,phone,bname,cdesc,chalana1,"ChalanaGenerated",username,stationname,pass1,"0",curdate);
                                   ref.child(id1).setValue(cdel);
                                   int permissionCheck = ContextCompat.checkSelfPermission(Conform_Comp.this, Manifest.permission.READ_PHONE_STATE);
                                   if (ContextCompat.checkSelfPermission(Conform_Comp.this, Manifest.permission.SEND_SMS)
                                           != PackageManager.PERMISSION_GRANTED) {
                                       ActivityCompat.requestPermissions((Activity) Conform_Comp.this, new String[]{Manifest.permission.SEND_SMS},
                                               MY_PERMISSIONS_REQUEST_SEND_SMS);
                                   } else if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                       ActivityCompat.requestPermissions((Activity) Conform_Comp.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                                   } else{
                                       SmsManager sms = SmsManager.getDefault();

                                       Log.d("hellow","sravani2");
                                       String msg="YOur Vehicle Chalana Details:"+"COMPLAINT TITLE:"+" " +bname+" "+ " ON:"+" "+cdate+" "+" Fine is:"+chalana1+ " "+" Login With your PHONENUMBER AND THIS PASSWORD"+" "+OTP;
                                       sms.sendTextMessage(phone, null,msg, sentPI, deliveredPI);


                                   }
                               }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });









    }
});



    }

    private void loadImageFromUrl(String receivedImage){
        Picasso.with(this).load(receivedImage).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            bgDrawable.draw(canvas);
        }   else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
}
