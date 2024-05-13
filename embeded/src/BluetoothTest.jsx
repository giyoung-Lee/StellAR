import { useCallback, useEffect } from 'react'
import useBluetoothStore from './stores/bluetoothStore'

const windowNavigator = navigator

export default function useBluetooth() {
  const { device, bluetoothConnect, readCallback, setDevice, setDeviceConnect, setReadCallback } = useBluetoothStore()

  const setBluetoothDevice = useCallback(() => {
    let characteristic; // characteristic 객체를 임시 저장할 변수
    return new Promise((resolve, reject) => {
      windowNavigator.bluetooth.requestDevice({
        filters: [{ services: [0xFFE0] }]
      })
        .then(function(device) {
          device.addEventListener('gattserverdisconnected', () => {
            alert("디바이스 연결이 끊어졌습니다.");
            setDevice(null)
            setDeviceConnect(false)
          });
          setDevice(device); // device 객체 저장
          return device.gatt.connect();
        }).then(function(server) {
          return server.getPrimaryService(0xFFE0);
        }).then(function(service) {
          return service.getCharacteristic(0xFFE1);
        }).then(function(_characteristic) {
          characteristic = _characteristic; // characteristic 객체 저장
          resolve(true)
          setDeviceConnect(true)
    
          characteristic.startNotifications().then(() => {
            console.log("bluetooth start Notifications!")
          })
  
          // characteristic 객체에 대한 참조를 다루는 로직 추가
        })
        .catch(function(error) {
          console.error('Connection failed!', error);
          setDevice(null)
          setDeviceConnect(false)
          reject(false);
        });
    })
  }, [])
  

  const sendData = useCallback((data) => {
    if (device !== null) {
      device.writeValue(data)
    }
  }, [device])

  useEffect(() => {
    if (device && readCallback) {
      device.addEventListener('characteristicvaluechanged', readCallback);
    }
    return (() => {
      if (device && readCallback) {
        device.removeEventListener('characteristicvaluechanged', readCallback);
      }
    })
  }, [device, readCallback])

  return {
    device,
    bluetoothConnect,
    sendData,
    setBluetoothDevice,
    setReadCallback
  }
}
