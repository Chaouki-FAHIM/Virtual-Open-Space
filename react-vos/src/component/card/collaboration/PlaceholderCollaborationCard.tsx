import React from 'react';

const PlaceholderCollaborationCard: React.FC = () => {
    return (
        <div className="card text-center" aria-hidden="true" style={{width: '50%', height : '5.5rem'}}>
            <div className="card-header bg-warning">
                <p className="card-text placeholder-glow">
                    <span className="placeholder col-12 m-6"></span>
                </p>
            </div>
            <div className="card-body" style={{width: '50%', height: '5rem' }}>
                <p className="placeholde col-auto text-center bg-warning" ></p>

            </div>
        </div>
    );
};

export default PlaceholderCollaborationCard;
