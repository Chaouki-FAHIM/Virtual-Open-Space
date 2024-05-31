import React, {useEffect, useState} from 'react';
import { Membre } from "../../../model/Membre";
import { Tooltip } from "bootstrap";
import './MembreCard.css'

interface MembreCardProps {
    membre: Membre;
    badgeColor?: string,
    onClick: () => void;
}

const MembreCard: React.FC<MembreCardProps> = ({ membre, onClick, badgeColor ='bg-warning' }) => {
    useEffect(() => {
        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));

        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    },[]);


    let roleColor = 'text-muted';

    if (badgeColor === 'bg-dark') {
        roleColor = 'text-white';
    }
    return (
        <div className="text-center mt-2 my-1" onClick={onClick} style={{cursor: 'pointer'}}>
            <img
                className="rounded-circle border border-black border-2"
                src='https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'
                alt={`${membre.nomMembre} ${membre.prenom}`}
                style={{width: '5.5rem', height: '5.5rem'}}
                data-bs-toggle="tooltip"
                data-bs-title="Image"
            />
            <div className="">
                <div className="text-black fw-bold" data-bs-toggle="tooltip"
                     data-bs-title="Nom & Prénom">{membre.nomMembre} {membre.prenom}</div>
                <p className={`badge ${badgeColor} ${roleColor}`} data-bs-toggle="tooltip"
                   data-bs-title="Rôle d'habilation">{membre.roleHabilation}</p>
            </div>
        </div>
    );
}

export default MembreCard;
