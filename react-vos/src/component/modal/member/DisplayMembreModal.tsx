import React, { useEffect, useState } from 'react';
import { GetOneMember } from "../../../service/members/GetOneMember";
import { DisplayMembreDTO } from "../../../model/membre/DisplayMembreDTO";
import { TeamDTO } from "../../../model/TeamDTO";
import FormRow from '../../form/FormRow';
import FormSubRow from "../../form/FormSubRow";
import { faUser, faIdBadge, faBuilding, faUsers } from '@fortawesome/free-solid-svg-icons';
import FormNameRow from "../../form/FormNameRow";
import './DisplayMemberModal.css';

interface MemberModalProps {
    show: boolean;
    membreId: string;
    onClose: () => void;
}

interface DetailMembre extends DisplayMembreDTO {
    teams: TeamDTO[];
}

function replaceUnderscoresWithSpace(text: string) {
    return text.replace(/_/g, ' ');
}

const DisplayMemberModal: React.FC<MemberModalProps> = ({ show, membreId, onClose }) => {
    const [memberDetail, setMemberDetail] = useState<DetailMembre | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchMember = async () => {
            try {
                const memberData = await GetOneMember(membreId);
                setMemberDetail(memberData);
                setLoading(false);
            } catch (error) {
                console.log('Failed to fetch member data');
                setLoading(false);
            }
        };

        fetchMember();
    }, [membreId]);

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
                            <p>Chargement des données du membre...</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    if (!memberDetail) {
        return (
            <div className="modal d-block" tabIndex={-1} style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
                <div className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div className="modal-content">
                        <div className="modal-header bg-dark text-bold text-white justify-content-center">
                            <h5 className="modal-title">Erreur</h5>
                            <button type="button" className="btn-close btn-close-white" aria-label="Close" onClick={onClose}></button>
                        </div>
                        <div className="modal-body text-center">
                            <p>Erreur lors de la récupération des données du membre.</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    const { nomMembre, prenom, roleHabilation, image, teams } = memberDetail;

    return (
        <div className="modal d-block" tabIndex={-1} style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
            <div className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div className="modal-content">
                    <div className="modal-header bg-dark text-bold text-white justify-content-center">
                        <h5 className="modal-title">Détails du Membre</h5>
                        <button type="button" className="btn-close btn-close-white" aria-label="Close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body text-center row">
                        <div className="col-12">
                            <img
                                src={image || 'https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'}
                                alt={`${nomMembre} ${prenom}`}
                                className="rounded-circle mb-3 border border-black border-2"
                                style={{ width: '5.5rem', height: '5.5rem' }}
                            />
                        </div>
                        <div className="col-6 mb-3">
                            <FormNameRow label="Nom" value={nomMembre} icon={faUser} disabled />
                        </div>
                        <div className="col-6 mb-3">
                            <FormNameRow label="Prénom" value={prenom} icon={faUser} disabled />
                        </div>
                        <div className="col-12 mb-3">
                            <FormRow label="Rôle" value={roleHabilation} icon={faIdBadge} disabled />
                        </div>
                        {teams.length === 1 && (
                            <>
                                <div className="col-12 mb-3">
                                    <FormRow label="Equipe" value={teams[0].nomTeam} icon={faUsers} disabled />
                                </div>
                                <div className="col-12 mb-3">
                                    <FormRow label="Siège" value={replaceUnderscoresWithSpace(teams[0].siege)} icon={faBuilding} disabled />
                                </div>
                            </>
                        )}
                        {teams.length > 1 && (
                            <div className="col-12 mt-3">
                                <h6>Squads</h6>
                                {teams.map((team: TeamDTO) => (
                                    <div key={team.idTeam} className="team-container border p-3 mb-2 rounded">
                                        <FormSubRow label="Equipe" value={team.nomTeam} icon={faUsers} />
                                        <FormSubRow label="Siège" value={replaceUnderscoresWithSpace(team.siege)} icon={faBuilding} />
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DisplayMemberModal;
