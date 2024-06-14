import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IconProp } from '@fortawesome/fontawesome-svg-core';

interface FormRowProps {
    label: string;
    value: string;
    disabled?: boolean;
    icon?: IconProp;
}

const FormRow: React.FC<FormRowProps> = ({ label, value, disabled = false, icon }) => {
    return (
        <div className="mb-3 row align-items-center">
            <div className="col-12 col-md-4 d-flex align-items-baseline mb-2 mb-md-0">
                {icon && <FontAwesomeIcon icon={icon} className='me-2' />}
                <label className="fw-bold text-start">{label}</label>
            </div>
            <div className="col-12 col-md-8">
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

export default FormRow;
