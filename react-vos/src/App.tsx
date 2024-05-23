import React, { useState } from 'react';
import './App.css';
import AppBar from "./component/AppBar";
import { BrowserRouter as Router } from "react-router-dom";
import AppRoutes from "./AppRoutes";


export default function MyApp() {

    return (
        <Router>
            <AppBar />
            <AppRoutes />
        </Router>
    );
}
