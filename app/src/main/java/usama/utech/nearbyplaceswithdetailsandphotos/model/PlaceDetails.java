package usama.utech.nearbyplaceswithdetailsandphotos.model;

public class PlaceDetails {


    String price_level,vicinity,formatted_address,opening_hours,name,rating,formatted_phone_number;

    public PlaceDetails(String price_level, String vicinity, String formatted_address, String opening_hours, String name, String rating, String formatted_phone_number) {
        this.price_level = price_level;
        this.vicinity = vicinity;
        this.formatted_address = formatted_address;
        this.opening_hours = opening_hours;
        this.name = name;
        this.rating = rating;
        this.formatted_phone_number = formatted_phone_number;
    }

    public String getPrice_level() {
        return price_level;
    }

    public void setPrice_level(String price_level) {
        this.price_level = price_level;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }
}
