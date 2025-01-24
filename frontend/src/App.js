import './App.css';
import ResponsiveAppBar from "./components/AppBar";
import Books from "./components/Books/Books";
import Users from "./components/Users/Users";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";

function App() {
    return (
        <Router>
            <div className="App">
                <ResponsiveAppBar />
                <Routes>
                    <Route path="/" element={<Books />} /> {/* Default Route */}
                    <Route path="/books" element={<Books />} />
                    <Route path="/users" element={<Users />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
