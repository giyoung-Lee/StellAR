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

    # 클릭일 때
    if action == 1:
        print("클릭입니다.")
        # pyautogui.click(data["start_x"], data["start_y"])
        
        # 최고점이 1개 일 때 
        if data['sensor_cnt']<2:
            x=((data['sensor_num'][0]*6.25))*screen_width/100
            y=screen_hegint-((data['height']-30.0)*screen_hegint/75)
            pyautogui.click(x,y)
            
        # 최고점이 2개 이상일 때
        else:
            sensor_num=sum(data['sensor_num'])//data["sensor_cnt"]
            x=((sensor_num*6.25)+3.125)*screen_width/100
            y=screen_hegint-((data['height']-30.0)*screen_hegint/75)
            pyautogui.click(x,y)

    # 화면 이동 드래그 일 때, 처음 좌표에서 끝 좌표로 드래그, duration 0.3
    elif action == 2:
        print("드래그입니다.")
        pyautogui.moveTo(data["start_x"], data["start_y"])
        pyautogui.dragTo(data["end_x"], data["end_y"], duration=0.3)

    # 확대일 때 100만큼 위로 스크롤
    elif action == 3:
        print("확대 입니다.")
        pyautogui.scroll(300)

    # 축소일 때 100만큼 아래로 스크롤
    elif action == 4:
        print("축소 입니다.")
        pyautogui.scroll(-100)

    # print("message received ", str(message.payload.decode("utf-8")))
    # print("message topic= ", message.topic)
    # print("message qos=", message.qos)
    # print("message retain flag= ", message.retain)


broker_address = "k10c105.p.ssafy.io"

# broker에 연결
client = mqtt.Client("Subscriber")
client.connect(broker_address)

#콜백 함수 
# client.subscribe("galaxy/action")
client.on_connect = on_connect
client.on_message = on_message

client.loop_forever()
