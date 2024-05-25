import React from 'react';

interface FormRowProps {
    label: string;
    value: string;
    disabled?: boolean;
}

const FormRow: React.FC<FormRowProps> = ({ label, value, disabled = false }) => {
    return (
        <div className="mb-3 row align-items-center">
            <label className="col-sm-3 col-form-label fw-bold text-start">{label}</label>
            <div className="col-sm-9">
                <input
                    className="form-control"
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
