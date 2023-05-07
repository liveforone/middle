package middle.reservationservice.controller.constant;

public final class ReservationUrl {
    private ReservationUrl() {}

    public static final String RESERVATION_DETAIL = "/reservation/detail/{id}";
    public static final String MY_RESERVATION = "/reservation/me";
    public static final String RESERVATION_BELONG_SHOP = "/reservation/shop/{shopId}";
    public static final String RESERVE = "/reserve/{timetableId}";
}
