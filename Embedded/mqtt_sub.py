import paho.mqtt.client as mqtt

def on_message(client, userdata, message):
        print("message received ", str(message.payload.decode("utf-8")))
        print("message topic= ", message.topic)
        print("message qos=", message.qos)
        print("message retain flag= ", message.retain)

broker_address="k10c105.p.ssafy.io"
client=mqtt.Client("Client")
client.connect(broker_address, 1883)
client.subscribe("asd")
client.on_message = on_message
client.loop_forever()