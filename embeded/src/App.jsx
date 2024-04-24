import './App.css'
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";
// import BluetoothTest from "./BluetoothTest";
import BluetoothReceive from "./BluetoothReceive_raspi";


function App() {
  return (
    <Router>
    <Routes>
      <Route path="/" element={<BluetoothReceive />} />
    </Routes>
  </Router>
  )
}

export default App
