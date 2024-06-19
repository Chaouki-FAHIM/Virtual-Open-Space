import React, { useEffect, useState } from 'react';
import Autosuggest from 'react-autosuggest';
import Pagination from "../../component/Pagination";
import { DisplayCollaborationDTO } from "../../model/collaboration/DisplayCollaborationDTO";
import { CreateCollaborationDTO } from "../../model/collaboration/CreateCollaborationDTO";
import { GetAllCollaborations } from "../../service/collaborations/GetAllCollaborations";
import { GetCollaborationByTitle } from "../../service/collaborations/GetCollaborationByTitle";
import CollaborationCard from "../../component/card/collaboration/CollaborationCard";
import DisplayCollaborationModal from "../../component/modal/collaboration/DisplayCollaborationModal";
import PlaceholderCollaborationCard from "../../component/card/collaboration/PlaceholderCollaborationCard";
import NewCollaborationModal from "../../component/modal/collaboration/NewCollaborationModal";


const CollaborationsPage: React.FC = () => {
    const [collaborations, setCollaborations] = useState<DisplayCollaborationDTO[]>([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [collaborationsParPage, setCollaborationsParPage] = useState(6);
    const [selectedCollaborationId, setSelectedCollaborationId] = useState<number | null>(null);
    const [showNewCollaborationModal, setShowNewCollaborationModal] = useState(false);
    const [searchTerm, setSearchTerm] = useState("");
    const [suggestions, setSuggestions] = useState<DisplayCollaborationDTO[]>([]);
    const [noResults, setNoResults] = useState(false); // State for no results

    const fetchAllCollaborations = async () => {
        try {
            const result = await GetAllCollaborations();
            setCollaborations(result);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching collaboration data', error);
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchAllCollaborations();
    }, []);

    const updateCollaborationParPage = () => {
        if (window.innerWidth >= 1400) {
            setCollaborationsParPage(24);
        } else if (window.innerWidth >= 1200) {
            setCollaborationsParPage(18);
        } else if (window.innerWidth >= 768) {
            setCollaborationsParPage(8);
        } else {
            setCollaborationsParPage(4);
        }
    };

    useEffect(() => {
        const handleResize = () => {
            updateCollaborationParPage();
        };

        window.addEventListener('resize', handleResize);
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
            idCollaboration: Date.now(),
            dateCreationCollaboration: new Date().toISOString(),
            titre: newCollaboration.titre,
            confidentielle: newCollaboration.confidentielle,
            dateDepart: newCollaboration.dateDepart,
            idProprietaire: newCollaboration.idProprietaire,
            participants: []
        };
        setCollaborations([...collaborations, displayCollaboration]);
    };

    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>, { newValue }: any) => {
        setSearchTerm(newValue);
    };

    const handleSuggestionsFetchRequested = async ({ value }: any) => {
        if (value.trim() === "") {
            fetchAllCollaborations();
        } else {
            try {
                const result = await GetCollaborationByTitle(value);
                setSuggestions(result);
            } catch (error) {
                console.error('Error fetching suggestions', error);
            }
        }
    };

    const handleSuggestionsClearRequested = () => {
        setSuggestions([]);
    };

    const handleSearchSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setLoading(true);
        try {
            const result = await GetCollaborationByTitle(searchTerm);
            if (result.length === 0) {
                setNoResults(true);
            } else {
                setNoResults(false);
            }
            setCollaborations(result);
            setLoading(false);
            setCurrentPage(1); // Reset to the first page after a search
        } catch (error) {
            console.error('Error fetching search results', error);
            setLoading(false);
        }
    };

    useEffect(() => {
        if (searchTerm.trim() === "") {
            fetchAllCollaborations();
        }
    }, [searchTerm]);

    const renderSuggestion = (suggestion: DisplayCollaborationDTO, { query }: any) => {
        const regex = new RegExp(`(${query})`, 'gi');
        const parts = suggestion.titre.split(regex);
        return (
            <div className="suggestion-item">
                {parts.map((part, index) =>
                    part.toLowerCase() === query.toLowerCase() ? (
                        <span key={index} style={{ fontWeight: 'bold', color: '#F6A500' }}>
                            {part}
                        </span>
                    ) : (
                        <span key={index}>{part}</span>
                    )
                )}
            </div>
        );
    };

    const inputProps = {
        className: "form-control custom-input block w-full rounded-md border-gray-300 shadow-sm focus:border-secondary focus:ring-secondary sm:text-sm",
        type: "search",
        placeholder: "Rechercher une collaboration",
        ariaLabel: "Search",
        value: searchTerm,
        onChange: handleSearchChange,
        style: { minWidth: '300px', maxWidth: '100%' }
    };

    const theme = {
        container: 'autosuggest-container',
        suggestionsContainer: 'suggestions-container',
        suggestionsList: 'suggestions-list',
        suggestion: 'suggestion-item',
        suggestionHighlighted: 'suggestion-item--highlighted'
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
                <form onSubmit={handleSearchSubmit} className="autosuggest-container w-full max-w-lg">
                    <Autosuggest
                        suggestions={suggestions}
                        onSuggestionsFetchRequested={handleSuggestionsFetchRequested}
                        onSuggestionsClearRequested={handleSuggestionsClearRequested}
                        getSuggestionValue={(suggestion: DisplayCollaborationDTO) => suggestion.titre}
                        renderSuggestion={renderSuggestion}
                        inputProps={inputProps}
                        theme={theme}
                    />
                </form>
            </div>
            <div className="container px-4 py-3 border border-gray-300 rounded-4 my-4 max-w-6xl">
                <div className="row justify-content-center">
                    {loading ? (
                        Array.from({ length: collaborationsParPage }).map((_, index) => (
                            <div className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4" key={index}>
                                <PlaceholderCollaborationCard />
                            </div>
                        ))
                    ) : noResults ? (
                        <div className="col-12 text-center">
                            <p>Aucune collaboration trouvée pour le titre <strong>"{searchTerm}"</strong></p>
                        </div>
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
