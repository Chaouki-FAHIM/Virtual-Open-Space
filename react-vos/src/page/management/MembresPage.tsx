import React, { useEffect, useState } from 'react';
import { GetAllMembers } from '../../service/members/GetAllMembers';
import { DisplayMembreDTO } from '../../model/membre/DisplayMembreDTO';
import MembreCard from '../../component/card/membre/MembreCard';
import PlaceholderMembreCard from '../../component/card/membre/PlaceholderMembreCard';
import Pagination from '../../component/Pagination';
import DisplayMemberModal from '../../component/modal/member/DisplayMembreModal';
import MembreConnectedList from "../../component/list/membre/MembreConnectedList";

const MembresPage: React.FC = () => {
    const [members, setMembers] = useState<DisplayMembreDTO[]>([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [membersPerPage] = useState(6);
    const [selectedMemberId, setSelectedMemberId] = useState<string | null>(null);

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

    const handleMemberClick = (id: string) => {
        setSelectedMemberId(id);
    };

    const handleCloseModal = () => {
        setSelectedMemberId(null);
    };

    return (
        <>
            <div className="container">
                <h1 className="text-center my-4">Membres Page</h1>
                <div className="row justify-content-center">
                    {loading ? (
                        Array.from({ length: membersPerPage }).map((_, index) => (
                            <div
                                className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-1"
                                key={index}
                            >
                                <PlaceholderMembreCard />
                            </div>
                        ))
                    ) : (
                        currentMembers.map((membreItem) => (
                            <div
                                className="col-6 col-sm-5 col-md-4 col-lg-2 d-flex justify-content-center"
                                key={membreItem.idMembre}
                            >
                                <MembreCard membre={membreItem} onClick={() => handleMemberClick(membreItem.idMembre)} />
                            </div>
                        ))
                    )}
                </div>

                {!loading && members.length > membersPerPage && (
                    <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={handlePageChange} />
                )}
                {selectedMemberId && (
                    <DisplayMemberModal show={!!selectedMemberId} membreId={selectedMemberId} onClose={handleCloseModal} />
                )}
            </div>
            <hr />
            <MembreConnectedList />
        </>
    );
};

export default MembresPage;