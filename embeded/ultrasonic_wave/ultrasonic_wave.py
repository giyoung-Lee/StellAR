import gpiod
import time

TRIG_PIN = 23
ECHO_PIN = 23

chip = gpiod.Chip('gpiochip4')
before_time = time.time()
before_distance = 0
state = 0
drag_num = 0
out_count = 0

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
        print("Distance : %.1f " %distance)
        if distance <= 110 and distance >= 30:
            state = 0
            out_count = 0
            print(abs(before_distance-distance))
            if abs(before_distance-distance) >= 3:
                if time.time()-before_time >= 0.3:
                    state = 0
                else:
                    drag_num += 1
                    if drag_num >= 6:
                        state = 1
            else:
                state = 0

            before_time = time.time()
            before_distance = distance

        else:
            if out_count == 3:
                if state == 0:
                    print("Touch")
                else:
                    print("Drag")
                #print(state)
                drag_num = 0
            out_count += 1
            #drag_num = 0
            #if out_count == 3:
            #    print(state)

        #print("Distance : %.2f cm" % distance)
        #print(time.time())
        echo_line.release()
        time.sleep(0.1)
finally:
    chip.close()

