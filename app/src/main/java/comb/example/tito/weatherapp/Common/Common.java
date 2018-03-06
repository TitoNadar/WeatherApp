package comb.example.tito.weatherapp.Common;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.util.Date;

/**
 * Created by Tito on 16/10/2017.
 */

public class Common {
    public static String API_KEY="f244ea91b89957039490a2e036ad16af";
    public static String API_LINK="http://api.openweathermap.org/data/2.5/weather";

    public  static String apiRequest(String lat,String lon)
    {
        StringBuilder stringBuilder=new StringBuilder(API_LINK);
        stringBuilder.append(String.format("?lat=%s&lon=%s&APPID=%s&units=metrics",lat,lon,API_KEY));
        return stringBuilder.toString();
    }
   public static String unixTimeStamptoDateTime(double unixTimeStamp)
   {
       DateFormat dateFormat=new SimpleDateFormat("HH:mm") ;
       Date date=new Date();
       date.setTime((long)unixTimeStamp*1000);
      return dateFormat.format(date);
   }
   public static String getImage(String icon )
   {
       return String.format("http://api.openweathermap.org/img/w/%s.png",icon);
   }
   public static String getDateNow( )
   {
       DateFormat dateFormat=new SimpleDateFormat("dd:mm:yyyy hh:mm");
       Date date=new Date();
       return dateFormat.format(date);

   }
}
