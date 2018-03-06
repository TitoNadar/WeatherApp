package comb.example.tito.weatherapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

import comb.example.tito.weatherapp.Common.Common;
import comb.example.tito.weatherapp.Helper.Helper;
import comb.example.tito.weatherapp.Model.OpenWeatherApp;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView city, lastupdated, description, humidity, time, celsius;
    ImageView imageView;


    static double lat, lon;
    LocationManager locationManager;
    String provider;
    OpenWeatherApp openWeatherApp = new OpenWeatherApp();


    int MY_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //CONTROL
        city = (TextView) findViewById(R.id.txtcity);
        lastupdated = (TextView) findViewById(R.id.lastupdated);
        description = (TextView) findViewById(R.id.description);
        humidity = (TextView) findViewById(R.id.humidity);
        time = (TextView) findViewById(R.id.time);
        celsius = (TextView) findViewById(R.id.celsius);

        imageView = (ImageView) findViewById(R.id.imageview);


        //GET LAT AND LON
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            (Manifest.permission.INTERNET),
                            (Manifest.permission.ACCESS_COARSE_LOCATION),
                            (Manifest.permission.ACCESS_FINE_LOCATION),
                            (Manifest.permission.ACCESS_NETWORK_STATE),
                            (Manifest.permission.SYSTEM_ALERT_WINDOW),
                            (Manifest.permission.WRITE_EXTERNAL_STORAGE)}
                    , MY_PERMISSION);

            Location location = locationManager.getLastKnownLocation(provider);

            if (location == null) {
                Log.e("tito", "NO LOCATION");
            }
            lat=location.getLatitude();
            lon=location.getLongitude();
          GetWether getWether= (GetWether) new GetWether().execute(Common.apiRequest(String.valueOf(lat),String.valueOf(lon)));
        getWether.doInBackground(Common.apiRequest(String.valueOf(lat),String.valueOf(lon)));}
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                (Manifest.permission.INTERNET),
                                (Manifest.permission.ACCESS_COARSE_LOCATION),
                                (Manifest.permission.ACCESS_FINE_LOCATION),
                                (Manifest.permission.ACCESS_NETWORK_STATE),
                                (Manifest.permission.SYSTEM_ALERT_WINDOW),
                                (Manifest.permission.WRITE_EXTERNAL_STORAGE)}
                        , MY_PERMISSION);

            }
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            (Manifest.permission.INTERNET),
                            (Manifest.permission.ACCESS_COARSE_LOCATION),
                            (Manifest.permission.ACCESS_FINE_LOCATION),
                            (Manifest.permission.ACCESS_NETWORK_STATE),
                            (Manifest.permission.SYSTEM_ALERT_WINDOW),
                            (Manifest.permission.WRITE_EXTERNAL_STORAGE)}
                    , MY_PERMISSION);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lon=location.getLongitude();

        new GetWether().execute(Common.apiRequest(String.valueOf(lat),String.valueOf(lon)));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public class GetWether extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String stream=null;
            String urlstring=params[0];
            Helper helper=new Helper();
            stream=helper.getHttpData(urlstring);
            return stream;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Type type = new TypeToken<OpenWeatherApp>() {
            }.getType();
            openWeatherApp = gson.fromJson(s, type);
            city.setText(String.format("%s,%s", openWeatherApp.getName(), openWeatherApp.getSys().getCountry()));
            lastupdated.setText(String.format("LAST UPDATED:%s", Common.getDateNow()));
            description.setText(String.format("%s", openWeatherApp.getWeather().get(0).getDescription()));
            humidity.setText(String.format("%d",openWeatherApp.getMain().getHumidity()));
            time.setText(String.format("%s/%s",Common.unixTimeStamptoDateTime(openWeatherApp.getSys().getSunrise()),Common.unixTimeStamptoDateTime(openWeatherApp.getSys().getSunset())));
            celsius.setText(String.format(".2f C",openWeatherApp.getMain().getHumidity()));
            Picasso.with(MainActivity.this).load(Common.getImage(openWeatherApp.getWeather().get(0).getIcon())).into(imageView);
        }


}
}
