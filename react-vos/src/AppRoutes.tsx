import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './page/Home'
import Authentification from './page/Authentification';
import TeamsPage from './page/management/teams/TeamsPage';
import MembresPage from './page/management/membres/MembresPage';
import CollaborationsPage from './page/management/collaborations/CollaborationsPage';


const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/equipes" element={<TeamsPage />} />
            <Route path="/membres" element={<MembresPage />} />
            <Route path="/collaborations" element={<CollaborationsPage />} />
            <Route path="/auth" element={<Authentification />} />
        </Routes>
    );
};

export default AppRoutes;
