import { useEffect } from "react";

const BluetoothReceive = () => {    

  // 블루투스 기기 연결
  const connectBluetoothDevice = async () => {
    try {
      const device = await navigator.bluetooth.requestDevice({
        acceptAllDevices:true,
        optionalServices:['battery_service'],
        // filters: [
        //   {services: ['0000180a-0000-1000-8000-00805f9b34fb']}
        //   // {name: 'raspberrypi'}
        // ],
      });

      console.log(device.name);

      console.log('Connecting to GATT Server...');
      const server = await device.gatt.connect();

      const services = await server.getPrimaryServices();
      services.forEach(service => console.log(`Service: ${service.uuid}`));
  
      console.log('Getting Battery Service...');
      const service = await server.getPrimaryService('battery_service');
  
      console.log('Getting Battery Level Characteristic...');
      const characteristic = await service.getCharacteristic('battery_level');
  
      console.log('Reading Battery Level...');
      const value = await characteristic.readValue();
  
      console.log('> Battery Level is ' + value.getUint8(0) + '%');

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
    //   navigator.bluetooth.requestDevice({
    //     filters: [{ services:["2C:CF:67:19:82:C8"]}]
    //   })
    // })

  //   navigator.bluetooth.requestDevice({ acceptAllDevices:true,optionalServices:['battery_service'] })
  //   .then(device => device.gatt.connect())
  //   .then(server => {
  //     // Getting Battery Service…
  //     return server.getPrimaryService('battery_service');
  //   })
  //   .then(service => {
  //     // Getting Battery Level Characteristic…
  //     return service.getCharacteristic('battery_level');
  //   })
  //   .then(characteristic => {
  //     // Reading Battery Level…
  //     return characteristic.readValue();
  //   })
  //   .then(value => {
  //     console.log(`Battery percentage is ${value.getUint8(0)}`);
  //   // })
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