package usama.utech.nearbyplaceswithdetailsandphotos.Adaptar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import usama.utech.nearbyplaceswithdetailsandphotos.R;
import usama.utech.nearbyplaceswithdetailsandphotos.model.Place;
import usama.utech.nearbyplaceswithdetailsandphotos.model.PlaceDetails;

public class AllShopsListAdaptar extends RecyclerView.Adapter<AllShopsListAdaptar.AllShopsListAdaptarViewHolder> {


    Context context;

    ArrayList<PlaceDetails> placeDetailsArrayList;
    ArrayList<Place> placeArrayList;

    DisplayMetrics dm;
    private Bitmap bitmapForReturn;


    public AllShopsListAdaptar(Context context, ArrayList<PlaceDetails> placeDetailsArrayList, ArrayList<Place> placeArrayList, DisplayMetrics dm1) {
        this.context = context;
        this.placeDetailsArrayList = placeDetailsArrayList;
        this.placeArrayList = placeArrayList;
        dm = dm1;
    }

    @NonNull
    @Override
    public AllShopsListAdaptarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rec_layout_for_list_of_shops, parent, false);

        return new AllShopsListAdaptarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllShopsListAdaptarViewHolder holder, int position) {

        holder.recFormattedAddress.setText("Address :" + placeDetailsArrayList.get(position).getFormatted_address());
        holder.recFormattedPhoneNumber.setText("Phone :" + placeDetailsArrayList.get(position).getFormatted_phone_number());
        holder.recName.setText("Name :" + placeDetailsArrayList.get(position).getName());
        holder.recOpeningHours.setText("Opening Hours :" + placeDetailsArrayList.get(position).getOpening_hours());
        holder.recPriceLevel.setText("Price Level :" + placeDetailsArrayList.get(position).getPrice_level());
        holder.recRating.setText("Rating :" + placeDetailsArrayList.get(position).getRating());
        holder.recVicinity.setText("Owner :" + placeDetailsArrayList.get(position).getVicinity());

        final Bitmap bitmap = null;
        if (placeArrayList != null && placeArrayList.get(position).mPhotos.length > 0){

           String  photoref = placeArrayList.get(position).mPhotos[0].mPhotoReference;


            if (!photoref.equals("Nill")) {

                int width = (int) (dm.widthPixels * 3) / 4;
                int height = (int) (dm.heightPixels * 1) / 2;


                String url = "https://maps.googleapis.com/maps/api/place/photo?";
                String key = "key=" + context.getResources().getString(R.string.google_direction_api);
                String sensor = "sensor=true";
                String maxWidth = "maxwidth=" + width;
                String maxHeight = "maxheight=" + height;
                url = url + "&" + key + "&" + sensor + "&" + maxWidth + "&" + maxHeight;

                String photoReference = "photoreference=" + photoref;

                url = url + "&" + photoReference;


                System.out.println("url is:"+url);

                Picasso.get().load(url).placeholder(R.drawable.ic_launcher).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                        if (bitmap != null)
                            holder.recImg.setImageBitmap(bitmap);
                        else holder.recImg.setImageResource(R.drawable.ic_launcher);
                        System.out.println("url is: success");
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        System.out.println("url is: failed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

            }

        }


    }

    @Override
    public int getItemCount() {
        return placeDetailsArrayList.size();
    }

    protected class AllShopsListAdaptarViewHolder extends RecyclerView.ViewHolder {
        private ImageView recImg;
        private TextView recName;
        private TextView recVicinity;
        private TextView recFormattedAddress;
        private TextView recFormattedPhoneNumber;
        private TextView recOpeningHours;
        private TextView recRating;
        private TextView recPriceLevel;

        public AllShopsListAdaptarViewHolder(View view) {
            super(view);
            recImg = (ImageView) view.findViewById(R.id.rec_img);
            recName = (TextView) view.findViewById(R.id.rec_name);
            recVicinity = (TextView) view.findViewById(R.id.rec_vicinity);
            recFormattedAddress = (TextView) view.findViewById(R.id.rec_formatted_address);
            recFormattedPhoneNumber = (TextView) view.findViewById(R.id.rec_formatted_phone_number);
            recOpeningHours = (TextView) view.findViewById(R.id.rec_opening_hours);
            recRating = (TextView) view.findViewById(R.id.rec_rating);
            recPriceLevel = (TextView) view.findViewById(R.id.rec_price_level);
        }
    }
}
