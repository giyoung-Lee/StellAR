import paho.mqtt.client as mqtt
import json
import pyautogui


def on_connect(clinet, userdata, flags, rc):
    if rc == 0:
        client.subscribe("galaxy/action")
    else:
        print("연결 실패")


def on_message(client, userdata, message):
    data = json.loads(message.payload.decode("utf-8"))
    action = data["action"]

    # 클릭일 때
    if action == 1:
        print("클릭입니다.")
        pyautogui.click(data["start_x"], data["start_y"])

    # 화면 이동 드래그 일 때, 처음 좌표에서 끝 좌표로 드래그, duration 0.3
    elif action == 2:
        print("드래그입니다.")
        pyautogui.moveTo(data["start_x"], data["start_y"])
        pyautogui.dragTo(data["end_x"], data["end_y"], duration=0.3)

    # 확대일 때 100만큼 위로 스크롤
    elif action == 3:
        print("확대 입니다.")
        pyautogui.scroll(100)

    # 축소일 때 100만큼 아래로 스크롤
    elif action == 4:
        print("축소 입니다.")
        pyautogui.scroll(-100)

    # print("message received ", str(message.payload.decode("utf-8")))
    # print("message topic= ", message.topic)
    # print("message qos=", message.qos)
    # print("message retain flag= ", message.retain)


broker_address = "k10c105.p.ssafy.io"
client = mqtt.Client("Subscriber")
client.connect(broker_address)
# client.on_connect = on_connect
client.subscribe("galaxy/action")
client.on_message = on_message
client.loop_forever()
