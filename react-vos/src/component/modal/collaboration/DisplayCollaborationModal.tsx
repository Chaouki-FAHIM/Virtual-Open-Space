import React, { useEffect, useState, useRef } from 'react';
// import { MdChevronLeft, MdChevronRight } from 'react-icons/md';
import { GetOneCollaboration } from '../../../service/collaborations/GetOneCollaboration';
import { GetOneMember } from '../../../service/members/GetOneMember';
import { DisplayCollaborationDTO } from '../../../model/collaboration/DisplayCollaborationDTO';
import FormRow from '../../form/FormRow';
import {
    faLock,
    faUsers,
    faPen,
    faCalendarDay,
    faCalendarAlt,
    faCrown
} from '@fortawesome/free-solid-svg-icons';

interface CollaborationModalProps {
    show: boolean;
    collaborationId: number;
    onClose: () => void;
}

const DisplayCollaborationModal: React.FC<CollaborationModalProps> = ({
                                                                          show,
                                                                          collaborationId,
                                                                          onClose
                                                                      }) => {
    const [currentCollaboration, setCurrentCollaboration] = useState<DisplayCollaborationDTO | null>(null);
    const [ownerName, setOwnerName] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(true);
    const sliderRef = useRef<HTMLDivElement>(null);

    const slideLeft = (): void => {
        if (sliderRef.current) {
            sliderRef.current.scrollLeft -= 500;
        }
    };

    const slideRight = (): void => {
        if (sliderRef.current) {
            sliderRef.current.scrollLeft += 500;
        }
    };

    useEffect(() => {
        const fetchCollaboration = async () => {
            try {
                const collaborationData = await GetOneCollaboration(collaborationId);
                setCurrentCollaboration(collaborationData);
                console.log('Id de propriétaire : ' + collaborationData.idProprietaire);
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
                            <h5 className="modal-title text-lg sm:text-xl md:text-2xl lg:text-3xl">Chargement...</h5>
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
                            <h5 className="modal-title text-lg sm:text-xl md:text-2xl lg:text-3xl">Erreur</h5>
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

    const { titre, dateCreationCollaboration, dateDepart,  confidentielle, participants } = currentCollaboration;
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
                        <h5 className="modal-title text-lg sm:text-xl md:text-2xl lg:text-3xl">Détails de la Collaboration</h5>
                        <button type="button" className="btn-close btn-close-white" aria-label="Close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body text-center">
                        <FormRow label="Titre" value={titre} disabled icon={faPen} />
                        <FormRow label="Date de création" value={formatDate(dateCreationCollaboration)} disabled icon={faCalendarDay} />
                        <FormRow label="Date de départ" value={formatDate(dateDepart)} disabled icon={faCalendarAlt} />
                        <FormRow label="Confidentialité" value={confidentielle ? 'Oui' : 'Non'} disabled icon={faLock} />
                        <FormRow label="Propriétaire" value={ownerName} disabled icon={faCrown} />
                        {participants.length !== 0 && (
                            <>
                                <div className="items-baseline space-x-2 mt-3">
                                    <h5>
                                        Participants{' '}
                                        <span
                                            className="badge rounded-pill text-start text-bg-dark flex items-center"
                                            data-bs-toggle="tooltip"
                                            data-bs-title="Nombre de participants"
                                        >
                                            {participants.length} <i className="bi bi-person-fill"></i>
                                        </span>
                                    </h5>
                                </div>
                                <div className="relative flex items-center">
                                    {/*<MdChevronLeft onClick={slideLeft} size={25} className="cursor-pointer absolute left-0 top-1/2 transform -translate-y-1/2 z-10" />*/}
                                    <div
                                        ref={sliderRef}
                                        id="slider"
                                        className="flex overflow-auto d-flex flex-row flex-nowrap border border-1 rounded-3 shadow-3xl"
                                        style={{ margin: '0 30px' }}
                                    >
                                        {participants.map((participant) => (
                                            <div key={participant.idMembre} className="m-3 text-center">
                                                <img
                                                    src={
                                                        participant.image ||
                                                        'https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'
                                                    }
                                                    alt={`${participant.nomMembre} ${participant.prenom}`}
                                                    className="rounded-circle border-black border-2"
                                                    style={{ width: '3.5rem', height: '3.5rem' }}
                                                />
                                                <div className="font-semibold text-base md:text-lg lg:text-xl">
                                                    {participant.nomMembre} {participant.prenom}
                                                </div>
                                                <p className="badge bg-warning text-base md:text-lg lg:text-xl">
                                                    {participant.roleHabilation}
                                                </p>
                                            </div>
                                        ))}
                                    </div>
                                    {/*<MdChevronRight onClick={slideRight} size={25} className="cursor-pointer absolute right-0 top-1/2 transform -translate-y-1/2 z-10" />*/}
                                </div>
                            </>
                        )}
                        {participants.length === 0 && (
                            <FormRow label="Participants" value="Aucun participant pour à ce moment" disabled icon={faUsers} />
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DisplayCollaborationModal;
