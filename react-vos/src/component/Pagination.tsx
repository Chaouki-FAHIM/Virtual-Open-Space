import React from 'react';

interface PaginationProps {
    currentPage: number;
    totalPages: number;
    onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {
    const handlePageChange = (page: number): void => {
        if (page > 0 && page <= totalPages) {
            onPageChange(page);
        }
    };

    const pages = Array.from({ length: totalPages }, (_, i: number) => i + 1);

    return (
        <nav aria-label="Page navigation" className='mt-2'>
            <ul className="pagination justify-content-center">
                <li className={`page-item ${currentPage === 1 ? 'disabled' : ''}`}>
                    <button
                        className="page-link"
                        aria-label="Previous"
                        onClick={() => handlePageChange(currentPage - 1)}
                        disabled={currentPage === 1}
                    >
                        <span aria-hidden="true">&laquo;</span>
                    </button>
                </li>
                {pages.map(page => (
                    <li key={page} className={`page-item ${page === currentPage ? 'active' : ''}`}>
                        <button
                            className={`page-link ${page === currentPage ? 'bg-dark border-dark text-white' : ''}`}
                            onClick={() => handlePageChange(page)}
                        >
                            {page}
                        </button>
                    </li>
                ))}
                <li className={`page-item ${currentPage === totalPages ? 'disabled' : ''}`}>
                    <button
                        className="page-link"
                        aria-label="Next"
                        onClick={() => handlePageChange(currentPage + 1)}
                        disabled={currentPage === totalPages}
                    >
                        <span aria-hidden="true">&raquo;</span>
                    </button>
                </li>
            </ul>
        </nav>
    );
};

export default Pagination;
