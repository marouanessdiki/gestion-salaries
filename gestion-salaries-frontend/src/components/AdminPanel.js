import React, { useState, useEffect } from 'react';
import api from '../services/api';
import { Card, CardContent, Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Alert } from '@mui/material';

const AdminPanel = () => {
    const [pendingHrs, setPendingHrs] = useState([]);
    const [message, setMessage] = useState('');

    useEffect(() => {
        loadPendingHrs();
    }, []);

    const loadPendingHrs = async () => {
        try {
            const res = await api.get('/hr/pending');
            setPendingHrs(res.data);
        } catch (err) {
            setMessage('Erreur lors du chargement des HR en attente');
        }
    };

    const approveHr = async (id) => {
        try {
            const res = await api.post(`/hr/approve/${id}`);
            if (res.data.success) {
                setMessage('HR approuvé avec succès');
                loadPendingHrs(); // Reload the list
            }
        } catch (err) {
            setMessage('Erreur lors de l\'approbation');
        }
    };

    return (
        <Card sx={{ maxWidth: 800, margin: '40px auto', boxShadow: 3 }}>
            <CardContent>
                <Typography variant="h5" color="primary" fontWeight={700} gutterBottom>Panel Administrateur</Typography>
                <Typography variant="subtitle1" color="text.secondary" gutterBottom>
                    HR en attente d'approbation
                </Typography>

                {message && (
                    <Alert severity="info" sx={{ mb: 2 }} onClose={() => setMessage('')}>
                        {message}
                    </Alert>
                )}

                {pendingHrs.length === 0 ? (
                    <Typography variant="body1" color="text.secondary" align="center" sx={{ py: 4 }}>
                        Aucun HR en attente d'approbation
                    </Typography>
                ) : (
                    <TableContainer component={Paper} elevation={0}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>ID</TableCell>
                                    <TableCell>Nom d'utilisateur</TableCell>
                                    <TableCell align="center">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {pendingHrs.map((hr) => (
                                    <TableRow key={hr.id} hover>
                                        <TableCell>{hr.id}</TableCell>
                                        <TableCell>{hr.username}</TableCell>
                                        <TableCell align="center">
                                            <Button
                                                variant="contained"
                                                color="success"
                                                size="small"
                                                onClick={() => approveHr(hr.id)}
                                            >
                                                Approuver
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                )}
            </CardContent>
        </Card>
    );
};

export default AdminPanel; 