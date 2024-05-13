import gpiod
import time
from threading import Thread

chip = gpiod.Chip('gpiochip4')

state = 0
out_count = 0
last_num = 0
last_distance = 0
value = 0
wave_num = 0
DISTANCES = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
PINS = [14, 15, 18, 23, 24, 25, 8, 7, 2, 3, 4, 17, 27, 22, 10, 9]

def cal_distance(now_pin):
    break_count = 0
    value = 0

    trigger_line = chip.get_line(now_pin)
    trigger_line.request(consumer="TRIG", type=gpiod.LINE_REQ_DIR_OUT)
    
    trigger_line.set_value(1)
    time.sleep(0.00001)
    trigger_line.set_value(0)
    trigger_line.release()
    
    echo_line = chip.get_line(now_pin)
    echo_line.request(consumer="ECHO", type=gpiod.LINE_REQ_DIR_IN)
    
    while echo_line.get_value() == 0:
        start = time.time()
        break_count += 1
        if break_count > 2000:
            value = 1
            echo_line.release()
            return 10
            
    while echo_line.get_value() != 0:
        stop = time.time()

        
    check_time = stop - start
    distance = check_time * 34300/2
    echo_line.release()

    return distance

def ul_wave(_delay,pin_index):
    print(f"핀번호 {PINS[pin_index]}번 일 시작합니다.")
        
    while True:
        DISTANCES[pin_index]=cal_distance(PINS[pin_index])
        print(f"{pin_index}번 초음파 값: {DISTANCES[pin_index]}")
        time.sleep(_delay)
        
def ul_wave_avg(_delay,pin_index):
    filteredValue=0
    while True:
        for i in range(100):
            filteredValue+=cal_distance(PINS[pin_index])
            time.sleep(0.01)

        filteredValue/=100
        print(f"{pin_index}번 초음파 값: {filteredValue}")
        time.sleep(_delay)
    
th_1=Thread(target=ul_wave_avg,args=(0.2,0))
# th_2=Thread(target=ul_wave_avg,args=(0.2,1))
# th_3=Thread(target=ul_wave_avg,args=(0.2,2))
# th_4=Thread(target=ul_wave_avg,args=(0.2,3))
# th_5=Thread(target=ul_wave_avg,args=(0.2,4))


th_1.start()
# th_2.start()
# th_3.start()
# th_4.start()
# th_5.start()
