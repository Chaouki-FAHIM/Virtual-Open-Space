import React, { useEffect, useState, useRef, useCallback } from 'react';
import { GetAllMembers } from "../service/members/GetAllMembers";
import { Membre } from "../model/Membre";
import MembreCard from "../component/card/membre/MembreCard";
import PlaceholderMembreCard from "../component/card/membre/PlaceholderMembreCard";
import MemberModal from "../component/modal/members/MembreModal";

const Home: React.FC = () => {
    const [members, setMembers] = useState<Membre[]>([]);
    const [loading, setLoading] = useState(true);
    const [selectedMemberId, setSelectedMemberId] = useState<string | null>(null);
    const [isScrolling, setIsScrolling] = useState(true);
    const scrollContainerRef = useRef<HTMLDivElement>(null);

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

    useEffect(() => {
        fetchData();
    }, []);

    const handleMemberClick = (id: string) => {
        setSelectedMemberId(id);
        setIsScrolling(false);
    };

    const handleCloseModal = () => {
        setSelectedMemberId(null);
        setIsScrolling(true);
    };

    const handleScroll = useCallback(() => {
        if (scrollContainerRef.current) {
            const { scrollLeft, scrollWidth, clientWidth } = scrollContainerRef.current;
            if (scrollLeft + clientWidth >= scrollWidth - 1) {
                // Reset scroll position
                scrollContainerRef.current.scrollLeft = 0;
                // Fetch new data
                setLoading(true);
                fetchData().then(() => {
                    setLoading(false);
                });
            }
        }
    }, [fetchData]);

    useEffect(() => {
        if (scrollContainerRef.current) {
            const element = scrollContainerRef.current;
            element.addEventListener('scroll', handleScroll);

            return () => {
                element.removeEventListener('scroll', handleScroll);
            };
        }
    }, [scrollContainerRef, handleScroll]);

    // Dupliquez les membres pour permettre un défilement infini
    const duplicatedMembers = [...members, ...members];

    return (
        <>
            <p className="text-center text-muted m-2 mt-5 text-sm-start">Les membres ci-dessous sont actuellement connectés</p>
            <div
                className="scroll-container flex overflow-x-auto d-flex flex-row flex-nowrap overflow-auto border border-1 rounded-3 shadow-3xl m-2"
                ref={scrollContainerRef}
            >
                <div className={`scroll-content ${isScrolling ? '' : 'paused'}`}>
                    {
                        loading ? (
                            <PlaceholderMembreCard/>
                        ) : (
                            duplicatedMembers.map((membreItem, index) => (
                                <div className="scroll-item" key={index}>
                                    <MembreCard membre={membreItem}
                                                badgeColor='bg-dark'
                                                onClick={() => handleMemberClick(membreItem.idMembre)}/>
                                </div>
                            ))
                        )
                    }
                </div>
            </div>

            {selectedMemberId && (
                <MemberModal show={!!selectedMemberId} membreId={selectedMemberId} onClose={handleCloseModal}/>
            )}
        </>
    );
};

export default Home;
