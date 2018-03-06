package comb.example.tito.weatherapp.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.MalformedInputException;

/**
 * Created by Tito on 16/10/2017.
 */

public class Helper {
    public Helper(){}
    static String stream=null;
    public String getHttpData(String urlString)
    {
       try
       {
        URL url=new URL(urlString);
           HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
           if(httpURLConnection.getResponseCode()==200)
           {
               BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
               StringBuilder stringBuilder=new StringBuilder();
               String line;
               while ((line=bufferedReader.readLine())!=null)
               {
                stringBuilder.append(line);
                   stream.toString();
                   httpURLConnection.disconnect();
               }
           }
        }
        catch (MalformedInputException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return stream;
    }
}
