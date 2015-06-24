package l2ol3otic.project.graphtestapplication;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static final String matrix = "http://api.thingspeak.com/channels/9/field/1.json";
    public static final String matrix2 = "http://api.thingspeak.com/channels/9/field/2.json";

    public int count = 0;
    public int countData1 = 0;
    public int countData2 = 0;
    public ArrayList<String> OU = new ArrayList<>();
    public ArrayList<Integer> OU2= new ArrayList<>();

    public ArrayList<String> IN = new ArrayList<>();
    public ArrayList<Integer> IN2 = new ArrayList<>();

    public String[] CF1 = new String[100];
    public Integer[] CF2 = new Integer[100];
    public String[] TF1 = new String[100];
    public Double[] TF2 = new Double[100];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sampleMultipleSeries();
        new SimpleTask().execute(matrix);

    }




    private class SimpleTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        protected String doInBackground(String... urls)   {
            String result = "";
            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return result;
        }

        protected void onPostExecute(String jsonString)  {
            // Dismiss ProgressBar

           Gson gson = new Gson();
            Bigsize blog = gson.fromJson(jsonString, Bigsize.class);
            List<Post> feeds = blog.getFeeds();
            for (Post post : feeds) {
                count++;
            }
            Log.i("Test", String.valueOf(count));
            showData1(jsonString, count);
            count =0;
        }
    }
    private class SimpleTask2 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        protected String doInBackground(String... urls)   {
            String result = "";
            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return result;
        }

        protected void onPostExecute(String jsonString)  {
            // Dismiss ProgressBar


            Log.i("Test2", String.valueOf(count));
            showData2(jsonString, count);
            count =0;

        }
    }
    private void showData1(String jsonString, int countloop) {

        Gson gson = new Gson();
        Bigsize blog = gson.fromJson(jsonString, Bigsize.class);
        List<Post> feeds = blog.getFeeds();


        StringBuilder builder = new StringBuilder();
        builder.setLength(0);
        StringBuilder builder2 = new StringBuilder();
        builder2.setLength(0);
        StringBuilder builder3 = new StringBuilder();
        builder3.setLength(0);

        for (Post post : feeds) {

            builder.append(post.getCreated_at().toString());
            String g = builder.toString();
            Log.i("CFCountCount", String.valueOf(countData1));
            CF1[countData1] = g;
            //Log.i("CFCount",CF1[count]);
            builder3.append(post.getField1());
            String a3 = builder3.toString();
            int a = Integer.parseInt(a3);
            CF2[countData1] = a;
            //Log.i("Zero", String.valueOf(CF2[count]));
            this.countData1++;

            builder.setLength(0);
            builder3.setLength(0);
        }
        countData1 =0;
        new SimpleTask2().execute(matrix2);
    }
    private void showData2(String jsonString, int countloop) {
        Gson gson = new Gson();
        Bigsize blog = gson.fromJson(jsonString, Bigsize.class);
        List<Post> feeds = blog.getFeeds();


        StringBuilder builder = new StringBuilder();
        builder.setLength(0);
        StringBuilder builder2 = new StringBuilder();
        builder2.setLength(0);
        StringBuilder builder3 = new StringBuilder();
        builder3.setLength(0);

        for (Post post : feeds) {

            builder.append(post.getCreated_at().toString());
            String g = builder.toString();
            TF1[countData2] = g;
           // Log.i("Zero2", TF1[count]);

            builder3.append(post.getField2());
            String a3 = builder3.toString();
            double a = Double.parseDouble(a3);
            TF2[countData2] = a;
            //Log.i("Zero2", String.valueOf(TF2[count]));
            this.countData2++;
            builder.setLength(0);
            builder3.setLength(0);
        }
        countData2 =0;
         Graph8();


        int[]xr =  {1,2,3,4};
        Log.i("XrLenght", String.valueOf(xr.length));
        Log.i("CFLenght", String.valueOf(CF1.length));
        Log.i("TFLenght", String.valueOf(TF1.length));
        Log.i("Countloop", String.valueOf(countloop));
    }
    private void Graph8() {
        GraphView.GraphViewData[] data = new GraphView.GraphViewData[CF1.length];
        for (int i = 0; i < CF1.length; i++) {

            data[i] = new GraphView.GraphViewData(i, CF2[i]);
        }
        GraphViewSeries seriesA = new GraphViewSeries("Light",
                new GraphViewSeries.GraphViewSeriesStyle(Color.RED, 5), data);

        data = new GraphView.GraphViewData[CF1.length];
        for (int i = 0; i < CF1.length; i++) {
            data[i] = new GraphView.GraphViewData(i, TF2[i]);
        }
        GraphViewSeries seriesB = new GraphViewSeries("Temp",
                new GraphViewSeries.GraphViewSeriesStyle(Color.BLUE, 5), data);


        LineGraphView graphView = new LineGraphView(this, "Light&Temp");

        graphView.addSeries(seriesA);
        graphView.setPadding(5,5,5,5);
        graphView.addSeries(seriesB);
        graphView.setDrawDataPoints(true);
        graphView.setDataPointsRadius(10);
        graphView.setManualYMinBound(60);
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return CF1[(int) value];
                } else
                    return String.format("%.2f", value);
            }
        });
        graphView.setViewPort(96, 3);
        graphView.getGraphViewStyle().setVerticalLabelsWidth(30);
        graphView.getGraphViewStyle().setNumHorizontalLabels(4);
        graphView.getGraphViewStyle().setTextSize(20);
        graphView.getGraphViewStyle().setNumVerticalLabels(8);
        graphView.setShowLegend(true);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout);
        layout.addView(graphView);

    }




}
