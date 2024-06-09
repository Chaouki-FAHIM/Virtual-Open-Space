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
    const [membersPerPage,setMembersPerPage] = useState(24);
    const [selectedMemberId, setSelectedMemberId] = useState<string | null>(null);

    const updateMembersPerPage = () => {
        console.log(window.innerWidth)
        if (window.innerWidth >= 1200) {
            setMembersPerPage(24);
        } else if (window.innerWidth >= 768) {
            setMembersPerPage(9);
        } else {
            setMembersPerPage(12);
        }
    };

    useEffect(() => {
        const handleResize = () => {
            updateMembersPerPage();
        };

        window.addEventListener('resize', handleResize);

        // Initial call to set membersPerPage based on current window size
        updateMembersPerPage();

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

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
                                className="flex space-x-2 col-4 col-sm-3 col-md-2 col-lg-1 justify-content-center"
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
