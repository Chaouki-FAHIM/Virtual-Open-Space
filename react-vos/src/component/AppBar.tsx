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
    const location = useLocation();

    useEffect(() => {
        const currentNavItem = navPageItems.find(item => item.link === location.pathname);
        if (currentNavItem) {
            setSelectedPage(currentNavItem.name);
        }
    }, [location]);

    // Fonction pour fermer le menu avec animation
    const closeMenu = () => {
        const nav = document.getElementById('navbarNavDropdown');
        if (nav && nav.classList.contains('show')) {
            nav.classList.remove('show');
            nav.classList.add('collapsing');
            setTimeout(() => {
                nav.classList.remove('collapsing');
                nav.classList.add('collapse');
            }, 350); // Durée de l'animation en millisecondes
        }
    };

    // Fonction pour gérer le clic sur le bouton de basculement de la barre de navigation
    const handleTogglerClick = () => {
        const nav = document.getElementById('navbarNavDropdown');
        if (nav) {
            if (nav.classList.contains('show')) {
                nav.classList.remove('show');
                nav.classList.add('collapsing');
                setTimeout(() => {
                    nav.classList.remove('collapsing');
                    nav.classList.add('collapse');
                }, 350); // Durée de l'animation en millisecondes
            } else {
                nav.classList.remove('collapse');
                nav.classList.add('show');
            }
        }
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/">
                    <div className="flex-container">
                        <img src="/logo/awb.png" alt="Logo" style={{ width: '50px', height: '50px' }} />
                        <div className="text-center">
                            <h6>Virtual <span className="text-warning">Open</span></h6>
                            <h5>Space</h5>
                        </div>
                    </div>
                </Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
                        aria-label="Toggle navigation" onClick={handleTogglerClick}>
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNavDropdown">
                    <ul className="navbar-nav">
                        {navPageItems.map((item, index) => (
                            <li className="nav-item m-1" key={index}>
                                <Link
                                    className={`btn nav-link ${selectedPage === item.name ? 'text-warning' : 'text-light'}`}
                                    to={item.link}
                                    onClick={() => {
                                        setSelectedPage(item.name);
                                        closeMenu(); // Fermer le menu après le clic
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
