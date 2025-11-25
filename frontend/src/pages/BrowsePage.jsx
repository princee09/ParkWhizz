import { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import Card from '../components/Card';
import { parkingService } from '../services/api';

export default function BrowsePage() {
    const [parkings, setParkings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');
    const [sortBy, setSortBy] = useState('name');

    useEffect(() => {
        loadParkings();
    }, []);

    const loadParkings = async () => {
        try {
            const data = await parkingService.getAllParkings();
            setParkings(data || []);
        } catch (error) {
            console.error('Error loading parkings:', error);
            // Use mock data if API fails
            setParkings([
                {
                    _id: '1',
                    p_name: 'Downtown Plaza Parking',
                    p_address: '123 Main St, Downtown',
                    cost: 5.00,
                    totalSpots: 50,
                    availableSpots: 23
                },
                {
                    _id: '2',
                    p_name: 'Airport Long-Term',
                    p_address: '456 Airport Rd',
                    cost: 3.50,
                    totalSpots: 200,
                    availableSpots: 150
                },
                {
                    _id: '3',
                    p_name: 'Mall Parking Center',
                    p_address: '789 Shopping Blvd',
                    cost: 4.00,
                    totalSpots: 100,
                    availableSpots: 45
                }
            ]);
        } finally {
            setLoading(false);
        }
    };

    const filteredParkings = parkings
        .filter(p =>
            p.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
            p.address_1?.toLowerCase().includes(searchTerm.toLowerCase()) ||
            p.city?.toLowerCase().includes(searchTerm.toLowerCase()) ||
            p.state?.toLowerCase().includes(searchTerm.toLowerCase())
        )
        .sort((a, b) => {
            if (sortBy === 'price') return (a.price || 0) - (b.price || 0);
            if (sortBy === 'availability') return (b.availableSpots || 0) - (a.availableSpots || 0);
            return (a.name || '').localeCompare(b.name || '');
        });

    return (
        <div className="min-h-screen pt-24 pb-12">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                {/* Header */}
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    className="mb-12"
                >
                    <h1 className="text-4xl md:text-5xl font-bold mb-4">
                        Find Your <span className="gradient-text">Perfect Spot</span>
                    </h1>
                    <p className="text-xl text-gray-400">
                        Browse available parking locations near you
                    </p>
                </motion.div>

                {/* Filters */}
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ delay: 0.1 }}
                    className="glass rounded-xl p-6 mb-8"
                >
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div>
                            <label className="block text-sm font-medium text-gray-300 mb-2">
                                Search Location
                            </label>
                            <input
                                type="text"
                                placeholder="Search by city, name or address..."
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                                className="w-full px-4 py-3 rounded-lg bg-white/5 border border-white/20 focus:outline-none focus:ring-2 focus:ring-primary-500"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-300 mb-2">
                                Sort By
                            </label>
                            <select
                                value={sortBy}
                                onChange={(e) => setSortBy(e.target.value)}
                                className="w-full px-4 py-3 rounded-lg bg-white/5 border border-white/20 focus:outline-none focus:ring-2 focus:ring-primary-500"
                            >
                                <option value="name">Name</option>
                                <option value="price">Price (Low to High)</option>
                                <option value="availability">Availability</option>
                            </select>
                        </div>
                    </div>
                </motion.div>

                {/* Results */}
                {loading ? (
                    <div className="text-center py-20">
                        <div className="inline-block w-12 h-12 border-4 border-primary-500 border-t-transparent rounded-full animate-spin"></div>
                        <p className="mt-4 text-gray-400">Loading parking spots...</p>
                    </div>
                ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {filteredParkings.map((parking, index) => (
                            <motion.div
                                key={parking._id}
                                initial={{ opacity: 0, y: 20 }}
                                animate={{ opacity: 1, y: 0 }}
                                transition={{ delay: index * 0.1 }}
                            >
                                <Link to={`/parking/${parking._id}`}>
                                    <Card>
                                        <div className="mb-4">
                                            <div className="w-full h-40 bg-gradient-to-br from-primary-500/20 to-accent-500/20 rounded-lg flex items-center justify-center mb-4">
                                                <span className="text-6xl">🅿️</span>
                                            </div>
                                            <h3 className="text-xl font-bold mb-2">{parking.name}</h3>
                                            {parking.city && parking.state && (
                                                <p className="text-primary-400 text-sm mb-1">📍 {parking.city}, {parking.state}</p>
                                            )}
                                            <p className="text-gray-400 text-sm mb-4">{parking.address_1}</p>
                                        </div>

                                        <div className="flex justify-between items-center mb-4">
                                            <div>
                                                <p className="text-2xl font-bold text-primary-400">
                                                    ₹{parking.price || '100'}/hr
                                                </p>
                                            </div>
                                            <div className="text-right">
                                                <p className="text-sm text-gray-400">Available</p>
                                                <p className="text-lg font-semibold">
                                                    {parking.availableSpots || 0}/{parking.totalSpots || 0}
                                                </p>
                                            </div>
                                        </div>

                                        <button className="w-full btn-primary">
                                            View Details
                                        </button>
                                    </Card>
                                </Link>
                            </motion.div>
                        ))}
                    </div>
                )}

                {!loading && filteredParkings.length === 0 && (
                    <div className="text-center py-20">
                        <p className="text-2xl text-gray-400">No parking spots found</p>
                        <p className="text-gray-500 mt-2">Try adjusting your search filters</p>
                    </div>
                )}
            </div>
        </div>
    );
}
