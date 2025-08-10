import React, { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import EmployeList from "./components/EmployeList";
import EmployeForm from "./components/EmployeForm";
import AttestationPage from "./components/AttestationPage";
import Menu from "./components/Menu";
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import api from './services/api';
import { Fab, Container } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import AuthLogin from './components/AuthLogin';
import HrSignup from './components/HrSignup';
import AdminPanel from './components/AdminPanel';
import EmployeProfile from './components/EmployeProfile';

const theme = createTheme({
    palette: {
        primary: {
            main: '#1976d2',
        },
        secondary: {
            main: '#f50057',
        },
        background: {
            default: '#f4f6fa',
        },
    },
    shape: {
        borderRadius: 12,
    },
    typography: {
        fontFamily: 'Roboto, Segoe UI, Arial, sans-serif',
    },
});

function App() {
    const [employes, setEmployes] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [selected, setSelected] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem('isLoggedIn') === 'true');

    console.log('App rendered, isLoggedIn:', isLoggedIn);
    console.log('localStorage isLoggedIn:', localStorage.getItem('isLoggedIn'));

    const handleLogout = () => {
        localStorage.removeItem('isLoggedIn');
        localStorage.removeItem('role');
        setIsLoggedIn(false);
    };

    const fetchEmployes = () => {
        api.get('/employes').then(res => setEmployes(res.data));
    };

    useEffect(() => {
        fetchEmployes();
    }, []);

    const handleAdd = () => {
        setSelected(null);
        setShowForm(true);
    };

    const handleEdit = (emp) => {
        setSelected(emp);
        setShowForm(true);
    };

    const handleSaved = () => {
        setShowForm(false);
        setSelected(null);
        fetchEmployes();
    };

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <BrowserRouter>
                <Routes>
                    <Route path="/signup" element={<HrSignup />} />
                    <Route path="/admin" element={<AdminPanel />} />
                    <Route path="/" element={
                        !isLoggedIn ? (
                            <AuthLogin onLogin={() => setIsLoggedIn(true)} />
                        ) : (
                            <>
                                <Menu onLogout={handleLogout} />
                                <Container>
                                    <EmployeList employes={employes} onEdit={handleEdit} onDelete={fetchEmployes} />
                                    <Fab color="primary" aria-label="add" onClick={handleAdd} sx={{ position: 'fixed', bottom: 32, right: 32 }}>
                                        <AddIcon />
                                    </Fab>
                                    {showForm && <EmployeForm selected={selected} onSaved={handleSaved} onCancel={() => setShowForm(false)} />}
                                </Container>
                            </>
                        )
                    } />
                    <Route path="/attestations" element={
                        !isLoggedIn ? (
                            <AuthLogin onLogin={() => setIsLoggedIn(true)} />
                        ) : (
                            <>
                                <Menu onLogout={handleLogout} />
                                <AttestationPage />
                            </>
                        )
                    } />
                    <Route path="/employes/:id" element={
                        !isLoggedIn ? (
                            <AuthLogin onLogin={() => setIsLoggedIn(true)} />
                        ) : (
                            <>
                                <Menu onLogout={handleLogout} />
                                <EmployeProfile />
                            </>
                        )
                    } />
                </Routes>
            </BrowserRouter>
        </ThemeProvider>
    );
}

export default App;
