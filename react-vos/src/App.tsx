import React from 'react';
import './App.css';
import AppBar from "./component/AppBar";
import { BrowserRouter as Router, useLocation } from "react-router-dom";
import AppRoutes from "./AppRoutes";

const MyApp = () => {
    const location = useLocation();
    const isAuthPage = location.pathname === '/auth';

    return (
        <>
            {!isAuthPage && <AppBar />}
            <AppRoutes />
        </>
    );
};

export default function AppWrapper() {
    return (
        <Router>
            <MyApp />
        </Router>
    );
}
