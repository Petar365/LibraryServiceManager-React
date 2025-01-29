import './App.css';
import ResponsiveAppBar from "./components/AppBar";
import Books from "./components/Books/Books";
import BooksDetails from "./components/Books/BookDetails";
import Users from "./components/Users/Users";
import Login from "./components/Login/Login"
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";

function App() {
    return (
        <Router>
            <div className="App">
                <ResponsiveAppBar />
                <Routes>
                    <Route path="/" element={<Books />} /> {/* Default Route */}
                    <Route path="/login" element={<Login />} />
                    <Route path="/books" element={<Books />} />
                    <Route path="/books/:id" element={<BooksDetails />} />
                    <Route path="/users" element={<Users />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
