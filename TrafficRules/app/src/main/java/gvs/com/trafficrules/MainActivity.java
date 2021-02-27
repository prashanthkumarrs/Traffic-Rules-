package gvs.com.trafficrules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void adminlog(View view){
        Intent intent=new Intent(MainActivity.this,Adminlog.class);
        startActivity(intent);

    }
    public void userlog(View view){
        Intent intent=new Intent(MainActivity.this,TFLog.class);
        startActivity(intent);

    }
    public void Cplog(View view){
        Intent intent=new Intent(MainActivity.this,CP_Log.class);
        startActivity(intent);

    }
    public void Vlog(View view){
        Intent intent=new Intent(MainActivity.this,VialotorLog.class);
        startActivity(intent);

    }
}
