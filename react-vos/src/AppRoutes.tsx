import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from './page/HomePage'
import AuthenticationPage from './page/auth/AuthenticationPage';
import TeamsPage from './page/management/TeamsPage';
import MembresPage from './page/management/MembresPage';
import CollaborationsPage from './page/management/CollaborationsPage';


const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/equipes" element={<TeamsPage />} />
            <Route path="/membres" element={<MembresPage />} />
            <Route path="/collaborations" element={<CollaborationsPage />} />
            <Route path="/auth" element={<AuthenticationPage />} />
        </Routes>
    );
};

export default AppRoutes;
