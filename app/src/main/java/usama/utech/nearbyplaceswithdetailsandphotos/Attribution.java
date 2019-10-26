package usama.utech.nearbyplaceswithdetailsandphotos;

import android.os.Parcel;
import android.os.Parcelable;

public class Attribution implements Parcelable {

    /**
     * Generates an instance of Attribution class from Parcel
     */
    public static final Creator<Attribution> CREATOR = new Creator<Attribution>() {

        @Override
        public Attribution createFromParcel(Parcel source) {
            return new Attribution(source);
        }

        @Override
        public Attribution[] newArray(int size) {
            // TODO Auto-generated method stub
            return null;
        }
    };
    // Attribution of the photo
    public String mHtmlAttribution = "";

    public Attribution() {

    }

    /**
     * Initializing Attribution object from Parcel object
     */
    public Attribution(Parcel in) {
        this.mHtmlAttribution = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writing Attribution object data to Parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mHtmlAttribution);
    }
}
