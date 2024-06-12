import React, { useEffect, useState } from 'react';
import Select, { components } from 'react-select';
import { Tooltip } from 'bootstrap';
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

    useEffect(() => {
        const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        const tooltipList = tooltipTriggerList.map(tooltipTriggerEl => new Tooltip(tooltipTriggerEl));
        return () => {
            tooltipList.forEach(tooltip => tooltip.dispose());
        };
    }, [members, selectedMembers]);

    const handleSelectChange = (selectedOptions: any) => {
        const selectedIds = selectedOptions.map((option: any) => option.value);
        onChange(selectedIds);
    };

    const handleRemoveMember = (memberId: string) => {
        const updatedSelectedMembers = selectedMembers.filter(id => id !== memberId);
        onChange(updatedSelectedMembers);
    };

    const MultiValueContainer = (props: any) => (
        <components.MultiValueContainer {...props}>
            <div style={{ position: 'relative', display: 'inline-block', width: '8vw', height: '8vw', maxWidth: '50px', maxHeight: '50px' }}>
                <img
                    src={props.data.image || 'https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'}
                    alt={props.data.label}
                    className="rounded-circle border border-danger border-2 mx-1"
                    style={{ width: '100%', height: '100%' }}
                    data-bs-toggle="tooltip"
                    title={props.data.label}
                />
                <span
                    className="badge rounded-pill text-bg-danger"
                    style={{ position: 'absolute', top: '0.2vw', right: '0.2vw', transform: 'translate(50%, -50%)', fontSize: '0.4em', cursor: 'pointer' }}
                    onClick={() => handleRemoveMember(props.data.idMembre)}
                >
                    <i className="bi bi-x-lg"></i>
                </span>
            </div>
        </components.MultiValueContainer>
    );

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
                    <img
                        src={option.image || 'https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-PNG-File.png'}
                        alt={option.label}
                        className="rounded-circle border border-danger border-2 mx-2"
                        style={{ width: '2.5rem', height: '2.5rem' }}
                    />
                    <span className='text-wrap text-dark'>{option.label}</span>
                </div>
            )}
            noOptionsMessage={() => 'Aucun invité trouvé'}
            components={{ MultiValueContainer }}
            styles={{
                multiValue: (styles) => ({ ...styles, backgroundColor: 'transparent' }),
                multiValueLabel: (styles) => ({ ...styles, display: 'none' }),
                container: (styles) => ({ ...styles, color: 'grey' }),
                control: (styles, state) => ({
                    ...styles,
                    boxShadow: state.isFocused ? '0 0 0 0.05rem rgba(108, 117, 125, 0.25)' : styles.boxShadow,
                    borderColor: state.isFocused ? 'var(--bs-secondary)' : styles.borderColor,
                    '&:hover': {
                        borderColor: state.isFocused ? 'var(--bs-secondary)' : styles.borderColor,
                    },
                }),
                option: (styles, state) => ({
                    ...styles,
                    backgroundColor: state.isFocused ? 'orange' : state.isSelected ? 'orange' : styles.backgroundColor,
                    color: state.isFocused || state.isSelected ? 'white' : styles.color,
                    '&:hover': {
                        backgroundColor: 'orange',
                        color: 'white',
                    },
                }),
            }}
        />
    );
};

export default SelectMember;
