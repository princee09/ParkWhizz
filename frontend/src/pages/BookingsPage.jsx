import { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { useNavigate, Link } from 'react-router-dom';
import Card from '../components/Card';
import { bookingService } from '../services/api';

export default function BookingsPage() {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user') || '{}');

    useEffect(() => {
        const userId = user._id || user.id;
        if (!userId) {
            navigate('/login');
            return;
        }
        loadBookings();
    }, []);;

    const loadBookings = async () => {
        try {
            const userId = user._id || user.id;
            const data = await bookingService.getUserBookings(userId);
            // Sort by booking date (newest first)
            const sortedData = (data || []).sort((a, b) =>
                new Date(b.bookingDate || b.startTime) - new Date(a.bookingDate || a.startTime)
            );
            setBookings(sortedData);
        } catch (error) {
            console.error('Error loading bookings:', error);
            // Mock data
            setBookings([
                {
                    _id: '1',
                    p_name: 'Downtown Plaza',
                    p_address: '123 Main St',
                    spotNo: 'A-101',
                    vehicle: 'Toyota Camry - ABC123',
                    startTime: new Date().toISOString(),
                    endTime: new Date(Date.now() + 3600000).toISOString(),
                    amount: 5.00,
                    bookingStatus: 'CONFIRMED'
                }
            ]);
        } finally {
            setLoading(false);
        }
    };

    const getStatusColor = (status) => {
        switch (status?.toUpperCase()) {
            case 'CONFIRMED':
                return 'text-green-400';
            case 'ACTIVE':
                return 'text-blue-400';
            case 'COMPLETED':
                return 'text-gray-400';
            case 'CANCELLED':
                return 'text-red-400';
            default:
                return 'text-gray-400';
        }
    };

    const formatDate = (dateString) => {
        return new Date(dateString).toLocaleString('en-US', {
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    if (loading) {
        return (
            <div className="min-h-screen pt-24 flex items-center justify-center">
                <div className="text-center">
                    <div className="inline-block w-12 h-12 border-4 border-primary-500 border-t-transparent rounded-full animate-spin"></div>
                    <p className="mt-4 text-gray-400">Loading bookings...</p>
                </div>
            </div>
        );
    }

    return (
        <div className="min-h-screen pt-24 pb-12">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                >
                    <h1 className="text-4xl md:text-5xl font-bold mb-4">
                        My <span className="gradient-text">Bookings</span>
                    </h1>
                    <p className="text-xl text-gray-400 mb-12">
                        Manage your parking reservations
                    </p>

                    {bookings.length === 0 ? (
                        <Card hover={false} className="text-center py-12">
                            <p className="text-2xl text-gray-400 mb-4">No bookings yet</p>
                            <p className="text-gray-500 mb-6">Start by finding a parking spot</p>
                            <button
                                onClick={() => navigate('/browse')}
                                className="btn-primary"
                            >
                                Browse Parking Spots
                            </button>
                        </Card>
                    ) : (
                        <div className="space-y-6">
                            {bookings.map((booking, index) => (
                                <Link key={booking._id} to={`/bookings/${booking._id}`}>
                                    <motion.div
                                        initial={{ opacity: 0, x: -20 }}
                                        animate={{ opacity: 1, x: 0 }}
                                        transition={{ delay: index * 0.1 }}
                                    >
                                        <Card className="cursor-pointer hover:border-primary-500">
                                            <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
                                                {/* Location Info */}
                                                <div className="md:col-span-2">
                                                    <h3 className="text-xl font-bold mb-2">{booking.p_name}</h3>
                                                    <p className="text-gray-400 text-sm mb-3">
                                                        📍 {booking.p_address}
                                                    </p>
                                                    <div className="space-y-1 text-sm">
                                                        <p><span className="text-gray-400">Spot:</span> <span className="font-semibold">{booking.spotNo}</span></p>
                                                        <p><span className="text-gray-400">Vehicle:</span> {booking.vehicle}</p>
                                                    </div>
                                                </div>

                                                {/* Time Info */}
                                                <div>
                                                    <p className="text-sm text-gray-400 mb-2">Duration</p>
                                                    <p className="font-semibold mb-1">
                                                        {formatDate(booking.startTime)}
                                                    </p>
                                                    <p className="text-gray-400 text-sm">to</p>
                                                    <p className="font-semibold">
                                                        {formatDate(booking.endTime)}
                                                    </p>
                                                </div>

                                                {/* Status & Price */}
                                                <div className="text-right">
                                                    <p className={`text-lg font-semibold mb-2 ${getStatusColor(booking.bookingStatus)}`}>
                                                        {booking.bookingStatus || 'CONFIRMED'}
                                                    </p>
                                                    <p className="text-3xl font-bold text-primary-400">
                                                        ₹{booking.amount?.toFixed(2)}
                                                    </p>
                                                </div>
                                            </div>
                                        </Card>
                                    </motion.div>
                                </Link>
                            ))}
                        </div>
                    )}
                </motion.div>
            </div>
        </div>
    );
}
