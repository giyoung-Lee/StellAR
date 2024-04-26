import { useEffect } from "react";

const BluetoothReceive = () => {  
  let text=document.querySelector('#text');
  
  function parseHeartRate(value) {
    let is16Bits = value.getUint8(0) & 0x1;
    if (is16Bits) return value.getUint16(1, true);
    console.log(value);
    return value.getUint8(1);
    // return value
  }

  function handlevalueChange(event){
    
    text.textContent=parseHeartRate(event.target.value);

  }

  // 블루투스 기기 연결
  const connectBluetoothDevice = async () => {
    try {
      const device = await navigator.bluetooth.requestDevice({
        acceptAllDevices:true,
        optionalServices: ["00003105-0000-1000-8000-00805f9b34fb"],
        // optionalServices:['00002105-0000-1000-8000-00805f9b34fb'],
        // filters: [
        //   {services: ['00002105-0000-1000-8000-00805f9b34fb']}
        //   // {name: 'raspberrypi'}
        // ],
      });

      // console.log(device);

      console.log('Connecting to GATT Server...');
      const server = await device.gatt.connect();
      // console.log(server);

      const services = await server.getPrimaryServices();
      services.forEach(service => console.log(`Service: ${service.uuid}`));
  
      console.log('Getting Battery Service...');
      const service = await server.getPrimaryService("00003105-0000-1000-8000-00805f9b34fb");
      
  
      console.log('Getting Battery Level Characteristic...');
      const characteristic = await service.getCharacteristic("00003005-0000-1000-8000-00805f9b34fb");

      // console.log(characteristic);
  
      console.log('Reading Battery Level...');

      // const value = await characteristic.readValue();

      const heart_service=await server.getPrimaryService("heart_rate");
      // console.log(heart_service);
      const heartRate = await heart_service.getCharacteristic("heart_rate_measurement");
      heartRate.addEventListener("characteristicvaluechanged", handlevalueChange);

      // const heart_char=await heart_service.getCharacteristic("heart_rate_control_point");
      // console.log(heart_char);

      
      // const resetEnergyExpended = Uint8Array.of(1);
      // await heart_char.writeValue(resetEnergyExpended);
      
  
      // console.log('> Battery Level is ' + value.getUint8(0) + '%');

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
  <div id="text">this is text</div>
    </>  
  )

};

export default BluetoothReceive;