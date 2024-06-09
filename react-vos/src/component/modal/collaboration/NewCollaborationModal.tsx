import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { CreateCollaborationDTO } from '../../../model/collaboration/CreateCollaborationDTO';
import SelectMember from '../../form/SelectMember';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faLock, faUsers, faPen, faCalendarAlt} from '@fortawesome/free-solid-svg-icons';
import './NewCollaborationModal.css'

interface NewCollaborationModalProps {
    show: boolean;
    onClose: () => void;
    onSave: (collaboration: CreateCollaborationDTO) => void;
}

const NewCollaborationModal: React.FC<NewCollaborationModalProps> = ({ show, onClose, onSave }) => {
    const [titre, setTitre] = useState('');
    const [dateDepart, setDateDepart] = useState(getCurrentDateTime());
    const [confidentielle, setConfidentielle] = useState(false);
    const [idParticipants, setIdParticipants] = useState<string[]>([]);

    useEffect(() => {
        setDateDepart(getCurrentDateTime());
    }, []);

    const handleSubmit = () => {
        const newCollaboration: CreateCollaborationDTO = {
            titre,
            confidentielle,
            dateDepart,
            idProprietaire: '', // Remplir avec l'ID du propriétaire actuel
            idParticipants
        };
        onSave(newCollaboration);
        onClose();
    };

    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton className="bg-danger text-white">
                <Modal.Title className="text-lg sm:text-xl md:text-2xl lg:text-3xl xl:text-4xl">Nouvelle Collaboration</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form className="row g-3">
                    <div className="col-12 sm:col-6">
                        <Form.Group controlId="titre">
                            <Form.Label className="block text-sm font-medium text-gray-700 flex items-center">
                                <FontAwesomeIcon icon={faPen}
                                                 className="mx-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl" />
                                Titre <span className="text-danger">*</span>
                            </Form.Label>
                            <Form.Control
                                type="text"
                                value={titre}
                                onChange={(e) => setTitre(e.target.value)}
                                required
                                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            />
                        </Form.Group>
                    </div>
                    <div className="col-12 sm:col-6">
                        <Form.Group controlId="dateDepart">
                            <Form.Label className="block text-sm font-medium text-gray-700 flex items-center">
                                <FontAwesomeIcon icon={faCalendarAlt}
                                                 className="mx-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl" />
                                Date de Départ <span className="text-danger">*</span>
                            </Form.Label>
                            <Form.Control
                                type="datetime-local"
                                value={dateDepart}
                                onChange={(e) => setDateDepart(e.target.value)}
                                required
                                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            />
                        </Form.Group>
                    </div>
                    <div className="col-12 sm:col-6">
                        <Form.Group controlId="confidentielle">
                            <Form.Label className="block text-sm font-medium text-gray-700 flex items-center">
                                <FontAwesomeIcon icon={faLock}
                                                 className="mx-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl" />
                                Confidentialité
                            </Form.Label>
                            <Form.Switch
                                type="checkbox"
                                checked={confidentielle}
                                onChange={(e) => setConfidentielle(e.target.checked)}
                                className="mt-2"
                            />
                        </Form.Group>
                    </div>
                    <div className="col-12 sm:col-6">
                        <Form.Group controlId="participants">
                            <Form.Label className="block text-sm font-medium text-gray-700 flex items-center">
                                <FontAwesomeIcon icon={faUsers}
                                                 className="mx-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl" />
                                Invité(s)
                            </Form.Label>
                            <div className="mt-1">
                                <SelectMember
                                    selectedMembers={idParticipants}
                                    onChange={setIdParticipants}
                                />
                            </div>
                        </Form.Group>
                    </div>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onClose}>Annuler</Button>
                <Button variant="danger" onClick={handleSubmit}>Enregistrer</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default NewCollaborationModal;

const getCurrentDateTime = () => {
    const now: Date = new Date();
    const day: string = String(now.getDate()).padStart(2, '0');
    const month: string = String(now.getMonth() + 1).padStart(2, '0'); // Janvier est 0
    const year: number = now.getFullYear();
    const hours: string = String(now.getHours()).padStart(2, '0');
    const minutes: string = String(now.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
}
