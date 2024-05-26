import React, { useEffect } from 'react';
import {Collaboration} from "../../model/Collaboration";
import { Tooltip } from "bootstrap";

interface CollaborationCardProps {
    collaboration: Collaboration;
    onClick: () => void;
}

const MembreCard: React.FC<CollaborationCardProps> = ({ collaboration, onClick }) => {
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
                alt={`${collaboration.titre}`}
                style={{ width: '100px', height: '100px' }}
                data-bs-toggle="tooltip"
                data-bs-title="Avatar"
            />
            <div className="mt-2">
                <div className="font-weight-bold" data-bs-toggle="tooltip" data-bs-title="Nom & Prénom">${collaboration.dateDepart} </div>
            </div>
        </div>
    );
}

export default MembreCard;
