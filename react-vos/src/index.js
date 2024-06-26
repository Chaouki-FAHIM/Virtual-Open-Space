import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import MyApp from './App.tsx';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <MyApp />
    </React.StrictMode>
);

reportWebVitals();
