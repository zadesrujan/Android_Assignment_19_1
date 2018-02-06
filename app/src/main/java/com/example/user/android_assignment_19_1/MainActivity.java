package com.example.user.android_assignment_19_1;
//Package objects contain version information about the implementation and specification of a Java package.
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    //public is a method and fields can be accessed by the members of any class.
    //class is a collections of objects.
    //created MainActivity and extends with AppCompatActivity which is Parent class.


    TextView tv;
    //Initializing the variables

    @Override
    //we use override to tells the compiler that the following method overrides a method of its superclass.
    protected void onCreate(Bundle savedInstanceState) {
        //protected can be accessed by within the package and class and subclasses
        //The Void class is an uninstantiable placeholder class to hold a reference to the Class object
        //representing the Java keyword void.
        //The savedInstanceState is a reference to a Bundle object that is passed into the onCreate method of every Android Activity
        //Activities have the ability, under special circumstances, to restore themselves to a previous state using the data stored in this bundle.
        super.onCreate(savedInstanceState);
        //Android class works in same.You are extending the Activity class which have onCreate(Bundle bundle) method in which meaningful code is written
        //So to execute that code in our defined activity. You have to use super.onCreate(bundle)
        setContentView(R.layout.activity_main);
        //R means Resource
        //layout means design
        //main is the xml you have created under res->layout->main.xml
        //Whenever you want to change your current Look of an Activity or when you move from one Activity to another .
        //he other Activity must have a design to show . So we call this method in onCreate and this is the second statement to set
        //the design

        tv =(TextView)findViewById(R.id.tv);
        //giving as id as tv
        new weather().execute("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1");
    }
    private class weather extends AsyncTask<String,Void,String> {
        //This class is responsible for fetching the weather data from the OpenWeatherMap API.
        //We use the HttpURLConnection class to make the remote request

        @Override
        //we use override to tells the compiler that the following method overrides a method of its superclass.
        protected String doInBackground(String... strings) {
            //here in this doinbackground method contains the code which needs to be executed in background.
            try {
                URL url = new URL(strings[0]);
                //craeting the object as url.
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                //Obtain a new HttpURLConnection by calling URL.openConnection() and casting the result to HttpURLConnection.
                InputStream stream=conn.getInputStream();
                //created object as stream and whatever the input stream will get it will store in stream.
                BufferedReader reader= new BufferedReader(new InputStreamReader(stream));
                //using BufferedReader to read the API's response into a StringBuilder.
                StringBuilder builder = new StringBuilder();
                //When we have the complete response, we convert it to a JSONObject object.
                //AsyncTask can be called only once. Executing it again will throw an exception
                //Using BufferReader we read the obj in the inputstream
                String inputstring;
                //here it reads the inputstring if it is not equal to zero it append the inputstring
                while ((inputstring=reader.readLine())!=null){
                    builder.append(inputstring);
                    //Appends the string representation of the inputstring argument to this sequence.
                }
                JSONObject json = new JSONObject(builder.toString());
                //here creating Json objects as in the url conn the viewer data will be in braces which is known as JSOn objects
                JSONObject main = json.getJSONObject("main");
                //now here in this Json obj we have main which contains json objects and initializing them in the getJSONobject string
                String temp = main.getString("temp");
                //here creating string type to get the temperature
                return temp;
                //reutrns the temp
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        //we use override to tells the compiler that the following method overrides a method of its superclass.
        protected void onPostExecute(String s) {
            //This method is called after doInBackground method completes processing.
            //Result from doInBackground is passed to this method
            super.onPostExecute(s);
            //it sets the text and displays the temp
            tv.setText("Temperature is "+s);
            //setting to text as temp is....

        }
    }
}

