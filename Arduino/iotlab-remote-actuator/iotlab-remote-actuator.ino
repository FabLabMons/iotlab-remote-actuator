#include <BridgeClient.h>
#include <PubSubClient.h>

const char* broker = "10.10.32.169";
const int port = 1883;
const char* topic = "teacher/remote-actuator/light";
const char* mqttClientId = "teacher-arduino";

const int ledPin = 3;

BridgeClient bridgeClient;
PubSubClient pubSubClient;

void setup() {
  Serial.begin(9600);
  delay(2000);

  Serial.println("Setup START");
  
  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, LOW);
  
  Bridge.begin();
  
  pubSubClient.setServer(broker, port);
  pubSubClient.setClient(bridgeClient);
  pubSubClient.setCallback(callback);
  
  pubSubClient.connect(mqttClientId);

  pubSubClient.subscribe(topic);

  Serial.println("Setup END");
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.println("Callback START");
  String message = "";
  for (int i = 0; i < length; i++) {
    message = message + ((char)payload[i]);
  }
  Serial.println(message);
  if (message == "ON") {
    Serial.println("Led ON");
    digitalWrite(ledPin, HIGH);
  } else {
    Serial.println("Led OFF");
    digitalWrite(ledPin, LOW);
  }
  Serial.println("Callback END");
}

void loop() {
  pubSubClient.loop();
}
