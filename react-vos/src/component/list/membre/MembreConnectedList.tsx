import React, { useEffect, useState } from 'react';
import { GetAllMembers } from '../../../service/members/GetAllMembers';
import { Membre } from '../../../model/Membre';
import PlaceholderMembreCard from '../../../component/card/membre/PlaceholderMembreCard';
import Pagination from '../../../component/Pagination';

const MembreConnectedList: React.FC = () => {
    const [members, setMembers] = useState<Membre[]>([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const membersPerPage = 12;

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

    return (
        <div className="card m-2 shadow-2xl">
            <div
                className="card-header"
                data-bs-toggle="collapse"
                data-bs-target="#memberConnected"
                aria-expanded="false"
                aria-controls="memberConnected"
            >
                Membres actuellement connectés
            </div>
            <div className="card-body collapse" id="memberConnected">
                <div className="flex overflow-auto d-flex flex-row flex-nowrap">
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
                        currentMembers.map((connected) => (
                            <div key={connected.idMembre} className="m-3 text-center">
                                <img
                                    src={connected.image || 'https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'}
                                    alt={`${connected.nomMembre} ${connected.prenom}`}
                                    className="rounded-circle border border-black border-2"
                                    style={{ width: '3.5rem', height: '3.5rem' }}
                                />
                                <div className="font-semibold text-sm md:text-lg lg:text-xl">
                                    {connected.nomMembre} {connected.prenom}
                                </div>
                                <p className="badge bg-danger text-sm md:text-lg lg:text-xl">
                                    {connected.roleHabilation}
                                </p>
                            </div>
                        ))
                    )}
                </div>
                {!loading && members.length > membersPerPage && (
                    <Pagination
                        currentPage={currentPage}
                        totalPages={totalPages}
                        onPageChange={(page) => setCurrentPage(page)}
                    />
                )}
            </div>
        </div>
    );
};

export default MembreConnectedList;
