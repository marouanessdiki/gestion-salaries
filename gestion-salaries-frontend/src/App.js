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
    const [error, setError] = useState(null);

    const handleLogout = () => {
        localStorage.removeItem('isLoggedIn');
        localStorage.removeItem('role');
        setIsLoggedIn(false);
    };

    const fetchEmployes = () => {
        console.log('Fetching employees...');
        api.get('/employes')
            .then(res => {
                console.log('Employees response:', res.data);
                if (Array.isArray(res.data)) {
                    setEmployes(res.data);
                } else {
                    console.error('Expected array but got:', typeof res.data, res.data);
                    setEmployes([]);
                }
            })
            .catch(err => {
                console.error('Error fetching employees:', err);
                console.error('Error response:', err.response);
                setError(`Failed to fetch employees: ${err.message}`);
                setEmployes([]);
            });
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

    // If there's an error, show it
    if (error) {
        return (
            <div style={{ padding: '20px', textAlign: 'center' }}>
                <h2>Error: {error}</h2>
                <button onClick={() => setError(null)}>Try Again</button>
            </div>
        );
    }

    // Show loading state while checking authentication
    if (isLoggedIn === undefined) {
        return (
            <div style={{ padding: '20px', textAlign: 'center' }}>
                <h2>Loading...</h2>
            </div>
        );
    }

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
