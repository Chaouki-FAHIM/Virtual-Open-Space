import React, { useState, useEffect } from 'react';
import './AuthenticationPage.css';
import { useNavigate } from 'react-router-dom';

function AuthenticationPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [emailIsValid, setEmailIsValid] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        setIsButtonDisabled(!emailIsValid || password === '');
    }, [email, password, emailIsValid]);

    const handleEmailChange = (e: { target: { value: any; }; }) => {
        const value = e.target.value;
        setEmail(value);
        setEmailIsValid(value.endsWith('@attijariwafa.com'));
    };

    const handlePasswordChange = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setPassword(e.target.value);
    };

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        // Logique d'authentification ici, puis redirection vers la page d'accueil
        navigate('/');
    };

    return (
        <div className='login'>
            <main className="d-flex justify-content-center align-items-center vh-100">
                <form onSubmit={handleSubmit} className="text-center form-signin w-100 m-auto">
                    <img className="mb-4" src="/logo/awb.png" alt="" width="150" height="150"/>
                    <h1 className="h3 mb-3 fw-normal">Login</h1>

                    <div className="form-floating">
                        <input
                            type="email"
                            className={`form-control ${email ? (emailIsValid ? 'is-valid' : 'is-invalid') : ''}`}
                            id="floatingInput"
                            placeholder="name@attijariwafa.com"
                            value={email}
                            onChange={handleEmailChange}
                        />
                        <label htmlFor="floatingInput">Adresse Email</label>
                    </div>
                    <div className="form-floating">
                        <input
                            type="password"
                            className="form-control"
                            id="floatingPassword"
                            placeholder="Mot de passe"
                            value={password}
                            onChange={handlePasswordChange}
                        />
                        <label htmlFor="floatingPassword">Mot de passe</label>
                    </div>

                    <button className="btn btn-warning w-100 py-2" type="submit" disabled={isButtonDisabled}>Connecter
                    </button>
                    <p className="mt-5 mb-3 text-body-secondary">© 2024</p>
                </form>
            </main>
        </div>
    );
}

export default AuthenticationPage;
