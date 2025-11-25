import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useState, useEffect } from 'react';
import { authService } from '../services/api';
import ProfileDropdown from './ProfileDropdown';

export default function Navbar() {
    const [isOpen, setIsOpen] = useState(false);
    const [user, setUser] = useState(null);

    // Check for user on mount and when localStorage changes
    useEffect(() => {
        const checkUser = () => {
            const userData = authService.getCurrentUser();
            setUser(userData);
        };

        checkUser();

        // Listen for storage events (login/logout from other tabs)
        window.addEventListener('storage', checkUser);

        // Custom event for same-tab login/logout
        window.addEventListener('userChanged', checkUser);

        return () => {
            window.removeEventListener('storage', checkUser);
            window.removeEventListener('userChanged', checkUser);
        };
    }, []);

    const handleLogout = () => {
        authService.logout();
        setUser(null);
        window.dispatchEvent(new Event('userChanged'));
        window.location.href = '/';
    };

    return (
        <motion.nav
            initial={{ y: -100 }}
            animate={{ y: 0 }}
            className="fixed w-full z-50 glass border-b border-white/10"
        >
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between items-center h-16">
                    {/* Logo */}
                    <Link to="/" className="flex items-center space-x-2">
                        <div className="w-10 h-10 rounded-lg bg-gradient-to-br from-primary-500 to-accent-500 flex items-center justify-center">
                            <span className="text-2xl font-bold">P</span>
                        </div>
                        <span className="text-xl font-bold gradient-text">ParkWhizz</span>
                    </Link>

                    {/* Desktop Navigation */}
                    <div className="hidden md:flex items-center space-x-8">
                        <Link to="/" className="hover:text-primary-400 transition-colors">
                            Home
                        </Link>
                        <Link to="/browse" className="hover:text-primary-400 transition-colors">
                            Find Parking
                        </Link>
                        {user ? (
                            <>
                                <Link to="/bookings" className="hover:text-primary-400 transition-colors">
                                    My Bookings
                                </Link>
                                <ProfileDropdown user={user} onLogout={handleLogout} />
                            </>
                        ) : (
                            <Link to="/login">
                                <button className="btn-primary">Login</button>
                            </Link>
                        )}
                    </div>

                    {/* Mobile menu button */}
                    <button
                        onClick={() => setIsOpen(!isOpen)}
                        className="md:hidden p-2 rounded-lg hover:bg-white/10"
                    >
                        <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                        </svg>
                    </button>
                </div>
            </div>

            {/* Mobile menu */}
            {isOpen && (
                <motion.div
                    initial={{ opacity: 0, y: -20 }}
                    animate={{ opacity: 1, y: 0 }}
                    className="md:hidden glass border-t border-white/10"
                >
                    <div className="px-4 py-4 space-y-3">
                        <Link to="/" className="block hover:text-primary-400 transition-colors">
                            Home
                        </Link>
                        <Link to="/browse" className="block hover:text-primary-400 transition-colors">
                            Find Parking
                        </Link>
                        {user ? (
                            <>
                                <Link to="/bookings" className="block hover:text-primary-400 transition-colors">
                                    My Bookings
                                </Link>
                                <div className="text-sm text-gray-400">Hi, {user.name || user.email}</div>
                                <button onClick={handleLogout} className="btn-secondary w-full">
                                    Logout
                                </button>
                            </>
                        ) : (
                            <Link to="/login" className="block">
                                <button className="btn-primary w-full">Login</button>
                            </Link>
                        )}
                    </div>
                </motion.div>
            )}
        </motion.nav>
    );
}

