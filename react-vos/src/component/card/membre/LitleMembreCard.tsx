import React, { useEffect } from 'react';
import { Membre } from "../../../model/Membre";
import { Tooltip } from "bootstrap";

interface LitleMembreCardProps {
    membre: Membre;
    onClick?: () => void;
    position: { top: string, left: string }; // Ajout de la position comme prop
}

const LitleMembreCard: React.FC<LitleMembreCardProps> = ({ membre, onClick, position }) => {
    useEffect(() => {
        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));
        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    }, []);

    return (
        <div className="position-absolute" style={{ top: position.top, left: position.left, cursor: 'pointer' }} onClick={onClick}>
            <img
                className="rounded-circle border border-black border-2"
                src='https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'
                alt={`${membre.nomMembre} ${membre.prenom}`}
                style={{ width: '3rem', height: '3rem' }} // Augmentation de la taille pour une meilleure visibilité
                data-bs-toggle="tooltip"
                data-bs-title={`${membre.nomMembre} ${membre.prenom}`}
            />
        </div>
    );
}

export default LitleMembreCard;
