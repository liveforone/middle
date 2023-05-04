package middle.timetableservice.controller.constant;

public final class TimetableUrl {

    private TimetableUrl() {}

    public static final String TIMETABLE_PAGE_BY_SHOP = "/timetables/{shopId}";
    public static final String TIMETABLE_DETAIL = "/timetable/detail/{id}";
    public static final String CREATE_TIMETABLE = "/timetable/create/{shopId}";
    public static final String UPDATE_TIME = "/timetable/update-time/{id}";
    public static final String DELETE_TIMETABLE = "/timetable/delete/{id}";
}