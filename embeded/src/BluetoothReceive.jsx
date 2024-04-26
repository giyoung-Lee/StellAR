import { useEffect } from "react";

const BluetoothReceive = () => {    

  // 블루투스 기기 연결
  const connectBluetoothDevice = async () => {
    try {
      const device = await navigator.bluetooth.requestDevice({
        filters: [
          {
            services: [0xFFE0],
          },
        ],
      });

      const server = await device.gatt.connect();
      const service = await server.getPrimaryService(0xFFE0);

      const characteristic = await service.getCharacteristic(0xFFE1);

      await characteristic.startNotifications();
      characteristic.addEventListener(
        "characteristicvaluechanged",
        handleCharacteristicValueChanged
      );
      console.log("블루투스 기기에 연결되었습니다.");
    } catch (error) {
      console.error("블루투스 연결 실패:", error);
    }
  };

  // 데이터 처리 함수
  const handleCharacteristicValueChanged = (event) => {
    const value = event.target.value;
    const receivedData = value.getInt8(0); // 8비트 정수로 데이터를 읽음
    console.log("수신된 데이터:", receivedData);
  };

  useEffect(() => {
    // connectBluetoothDevice();
    const bluetoothButton=document.getElementById("bluetoothButton")

    bluetoothButton.addEventListener("click",()=>{
      connectBluetoothDevice();

    })
  

  }, []);

  

  return (
    <>
  <div>BluetoothReceive</div>
  <button id="bluetoothButton">블루투스 검색!</button>
    </>  
  )

};

export default BluetoothReceive;