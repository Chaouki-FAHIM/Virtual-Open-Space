import React, { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';

interface NavPageItem {
    name: string;
    icone: string;
    link: string;
}

const navPageItems: Array<NavPageItem> = [
    { name: "Accueil", icone: "house", link: "/" },
    { name: "Equipes", icone: "collection", link: "/equipes" },
    { name: "Membres", icone: "people", link: "/membres" },
    { name: "Collaborations", icone: "microsoft-teams", link: "/collaborations" },
    { name: "Déconnecter", icone: "box-arrow-right", link: "/auth" }
];

function AppBar() {
    const [selectedPage, setSelectedPage] = useState<string>(navPageItems[0].name);
    const [isMenuOpen, setIsMenuOpen] = useState<boolean>(false);
    const location = useLocation();

    useEffect(() => {
        const currentNavItem = navPageItems.find(item => item.link === location.pathname);
        if (currentNavItem) {
            setSelectedPage(currentNavItem.name);
        }
    }, [location]);

    const handleTogglerClick = () => {
        setIsMenuOpen(!isMenuOpen);
    };

    const closeMenu = () => {
        setIsMenuOpen(false);
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/">
                    <div className="flex-container">
                        <img src="/logo/awb.png" alt="Logo" style={{ width: '3rem', height: '3rem' }} />
                        <div className="text-center">
                            <div className='text-sm'>Virtual <span className="text-warning">Open</span></div>
                            <div className='text-xl'><strong>Space</strong></div>
                        </div>
                    </div>
                </Link>
                <button className="navbar-toggler" type="button" onClick={handleTogglerClick}>
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className={`collapse navbar-collapse ${isMenuOpen ? 'show' : ''}`} id="navbarNavDropdown">
                    <ul className="navbar-nav">
                        {navPageItems.map((item: NavPageItem, index: number) => (
                            <li className="nav-item m-1" key={index}>
                                <Link
                                    className={`btn nav-link text-start ${selectedPage === item.name ? 'text-warning' : 'text-light'}`}
                                    to={item.link}
                                    onClick={() => {
                                        setSelectedPage(item.name);
                                        closeMenu();
                                    }}
                                >
                                    {item.name}
                                    <i className={`bi bi-${item.icone} ${selectedPage === item.name ? 'text-warning' : 'text-light'} m-2`}></i>
                                </Link>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default AppBar;
