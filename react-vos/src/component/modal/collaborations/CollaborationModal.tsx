import React, { useEffect, useState } from 'react';
import { GetOneCollaboration } from "../../../service/collaborations/GetOneCollaboration";
import { GetOneMember } from "../../../service/members/GetOneMember";
import { Collaboration } from "../../../model/Collaboration";
import FormRow from "../../form/FormRow";

interface CollaborationModalProps {
    show: boolean;
    collaborationId: number;
    onClose: () => void;
}

const CollaborationModal: React.FC<CollaborationModalProps> = ({ show, collaborationId, onClose }) => {
    const [currentCollaboration, setCurrentCollaboration] = useState<Collaboration | null>(null);
    const [ownerName, setOwnerName] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchCollaboration = async () => {
            try {
                const collaborationData = await GetOneCollaboration(collaborationId);
                setCurrentCollaboration(collaborationData);
                console.log("Id de propriétaire : " + collaborationData.idProprietaire);
                // Fetch owner details
                const ownerData = await GetOneMember(collaborationData.idProprietaire);
                setOwnerName(`${ownerData.prenom} ${ownerData.nomMembre}`);

                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch collaboration data or owner data');
                setLoading(false);
            }
        };

        fetchCollaboration();
    }, [collaborationId]);

    if (!show) {
        return null;
    }

    if (loading) {
        return (
            <div className="modal d-block" tabIndex={-1} style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
                <div className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div className="modal-content">
                        <div className="modal-header bg-dark text-bold text-white justify-content-center">
                            <h5 className="modal-title">Chargement...</h5>
                            <button type="button" className="btn-close btn-close-white" aria-label="Close" onClick={onClose}></button>
                        </div>
                        <div className="modal-body text-center">
                            <p>Chargement des données de la collaboration...</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    if (!currentCollaboration) {
        return (
            <div className="modal d-block" tabIndex={-1} style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
                <div className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div className="modal-content">
                        <div className="modal-header bg-dark text-bold text-white justify-content-center">
                            <h5 className="modal-title">Erreur</h5>
                            <button type="button" className="btn-close btn-close-white" aria-label="Close" onClick={onClose}></button>
                        </div>
                        <div className="modal-body text-center">
                            <p>Erreur lors de la récupération des données de la collaboration.</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    const { titre, dateCreationCollaboration, dateDepart, participants } = currentCollaboration;

    const formatDate = (dateString: string) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('fr-FR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        }).format(date);
    };

    return (
        <div className="modal d-block" tabIndex={-1} style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
            <div className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div className="modal-content">
                    <div className="modal-header bg-dark text-bold text-white justify-content-center">
                        <h5 className="modal-title">Détails de la Collaboration</h5>
                        <button type="button" className="btn-close btn-close-white" aria-label="Close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body text-center">
                        <FormRow label="Titre" value={titre} disabled />
                        <FormRow label="Date de création" value={formatDate(dateCreationCollaboration)} disabled />
                        <FormRow label="Date de départ" value={formatDate(dateDepart)} disabled />
                        <FormRow label="Propriétaire" value={ownerName} disabled />
                        {participants.length !== 0 && (
                            <>
                                <h5>Participants</h5>
                                <div className="flex overflow-x-auto d-flex flex-row flex-nowrap overflow-auto border broder-1 rounded-3 shadow-3xl">
                                    {participants.map(participant => (
                                        <div key={participant.idMembre} className="m-3 text-center">
                                            <img
                                                src={participant.image || 'https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'}
                                                alt={`${participant.nomMembre} ${participant.prenom}`}
                                                className="rounded-circle border border-black border-2"
                                                style={{width: '3.5rem', height: '3.5rem'}}
                                            />
                                            <div className="font-semibold text-base md:text-lg lg:text-xl">
                                                {participant.nomMembre} {participant.prenom}
                                            </div>
                                            <p className="badge bg-warning text-base md:text-lg lg:text-xl">{participant.roleHabilation}</p>
                                        </div>
                                    ))}
                                </div>
                            </>
                        )}
                        {participants.length === 0 && (
                            <FormRow label="Participants" value="Aucun participant pour à ce moment" disabled />
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CollaborationModal;
