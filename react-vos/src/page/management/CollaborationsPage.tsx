import React, { useEffect, useState } from 'react';
import Pagination from "../../component/Pagination";
import { DisplayCollaborationDTO } from "../../model/collaboration/DisplayCollaborationDTO";
import { CreateCollaborationDTO } from "../../model/collaboration/CreateCollaborationDTO";
import { GetAllCollaborations } from "../../service/collaborations/GetAllCollaborations";
import CollaborationCard from "../../component/card/collaboration/CollaborationCard";
import DisplayCollaborationModal from "../../component/modal/collaboration/DisplayCollaborationModal";
import PlaceholderCollaborationCard from "../../component/card/collaboration/PlaceholderCollaborationCard";
import NewCollaborationModal from "../../component/modal/collaboration/NewCollaborationModal";

const CollaborationsPage: React.FC = () => {
    const [collaborations, setCollaborations] = useState<DisplayCollaborationDTO[]>([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [collaborationsParPage,setCollaborationsParPage] = useState(6);
    const [selectedCollaborationId, setSelectedCollaborationId] = useState<number | null>(null);
    const [showNewCollaborationModal, setShowNewCollaborationModal] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await GetAllCollaborations();
                setCollaborations(result);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching collaboration data', error);
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    const updateCollaborationParPage = () => {
        if (window.innerWidth >= 1400) {
            setCollaborationsParPage(24); // alors 6 collab par ligne ( 3 lignes maximum)
        }
        else if (window.innerWidth >= 1200) {
            setCollaborationsParPage(18); // alors 6 collab par ligne ( 3 lignes maximum)
        } else if (window.innerWidth >= 768) {
            setCollaborationsParPage(8); // alors 4 collab par ligne ( 2 lignes maximum)
        } else {
            setCollaborationsParPage(4);
        }
    };

    useEffect(() => {
        const handleResize = () => {
            updateCollaborationParPage();
        };

        window.addEventListener('resize', handleResize);

        // Initial call to set membersPerPage based on current window size
        updateCollaborationParPage();

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    const indexOfLastCollaboration = currentPage * collaborationsParPage;
    const indexOfFirstCollaborations = indexOfLastCollaboration - collaborationsParPage;
    const currentCollaborations = collaborations.slice(indexOfFirstCollaborations, indexOfLastCollaboration);

    const totalPages = Math.ceil(collaborations.length / collaborationsParPage);

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    const handleCollaborationClick = (id: number) => {
        setSelectedCollaborationId(id);
    };

    const handleCloseModal = () => {
        setSelectedCollaborationId(null);
    };

    const handleSaveNewCollaboration = (newCollaboration: CreateCollaborationDTO) => {
        const displayCollaboration: DisplayCollaborationDTO = {
            idCollaboration: Date.now(), // ou un autre identifiant unique
            dateCreationCollaboration: new Date().toISOString(),
            titre: newCollaboration.titre,
            confidentielle: newCollaboration.confidentielle,
            dateDepart: newCollaboration.dateDepart,
            idProprietaire: newCollaboration.idProprietaire,
            participants: [] // ou vous pouvez initialiser avec les participants si nécessaire
        };
        setCollaborations([...collaborations, displayCollaboration]);
    };

    return (
        <>
            <div className="text-center my-4">
                <h1>Espace de collaborations</h1>
                <button
                    className="btn btn-danger btn-lg mt-3 px-5 fw-bold"
                    onClick={() => setShowNewCollaborationModal(true)}
                >
                    <i className="bi bi-cloud-plus mx-2"></i> Créer
                </button>
            </div>
            <div className="d-flex justify-content-center my-4">
                <input
                    className="form-control custom-input block w-full rounded-md border-gray-300 shadow-sm focus:border-secondary focus:ring-secondary sm:text-sm"
                    type="search"
                    placeholder="Rechercher une collaboration"
                    aria-label="Search"
                    style={{ maxWidth: '300px' }}
                />
            </div>
            <div className="container px-4 py-3 border border-gray-300 rounded-4 my-4 max-w-6xl">
                <div className="row justify-content-center">
                    {loading ? (
                        Array.from({ length: collaborationsParPage }).map((_, index) => (
                            <div className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4" key={index}>
                                <PlaceholderCollaborationCard />
                            </div>
                        ))
                    ) : (
                        currentCollaborations.map((collaborationItem) => (
                            <div className="col-12 col-xl-2 col-sm-3 d-flex justify-content-center my-3" key={collaborationItem.idCollaboration}>
                                <CollaborationCard
                                    collaboration={collaborationItem}
                                    onClick={() => handleCollaborationClick(collaborationItem.idCollaboration)}
                                    nombreCollabParligne={collaborationsParPage}
                                />
                            </div>
                        ))
                    )}
                </div>

                {!loading && collaborations.length > collaborationsParPage && (
                    <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={handlePageChange} />
                )}
                {selectedCollaborationId && (
                    <DisplayCollaborationModal
                        show={!!selectedCollaborationId}
                        collaborationId={selectedCollaborationId}
                        onClose={handleCloseModal}
                    />
                )}
                <NewCollaborationModal
                    show={showNewCollaborationModal}
                    onClose={() => setShowNewCollaborationModal(false)}
                    onSave={handleSaveNewCollaboration}
                />
            </div>
        </>
    );
};

export default CollaborationsPage;
