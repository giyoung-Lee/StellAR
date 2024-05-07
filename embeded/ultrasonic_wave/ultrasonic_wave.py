import gpiod
import time

SENSOR_PINS = [14, 15, 18, 23, 24, 25, 8]

chip = gpiod.Chip('gpiochip4')
#states = [0, 0, 0, 0, 0, 0, 0]  
#out_counts = [0, 0, 0, 0, 0, 0, 0]  
now_distances = [0, 0, 0, 0, 0, 0, 0]
last_distances = [0, 0, 0, 0, 0, 0, 0] 
last_distance = 0
last_num = 0
break_count = 0
visited_count = 0
last_visited = 0
out_count = 0
state = 0

try:
    while True:
        visited = [0, 0, 0, 0, 0, 0, 0]
        visited_num = [0, 0, 0, 0, 0, 0, 0]
        visited_distances = [0, 0, 0, 0, 0, 0, 0]
        for i in range(6):
            break_count=0
            trigger_line = chip.get_line(SENSOR_PINS[i])
            trigger_line.request(consumer="TRIG", type=gpiod.LINE_REQ_DIR_OUT)
    
            trigger_line.set_value(1)
            time.sleep(0.00001)
            trigger_line.set_value(0)
            trigger_line.release()
    
            echo_line = chip.get_line(SENSOR_PINS[i])
            echo_line.request(consumer="ECHO", type=gpiod.LINE_REQ_DIR_IN)
            
            while echo_line.get_value() == 0:
                start = time.time()
                break_count += 1
                if break_count > 5000:
                    break

            stop = time.time()
            while echo_line.get_value() != 0:
                stop = time.time()

            check_time = stop - start
            distance = check_time * 34300 / 2
            now_distances[i]=distance

            if 30 <= now_distances[i] <= 110:
                visited[i] = 1

            echo_line.release()
            time.sleep(0.007)

        visited_count = 0
        for i in range(6):
            if visited[i] == 1:
                visited_num[visited_count] = i
                visited_distances[visited_count] = now_distances[i]
                visited_count += 1
                last_visited = i

        if visited_count > 0:
            ul_num = 0
            ul_distance = 0
            for i in range(visited_count):
                ul_num += visited_num[i]
                ul_distance += visited_distances[i]
            ul_num = ul_num/visited_count
            ul_distance = ul_distance/visited_count
            out_count = 0
            if state == 0:
                state = 1
                last_num = ul_num
                last_distance = ul_distance
                print("Sensor %.1f: Down - Height: %.2f cm" % (ul_num, ul_distance))
                #print("Down")
            else:
                last_num = ul_num
                last_distance = ul_distance
                #last_distance = now_distances[last_visited]
                print("Sensor %.1f: Move - Height: %.2f cm" % (ul_num, ul_distance))
                #print("Move")


        else:
            out_count += 1
            if out_count == 3:
                state = 0
                print("Sensor %.1f: Up - Height: %.2f cm" % (last_num, last_distance))
                
finally:
    chip.close()


