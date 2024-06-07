import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IconProp } from '@fortawesome/fontawesome-svg-core';

interface FormRowProps {
    label: string;
    value: string;
    disabled?: boolean;
    icon?: IconProp; // Nouvelle prop pour l'icône
}

const FormRow: React.FC<FormRowProps> = ({ label, value, disabled = false, icon }) => {
    return (
        <div className="mb-3 row items-center">
            <label className="col-12 sm:col-3 col-form-label fw-bold text-start d-flex align-items-center">
                {icon && <FontAwesomeIcon icon={icon} className="mr-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl" />}
                {label}
            </label>
            <div className="col-12 sm:col-9">
                <input
                    className="form-control w-full sm:w-auto"
                    type="text"
                    value={value}
                    placeholder="Disabled input"
                    aria-label="Disabled input example"
                    disabled={disabled}
                />
            </div>
        </div>
    );
};

export default FormRow;
