import React, { useEffect } from 'react';
import { format } from 'date-fns';
import { Collaboration } from "../../../model/Collaboration";
import { Tooltip } from "bootstrap";
import { DetailsMembre } from "../../../model/DetailsMembre";
import LitleMembreCard from "../membre/LitleMembreCard";

interface CollaborationCardProps {
    collaboration: Collaboration;
    onClick: () => void;
}

const CollaborationCard: React.FC<CollaborationCardProps> = ({ collaboration, onClick }) => {

    useEffect(() => {
        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));
        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    }, []);

    const getImageSrc = (participantCount: number) => {
        if (participantCount <= 4) {
            return '/design/table-metting-for-4persons.png';
        } else if (participantCount <= 8) {
            return '/design/table-metting-for-8persons.png';
        } else {
            return '/design/table-metting-for-10persons.png';
        }
    };

    const getPositionFor4 = (index: number) => {
        const positions = [
            { top: '-20%', left: '5%' },
            { top: '-20%', left: '70%' },
            { top: '-60%', left: '10%' },
            { top: '-60%', left: '70%' }
        ];
        return positions[index % positions.length];
    };

    const getPositionFor8 = (index: number) => {
        const positions = [
            { top: '-90%', left: '40%' }, // Top
            { top: '-77%', left: '2%' },
            { top: '-77%', left: '75%' },
            { top: '-53%', left: '2%' },
            { top: '-53%', left: '75%' },
            { top: '-30%', left: '5%' },
            { top: '-30%', left: '75%' },
            { top: '-15%', left: '40%' } // Bottom
        ];
        return positions[index % positions.length];
    };

    const getPosition = (index: number) => {
        if (collaboration.participants.length <= 4) return getPositionFor4(index);
        else return getPositionFor8(index);
    };

    const ownerTeams = collaboration.participants.find(p => p.idMembre === collaboration.idProprietaire)?.teams.map(team => team.idTeam) || [];

    const formatDate = (dateString: string) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('fr-FR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        }).format(date);
    };

    const formattedDate = formatDate(collaboration.dateDepart);

    return (
        <div className="text-center mx-3 my-3 position-relative" style={{ cursor: 'pointer', width: '75%', height: 'auto' }} onClick={onClick}>
            {/*<div className="font-weight-bold mb-2" data-bs-toggle="tooltip" data-bs-title="Date de départ">{formattedDate}</div>*/}
            <div className="mb-2 flex items-baseline">
                <strong data-bs-toggle="tooltip" data-bs-title="Titre">{collaboration.titre}</strong>

                <span className="badge rounded-pill text-bg-warning mx-1" data-bs-toggle="tooltip"
                      data-bs-title="Nombre de participants">
                    {collaboration.participants.length} <i className="bi bi-person-fill"></i>
                </span>
                {
                    collaboration.confidentielle && (
                        <span className="badge rounded-pill text-bg-danger m-1" data-bs-toggle="tooltip"
                              data-bs-title="Confidentielle">
                            <i className="bi bi-lock-fill"></i>
                        </span>

                    )
                }


            </div>
            <img
                src={getImageSrc(collaboration.participants.length)}
                alt={`Collaboration ${collaboration.titre}`}
                className="img-fluid"
                data-bs-toggle="tooltip"
                data-bs-title="Collaboration"
            />
            <div className="position-relative" style={{ width: '100%', height: '100%' }}>
                {collaboration.participants.length > 0 &&
                    collaboration.participants.map((participant: DetailsMembre, index: number) => (
                        <LitleMembreCard
                            membre={participant}
                            key={index}
                            ownerTeams={ownerTeams}
                            idProprietaire={collaboration.idProprietaire}
                            position={getPosition(index)}
                        />
                    ))
                }
            </div>
        </div>
    );
}

export default CollaborationCard;
