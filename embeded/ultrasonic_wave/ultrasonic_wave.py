import gpiod
import time
import paho.mqtt.client as mqtt
import json

TRIG_PIN = 23
ECHO_PIN = 23

chip = gpiod.Chip('gpiochip4')
state = 0
out_count = 0
last_distance = 0

def make_to_json(action,sensor_num=None,height=None):
  action={
  "action": action,
  "sensor_num": sensor_num,
  "height":height
  }

  data=json.dumps(action)
  return data

def publish(client,msg):
  client.publish("galaxy/action",msg)
  
broker_address="k10c105.p.ssafy.io"

#broker에 연결 
client=mqtt.Client("Publisher")
client.connect(broker_address,1883)

try:
    while True:
        trigger_line = chip.get_line(TRIG_PIN)
        trigger_line.request(consumer="TRIG", type=gpiod.LINE_REQ_DIR_OUT)

        trigger_line.set_value(1)
        time.sleep(0.00001)
        trigger_line.set_value(0)
        trigger_line.release()

        echo_line = chip.get_line(ECHO_PIN)
        echo_line.request(consumer="ECHO", type=gpiod.LINE_REQ_DIR_IN)
        
        while echo_line.get_value() == 0:
            start = time.time()
            
        while echo_line.get_value() != 0:
            stop = time.time()
        
        check_time = stop - start
        distance = check_time * 34300/2
        #print("Distance : %.1f " %distance)
        if distance <= 110 and distance >= 30:
            out_count = 0
            if state == 0:
                state=1
                data=make_to_json("down",ECHO_PIN,distance)
                publish(client,data)
                last_distance = distance
                print("action: Down!!!!!!!!!!!!!!!!!!!!!\nsensor_num: %d\nheight: %d" % (ECHO_PIN, distance))

            else:
                data=make_to_json("move",ECHO_PIN,distance)
                publish(client,data)
                last_distance = distance
                print("action: Move\nsensor_num: %d\nheight: %d" % (ECHO_PIN, distance))

        else:
            out_count += 1
            if out_count == 3:
                state = 0
                data=make_to_json("Up",ECHO_PIN,last_distance)
                publish(client,data)
                print("action: Up\nsensor_num: %d\nheight: %d" % (ECHO_PIN, last_distance))

        #print("Distance : %.2f cm" % distance)
        #print(time.time())
        echo_line.release()
        time.sleep(0.1)
finally:
    chip.close()

