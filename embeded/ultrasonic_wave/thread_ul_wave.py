from gpiozero import DistanceSensor
import time
from threading import Thread
import paho.mqtt.client as mqtt
import json

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

thread_break = False

TRIGGER_1 = 14
ECHO_1 = 15
TRIGGER_2 = 18
ECHO_2 = 23
TRIGGER_3 = 24
ECHO_3 = 25
TRIGGER_4 = 1
ECHO_4 = 12
TRIGGER_5 = 16
ECHO_5 = 20
TRIGGER_6 = 21
ECHO_6 = 17
TRIGGER_7 = 27
ECHO_7 = 22
TRIGGER_8 = 10
ECHO_8 = 9
TRIGGER_9 = 11
ECHO_9 = 0

sensor1 = DistanceSensor(echo=ECHO_1, trigger=TRIGGER_1, max_distance=1.5) 
sensor2 = DistanceSensor(echo=ECHO_2, trigger=TRIGGER_2, max_distance=1.5)
sensor3 = DistanceSensor(echo=ECHO_3, trigger=TRIGGER_3, max_distance=1.5)
sensor4 = DistanceSensor(echo=ECHO_4, trigger=TRIGGER_4, max_distance=1.5)
sensor5 = DistanceSensor(echo=ECHO_5, trigger=TRIGGER_5, max_distance=1.5)
sensor6 = DistanceSensor(echo=ECHO_6, trigger=TRIGGER_6, max_distance=1.5)
sensor7 = DistanceSensor(echo=ECHO_7, trigger=TRIGGER_7, max_distance=1.5)
sensor8 = DistanceSensor(echo=ECHO_8, trigger=TRIGGER_8, max_distance=1.5)
sensor9 = DistanceSensor(echo=ECHO_9, trigger=TRIGGER_9, max_distance=1.5)

sensor_arr = [sensor1, sensor2, sensor3, sensor4, sensor5, sensor6, sensor7, sensor8, sensor9]

state = 0
out_count = 4
down_count = 0
last_num = 0
last_distance = 0
value = 0
wave_num = 0
DISTANCES = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]

def cal_distance(now_pin):
    distance = sensor_arr[now_pin].distance * 100

    return distance

def ul_wave(_delay,pin_index):
    print(f"핀번호 {pin_index+1}번 일 시작합니다.")
        
    while True:
        if thread_break:
            print(f"{pin_index+1} out")
            break
        DISTANCES[pin_index]=cal_distance(pin_index)
        #print(f"{pin_index+1}번 초음파 값: {DISTANCES[pin_index]}")
        time.sleep(_delay)
        
th_1=Thread(target=ul_wave,args=(0.06,0))
th_2=Thread(target=ul_wave,args=(0.06,1))
th_3=Thread(target=ul_wave,args=(0.06,2))
th_4=Thread(target=ul_wave,args=(0.06,3))
th_5=Thread(target=ul_wave,args=(0.06,4))
th_6=Thread(target=ul_wave,args=(0.06,5))
th_7=Thread(target=ul_wave,args=(0.06,6))
th_8=Thread(target=ul_wave,args=(0.06,7))
th_9=Thread(target=ul_wave,args=(0.06,8))

th_1.start()
th_2.start()
th_3.start()
th_4.start()
th_5.start()
th_6.start()
th_7.start()
th_8.start()
th_9.start()

try:
    while True:
    #    print(DISTANCES[4])
        last_visited = 0
        visited_count = 0
        visited = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        visited_num = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        visited_distances = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        for i in range(9):
            if 30 < DISTANCES[i] < 100:
                if i - last_visited <= 1:
                    print(i)
                    visited_num[visited_count] = i
                    visited_distances[visited_count] = DISTANCES[i]
                    visited_count += 1
                    last_visited = i
    
    #    print(visited_count)
    
        if visited_count > 0:
            if visited_count > 8:
                #print("scroll")
                a=1
            else:
                ul_num = 0
                ul_distance = 0
                for i in range(visited_count):
                    ul_num += visited_num[i]
                    ul_distance += visited_distances[i]
                ul_num = ul_num/visited_count
                ul_distance = ul_distance/visited_count
                out_count = 0
                if state == 0:
                    down_count += 1
                    if down_count == 2:
                        state = 1
                        last_num = ul_num
                        last_distance = ul_distance
                        data = make_to_json("down", ul_num, ul_distance)
                        publish(client, data)
                        print("Sensor %.1f: Down - Height: %.2f cm" % (ul_num, ul_distance))
                        #print("Down")
                else:
                    last_num = ul_num
                    last_distance = ul_distance
                    last_distance = DISTANCES[last_visited]
                    data=make_to_json("move", ul_num, ul_distance)
                    publish(client,data)
                    print("Sensor %.1f: Move - Height: %.2f cm" % (ul_num, ul_distance))
                    #print("Move")
    
    
        else:
            #down_cout = 0
            out_count += 1
            if out_count == 3:
                state = 0
                down_count = 0
                data=make_to_json("Up", last_num, last_distance)
                publish(client,data)
                print("Sensor %.1f: Up - Height: %.2f cm" % (last_num, last_distance))
    
        time.sleep(0.06)
        
finally:
    thread_break = True
