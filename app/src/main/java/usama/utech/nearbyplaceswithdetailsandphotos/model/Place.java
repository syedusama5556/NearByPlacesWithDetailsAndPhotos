package usama.utech.nearbyplaceswithdetailsandphotos.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {
    /**
     * Generates an instance of Place class from Parcel
     */
    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            // TODO Auto-generated method stub
            return null;
        }
    };
    // Latitude of the place

    public String mLat = "",mPlaceId ="";
    // Longitude of the place
    public String mLng = "";
    // Place Name
    public String mPlaceName = "";
    // Vicinity of the place
    public String mVicinity = "";
    // Photos of the place
    // Photo is a Parcelable class
    public Photo[] mPhotos = {};

    public Place() {
    }

    /**
     * Initializing Place object from Parcel object
     */
    public Place(Parcel in) {
        this.mLat = in.readString();
        this.mLng = in.readString();
        this.mPlaceName = in.readString();
        this.mVicinity = in.readString();
        this.mPlaceId = in.readString();
        this.mPhotos = (Photo[]) in.readParcelableArray(Photo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Writing Place object data to Parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLat);
        dest.writeString(mLng);
        dest.writeString(mPlaceName);
        dest.writeString(mVicinity);
        dest.writeString(mPlaceId);
        dest.writeParcelableArray(mPhotos, 0);
    }
}