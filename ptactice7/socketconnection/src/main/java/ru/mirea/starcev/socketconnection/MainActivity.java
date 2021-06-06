package ru.mirea.starcev.socketconnection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private int port = 13;
    private String host = "time-a.nist.gov";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    }

    public void onClick(View view) {
        GetTimeTask timeTask = new GetTimeTask();
        timeTask.execute();
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String timeResult = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine();
                timeResult = reader.readLine();
                socket.close();
                //Log.d("tag", timeResult );
            } catch (IOException e) {
                e.printStackTrace();
            }

            return timeResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
        }
    }
}