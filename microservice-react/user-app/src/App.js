import Menu from "./shared/Menu";
import {Route, Routes} from "react-router-dom";
import Home from "./page/Home";
import About from "./page/About";
import User from "./page/User";
import AccessLog from "./page/AccessLog";

function App() {
    return (
        <div className="app">
            <Menu/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/user" element={<User/>}/>
                <Route path="/access" element={<AccessLog/>}/>
                <Route path="/about" element={<About/>}/>
            </Routes>
        </div>
    );
}

export default App;
