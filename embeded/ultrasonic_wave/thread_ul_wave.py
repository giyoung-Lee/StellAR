import gpiod
import time
from threading import Thread
import paho.mqtt.client as mqtt
import json

chip = gpiod.Chip('gpiochip4')

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

state = 0
out_count = 0
last_distance = 0 

DISTANCES = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]

trigger1_line = chip.get_line(11)
trigger1_line.request(consumer="TRIG", type=gpiod.LINE_REQ_DIR_OUT)
echo1_line = chip.get_line(0)
echo1_line.request(consumer="ECHO", type=gpiod.LINE_REQ_DIR_IN)

trigger2_line = chip.get_line(10)
trigger2_line.request(consumer="TRIG2", type=gpiod.LINE_REQ_DIR_OUT)
echo2_line = chip.get_line(9)
echo2_line.request(consumer="ECHO2", type=gpiod.LINE_REQ_DIR_IN)

trigger3_line = chip.get_line(27)
trigger3_line.request(consumer="TRIG3", type=gpiod.LINE_REQ_DIR_OUT)
echo3_line = chip.get_line(22)
echo3_line.request(consumer="ECHO3", type=gpiod.LINE_REQ_DIR_IN)

trigger4_line = chip.get_line(21)
trigger4_line.request(consumer="TRIG4", type=gpiod.LINE_REQ_DIR_OUT)
echo4_line = chip.get_line(17)
echo4_line.request(consumer="ECHO4", type=gpiod.LINE_REQ_DIR_IN)

trigger5_line = chip.get_line(16)
trigger5_line.request(consumer="TRIG5", type=gpiod.LINE_REQ_DIR_OUT)
echo5_line = chip.get_line(20)
echo5_line.request(consumer="ECHO5", type=gpiod.LINE_REQ_DIR_IN)

trigger6_line = chip.get_line(1)
trigger6_line.request(consumer="TRIG6", type=gpiod.LINE_REQ_DIR_OUT)
echo6_line = chip.get_line(12)
echo6_line.request(consumer="ECHO6", type=gpiod.LINE_REQ_DIR_IN)

trigger7_line = chip.get_line(24)
trigger7_line.request(consumer="TRIG7", type=gpiod.LINE_REQ_DIR_OUT)
echo7_line = chip.get_line(25)
echo7_line.request(consumer="ECHO7", type=gpiod.LINE_REQ_DIR_IN)

trigger8_line = chip.get_line(18)
trigger8_line.request(consumer="TRIG8", type=gpiod.LINE_REQ_DIR_OUT)
echo8_line = chip.get_line(23)
echo8_line.request(consumer="ECHO8", type=gpiod.LINE_REQ_DIR_IN)

trigger9_line = chip.get_line(14)
trigger9_line.request(consumer="TRIG9", type=gpiod.LINE_REQ_DIR_OUT)
echo9_line = chip.get_line(15)
echo9_line.request(consumer="ECHO9", type=gpiod.LINE_REQ_DIR_IN)

trig_arr = [trigger1_line, trigger2_line, trigger3_line, trigger4_line, trigger5_line, trigger6_line, trigger7_line, trigger8_line, trigger9_line]
echo_arr = [echo1_line, echo2_line, echo3_line, echo4_line, echo5_line, echo6_line, echo7_line, echo8_line, echo9_line]

state = 0
out_count = 10
down_count = 0
move_count = 0
last_num = 0
last_distance = 0
value = 0
wave_num = 0
down_average = 0
pin_num = 0
move_state = 0
DISTANCES = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]

def cal_distance(now_pin):
    trig_arr[now_pin].set_value(1)
    time.sleep(0.00001)
    trig_arr[now_pin].set_value(0)
    
    while echo_arr[now_pin].get_value() == 0:
        start = time.time()
        
    while echo_arr[now_pin].get_value() != 0:
        stop = time.time()
    
    check_time = stop - start
    distance = check_time * 34300/2

    return distance

def ul_wave(_delay,pin_index):
    pin_num = 0
    print("Thread start")

    while True:
        if thread_break:
            print("ul_wave out")
            break
        DISTANCES[pin_num]=cal_distance(pin_num)
        #print(f"{pin_num+1}번 초음파 값: {DISTANCES[pin_num]}")
        if pin_num < 8:
            pin_num += 1
        else:
            pin_num = 0
        time.sleep(_delay)
        
def connect(_delay):
    while True:
        if thread_break:
            print("connect out")
            break
        #client=mqtt.Client("Publisher")
        client.connect(broker_address,1883)
        time.sleep(_delay)

dis_thread = Thread(target=ul_wave,args=(0.025,0))
connect_thread = Thread(target=connect,args=(1,))

dis_thread.start()
connect_thread.start()

try:
    while True:
        #client=mqtt.Client("Publisher")
        #client.connect(broker_address,1883)
        last_visited = 0
        visited_count = 0
        visited = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        visited_num = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        visited_distances = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        for i in range(9):
            #print(DISTANCES[8])
            if 30 < DISTANCES[i] < 90:
                #print(i)
                if i == 0:
                    pin_num = 0.5
                else:
                    pin_num = i
                visited_num[visited_count] = pin_num
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
                ul_distance = (ul_distance/visited_count)//1
                out_count = 0
                if state == 0:
                    down_count += 1
                    down_average += ul_distance
                    if down_count == 10:
                        ul_distance = (down_average/down_count)//1
                        down_average = 0
                        state = 1
                        last_num = ul_num
                        last_distance = ul_distance
                        
                        #data = make_to_json("click", ul_num, ul_distance)
                        #publish(client, data)
                        #print("Sensor %.1f: Click - Height: %.2f cm" % (ul_num, ul_distance))
                        #print("Down")
                else:
                    move_count += 1
                    if move_count == 2:
                        move_state = 1
                        data = make_to_json("down", ul_num, ul_distance)
                        publish(client, data)
                        print("Sensor %.1f: Down - Height: %.2f cm" % (ul_num, ul_distance))

                    if move_state == 1:
                        #if abs(last_distance - ul_distance) < 15:
                        move_count = 0
                        last_num = ul_num
                        last_distance = ul_distance
                        data=make_to_json("move", ul_num, ul_distance)
                        publish(client,data)
                        print("Sensor %.1f: Move - Height: %.2f cm" % (ul_num, ul_distance))
                            #print("Move")
    
    
        else:
            #down_cout = 0
            out_count += 1
            if out_count == 8:
                if state == 0:
                    state = 0
                    down_count = 0
                    move_count = 0
                    move_state = 0
                    data = make_to_json("click", ul_num, ul_distance)
                    publish(client, data)
                    print("Sensor %.1f: Click - Height: %.2f cm" % (ul_num, ul_distance))
                else:
                    state = 0
                    down_count = 0
                    move_count = 0
                    move_state = 0
                    data=make_to_json("up", last_num, last_distance)
                    publish(client,data)
                    print("Sensor %.1f: Up - Height: %.2f cm" % (last_num, last_distance))
    
        time.sleep(0.1)
        
finally:
    thread_break = True

