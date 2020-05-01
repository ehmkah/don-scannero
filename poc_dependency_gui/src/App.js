import React from 'react';
import './App.css';
import Overview from "./Overview";
import Copyright from "./Copyright";

function App() {
  return (
    <div className="App">
        <div>Hello - show some consumers and there dependencies. Hope you like it </div>
        <Overview/>
        <Copyright/>
    </div>
  );
}

export default App;
