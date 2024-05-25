import React from 'react';


interface FormSubRowProps {
    label: string;
    value: string;
}

const FormSubRow: React.FC<FormSubRowProps> = ({ label, value}) => {
    return (
        <div className="mb-3 row align-items-center">
            <label className="col-sm-3 col-form-label fw-bold text-sm-start">{label}</label>
            <div className="col-sm-9">
                <input
                    className="form-control form-control-sm"
                    type="text"
                    value={value}
                    disabled
                />
            </div>
        </div>
    );
};

export default FormSubRow;
