import { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import Card from '../components/Card';
import Button from '../components/Button';
import { bookingService } from '../services/api';

export default function BookingDetailPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [booking, setBooking] = useState(null);
    const [loading, setLoading] = useState(true);
    const [cancelling, setCancelling] = useState(false);

    useEffect(() => {
        loadBooking();
    }, [id]);

    const loadBooking = async () => {
        try {
            const data = await bookingService.getBookingById(id);
            setBooking(data);
        } catch (error) {
            console.error('Error loading booking:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleCancelBooking = async () => {
        // No confirmation popup
        // if (!confirm('Are you sure you want to cancel this booking?')) {
        //     return;
        // }

        setCancelling(true);
        try {
            await bookingService.updateBooking(id, {
                ...booking,
                bookingStatus: 'Cancelled'
            });

            // No success popup
            // alert('Booking cancelled successfully! Check your email for confirmation.');
            navigate('/bookings');
        } catch (error) {
            console.error('Error cancelling booking:', error);
            alert('Failed to cancel booking. Please try again.');
        } finally {
            setCancelling(false);
        }
    };

    const getStatusColor = (status) => {
        switch (status?.toUpperCase()) {
            case 'CONFIRMED':
                return 'text-green-400 bg-green-500/10';
            case 'ACTIVE':
                return 'text-blue-400 bg-blue-500/10';
            case 'COMPLETED':
                return 'text-gray-400 bg-gray-500/10';
            case 'CANCELLED':
                return 'text-red-400 bg-red-500/10';
            default:
                return 'text-gray-400 bg-gray-500/10';
        }
    };

    const formatDate = (dateString) => {
        return new Date(dateString).toLocaleString('en-IN', {
            year: 'numeric',
            month: 'long',
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
                    <p className="mt-4 text-gray-400">Loading booking...</p>
                </div>
            </div>
        );
    }

    if (!booking) {
        return (
            <div className="min-h-screen pt-24 flex items-center justify-center">
                <Card className="text-center">
                    <p className="text-xl mb-4">Booking not found</p>
                    <Link to="/bookings">
                        <Button>Back to Bookings</Button>
                    </Link>
                </Card>
            </div>
        );
    }

    const isCancellable = booking.bookingStatus?.toUpperCase() === 'CONFIRMED';

    return (
        <div className="min-h-screen pt-24 pb-12">
            <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                >
                    {/* Header */}
                    <div className="mb-8">
                        <Link to="/bookings" className="text-primary-400 hover:text-primary-300 mb-4 inline-block">
                            ← Back to All Bookings
                        </Link>
                        <h1 className="text-4xl font-bold mb-2">
                            Booking <span className="gradient-text">Details</span>
                        </h1>
                        <p className="text-gray-400">Booking ID: {booking._id}</p>
                    </div>

                    {/* Status Badge */}
                    <div className="mb-6">
                        <span className={`inline-block px-4 py-2 rounded-full font-semibold ${getStatusColor(booking.bookingStatus)}`}>
                            {booking.bookingStatus || 'CONFIRMED'}
                        </span>
                    </div>

                    {/* Main Details Card */}
                    <Card hover={false} className="mb-6">
                        <h2 className="text-2xl font-bold mb-6">Parking Information</h2>
                        <div className="space-y-4">
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <div>
                                    <p className="text-sm text-gray-400 mb-1">Parking Location</p>
                                    <p className="text-lg font-semibold">{booking.p_name}</p>
                                    <p className="text-gray-300">{booking.p_address}</p>
                                </div>

                                <div>
                                    <p className="text-sm text-gray-400 mb-1">Spot Number</p>
                                    <p className="text-3xl font-bold text-primary-400">{booking.spotNo}</p>
                                </div>

                                <div>
                                    <p className="text-sm text-gray-400 mb-1">Vehicle Details</p>
                                    <p className="text-lg">{booking.vehicle}</p>
                                </div>

                                <div>
                                    <p className="text-sm text-gray-400 mb-1">Total Amount</p>
                                    <p className="text-3xl font-bold text-primary-400">
                                        ₹{booking.amount?.toFixed(2)}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </Card>

                    {/* Time Details Card */}
                    <Card hover={false} className="mb-6">
                        <h2 className="text-2xl font-bold mb-6">Booking Duration</h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div>
                                <p className="text-sm text-gray-400 mb-1">Start Time</p>
                                <p className="text-lg font-semibold">{formatDate(booking.startTime)}</p>
                            </div>

                            <div>
                                <p className="text-sm text-gray-400 mb-1">End Time</p>
                                <p className="text-lg font-semibold">{formatDate(booking.endTime)}</p>
                            </div>

                            {booking.duration && (
                                <div className="md:col-span-2">
                                    <p className="text-sm text-gray-400 mb-1">Duration</p>
                                    <p className="text-lg font-semibold">{booking.duration} hour{booking.duration > 1 ? 's' : ''}</p>
                                </div>
                            )}
                        </div>
                    </Card>

                    {/* Action Buttons */}
                    <div className="flex gap-4">
                        {isCancellable && (
                            <div className="flex-1">
                                <Button
                                    onClick={handleCancelBooking}
                                    disabled={cancelling}
                                    className="w-full bg-red-600 hover:bg-red-700"
                                >
                                    {cancelling ? 'Cancelling...' : 'Cancel Booking'}
                                </Button>
                            </div>
                        )}
                        <Link to="/bookings" className="flex-1">
                            <Button className="w-full btn-secondary">
                                Back to Bookings
                            </Button>
                        </Link>
                    </div>

                    {/* Note */}
                    {/* Note removed as requested */
                        /* {isCancellable && (
                            <div className="mt-6 p-4 glass rounded-lg border border-yellow-500/50">
                                <p className="text-sm text-yellow-400">
                                    <strong>Note:</strong> Cancelling this booking will send a confirmation email and free up the parking spot.
                                </p>
                            </div>
                        )} */
                    }
                </motion.div>
            </div>
        </div>
    );
}
