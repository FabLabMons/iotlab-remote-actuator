package be.fablabmons.iotlab.remote_actuator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LightControl lightControl = new MqttControl();
        setLightOffClickListener(lightControl);
        setLightOnClickListener(lightControl);
    }

    private void setLightOffClickListener(final LightControl lightControl) {
        View lightOffIcon = findViewById(R.id.lightOff);
        lightOffIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightControl.turnOff();
            }
        });
    }

    private void setLightOnClickListener(final LightControl lightControl) {
        View lightOnIcon = findViewById(R.id.lightOn);
        lightOnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightControl.turnOn();
            }
        });
    }
}
