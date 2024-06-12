import React from "react";

interface AlertPropos {
    msgError:string
}

const Alert: React.FC<AlertPropos> = ({msgError}) => {
    return (
        <>
            <div className="alert alert-warning d-flex align-items-center" role="alert">
                <i className="bi bi-exclamation-triangle-fill flex-shrink-0 me-2"></i>
                <div>
                    {msgError}
                </div>
            </div>
        </>
    )
};

export default Alert;