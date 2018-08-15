package be.fablabmons.iotlab.remote_actuator;

import android.util.Log;

class MqttControl implements LightControl {
    private static final String STUDENT_ID = "student00"; // TODO change to unique ID

    private static final String TAG = "MqttControl";
    private static final String BROKER_CONNECT_STRING = "tcp://10.130.1.204:1883";
    private static final String TOPIC = STUDENT_ID + "/remote-actuator/light";
    private static final String CLIENT_ID = STUDENT_ID + "-android";

    @Override
    public void turnOn() {
        Log.i(TAG, "turn on the light");
        // TODO implement
    }

    @Override
    public void turnOff() {
        Log.i(TAG, "turn off the light");
        // TODO implement
    }
}
