import React from 'react';


const PlaceholderMembreCard: React.FC = () => {
    return (
        <div className="card text-center" aria-hidden="true">
            <div className="card-body">
                <div className="rounded-circle bg-warning mb-3" style={{ width: '100px', height: '100px', margin: '0 auto' }}></div>
                <h5 className="card-title placeholder-glow">
                    <span className="placeholder col-6"></span>
                </h5>
                <p className="card-text placeholder-glow">
                    <span className="placeholder col-4 bg-warning"></span>
                </p>
            </div>
        </div>
    );
};

export default PlaceholderMembreCard;
