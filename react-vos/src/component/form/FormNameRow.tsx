import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IconProp } from '@fortawesome/fontawesome-svg-core';

interface FormRowProps {
    label: string;
    value: string;
    disabled?: boolean;
    icon?: IconProp;
}

const FormNameRow: React.FC<FormRowProps> = ({ label, value, disabled = false, icon }) => {
    return (
        <div className="mb-3 row align-items-center">
            <div className="col-12 col-sm-5 d-flex align-items-center mb-2 mb-sm-0">
                {icon && <FontAwesomeIcon icon={icon} className="me-2 text-xs sm:text-sm md:text-base lg:text-lg xl:text-xl" />}
                <label className="fw-bold text-start">{label}</label>
            </div>
            <div className="col-12 col-sm-7">
                <input
                    className="form-control w-100"
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

export default FormNameRow;
