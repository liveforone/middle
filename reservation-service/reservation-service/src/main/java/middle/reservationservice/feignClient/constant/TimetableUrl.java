package middle.reservationservice.feignClient.constant;

public final class TimetableUrl {
    private TimetableUrl() {}

    public static final String BASE = "timetable-service";
    public static final String GET_SHOP_ID = "/get/shop-id/{id}";
    public static final String RESERVE_TIMETABLE = "/reserve/timetable/{id}";
    public static final String CANCEL_TIMETABLE = "/cancel/timetable/{id}";
}
