package usama.utech.nearbyplaceswithdetailsandphotos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ShopsMuncipilityList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_muncipility_list);
    }

    void gotoActivity(String intentdata){

        Intent intent = new Intent(getApplicationContext(), ListOfAllShops.class);

        intent.putExtra("type",intentdata);
        startActivity(intent);
        finish();

    }

    public void gowith_SanBenito(View view) {

        gotoActivity("San Benito");
    }

    public void gowith_del(View view) {
        gotoActivity("Del Carmen");
    }

    public void gowith_burgos(View view) {
        gotoActivity("Burgos");
    }

    public void gowith_Dapa(View view) {
        gotoActivity("Dapa");
    }

    public void gowith_Pilar(View view) {
        gotoActivity("Pilar");
    }

    public void gowith_Luna(View view) {
        gotoActivity("General Luna");
    }

    public void gowith_Santa(View view) {
        gotoActivity("Santa Monica");
    }

    public void gowith_SanIsidro(View view) {
        gotoActivity("San Isidro");
    }

    public void gowith_showalllist(View view) {

        Intent intent = new Intent(getApplicationContext(), ListOfAllShops.class);

        intent.putExtra("type","all");
        startActivity(intent);
        finish();

    }
}
