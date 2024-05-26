import React, { useEffect } from 'react';
import { Collaboration } from "../../../model/Collaboration";
import { Tooltip } from "bootstrap";
import { Membre } from "../../../model/Membre";
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
            { top: '-80%', left: '40%' }, // Top
            { top: '-67%', left: '2%' },
            { top: '-67%', left: '75%' },
            { top: '-47%', left: '2%' },
            { top: '-47%', left: '75%' },
            { top: '-27%', left: '5%' },
            { top: '-27%', left: '75%' },
            { top: '-15%', left: '40%' } // Bouttom
        ];
        return positions[index % positions.length];
    };

    const getPositionFor10 = (index: number) => {
        const positions = [
            { top: '0%', left: '40%' },
            { top: '10%', left: '5%' },
            { top: '10%', left: '75%' },
            { top: '40%', left: '5%' },
            { top: '40%', left: '75%' },
            { top: '65%', left: '5%' },
            { top: '65%', left: '75%' },
            { top: '90%', left: '40%' }
        ];
        return positions[index % positions.length];
    };

    const getPosition = (index: number) => {
        if (collaboration.participants.length <= 4) {
            return getPositionFor4(index);
        } else if (collaboration.participants.length <= 8) {
            return getPositionFor8(index);
        } else {
            return getPositionFor10(index);
        }
    };

    return (
        <div className="text-center mx-3 my-3 position-relative" style={{ cursor: 'pointer', width: '75%', height: 'auto' }} onClick={onClick}>
            <img
                src={getImageSrc(collaboration.participants.length)}
                alt={`Collaboration ${collaboration.titre}`}
                className="img-fluid"
                data-bs-toggle="tooltip"
                data-bs-title="Collaboration"
            />
            <div className="position-relative" style={{ width: '100%', height: '100%' }}>
                {collaboration.participants.length > 0 &&
                    collaboration.participants.map((participant: Membre, index: number) => (
                        <LitleMembreCard membre={participant} key={index} position={getPosition(index)} />
                    ))
                }
            </div>
            <div className="font-weight-bold mt-4" data-bs-toggle="tooltip" data-bs-title="Date de départ">
                {collaboration.dateDepart}
            </div>
            <div className="font-weight-bold" data-bs-toggle="tooltip" data-bs-title="Titre">
                {collaboration.titre}
            </div>
        </div>
    );
}

export default CollaborationCard;
