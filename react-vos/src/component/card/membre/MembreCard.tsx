import React, { useEffect } from 'react';
import { DisplayMembreDTO } from "../../../model/membre/DisplayMembreDTO";
import { Tooltip } from "bootstrap";
import './MembreCard.css';

interface MembreCardProps {
    membre: DisplayMembreDTO;
    badgeColor?: string;
    onClick: () => void;
}

const MembreCard: React.FC<MembreCardProps> = ({ membre, onClick, badgeColor = 'bg-warning' }) => {
    useEffect(() => {
        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));

        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    }, []);

    let roleColor = 'text-muted';

    if (badgeColor === 'bg-dark') {
        roleColor = 'text-white';
    }

    return (
        <div className="text-center mt-2 my-1" onClick={onClick} style={{ cursor: 'pointer' }}>
            <img
                className="rounded-circle border border-black border-2"
                src='https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'
                alt={`${membre.nomMembre} ${membre.prenom}`}
                style={{ width: '4.5rem', height: '4.5rem' }}
                data-bs-toggle="tooltip"
                data-bs-title="Image"
            />
            <div>
                <div className="text-black font-bold text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl" data-bs-toggle="tooltip"
                     data-bs-title="Nom & Prénom">{membre.nomMembre} {membre.prenom}</div>
                <p className={`badge ${badgeColor} ${roleColor} text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl`} data-bs-toggle="tooltip"
                   data-bs-title="Rôle d'habilation">{membre.roleHabilation}</p>
            </div>
        </div>
    );
}

export default MembreCard;
