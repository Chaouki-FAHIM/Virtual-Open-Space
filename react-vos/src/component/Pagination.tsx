import React from 'react';

interface PaginationProps {
    currentPage: number;
    totalPages: number;
    onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {
    const handlePageChange = (page: number):void => {
        if (page > 0 && page <= totalPages) {
            onPageChange(page);
        }
    };

    const pages = Array.from({ length: totalPages }, (_, i:number) => i + 1);

    return (
        <nav aria-label="Page navigation">
            <ul className="pagination pagination-sm justify-content-center">
                {pages.map(page => (
                    <li key={page} className={`page-item ${page === currentPage ? 'active' : ''}`}>
                        <button className="page-link" onClick={() => handlePageChange(page)}>
                            {page}
                        </button>
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default Pagination;
