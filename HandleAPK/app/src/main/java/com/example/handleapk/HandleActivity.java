package com.example.handleapk;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HandleActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private Socket client;
    private OutputStream sender;
    private Thread connectionThread;

    private String userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle);

        // Intent에서 "userInput" 키에 해당하는 문자열 값을 가져옵니다.
        userInput = getIntent().getStringExtra("userInput");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscopeSensor == null) {
            showToast("자이로스코프 센서를 찾을 수 없습니다.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        startSocketConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        closeSocketConnection();
    }

    private void startSocketConnection() {
        connectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new Socket();
                    InetSocketAddress ipep = new InetSocketAddress("172.20.10.11", 1370);
                    client.connect(ipep);
                    sender = client.getOutputStream();
                } catch (Exception e) {
                    showToast("소켓 연결 실패");
                }
            }
        });
        connectionThread.start();
    }

    private void closeSocketConnection() {
        try {
            if (sender != null) {
                sender.close();
            }
            if (client != null) {
                client.close();
            }
            if (connectionThread != null) {
                connectionThread.interrupt();
            }
        } catch (Exception e) {
            showToast("소켓 종료 실패");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            String msg = "{\"userName\":" + "\"" + userInput + "\"" + "," + "\"message\":" + "\"handle\"," +
                    "\"data\": {\"X\": " + "\"" + event.values[0] + "\"" +
                    ", \"Y\": " + "\"" + event.values[1] + "\"" +
                    ", \"Z\": " + "\"" + event.values[2] + "\"" + "}}" + "\n";
            Log.i("MainActivity", msg);
            sendData(msg.getBytes());
        }
    }

    private void sendData(final byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sender != null) {
                        sender.write(data);
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "데이터 전송 실패");
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 센서 정확도가 변경되면 호출됩니다. 본 예제에서는 사용되지 않습니다.
    }

    private void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
