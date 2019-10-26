package usama.utech.nearbyplaceswithdetailsandphotos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AboutApp.this,MainActivity.class));
        finish();
    }

}
