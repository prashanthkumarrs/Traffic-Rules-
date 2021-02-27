package gvs.com.trafficrules;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class View_Video extends AppCompatActivity {
    String str;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__video);
        Intent intent = getIntent();
        str = intent.getStringExtra("videourl");
        uri = Uri.parse(str);
        setImage(str);
    }


    public void setImage (String video){
            VideoView post_image = (VideoView) findViewById(R.id.post_image);
            post_image.setVideoURI(uri);
            post_image.requestFocus();
            post_image.start();


    }
    public void back(View view){
        Intent intent=new Intent(View_Video.this,ConformComplaints.class);
        startActivity(intent);

    }
}