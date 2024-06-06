import React, { useEffect, useState } from 'react';
import Select from 'react-select';
import { DisplayMembreDTO } from '../../model/membre/DisplayMembreDTO';
import { GetAllMembers } from '../../service/members/GetAllMembers';

interface SelectMemberProps {
    selectedMembers: string[];
    onChange: (selectedMembers: string[]) => void;
}

const SelectMember: React.FC<SelectMemberProps> = ({ selectedMembers, onChange }) => {
    const [members, setMembers] = useState<DisplayMembreDTO[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchMembers = async () => {
            try {
                const result = await GetAllMembers();
                const formattedMembers = result.map((member: { idMembre: any; nomMembre: any; prenom: any; image: any; }) => ({
                    ...member,
                    value: member.idMembre,
                    label: `${member.nomMembre} ${member.prenom}`
                }));
                setMembers(formattedMembers);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching members data', error);
                setLoading(false);
            }
        };

        fetchMembers();
    }, []);

    const handleSelectChange = (selectedOptions: any) => {
        const selectedIds = selectedOptions.map((option: any) => option.value);
        onChange(selectedIds);
    };

    if (loading) {
        return <div>Loading members...</div>;
    }

    return (
        <Select
            options={members}
            isMulti
            value={members.filter(member => selectedMembers.includes(member.idMembre))}
            onChange={handleSelectChange}
            formatOptionLabel={(option: any) => (
                <div className="flex items-center">
                    <img src={option.image || 'https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'}
                         alt={option.label}
                         className="rounded-circle border border-danger border-2 mx-2"
                         style={{ width: '2.5rem', height: '2.5rem' }} />
                    <span>{option.label}</span>
                </div>
            )}
            noOptionsMessage={() => 'Aucun invité trouvé'}
            styles={{
                multiValue: (styles) => ({ ...styles, backgroundColor: 'black' }),
                multiValueLabel: (styles) => ({ ...styles, color: 'white' }),
                container: (styles) => ({ ...styles, color: 'grey' }),
            }}
        />
    );
};

export default SelectMember;
