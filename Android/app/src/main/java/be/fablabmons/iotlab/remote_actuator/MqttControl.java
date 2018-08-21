package be.fablabmons.iotlab.remote_actuator;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

class MqttControl implements LightControl {
    private static final String STUDENT_ID = "teacher";

    private static final String TAG = "MqttControl";
    private static final String BROKER_CONNECT_STRING = "tcp://10.10.32.169:1883";
    private static final String TOPIC = STUDENT_ID + "/remote-actuator/light";
    private static final String CLIENT_ID = STUDENT_ID + "-android";
    private MemoryPersistence persistence = new MemoryPersistence();

    @Override
    public void turnOn() {
        Log.i(TAG, "turn on the light");
        sendMessage("ON");
    }

    private void sendMessage(String msgString) {
        try {
            MqttClient sampleClient = new MqttClient(BROKER_CONNECT_STRING, CLIENT_ID, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
            MqttMessage message = new MqttMessage(msgString.getBytes());
            message.setQos(0);
            sampleClient.publish(TOPIC, message);
            sampleClient.disconnect();
        } catch (MqttException e) {
            Log.i(TAG, "turnOn: MQTT error", e);
        }
    }

    @Override
    public void turnOff() {
        Log.i(TAG, "turn off the light");
        sendMessage("OFF");
    }
}
