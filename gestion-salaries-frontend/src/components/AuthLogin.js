import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import { Card, CardContent, Typography, TextField, Button, Stack, Alert } from '@mui/material';

const AuthLogin = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            const res = await api.post('/auth/login', { username, password });
            if (res.data.success) {
                localStorage.setItem('isLoggedIn', 'true');
                localStorage.setItem('role', res.data.role);
                onLogin();
                navigate('/');
            } else {
                setError(res.data.message);
            }
        } catch (err) {
            setError(err.response?.data?.message || 'Identifiants invalides');
        }
    };

    return (
        <Card sx={{ maxWidth: 400, margin: 'auto', mt: 8, boxShadow: 3 }}>
            <CardContent>
                <Typography variant="h6" color="primary" fontWeight={700} gutterBottom>Connexion HR</Typography>
                <form onSubmit={handleSubmit} noValidate>
                    <Stack spacing={2}>
                        <TextField label="Nom d'utilisateur" value={username} onChange={e => setUsername(e.target.value)} fullWidth required />
                        <TextField label="Mot de passe" type="password" value={password} onChange={e => setPassword(e.target.value)} fullWidth required />
                        {error && <Alert severity="error">{error}</Alert>}
                        <Button type="submit" variant="contained" color="primary" size="large">Se connecter</Button>
                        <Button
                            variant="text"
                            onClick={() => navigate('/signup')}
                            size="small"
                        >
                            Pas encore inscrit ? S'inscrire
                        </Button>
                    </Stack>
                </form>
            </CardContent>
        </Card>
    );
};

export default AuthLogin;
