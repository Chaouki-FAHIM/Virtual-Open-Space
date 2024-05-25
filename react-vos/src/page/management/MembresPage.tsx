import React, { useEffect, useState } from 'react';
import { GetAllMembers } from "../../service/members/GetAllMembers";
import { Membre } from "../../model/Membre";
import MembreCard from "../../component/card/MembreCard";
import PlaceholderCard from "../../component/card/PlaceholderCard";
import Pagination from "../../component/Pagination";
import MemberModal from "../../component/modal/modal-members/MembreModal";

const MembresPage: React.FC = () => {
    const [members, setMembers] = useState<Membre[]>([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [membersPerPage] = useState(4);
    const [selectedMember, setSelectedMember] = useState<Membre | null>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await GetAllMembers();
                setMembers(result);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching data', error);
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    const indexOfLastMember = currentPage * membersPerPage;
    const indexOfFirstMember = indexOfLastMember - membersPerPage;
    const currentMembers = members.slice(indexOfFirstMember, indexOfLastMember);

    const totalPages = Math.ceil(members.length / membersPerPage);

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    const handleMemberClick = (member: Membre) => {
        setSelectedMember(member);
    };

    const handleCloseModal = () => {
        setSelectedMember(null);
    };

    return (
        <div className="container">
            <h1 className="text-center my-4">Membres Page</h1>
            <hr />
            <div className="row justify-content-center">
                {loading ? (
                    // Afficher les placeholders pendant le chargement
                    Array.from({ length: membersPerPage }).map((_, index) => (
                        <div className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4" key={index}>
                            <PlaceholderCard />
                        </div>
                    ))
                ) : (
                    // Afficher les cartes membres une fois les données chargées
                    currentMembers.map((membreItem) => (
                        <div className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4" key={membreItem.idMembre}>
                            <MembreCard membre={membreItem} onClick={() => handleMemberClick(membreItem)} />
                        </div>
                    ))
                )}
            </div>
            {!loading && members.length > membersPerPage && (
                <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={handlePageChange} />
            )}
            {selectedMember && (
                <MemberModal show={!!selectedMember} member={selectedMember} onClose={handleCloseModal} />
            )}
        </div>
    );
};

export default MembresPage;
