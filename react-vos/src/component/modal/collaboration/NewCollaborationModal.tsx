import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { Tooltip } from 'bootstrap';
import { faLock, faUsers, faPen, faCalendarAlt } from '@fortawesome/free-solid-svg-icons';
import { CreateCollaborationDTO } from '../../../model/collaboration/CreateCollaborationDTO';
import SelectMember from '../../form/SelectMember';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { CreateNewCollaboration } from '../../../service/collaborations/CreateNewCollaboration';
import Alert from "../../Alert";
import './NewCollaborationModal.css';
import TimePicker from "../../form/TimePicker";

interface NewCollaborationModalProps {
    show: boolean;
    onClose: () => void;
    onSave: (collaboration: CreateCollaborationDTO) => void;
}

const getCurrentDate = () => {
    const now = new Date();
    const day = String(now.getDate()).padStart(2, '0');
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const year = now.getFullYear();
    return `${year}-${month}-${day}`;
};

const calculateTimeRemaining = (dateString: string, timeString: string) => {
    const now = new Date();
    const [year, month, day] = dateString.split('-').map(Number);
    const [hours, minutes] = timeString.split(':').map(Number);
    const startDate = new Date(year, month - 1, day, hours, minutes);
    const timeDiff = startDate.getTime() - now.getTime();
    const daysRemaining = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
    const hoursRemaining = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutesRemaining = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
    return { daysRemaining, hoursRemaining, minutesRemaining };
};

const NewCollaborationModal: React.FC<NewCollaborationModalProps> = ({ show, onClose, onSave }) => {
    const [titre, setTitre] = useState('');
    const [date, setDate] = useState(getCurrentDate());
    const [time, setTime] = useState('');
    const [confidentielle, setConfidentielle] = useState(true);
    const [idParticipants, setIdParticipants] = useState<string[]>([]);
    const [timeRemaining, setTimeRemaining] = useState<{ daysRemaining: number, hoursRemaining: number, minutesRemaining: number } | null>(null);
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    const handleTimeChange = (value: string) => {
        setTime(value);
        updateTimeRemaining(date, value);
    };

    const handleDateChange = (value: string) => {
        setDate(value);
        setTime('');
        setTimeRemaining(null);
    };

    const handleSubmit = async () => {
        const dateDepart = `${date}T${time}`;
        const newCollaboration: CreateCollaborationDTO = {
            titre,
            confidentielle,
            dateDepart,
            idProprietaire: '66512b04ee881f13a4e14d4d',
            idInvites: idParticipants
        };

        try {
            await CreateNewCollaboration(newCollaboration);
            onSave(newCollaboration);
            onClose();
        } catch (error) {
            console.error('Error saving the new collaboration', error);
        }
    };

    const updateTimeRemaining = (date: string, time: string) => {
        const remaining = calculateTimeRemaining(date, time);
        setTimeRemaining(remaining);
    };

    useEffect(() => {
        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));
        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    }, [idParticipants]);

    useEffect(() => {
        setIsButtonDisabled(titre === '' || date === '' || time === '');
    }, [titre, date, time]);

    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton className="bg-danger text-white">
                <Modal.Title className="text-lg sm:text-xl md:text-2xl lg:text-3xl xl:text-4xl">Nouvelle Collaboration</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form className="row g-3">
                    <div className="col-12 col-sm-5">
                        <Form.Group controlId="titre">
                            <Form.Label className="block text-sm font-medium text-gray-700 flex items-center">
                                <FontAwesomeIcon icon={faPen}
                                                 className="mx-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl"/>
                                Titre <span className="text-danger">*</span>
                            </Form.Label>
                            <Form.Control
                                type="text"
                                value={titre}
                                onChange={(e) => setTitre(e.target.value)}
                                required
                                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-secondary focus:ring-secondary custom-input sm:text-sm"
                            />
                            <div className="invalid-tooltip">
                                Entrer un titre
                            </div>
                        </Form.Group>
                    </div>
                    <div className="col-12 col-sm-7">
                    <Form.Group controlId="date">
                            <Form.Label className="block text-sm font-medium text-gray-700 flex items-center">
                                <FontAwesomeIcon icon={faCalendarAlt} className="mx-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl" />
                                Date de départ <span className="text-danger">*</span>
                                { date !== '' && time !== '' && timeRemaining && (
                                    <span className="badge rounded-pill text-bg-dark mx-1 ease-in-out duration-300"
                                          data-bs-toggle="tooltip" data-bs-title="Temps resté">
                                        {timeRemaining.daysRemaining > 0 && `${timeRemaining.daysRemaining}j `}
                                        {timeRemaining.hoursRemaining > 0 && `${timeRemaining.hoursRemaining}h `}
                                        {timeRemaining.minutesRemaining > 0 && `${timeRemaining.minutesRemaining}m `}
                                        {timeRemaining.minutesRemaining === 0 && timeRemaining.hoursRemaining === 0 && timeRemaining.minutesRemaining === 0 && `maintenant `}
                                        <i className="bi bi-hourglass-top"></i>
                                </span>
                                )}
                            </Form.Label>
                            <div className='d-flex align-items-center'>
                                <div className="col-7 mx-1">
                                    <Form.Control
                                        type="date"
                                        value={date}
                                        onChange={(e) => handleDateChange(e.target.value)}
                                        min={getCurrentDate()}
                                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-secondary focus:ring-secondary custom-input sm:text-sm"
                                        required
                                    />
                                </div>
                                <div className="col-5">
                                    <TimePicker selectedTime={time} onTimeChange={handleTimeChange} selectedDate={date} />
                                </div>
                            </div>
                        </Form.Group>
                    </div>
                    <div className="col-12">
                        <Form.Group controlId="participants">
                            <Form.Label className="block text-sm font-medium text-gray-700 flex items-center">
                                <FontAwesomeIcon icon={faUsers}
                                                 className="mx-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl"/>
                                Invité(s)
                            </Form.Label>
                            {idParticipants.length > 0 && (
                                <span className="badge rounded-pill text-bg-dark mx-1 ease-in-out duration-300"
                                      data-bs-toggle="tooltip" data-bs-title="Nombre des invités sélectionnés">
                                    {idParticipants.length} <i className="bi bi-person-fill"></i>
                                </span>
                            )}
                            <div className="mt-1">
                                <SelectMember
                                    selectedMembers={idParticipants}
                                    onChange={setIdParticipants}
                                />
                            </div>
                        </Form.Group>
                    </div>
                    <div className="col-12">
                        <Form.Group controlId="confidentielle" className="d-flex align-items-center">
                            <Form.Label className="block text-sm font-medium text-gray-700 flex items-center mb-0">
                                <FontAwesomeIcon icon={faLock} className="mx-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl" />
                                Confidentialité
                            </Form.Label>
                            <Form.Switch
                                className="ms-3 custom-switch"
                                type="checkbox"
                                checked={confidentielle}
                                onChange={(e) => setConfidentielle(e.target.checked)}
                            />
                        </Form.Group>
                    </div>
                </Form>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={onClose}>Annuler</Button>
                <Button variant="danger" onClick={handleSubmit} disabled={isButtonDisabled}>Enregistrer</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default NewCollaborationModal;
