import React, { useEffect, useState } from "react";
import api from "../services/api";
import { Card, CardContent, Typography, FormControl, InputLabel, Select, MenuItem, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Stack, IconButton } from '@mui/material';
import DownloadIcon from '@mui/icons-material/Download';
import DeleteIcon from '@mui/icons-material/Delete';

const AttestationPage = () => {
    const [employes, setEmployes] = useState([]);
    const [attestations, setAttestations] = useState([]);
    const [selected, setSelected] = useState("");
    const [type, setType] = useState("Travail");
    const [generating, setGenerating] = useState(false);

    // Helper to render employee label using name when available
    const getEmployeLabel = (employeId) => {
        const emp = Array.isArray(employes) ? employes.find(e => e.id === employeId) : undefined;
        return emp ? `${emp.nom} ${emp.prenom}` : `ID: ${employeId}`;
    };

    useEffect(() => {
        api.get("/employes").then((res) => setEmployes(res.data));
        loadAttestations();
    }, []);

    const loadAttestations = () => {
        api.get("/attestations")
            .then((res) => setAttestations(res.data))
            .catch((err) => console.error(err));
    };

    const generateAttestation = () => {
        if (!selected) {
            alert("Veuillez sélectionner un employé");
            return;
        }
        setGenerating(true);
        api.post("/attestations", {
            employeId: selected,
            typeAttestation: type,
        })
            .then(() => {
                loadAttestations();
            })
            .catch((err) => {
                console.error(err);
                alert("Erreur lors de la génération de l'attestation");
            })
            .finally(() => setGenerating(false));
    };

    const deleteAttestation = (id) => {
        if (window.confirm("Êtes-vous sûr de vouloir supprimer cette attestation ?")) {
            api.delete(`/attestations/${id}`)
                .then(() => {
                    alert("Attestation supprimée");
                    loadAttestations();
                })
                .catch((err) => {
                    console.error(err);
                    alert("Erreur lors de la suppression");
                });
        }
    };

    return (
        <Card sx={{ maxWidth: 1100, margin: 'auto', mt: 4, boxShadow: 3 }}>
            <CardContent>
                <Typography variant="h5" color="primary" fontWeight={700} gutterBottom>Générer une attestation</Typography>
                <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} alignItems="center" mb={3}>
                    <FormControl sx={{ minWidth: 200 }}>
                        <InputLabel id="employe-label">Salarié</InputLabel>
                        <Select
                            labelId="employe-label"
                            value={selected}
                            label="Salarié"
                            onChange={(e) => setSelected(e.target.value)}
                        >
                            <MenuItem value="">-- Sélectionner --</MenuItem>
                            {employes.map((emp) => (
                                <MenuItem key={emp.id} value={emp.id}>
                                    {emp.nom} {emp.prenom}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <FormControl sx={{ minWidth: 180 }}>
                        <InputLabel id="type-label">Type d'attestation</InputLabel>
                        <Select
                            labelId="type-label"
                            value={type}
                            label="Type d'attestation"
                            onChange={(e) => setType(e.target.value)}
                        >
                            <MenuItem value="Travail">Travail</MenuItem>
                            <MenuItem value="Salaire">Salaire</MenuItem>
                        </Select>
                    </FormControl>
                    <Button
                        variant="contained"
                        color="primary"
                        disabled={!selected || generating}
                        onClick={generateAttestation}
                        size="large"
                    >
                        {generating ? 'Génération...' : 'Générer'}
                    </Button>
                </Stack>
                <Typography variant="h6" fontWeight={600} gutterBottom>Attestations générées</Typography>
                <TableContainer component={Paper} elevation={0}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Salarié (ID)</TableCell>
                                <TableCell>Type</TableCell>
                                <TableCell>Date</TableCell>
                                <TableCell align="center">Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {attestations.map((a) => (
                                <TableRow key={a.id} hover>
                                    <TableCell>{getEmployeLabel(a.employeId)}</TableCell>
                                    <TableCell>{a.typeAttestation}</TableCell>
                                    <TableCell>{a.dateGeneration?.substring(0, 10)}</TableCell>
                                    <TableCell align="center">
                                        <Stack direction="row" spacing={1} justifyContent="center">
                                            {a.id && (
                                                <Button
                                                    variant="outlined"
                                                    color="success"
                                                    href={`http://localhost:8080/api/attestations/download/${a.id}`}
                                                    target="_blank"
                                                    rel="noopener noreferrer"
                                                    startIcon={<DownloadIcon />}
                                                    size="small"
                                                >
                                                    Télécharger
                                                </Button>
                                            )}
                                            <IconButton
                                                color="error"
                                                onClick={() => deleteAttestation(a.id)}
                                                size="small"
                                                data-testid="delete-button"
                                            >
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

export default AttestationPage;
