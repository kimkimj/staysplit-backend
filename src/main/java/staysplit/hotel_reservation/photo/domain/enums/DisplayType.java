package staysplit.hotel_reservation.photo.domain.enums;

public enum DisplayType {
    MAIN,
    ADDITIONAL;

    public static DisplayType from(String value) {
        return DisplayType.valueOf(value.toUpperCase());
    }
}
