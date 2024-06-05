import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { CreateCollaborationDTO } from '../../../model/collaboration/CreateCollaborationDTO';
import { GetAllMembers } from '../../../service/members/GetAllMembers';
import { DisplayMembreDTO } from '../../../model/membre/DisplayMembreDTO';

interface NewCollaborationModalProps {
    show: boolean;
    onClose: () => void;
    onSave: (collaboration: CreateCollaborationDTO) => void;
}

const NewCollaborationModal: React.FC<NewCollaborationModalProps> = ({ show, onClose, onSave }) => {
    const [titre, setTitre] = useState('');
    const [dateDepart, setDateDepart] = useState('');
    const [confidentielle, setConfidentielle] = useState(false);
    const [idParticipants, setIdParticipants] = useState<string[]>([]);
    const [members, setMembers] = useState<DisplayMembreDTO[]>([]);
    const [loadingMembers, setLoadingMembers] = useState(true);

    useEffect(() => {
        const fetchMembers = async () => {
            try {
                const result = await GetAllMembers();
                setMembers(result);
                setLoadingMembers(false);
            } catch (error) {
                console.error('Error fetching members data', error);
                setLoadingMembers(false);
            }
        };

        if (show) {
            fetchMembers();
        }
    }, [show]);

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

    const handleParticipantChange = (id: string) => {
        setIdParticipants(prev =>
            prev.includes(id) ? prev.filter(pid => pid !== id) : [...prev, id]
        );
    };

    return (
        <Modal show={show} onHide={onClose}>
            <Modal.Header closeButton className="bg-danger text-white">
                <Modal.Title>Nouvelle Collaboration</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="titre">
                        <Form.Label>Titre</Form.Label>
                        <Form.Control
                            type="text"
                            value={titre}
                            onChange={(e) => setTitre(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="dateDepart">
                        <Form.Label>Date de Départ</Form.Label>
                        <Form.Control
                            type="date"
                            value={dateDepart}
                            onChange={(e) => setDateDepart(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="confidentielle">
                        <Form.Check
                            type="checkbox"
                            label="Confidentielle"
                            checked={confidentielle}
                            onChange={(e) => setConfidentielle(e.target.checked)}
                        />
                    </Form.Group>
                    <Form.Group controlId="participants">
                        <Form.Label>Participants</Form.Label>
                        {loadingMembers ? (
                            <div>Loading members...</div>
                        ) : (
                            members.map(member => (
                                <Form.Check
                                    key={member.idMembre}
                                    type="checkbox"
                                    label={`${member.nomMembre} ${member.prenom}`}
                                    checked={idParticipants.includes(member.idMembre)}
                                    onChange={() => handleParticipantChange(member.idMembre)}
                                />
                            ))
                        )}
                    </Form.Group>
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
