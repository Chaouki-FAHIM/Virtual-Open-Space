import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './page/Home'
import Authentification from './page/Authentification';
import TeamsPages from './page/management/TeamsPages';
import MembresPage from './page/management/MembresPage';
import CollaborationsPage from './page/management/CollaborationsPage';


const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/equipes" element={<TeamsPages />} />
            <Route path="/membres" element={<MembresPage />} />
            <Route path="/collaborations" element={<CollaborationsPage />} />
            <Route path="/auth" element={<Authentification />} />
        </Routes>
    );
};

export default AppRoutes;
