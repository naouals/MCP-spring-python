import React, { useState } from 'react';
import axios from 'axios';
import './App.css'; // On va styliser dans ce fichier aussi

function App() {
    const [query, setQuery] = useState('');
    const [response, setResponse] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const res = await axios.get('http://localhost:8066/chat', {
                params: { query }
            });
            setResponse(res.data);
        } catch (error) {
            setResponse('❌ Erreur : ' + error.message);
        }
        setLoading(false);
    };

    return (
        <div className="container">
            <h1>💬 IA Chat</h1>
            <form onSubmit={handleSubmit} className="chat-form">
                <input
                    type="text"
                    value={query}
                    onChange={e => setQuery(e.target.value)}
                    placeholder="Pose ta question ici..."
                />
                <button type="submit" disabled={loading}>
                    {loading ? 'Envoi...' : 'Envoyer'}
                </button>
            </form>

            <div className="response-box">
                <strong>Réponse :</strong>
                <p>{response}</p>
            </div>
        </div>
    );
}

export default App;
