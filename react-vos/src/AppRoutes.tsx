import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './page/Home'
import Authentification from './page/Authentification';
import Teams from './page/management/Teams';
import Membres from './page/management/Membres';
import Collaborations from './page/management/Collaborations';


const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/equipes" element={<Teams />} />
            <Route path="/membres" element={<Membres />} />
            <Route path="/collaborations" element={<Collaborations />} />
            <Route path="/auth" element={<Authentification />} />
        </Routes>
    );
};

export default AppRoutes;
