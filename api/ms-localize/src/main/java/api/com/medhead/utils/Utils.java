package api.com.medhead.utils;

public class Utils {
    public static String convertSecondsToTime(Long seconds){
        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;
        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }


}
