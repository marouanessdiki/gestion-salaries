import React from 'react';
import { Card, CardContent, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, Stack } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import api from '../services/api';
import { Link } from 'react-router-dom';

const EmployeList = ({ employes, onEdit, onDelete }) => {
    const handleDelete = id => {
        api.delete(`/employes/${id}`).then(() => onDelete());
    };

    // Safety check to ensure employes is an array
    if (!Array.isArray(employes)) {
        return (
            <Card sx={{ maxWidth: 1100, margin: 'auto', mt: 4, boxShadow: 3 }}>
                <CardContent>
                    <Typography variant="h5" gutterBottom fontWeight={700} color="primary">Liste des employés</Typography>
                    <Typography variant="body1" color="text.secondary" align="center" sx={{ py: 4 }}>
                        {employes === null || employes === undefined ? 'Chargement...' : 'Aucun employé trouvé'}
                    </Typography>
                </CardContent>
            </Card>
        );
    }

    if (employes.length === 0) {
        return (
            <Card sx={{ maxWidth: 1100, margin: 'auto', mt: 4, boxShadow: 3 }}>
                <CardContent>
                    <Typography variant="h5" gutterBottom fontWeight={700} color="primary">Liste des employés</Typography>
                    <Typography variant="body1" color="text.secondary" align="center" sx={{ py: 4 }}>
                        Aucun employé trouvé
                    </Typography>
                </CardContent>
            </Card>
        );
    }

    return (
        <Card sx={{ maxWidth: 1100, margin: 'auto', mt: 4, boxShadow: 3 }}>
            <CardContent>
                <Typography variant="h5" gutterBottom fontWeight={700} color="primary">Liste des employés</Typography>
                <TableContainer component={Paper} elevation={0}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Nom</TableCell>
                                <TableCell>Prénom</TableCell>
                                <TableCell>CIN</TableCell>
                                <TableCell>Poste</TableCell>
                                <TableCell>Service</TableCell>
                                <TableCell>Date d'embauche</TableCell>
                                <TableCell align="center">Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {employes.map(emp => (
                                <TableRow key={emp.id} hover>
                                    <TableCell>
                                        <Link to={`/employes/${emp.id}`} style={{ textDecoration: 'none', color: '#1976d2', fontWeight: 500 }}>
                                            {emp.nom}
                                        </Link>
                                    </TableCell>
                                    <TableCell>{emp.prenom}</TableCell>
                                    <TableCell>{emp.cin}</TableCell>
                                    <TableCell>{emp.poste}</TableCell>
                                    <TableCell>{emp.service}</TableCell>
                                    <TableCell>{emp.dateEmbauche}</TableCell>
                                    <TableCell align="center">
                                        <Stack direction="row" spacing={1} justifyContent="center">
                                            <IconButton color="primary" onClick={() => onEdit(emp)} size="small">
                                                <EditIcon />
                                            </IconButton>
                                            <IconButton color="error" onClick={() => handleDelete(emp.id)} size="small">
                                                <DeleteIcon />
                                            </IconButton>
                                        </Stack>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </CardContent>
        </Card>
    );
};

export default EmployeList;
