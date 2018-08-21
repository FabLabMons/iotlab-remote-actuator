package be.fablabmons.iotlab.remote_actuator;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

class MqttControl implements LightControl {
    private static final String STUDENT_ID = "teacher"; // TODO change to unique ID

    private static final String TAG = "MqttControl";
    private static final String BROKER_CONNECT_STRING = "tcp://10.10.32.169:1883";
    private static final String TOPIC = STUDENT_ID + "/remote-actuator/light";
    private static final String CLIENT_ID = STUDENT_ID + "-android";

    private MqttClientPersistence persistence = new MemoryPersistence();

    @Override
    public void turnOn() {
        Log.i(TAG, "turn on the light");
        tryToSendMessage("ON");
    }

    private void tryToSendMessage(String message) {
        try {
            sendMessage(message);
        } catch (MqttException e) {
            Log.i(TAG, "turnOn: MQTT exception", e);
        }
    }

    private void sendMessage(String message) throws MqttException {
        MqttClient mqttClient = connectClient();
        publishMessage(mqttClient, message);
        mqttClient.disconnect();
    }

    private MqttClient connectClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(BROKER_CONNECT_STRING, CLIENT_ID, persistence);
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        mqttClient.connect(connectOptions);
        return mqttClient;
    }

    private void publishMessage(MqttClient mqttClient, String msgString) throws MqttException {
        MqttMessage message = new MqttMessage(msgString.getBytes());
        message.setQos(0);
        message.setRetained(true);
        mqttClient.publish(TOPIC, message);
    }

    @Override
    public void turnOff() {
        Log.i(TAG, "turn off the light");
        tryToSendMessage("OFF");
    }
}
