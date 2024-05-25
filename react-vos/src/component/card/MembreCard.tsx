import React, { useEffect } from 'react';
import { Membre } from "../../model/Membre";
import { Tooltip } from "bootstrap";

interface MembreCardProps {
    membre: Membre;
    onClick: () => void;
}

const MembreCard: React.FC<MembreCardProps> = ({ membre, onClick }) => {
    useEffect(() => {
        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));
        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    }, []);

    return (
        <div className="text-center mx-3 my-3" onClick={onClick} style={{ cursor: 'pointer' }}>
            <img
                className="rounded-circle border border-black border-2"
                src='https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'
                alt={`${membre.nomMembre} ${membre.prenom}`}
                style={{ width: '100px', height: '100px' }}
                data-bs-toggle="tooltip"
                data-bs-title="Avatar"
            />
            <div className="mt-2">
                <div className="font-weight-bold" data-bs-toggle="tooltip" data-bs-title="Nom & Prénom">{membre.nomMembre} {membre.prenom}</div>
                <p className="badge bg-warning text-muted" data-bs-toggle="tooltip" data-bs-title="Rôle d'habilation">{membre.roleHabilation}</p>
            </div>
        </div>
    );
}

export default MembreCard;
