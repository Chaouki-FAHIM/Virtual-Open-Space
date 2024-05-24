import React, {useEffect, useState} from 'react';
import {GetAllMembers} from "../../service/members/GetAllMembers";


interface MembresData {
    idMembres: string;
    nomMembres: string;
    prenom: string;
    roleHabilation: string;
}

function Membres() {
    const [members, setMembers] = useState<MembresData[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await GetAllMembers();
                setMembers(result);
            } catch (error) {
                console.error('Error fetching data', error);
            }
        };

        fetchData();
    }, []);

    return (
        <div>
            <h1>Membres Page</h1>
            <ul>
                {members.map((item:MembresData, index:number) => (
                    <li key={index}>{item.nomMembres} {item.prenom}</li>

                ))}
            </ul>
        </div>
    );
}

export default Membres;





