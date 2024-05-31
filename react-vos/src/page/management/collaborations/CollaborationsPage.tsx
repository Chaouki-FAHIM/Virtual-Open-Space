import React, { useEffect, useState } from 'react';
import PlaceholderCollaborationCard from "../../../component/card/collaboration/PlaceholderCollaborationCard";
import Pagination from "../../../component/Pagination";
import { Collaboration } from "../../../model/Collaboration";
import { GetAllCollaborations } from "../../../service/collaborations/GetAllCollaborations";
import CollaborationCard from "../../../component/card/collaboration/CollaborationCard";
import CollaborationModal from "../../../component/modal/collaborations/CollaborationModal";

const CollaborationsPage: React.FC = () => {
    const [collaborations, setCollaborations] = useState<Collaboration[]>([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [collaborationsPerPage] = useState(3);
    const [selectedCollaborationId, setSelectedCollaborationId] = useState<number | null>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await GetAllCollaborations();
                setCollaborations(result);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching data', error);
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    const indexOfLastCollaboration = currentPage * collaborationsPerPage;
    const indexOfFirstCollaborations = indexOfLastCollaboration - collaborationsPerPage;
    const currentCollaborations = collaborations.slice(indexOfFirstCollaborations, indexOfLastCollaboration);

    const totalPages = Math.ceil(collaborations.length / collaborationsPerPage);

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    const handleCollaborationClick = (id: number) => {
        setSelectedCollaborationId(id);
    };

    const handleCloseModal = () => {
        setSelectedCollaborationId(null);
    };

    return (
        <>
            <div className="text-center my-4">
                <h1>Espace de collaborations</h1>
                <button className="btn btn-danger btn-lg mt-3 px-5 fw-bold">
                    <i className="bi bi-cloud-plus mx-2"></i> Créer
                </button>
            </div>
            <div className="container mx-auto px-4 py-3 border border-gray-300 rounded-4 my-4 max-w-6xl">
                <div className="row justify-content-center">
                    {loading ? (
                        Array.from({ length: collaborationsPerPage }).map((_, index) => (
                            <div className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4"
                                 key={index}>
                                <PlaceholderCollaborationCard />
                            </div>
                        ))
                    ) : (
                        currentCollaborations.map((collaborationItem) => (
                            <div className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4"
                                 key={collaborationItem.idCollaboration}>
                                <CollaborationCard collaboration={collaborationItem}
                                                   onClick={() => handleCollaborationClick(collaborationItem.idCollaboration)} />
                            </div>
                        ))
                    )}
                </div>
                {!loading && collaborations.length > collaborationsPerPage && (
                    <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={handlePageChange} />
                )}
                {selectedCollaborationId && (
                    <CollaborationModal show={!!selectedCollaborationId} collaborationId={selectedCollaborationId}
                                        onClose={handleCloseModal} />
                )}
            </div>
        </>
    );
};

export default CollaborationsPage;
