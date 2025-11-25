import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import Card from '../components/Card';
import Input from '../components/Input';
import Button from '../components/Button';
import { parkingService, bookingService } from '../services/api';

export default function SpotDetailsPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [parking, setParking] = useState(null);
    const [spots, setSpots] = useState([]);
    const [loading, setLoading] = useState(true);
    const [loadingSpots, setLoadingSpots] = useState(false);
    const [submitting, setSubmitting] = useState(false);
    const [selectedSpot, setSelectedSpot] = useState(null);
    const [bookingData, setBookingData] = useState({
        startTime: '',
        endTime: '',
        vehicle: ''
    });

    useEffect(() => {
        loadParkingDetails();
    }, [id]);

    const loadParkingDetails = async () => {
        try {
            const data = await parkingService.getParkingById(id);
            setParking(data);
        } catch (error) {
            console.error('Error loading parking:', error);
        } finally {
            setLoading(false);
        }
    };

    const loadSpots = async () => {
        setLoadingSpots(true);
        try {
            const formatDateTime = (datetime) => {
                if (!datetime) return '';
                return datetime.includes(':') && datetime.split(':').length === 2
                    ? `${datetime}:00`
                    : datetime;
            };

            const startTimeFormatted = formatDateTime(bookingData.startTime);
            const endTimeFormatted = formatDateTime(bookingData.endTime);

            const data = await parkingService.getSpotsByParking(
                id,
                startTimeFormatted,
                endTimeFormatted
            );
            setSpots(data || []);
        } catch (error) {
            console.error('Error loading spots:', error);
            setSpots([]);
        } finally {
            setLoadingSpots(false);
        }
    };

    const handleBooking = async (e) => {
        e.preventDefault();
        if (!selectedSpot) {
            alert('Please select a spot');
            return;
        }

        const user = JSON.parse(localStorage.getItem('user') || '{}');
        const userId = user._id || user.id;
        if (!userId) {
            navigate('/login');
            return;
        }

        setSubmitting(true);
        try {
            const duration = calculateDuration(bookingData.startTime, bookingData.endTime);
            const amount = duration * (parking?.price || 100);

            await bookingService.createBooking(userId, selectedSpot._id, {
                p_name: parking.name,
                p_address: parking.address_1,
                vehicle: bookingData.vehicle,
                spotNo: selectedSpot.spotNo,
                bookingDate: new Date().toISOString(),
                duration,
                spotId: selectedSpot._id,
                startTime: bookingData.startTime,
                endTime: bookingData.endTime,
                amount,
                bookingStatus: 'CONFIRMED'
            });

            // No popup - just redirect
            navigate('/bookings');
        } catch (error) {
            console.error('Error creating booking:', error);
            alert('Booking failed. Please try again.');
        } finally {
            setSubmitting(false);
        }
    };

    const calculateDuration = (start, end) => {
        const startDate = new Date(start);
        const endDate = new Date(end);
        return Math.ceil((endDate - startDate) / (1000 * 60 * 60));
    };

    if (loading) {
        return (
            <div className="min-h-screen pt-24 flex items-center justify-center">
                <div className="text-center">
                    <div className="inline-block w-12 h-12 border-4 border-primary-500 border-t-transparent rounded-full animate-spin"></div>
                    <p className="mt-4 text-gray-400">Loading...</p>
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
                    <div className="mb-8">
                        <button
                            onClick={() => navigate(-1)}
                            className="text-primary-400 hover:text-primary-300 mb-4"
                        >
                            ← Back
                        </button>
                        <h1 className="text-4xl font-bold mb-2">{parking?.name}</h1>
                        <p className="text-xl text-gray-400">📍 {parking?.city}, {parking?.state}</p>
                        <p className="text-gray-500">{parking?.address_1}</p>
                    </div>

                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <div className="lg:col-span-2 space-y-6">
                            <Card hover={false}>
                                <div className="w-full h-64 bg-gradient-to-br from-primary-500/20 to-accent-500/20 rounded-lg flex items-center justify-center mb-6">
                                    <span className="text-9xl">🅿️</span>
                                </div>
                                <h2 className="text-2xl font-bold mb-4">About This Parking</h2>
                                <p className="text-gray-300 mb-6">
                                    {parking?.description || 'Premium parking facility with excellent amenities.'}
                                </p>
                                <div className="grid grid-cols-2 gap-4">
                                    {(parking?.amenities || ['24/7 Security', 'Covered', 'EV Charging', 'Accessible']).map((amenity, index) => (
                                        <div key={index} className="flex items-center space-x-2">
                                            <span className="text-green-400">✓</span>
                                            <span>{amenity}</span>
                                        </div>
                                    ))}
                                </div>
                            </Card>

                            {spots.length > 0 && (
                                <Card hover={false} id="spots-section">
                                    <h2 className="text-2xl font-bold mb-4">Available Spots</h2>
                                    <div className="max-h-96 overflow-y-auto pr-2 scrollbar-thin scrollbar-thumb-primary-500 scrollbar-track-gray-800">
                                        <div className="grid grid-cols-3 md:grid-cols-5 gap-3">
                                            {spots.map((spot) => (
                                                <button
                                                    key={spot._id}
                                                    onClick={() => {
                                                        if (spot.isAvailable !== false) {
                                                            setSelectedSpot(spot);
                                                            // Scroll back to booking form
                                                            document.getElementById('booking-form')?.scrollIntoView({ behavior: 'smooth' });
                                                        }
                                                    }}
                                                    disabled={spot.isAvailable === false}
                                                    className={`p-4 rounded-lg font-semibold transition-all ${selectedSpot?._id === spot._id
                                                        ? 'bg-primary-500 ring-2 ring-primary-400'
                                                        : spot.isAvailable === false
                                                            ? 'bg-gray-700 cursor-not-allowed opacity-50'
                                                            : 'glass hover:bg-white/20'
                                                        }`}
                                                >
                                                    {spot.spotNo}
                                                </button>
                                            ))}
                                        </div>
                                    </div>
                                </Card>
                            )}
                        </div>

                        <div className="lg:col-span-1">
                            <Card hover={false} className="sticky top-24">
                                <h2 className="text-2xl font-bold mb-6">Book Now</h2>
                                <form id="booking-form" onSubmit={handleBooking} className="space-y-4">
                                    <div>
                                        <p className="text-3xl font-bold text-primary-400 mb-2">
                                            ₹{parking?.price || '100'}/hr
                                        </p>
                                    </div>

                                    <Input
                                        label="Start Time"
                                        type="datetime-local"
                                        value={bookingData.startTime}
                                        onChange={(e) => {
                                            setBookingData({ ...bookingData, startTime: e.target.value });
                                            // Auto-close picker after selection
                                            e.target.blur();
                                        }}
                                        required
                                    />

                                    <Input
                                        label="End Time"
                                        type="datetime-local"
                                        value={bookingData.endTime}
                                        onChange={(e) => {
                                            setBookingData({ ...bookingData, endTime: e.target.value });
                                            // Auto-close picker after selection
                                            e.target.blur();
                                        }}
                                        required
                                    />

                                    {bookingData.startTime && bookingData.endTime && (
                                        <button
                                            type="button"
                                            onClick={() => {
                                                loadSpots();
                                                // Scroll to spots section on mobile
                                                document.getElementById('spots-section')?.scrollIntoView({ behavior: 'smooth' });
                                            }}
                                            disabled={loadingSpots}
                                            className="w-full bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700 disabled:from-gray-600 disabled:to-gray-700 disabled:cursor-not-allowed text-white rounded-lg px-6 py-3 font-semibold transition-all shadow-lg hover:shadow-xl flex items-center justify-center space-x-2"
                                        >
                                            {loadingSpots ? (
                                                <>
                                                    <svg className="animate-spin h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                                        <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                                                        <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                                                    </svg>
                                                    <span>Please wait...</span>
                                                </>
                                            ) : (
                                                <span>Check Availability</span>
                                            )}
                                        </button>
                                    )}

                                    <Input
                                        label="Vehicle Details"
                                        type="text"
                                        placeholder="e.g., Toyota Camry - ABC123"
                                        value={bookingData.vehicle}
                                        onChange={(e) => setBookingData({ ...bookingData, vehicle: e.target.value })}
                                        required
                                    />

                                    {selectedSpot && (
                                        <div className="p-4 glass rounded-lg border border-primary-500/30">
                                            <p className="text-sm text-gray-400">Selected Spot</p>
                                            <p className="text-xl font-bold text-primary-400">{selectedSpot.spotNo}</p>
                                        </div>
                                    )}

                                    {bookingData.startTime && bookingData.endTime && (
                                        <div className="p-4 glass rounded-lg">
                                            <p className="text-sm text-gray-400">Total Cost</p>
                                            <p className="text-2xl font-bold text-white">
                                                ₹{(calculateDuration(bookingData.startTime, bookingData.endTime) * (parking?.price || 100)).toFixed(2)}
                                            </p>
                                            <p className="text-sm text-gray-400">
                                                {calculateDuration(bookingData.startTime, bookingData.endTime)} hours
                                            </p>
                                        </div>
                                    )}

                                    {selectedSpot ? (
                                        <Button
                                            type="submit"
                                            disabled={submitting}
                                            className="w-full bg-gradient-to-r from-green-500 to-emerald-600 hover:from-green-600 hover:to-emerald-700 disabled:from-gray-600 disabled:to-gray-700 disabled:cursor-not-allowed flex items-center justify-center space-x-2"
                                        >
                                            {submitting ? (
                                                <>
                                                    <svg className="animate-spin h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                                        <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                                                        <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                                                    </svg>
                                                    <span>Please wait...</span>
                                                </>
                                            ) : (
                                                <span>Confirm Booking</span>
                                            )}
                                        </Button>
                                    ) : (
                                        <div className="text-center p-3 rounded-lg bg-white/5 text-gray-400 text-sm">
                                            Select a spot to continue
                                        </div>
                                    )}
                                </form>
                            </Card>
                        </div>
                    </div>
                </motion.div>
            </div>
        </div>
    );
}
