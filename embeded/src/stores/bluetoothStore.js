import create from 'zustand'

const useBluetoothStore = create(set => ({
  device: null,
  bluetoothConnect: false,
  readCallback: null,
  setDevice: (device) => set(() => ({ device })),
  setDeviceConnect: (bluetoothConnect) => set(() => ({ bluetoothConnect })),
  setReadCallback: (readCallback) => set(() => ({ readCallback })),
}))

export default useBluetoothStore