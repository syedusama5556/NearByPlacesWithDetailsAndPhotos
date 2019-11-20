package usama.utech.nearbyplaceswithdetailsandphotos.Adaptar;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.recFormattedPhoneNumber.setText("MOBILE PHONE :" + placeDetailsArrayList.get(position).getFormatted_phone_number());
        holder.recName.setText("" + placeDetailsArrayList.get(position).getName());
        holder.recOpeningHours.setText("BUSINESS HOURS :" + placeDetailsArrayList.get(position).getOpening_hours());
        holder.recPriceLevel.setText("Payment Method :" + placeDetailsArrayList.get(position).getPrice_level());
        holder.recRating.setText("Services :" + placeDetailsArrayList.get(position).getRating());
        holder.recVicinity.setText("Owner :" + placeDetailsArrayList.get(position).getVicinity());


        if (placeDetailsArrayList.get(position).getImguri() != 0) {
            holder.recImg.setImageResource(placeDetailsArrayList.get(position).getImguri());
            System.out.println("url is: success");

        } else {
            holder.recImg.setImageResource(R.drawable.ic_launcher);
        }
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

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
        private CardView recCard;

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
            recCard = (CardView) view.findViewById(R.id.rec_cardview);

        }
    }
}
