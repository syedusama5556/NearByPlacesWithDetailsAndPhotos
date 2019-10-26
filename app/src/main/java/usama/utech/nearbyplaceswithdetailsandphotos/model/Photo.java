package usama.utech.nearbyplaceswithdetailsandphotos.model;

import android.os.Parcel;
import android.os.Parcelable;

import usama.utech.nearbyplaceswithdetailsandphotos.Attribution;

public class Photo implements Parcelable {

    /**
     * Generates an instance of Place class from Parcel
     */
    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            // TODO Auto-generated method stub
            return null;
        }
    };
    // Width of the Photo
    public int mWidth = 0;
    // Height of the Photo
    public int mHeight = 0;
    // Reference of the photo to be used in Google Web Services
    public String mPhotoReference = "";
    // Attributions of the photo
    // Attribution is a Parcelable class
    public Attribution[] mAttributions = {};

    public Photo() {
    }

    /**
     * Initializing Photo object from Parcel object
     */
    public Photo(Parcel in) {
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
        this.mPhotoReference = in.readString();
        this.mAttributions = (Attribution[]) in.readParcelableArray(Attribution.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Writing Photo object data to Parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mWidth);
        dest.writeInt(mHeight);
        dest.writeString(mPhotoReference);
        dest.writeParcelableArray(mAttributions, 0);

    }
}