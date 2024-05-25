import React, {useEffect} from 'react';
import { Membre } from "../../../model/Membre";
import FormRow from '../../form/FormRow';
import './MemberModal.css';
import {Tooltip} from "bootstrap";

interface MemberModalProps {
    show: boolean;
    member: Membre | null;
    onClose: () => void;
}

const MemberModal: React.FC<MemberModalProps> = ({ show, member, onClose }) => {
    if (!member) {
        return null;
    }



    return (
        <div className={`modal ${show ? 'd-block' : 'd-none'}`} tabIndex={-1} style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
            <div className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div className="modal-content">
                    <div className="modal-header bg-dark text-bold text-white">
                        <h5 className="modal-title">Détails du Membre</h5>
                        <button type="button" className="btn-close btn-close-white" aria-label="Close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body text-center">
                        <img
                            src='https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'
                            alt={`${member.nomMembre} ${member.prenom}`}
                            className="rounded-circle mb-3 border border-black border-2"
                            style={{ width: '100px', height: '100px' }}
                        />
                        <FormRow label="Nom" value={member.nomMembre} disabled />
                        <FormRow label="Prénom" value={member.prenom} disabled />
                        <FormRow label="Rôle" value={member.roleHabilation} disabled />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MemberModal;
