import paho.mqtt.client as mqtt
import json
import pyautogui

screen_width, screen_hegint=pyautogui.size()

#on_connect 콜백 함수, 처음 연결될 때 실행 됨
def on_connect(client, userdata, flags, rc):
    if rc == 0:
        client.subscribe("galaxy/action")
    else:
        print("연결 실패")


#on_message 콜백함수, 새로운 메세지가 들어올 때 마다 실행 됨
def on_message(client, userdata, message):
    data = json.loads(message.payload.decode("utf-8"))
    action = data["action"]   

    # mouse_down일 때 
    if action == "down":
        x=((data['sensor_num']*6.25))*screen_width/100
        y=screen_hegint-((data['height']-30.0)*screen_hegint/75)
        pyautogui.mouseDown(x,y)

    # mouse_up 일 때 
    elif action == "up":
        x=((data['sensor_num']*6.25))*screen_width/100
        y=screen_hegint-((data['height']-30.0)*screen_hegint/75)
        pyautogui.mouseUp(x,y)

    # move일 때 
    elif action == "move":
        x=((data['sensor_num']*6.25))*screen_width/100
        y=screen_hegint-((data['height']-30.0)*screen_hegint/75)
        pyautogui.moveTo(x,y)
        
    # scroll_up 일 때 
    elif action == "scroll_up":
        pyautogui.scroll(300)
        
        
    # scroll_dwon 일 때
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
