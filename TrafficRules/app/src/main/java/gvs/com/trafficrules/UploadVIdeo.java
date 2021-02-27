package gvs.com.trafficrules;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class UploadVIdeo extends AppCompatActivity {
    Button selectFile,upload;
    TextView notification;
    FirebaseStorage storage;
    FirebaseDatabase database;
    DatabaseReference mDatabaseRef;
    Uri pdfUri;
    String url;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    int fileid;
    CountDownLatch latch = new CountDownLatch(1);
    ResultSet rs = null;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        sharedPreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Complaint_Details");

        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        selectFile=findViewById(R.id.selectfile);
        upload=findViewById(R.id.upload);
        notification=findViewById(R.id.notification);
        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(UploadVIdeo.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }else {
                    ActivityCompat.requestPermissions(UploadVIdeo.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri!=null)
                    uploadFile(pdfUri);
                else
                    Toast.makeText(UploadVIdeo.this,"Please Provide Permission",Toast.LENGTH_LONG).show();


            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference("Traffic_Police_Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> fdel=new ArrayList<>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                    String areaName = areaSnapshot.child("stationname").getValue(String.class);
                    fdel.add(areaName);}
                Spinner areaSpinner = (Spinner)findViewById(R.id.vspiner);
                final String[] areas = fdel.toArray(new String[fdel.size()]);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(UploadVIdeo.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void uploadFile(Uri pdfUri){
        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploadinf File....");
        progressDialog.setProgress(0);
        progressDialog.show();
        final String fileName=System.currentTimeMillis()+"";
        StorageReference storageReference=storage.getReference();
        storageReference.child("uploads").child(fileName).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                url=taskSnapshot.getDownloadUrl().toString();
                Log.d("url is",url);
                DatabaseReference reference=database.getReference();
                reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(UploadVIdeo.this,"File Uploaded Sucessfully",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(UploadVIdeo.this,"File Uploaded Failed",Toast.LENGTH_LONG).show();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadVIdeo.this,"File Uploaded Failed",Toast.LENGTH_LONG).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress=(int)(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }else {
            Toast.makeText(UploadVIdeo.this,"Please Provide Permission",Toast.LENGTH_LONG).show();
        }
    }
    private void selectPdf(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==86 && resultCode==RESULT_OK && data!=null){
            pdfUri=data.getData();
            Log.d("pdfuri", String.valueOf(pdfUri));
            fileid= Integer.parseInt(data.getData().getLastPathSegment());
            notification.setText("A File is Selected :"+data.getData().getLastPathSegment() );
            Log.d("fileid", String.valueOf(fileid));
        }else{
            Toast.makeText(UploadVIdeo.this,"Please Select  File",Toast.LENGTH_LONG).show();

        }
    }
    public void store(View  view){

        final String title = ((EditText) findViewById(R.id.vtitle)).getText().toString();
        final String desc = ((EditText) findViewById(R.id.vdesc)).getText().toString();
        final String psname = ((Spinner) findViewById(R.id.vspiner)).getSelectedItem().toString();

        if (title.length() <= 1 ) {
            Toast.makeText(UploadVIdeo.this, "All Fields Should be More then 3 and Phone Number should be 10 Characters", Toast.LENGTH_SHORT).show();
        }else {
        final String username= sharedPreferences.getString("cpusername","");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String curdate = df.format(c);
        String id=mDatabaseRef.push().getKey();

        Complaint_Model imageUpload = new Complaint_Model(title,id,desc,url,username,curdate,"Pending",psname);
        Toast.makeText(getApplicationContext(), "Video uploaded to Database", Toast.LENGTH_SHORT).show();

        mDatabaseRef.child(id).setValue(imageUpload);
        Intent intent=new Intent(UploadVIdeo.this,CPHome.class);

        startActivity(intent);


        }


    }
}
