import React, { useEffect, useState } from 'react';
import Select from 'react-select';

interface TimePickerProps {
    selectedTime: string;
    onTimeChange: (time: string) => void;
    selectedDate: string;
}

const TimePicker: React.FC<TimePickerProps> = ({ selectedTime, onTimeChange, selectedDate }) => {
    const now: Date = new Date();
    const currentTime: number = now.getHours() * 60 + now.getMinutes();

    const generateTimes = () => {
        const times = [];
        for (let hour = 0; hour < 24; hour++) {
            for (let minute = 0; minute < 60; minute += 5) {
                const time = `${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
                const timeInMinutes = hour * 60 + minute;

                // Filtrer les temps disponibles en fonction de la date et de l'heure actuelle
                if (selectedDate === now.toISOString().split('T')[0] && timeInMinutes <= currentTime) {
                    continue;
                }

                times.push({ value: time, label: time });
            }
        }
        return times;
    };

    const [times, setTimes] = useState(generateTimes);

    useEffect(() => {
        const newTimes = generateTimes();
        setTimes(newTimes);

        if (selectedTime === '' && newTimes.length > 0) {
            onTimeChange(newTimes[0].value);
        }
    }, [selectedDate, onTimeChange, selectedTime]);

    const customStyles = {
        control: (provided: any) => ({
            ...provided,
            borderColor: '#d1d5db', // border-gray-300
            boxShadow: 'none',
            '&:hover': {
                borderColor: '#d1d5db', // border-gray-300
            },
        }),
        option: (provided: any, state: { isSelected: any; isFocused: any; }) => ({
            ...provided,
            backgroundColor: state.isSelected ? 'black' : state.isFocused ? 'black' : 'white',
            color: state.isSelected || state.isFocused ? 'white' : 'black',
            '&:hover': {
                backgroundColor: 'black',
                color: 'white',
            },
        }),
    };

    return (
        <Select
            value={times.find(time => time.value === selectedTime)}
            onChange={(option) => {
                if (option)
                    onTimeChange(option.value);
            }}
            options={times}
            styles={customStyles}
            className="mt-1 block w-full rounded-md shadow-sm focus:border-secondary focus:ring-secondary custom-input sm:text-sm"
        />
    );
};

export default TimePicker;
