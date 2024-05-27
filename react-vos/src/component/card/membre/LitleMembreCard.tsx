import React, { useEffect } from 'react';
import { Tooltip } from "bootstrap";
import { DetailsMembre } from "../../../model/DetailsMembre";

interface LitleMembreCardProps {
    membre: DetailsMembre;
    ownerTeams: string[]; // Nouvelle prop pour les équipes du propriétaire
    idProprietaire: string; // Nouvelle prop pour l'ID du propriétaire
    onClick?: () => void;
    position: { top: string, left: string };
}

const LitleMembreCard: React.FC<LitleMembreCardProps> = ({ membre, ownerTeams, idProprietaire, onClick, position }) => {
    useEffect(() => {
        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));
        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    }, []);

    // Vérifier si le membre est le propriétaire
    const isOwner = membre.idMembre === idProprietaire;

    // Vérifier si le membre est dans au moins une des équipes du propriétaire
    const isInOwnerTeam = membre.teams.some(team => ownerTeams.includes(team.idTeam));

    // Définir la couleur de la bordure et l'ombre
    let borderColor = 'black';
    let boxShadow = 'none';
    let borderWidth = '2px';

    if (isOwner) {
        borderColor = 'gold';
        boxShadow = '0 0 10px gold';
        borderWidth = '3px';
    } else if (!isInOwnerTeam) {
        borderColor = 'red';
        boxShadow = '0 0 10px red';
    }

    return (
        <div className="position-absolute" style={{ top: position.top, left: position.left, cursor: 'pointer' }} onClick={onClick}>
            <img
                className="rounded-circle"
                src='https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'
                alt={`${membre.nomMembre} ${membre.prenom}`}
                style={{
                    width: '3rem',
                    height: '3rem',
                    border: `${borderWidth} solid ${borderColor}`,
                    boxShadow: boxShadow
                }}
                data-bs-toggle="tooltip"
                data-bs-title={`${membre.nomMembre} ${membre.prenom}`}
            />
        </div>
    );
}

export default LitleMembreCard;
