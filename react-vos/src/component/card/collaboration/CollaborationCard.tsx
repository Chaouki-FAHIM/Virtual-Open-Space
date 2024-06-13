import React, { useEffect } from 'react';
import { Tooltip } from "bootstrap";
import { DisplayCollaborationDTO } from "../../../model/collaboration/DisplayCollaborationDTO";
import { DisplayMemberDetailDTO } from "../../../model/membre/DisplayMemberDetailDTO";
import LitleMembreCard from "../membre/LitleMembreCard";

interface CollaborationCardProps {
    collaboration: DisplayCollaborationDTO;
    onClick: () => void;
    nombreCollabParligne:number
}

const CollaborationCard: React.FC<CollaborationCardProps> = ({ collaboration, onClick, nombreCollabParligne }) => {

    console.log(nombreCollabParligne)
    useEffect(() => {
        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));
        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    }, []);

    const getImageSrc = (participantCount: number) => {
        if (participantCount <= 4) {
            return '/design/table-meeting-for-4persons.png';
        } else if (participantCount <= 8) {
            return '/design/table-meeting-for-8persons.png';
        } else {
            return '/design/table-meeting-for-10persons.png';
        }
    };

    const getPositionFor4 = (index: number) => {
        let positions:{top:string,left:string}[];

        if (nombreCollabParligne == 4)
            positions = [
                { top: '-80%', left: '22.5%' },
                { top: '-80%', left: '65%' },
                { top: '-22.5%', left: '22.5%' },
                { top: '-22.5%', left: '65%' },
            ];
        else if (nombreCollabParligne == 8)
            positions = [
                { top: '-58%', left: '5%' },
                { top: '-58%', left: '70%' },
                { top: '-20%', left: '5%' },
                { top: '-20%', left: '70%' }
            ];
        else if (nombreCollabParligne == 18)
            positions = [
                { top: '-57%', left: '5%' },
                { top: '-57%', left: '70%' },
                { top: '-20%', left: '5%' },
                { top: '-20%', left: '70%' }
            ];
        else
            positions = [
                { top: '-63%', left: '5%' },
                { top: '-63%', left: '70%' },
                { top: '-20%', left: '5%' },
                { top: '-20%', left: '70%' }
            ];
        return positions[index % positions.length];
    };

    const getPositionFor8 = (index: number) => {
        let positions:{top:string,left:string}[];
        if (nombreCollabParligne == 4)
            positions = [
                { top: '-90%', left: '42.5%' }, // Top
                { top: '-77%', left: '22.5%' },
                { top: '-77%', left: '65%' },
                { top: '-53%', left: '22.5%' },
                { top: '-53%', left: '65%' },
                { top: '-30%', left: '22.5%' },
                { top: '-30%', left: '65%' },
                { top: '-15%', left: '42.5%' } // Bottom
            ];
        else  if (nombreCollabParligne == 8)
            positions = [
                { top: '-85%', left: '35%' }, // Top
                { top: '-71%', left: '0%' },
                { top: '-71%', left: '75%' },
                { top: '-49%', left: '0%' },
                { top: '-49%', left: '75%' },
                { top: '-28%', left: '0%' },
                { top: '-28%', left: '75%' },
                { top: '-15%', left: '35%' } // Bottom
            ];
        else if (nombreCollabParligne == 18)
            positions = [
                { top: '-80%', left: '35%' }, // Top
                { top: '-68%', left: '3%' },
                { top: '-68%', left: '70%' },
                { top: '-48%', left: '0%' },
                { top: '-48%', left: '75%' },
                { top: '-28%', left: '3%' },
                { top: '-28%', left: '75%' },
                { top: '-15%', left: '35%' } // Bottom
            ];
        else
            positions = [
                { top: '-90%', left: '35%' }, // Top
                { top: '-75%', left: '5%' },
                { top: '-75%', left: '75%' },
                { top: '-53%', left: '0%' },
                { top: '-53%', left: '77%' },
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

    // Récupérer les équipes du propriétaire
    const ownerTeams = collaboration.participants
        .find(p => p.idMembre === collaboration.idProprietaire)
        ?.teams.map(team => team.idTeam) || [];

    return (
        <div className="text-center position-relative">
            <div className="mb-3 flex items-baseline">
                <strong data-bs-toggle="tooltip" data-bs-title="Titre">{collaboration.titre}</strong>
                <span className="badge rounded-pill text-bg-warning mx-1" data-bs-toggle="tooltip" data-bs-title="Nombre de participants">
                    {collaboration.participants.length} <i className="bi bi-person-fill"></i>
                </span>
                {collaboration.confidentielle && (
                    <span className="badge rounded-pill text-bg-danger" data-bs-toggle="tooltip" data-bs-title="Confidentielle">
                        <i className="bi bi-lock-fill"></i>
                    </span>
                )}
            </div>
            <img
                src={getImageSrc(collaboration.participants.length)}
                alt={`Collaboration ${collaboration.titre}`}
                className="img-fluid"
                data-bs-toggle="tooltip"
                data-bs-title="Collaboration"
                onClick={onClick}
                style={{ cursor: 'pointer',
                    width: nombreCollabParligne == 4 ? '60%' : (nombreCollabParligne == 18 ? '1000%' : (nombreCollabParligne == 24 ? '100%' : '50rem') ),  // end is nombreCollabParligne 8
                    height: 'auto'}}
            />
            <div className="position-relative" style={{ width: '100%', height: '100%' }}>
                {collaboration.participants.length > 0 &&
                    collaboration.participants.map((participant: DisplayMemberDetailDTO, index: number) => (
                        <LitleMembreCard
                            membre={participant}
                            key={index}
                            ownerTeams={ownerTeams}
                            position={getPosition(index)}
                        />
                    ))}
            </div>
        </div>
    );
}

export default CollaborationCard;
