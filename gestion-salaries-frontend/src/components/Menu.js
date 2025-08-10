import React from "react";
import { Link, useLocation } from "react-router-dom";
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import WorkIcon from '@mui/icons-material/Work';

const Menu = ({ onLogout }) => {
    const location = useLocation();

    return (
        <AppBar position="static" color="primary" elevation={2} sx={{ mb: 4 }}>
            <Toolbar>
                <WorkIcon sx={{ mr: 1 }} />
                <Typography variant="h6" component={Link} to="/" sx={{ flexGrow: 1, textDecoration: 'none', color: 'inherit', fontWeight: 700 }}>
                    Gestion Salariés
                </Typography>
                <Box>
                    <Button color={location.pathname === '/' ? 'secondary' : 'inherit'} component={Link} to="/" sx={{ fontWeight: 500 }}>
                        Employés
                    </Button>
                    <Button color={location.pathname === '/attestations' ? 'secondary' : 'inherit'} component={Link} to="/attestations" sx={{ fontWeight: 500 }}>
                        Attestations
                    </Button>
                    <Button color="inherit" onClick={onLogout} sx={{ fontWeight: 500, ml: 2 }}>
                        Déconnexion
                    </Button>
                </Box>
            </Toolbar>
        </AppBar>
    );
};

export default Menu;
