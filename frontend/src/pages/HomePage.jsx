import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { authService } from '../services/api';

export default function HomePage() {
    const [searchLocation, setSearchLocation] = useState('');
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const user = authService.getCurrentUser();
        setIsLoggedIn(!!user);
    }, []);

    const features = [
        {
            icon: '🚗',
            title: 'Easy Booking',
            description: 'Reserve your spot in seconds with our streamlined process'
        },
        {
            icon: '💳',
            title: 'Secure Payments',
            description: 'Safe and encrypted transactions for peace of mind'
        },
        {
            icon: '📍',
            title: 'Prime Locations',
            description: 'Access to parking spots in the most convenient areas'
        },
        {
            icon: '⏰',
            title: '24/7 Access',
            description: 'Book anytime, anywhere with our always-on platform'
        }
    ];

    const handleSearch = (e) => {
        e.preventDefault();
        if (searchLocation) {
            window.location.href = `/browse?location=${searchLocation}`;
        }
    };

    return (
        <div className="min-h-screen pt-16">
            {/* Hero Section */}
            <section className="relative overflow-hidden">
                {/* Animated Background */}
                <div className="absolute inset-0 z-0">
                    <div className="absolute top-20 left-10 w-72 h-72 bg-primary-500/30 rounded-full blur-3xl animate-float"></div>
                    <div className="absolute bottom-20 right-10 w-96 h-96 bg-accent-500/30 rounded-full blur-3xl animate-float" style={{ animationDelay: '2s' }}></div>
                </div>

                <div className="relative z-10 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20">
                    <motion.div
                        initial={{ opacity: 0, y: 30 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ duration: 0.8 }}
                        className="text-center"
                    >
                        <h1 className="text-5xl md:text-7xl font-bold mb-6 pb-6 leading-[1.2] overflow-visible">
                            Find Your Perfect
                            <span className="block gradient-text overflow-visible">Parking Spot</span>
                        </h1>
                        <p className="text-xl md:text-2xl text-gray-300 mb-12 max-w-3xl mx-auto">
                            Reserve parking in advance. Save time, money, and hassle with ParkWhizz.
                        </p>

                        {/* Search Bar */}
                        <motion.form
                            initial={{ opacity: 0, scale: 0.9 }}
                            animate={{ opacity: 1, scale: 1 }}
                            transition={{ delay: 0.3 }}
                            onSubmit={handleSearch}
                            className="max-w-2xl mx-auto"
                        >
                            <div className="glass rounded-2xl p-3 flex gap-3">
                                <input
                                    type="text"
                                    placeholder="Search by location..."
                                    value={searchLocation}
                                    onChange={(e) => setSearchLocation(e.target.value)}
                                    className="flex-1 bg-transparent px-4 py-3 outline-none text-lg"
                                />
                                <button
                                    type="submit"
                                    className="btn-primary"
                                >
                                    Search
                                </button>
                            </div>
                        </motion.form>

                        {/* Quick Actions */}
                        <motion.div
                            initial={{ opacity: 0 }}
                            animate={{ opacity: 1 }}
                            transition={{ delay: 0.5 }}
                            className="mt-12 flex flex-wrap justify-center gap-4"
                        >
                            <Link to="/browse">
                                <button className="btn-primary text-lg px-8 py-4">
                                    Browse All Spots
                                </button>
                            </Link>
                            {!isLoggedIn && (
                                <Link to="/login">
                                    <button className="btn-secondary text-lg px-8 py-4">
                                        Get Started
                                    </button>
                                </Link>
                            )}
                        </motion.div>
                    </motion.div>
                </div>
            </section>

            {/* Features Section */}
            <section className="py-20 relative">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <motion.div
                        initial={{ opacity: 0 }}
                        whileInView={{ opacity: 1 }}
                        viewport={{ once: true }}
                        className="text-center mb-16"
                    >
                        <h2 className="text-4xl md:text-5xl font-bold mb-4">
                            Why Choose <span className="gradient-text">ParkWhizz</span>?
                        </h2>
                        <p className="text-xl text-gray-400">
                            The smartest way to park in the city
                        </p>
                    </motion.div>

                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
                        {features.map((feature, index) => (
                            <motion.div
                                key={index}
                                initial={{ opacity: 0, y: 20 }}
                                whileInView={{ opacity: 1, y: 0 }}
                                viewport={{ once: true }}
                                transition={{ delay: index * 0.1 }}
                                className="glass rounded-xl p-8 text-center card-hover"
                            >
                                <div className="text-5xl mb-4">{feature.icon}</div>
                                <h3 className="text-xl font-bold mb-3">{feature.title}</h3>
                                <p className="text-gray-400">{feature.description}</p>
                            </motion.div>
                        ))}
                    </div>
                </div>
            </section>

            {/* CTA Section - Only show if not logged in */}
            {!isLoggedIn && (
                <section className="py-20">
                    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
                        <motion.div
                            initial={{ opacity: 0, scale: 0.9 }}
                            whileInView={{ opacity: 1, scale: 1 }}
                            viewport={{ once: true }}
                            className="glass rounded-2xl p-12"
                        >
                            <h2 className="text-4xl font-bold mb-6">
                                Ready to Start Parking Smarter?
                            </h2>
                            <p className="text-xl text-gray-300 mb-8">
                                Join thousands of drivers who save time and money every day
                            </p>
                            <Link to="/login">
                                <button className="btn-primary text-lg px-12 py-4">
                                    Create Free Account
                                </button>
                            </Link>
                        </motion.div>
                    </div>
                </section>
            )}
        </div>
    );
}

