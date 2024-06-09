import React, { useEffect } from 'react';
import { Tooltip } from "bootstrap";
import { DisplayMemberDetailDTO } from "../../../model/membre/DisplayMemberDetailDTO";

interface LitleMembreCardProps {
    membre: DisplayMemberDetailDTO;
    ownerTeams: string[];
    onClick?: () => void;
    position: { top: string, left: string };
}

const LitleMembreCard: React.FC<LitleMembreCardProps> = ({ membre, ownerTeams, onClick, position }) => {
    useEffect(() => {

        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));
        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    }, []);

    // Vérifier si le membre est dans au moins une des équipes du propriétaire
    const isInOwnerTeam = membre.teams.some(team => ownerTeams.includes(team.idTeam));

    // Définir la couleur de la bordure et l'ombre pour les membres étrangers
    let borderColor:string = 'black';
    let boxShadow:string = 'none';
    let borderWidth:string = '2px';

    if (!isInOwnerTeam) {
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
