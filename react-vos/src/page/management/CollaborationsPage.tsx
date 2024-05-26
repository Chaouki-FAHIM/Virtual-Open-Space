import React, {useEffect, useState} from 'react';
import PlaceholderCard from "../../component/card/PlaceholderCard";
import Pagination from "../../component/Pagination";
import {Collaboration} from "../../model/Collaboration";
import {GetAllCollaborations} from "../../service/collaborations/GelAllCollaborations";
import CollaborationCard from "../../component/card/CollaborationCard";

function CollaborationsPage() {

    const [collaborations, setCollaborations] = useState<Collaboration[]>([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [collaborationsPerPage] = useState(4);
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

    return (
        <div className="container">
            <h1 className="text-center my-4">Collaborations Page</h1>
            <hr />
            <div className="row justify-content-center">
                {loading ? (
                    // Afficher les placeholders pendant le chargement
                    Array.from({ length: collaborationsPerPage }).map((_, index) => (
                        <div className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4" key={index}>
                            <PlaceholderCard />
                        </div>
                    ))
                ) : (
                    // Afficher les cartes membres une fois les données chargées
                    currentCollaborations.map((collaborationItem) => (
                        <div className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4" key={collaborationItem.idCollaboration}>
                            <CollaborationCard collaboration={collaborationItem} onClick={() => handleCollaborationClick(collaborationItem.idCollaboration)}/>
                        </div>
                    ))
                )}
            </div>
            {!loading && collaborations.length > collaborationsPerPage && (
                <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={handlePageChange} />
            )}
            {/*{selectedCollaborationId && (*/}
            {/*    <MemberModal show={!!selectedCollaborationId} collaborationId={selectedCollaborationId} onClose={handleCloseModal} />*/}
            {/*)}*/}
        </div>
    );
}

export default CollaborationsPage;
