import paho.mqtt.client as mqtt
import json
import pyautogui
import sys

screen_width, screen_hegint=pyautogui.size()
pyautogui.FAILSAFE = False

#on_connect 콜백 함수, 처음 연결될 때 실행 됨
def on_connect(client, userdata, flags, rc):
    if rc == 0:
        client.subscribe("galaxy/action")
    else:
        print("연결 실패")

def on_disconnect(client, userdata, rc):
    print("mqtt 연결이 끊겼습니다. 종료합니다.")
    
    sys.exit(0)
    

#on_message 콜백함수, 새로운 메세지가 들어올 때 마다 실행 됨
def on_message(client, userdata, message):
    data = json.loads(message.payload.decode("utf-8"))
    action = data["action"] 
    # if sensor_num<0.3:
    #     x_value=sensor_num*1.25
    # else:
    #     x_value=sensor_num*12.5
    
    if data['sensor_num']  is not None:
        sensor_num=data['sensor_num']

        x=((sensor_num*12.5)-3.125)*screen_width/100
        y=screen_hegint-((data['height']-30.0)*screen_hegint/61.5)
    print("msg: ",data)

    # mouse_down일 때 
    if action == "down":
        pyautogui.mouseDown(x,y)

    # mouse_up 일 때 
    elif action == "up":
        pyautogui.mouseUp()
        
    elif action =="click":
        pyautogui.click(x,y)

    # move일 때 
    elif action == "move":
        pyautogui.moveTo(x,y)
        
    # scroll_up 일 때 
    elif action == "scroll_up":
        pyautogui.scroll(300)
        
        
    # scroll_down 일 때
    elif action == "scroll_down":
        pyautogui.scroll(-300)

broker_address = "k10c105.p.ssafy.io"

# broker에 연결
client = mqtt.Client("Subscriber")
client.connect(broker_address)

#콜백 함수 
# client.subscribe("galaxy/action")
client.on_connect = on_connect
client.on_message = on_message

client.loop_forever()
