import React, { useEffect, useState } from 'react';
import { GetOneMembers } from "../../../service/members/GetOneMembers";
import { Membre } from "../../../model/Membre";
import { Team } from "../../../model/Team";
import FormRow from '../../form/FormRow';
import FormSubRow from "../../form/FormSubRow";
import './MemberModal.css';


interface MemberModalProps {
    show: boolean;
    memberId: string;
    onClose: () => void;
}

interface DetailMembre extends Membre {
    teams: Team[];
}

function replaceUnderscoresWithSpace(text: string) {
    return text.replace(/_/g, ' ');
}

const MemberModal: React.FC<MemberModalProps> = ({ show, memberId, onClose }) => {
    const [memberDetail, setMemberDetail] = useState<DetailMembre | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchMember = async () => {
            try {
                const memberData = await GetOneMembers(memberId);
                setMemberDetail(memberData);
                setLoading(false);
            } catch (error) {
                console.log('Failed to fetch member data');
                setLoading(false);
            }
        };

        fetchMember();
    }, [memberId]);

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
                    <div className="modal-body text-center">
                        <img
                            src={image || 'https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'}
                            alt={`${nomMembre} ${prenom}`}
                            className="rounded-circle mb-3 border border-black border-2"
                            style={{ width: '100px', height: '100px' }}
                        />
                        <FormRow label="Nom" value={nomMembre} disabled />
                        <FormRow label="Prénom" value={prenom} disabled />
                        <FormRow label="Rôle" value={roleHabilation} disabled />
                        { teams.length === 1 && (
                            <>
                                <FormRow label="Equipe" value={teams[0].nomTeam} disabled />
                                <FormRow label="Siège" value= {replaceUnderscoresWithSpace(teams[0].siege)} disabled />
                            </>
                        )}
                        {teams.length > 1 && (
                            <div className="mt-3">
                                <h6>Squads</h6>
                                {teams.map((team) => (
                                    <div key={team.idTeam} className="team-container border p-3 mb-2 rounded">
                                        <FormSubRow label="Equipe" value={team.nomTeam} />
                                        <FormSubRow label="Siège" value={team.siege} />
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

export default MemberModal;
