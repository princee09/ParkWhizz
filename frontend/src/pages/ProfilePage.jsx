import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import { authService } from '../services/api';
import apiClient from '../services/apiClient';
import Card from '../components/Card';
import Button from '../components/Button';

export default function ProfilePage() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [message, setMessage] = useState('');
    const [formData, setFormData] = useState({
        first_name: '',
        last_name: '',
        email: '',
        mobileNo: '',
        gender: '',
        dateOfBirth: '',
        city: '',
        state: ''
    });
    const [previewPicture, setPreviewPicture] = useState('');

    const indianStates = [
        'Andhra Pradesh', 'Arunachal Pradesh', 'Assam', 'Bihar', 'Chhattisgarh',
        'Goa', 'Gujarat', 'Haryana', 'Himachal Pradesh', 'Jharkhand', 'Karnataka',
        'Kerala', 'Madhya Pradesh', 'Maharashtra', 'Manipur', 'Meghalaya', 'Mizoram',
        'Nagaland', 'Odisha', 'Punjab', 'Rajasthan', 'Sikkim', 'Tamil Nadu',
        'Telangana', 'Tripura', 'Uttar Pradesh', 'Uttarakhand', 'West Bengal',
        'Delhi', 'Jammu and Kashmir', 'Ladakh', 'Puducherry', 'Chandig arh'
    ];

    useEffect(() => {
        loadUserData();
    }, []);

    const loadUserData = async () => {
        try {
            const currentUser = authService.getCurrentUser();
            if (!currentUser) {
                navigate('/login');
                return;
            }

            // Fetch full user data from API
            const response = await apiClient.get(`/user/${currentUser.email}`);
            if (response.data && response.data.length > 0) {
                const userData = response.data[0];
                setUser(userData);
                setFormData({
                    first_name: userData.first_name || '',
                    last_name: userData.last_name || '',
                    email: userData.email || '',
                    mobileNo: userData.mobileNo || '',
                    gender: userData.gender || '',
                    dateOfBirth: userData.dateOfBirth || '',
                    city: userData.city || '',
                    state: userData.state || ''
                });
                setPreviewPicture(userData.profilePicture || '');
            }
        } catch (error) {
            console.error('Failed to load user data:', error);
            setMessage('Failed to load profile data');
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleImageUpload = (e) => {
        const file = e.target.files[0];
        if (file) {
            // For now, convert to base64
            const reader = new FileReader();
            reader.onloadend = () => {
                setPreviewPicture(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSaving(true);
        setMessage('');

        try {
            const updateData = {
                ...formData,
                profilePicture: previewPicture
            };

            await apiClient.put(`/user/${formData.email}/profile`, updateData);

            // Update local storage
            const updatedUser = {
                email: formData.email,
                name: `${formData.first_name} ${formData.last_name}`.trim(),
                id: user._id,
                profilePicture: previewPicture
            };
            localStorage.setItem('user', JSON.stringify(updatedUser));

            // Trigger UI update
            window.dispatchEvent(new Event('userChanged'));

            setMessage('Profile updated successfully!');
            setTimeout(() => setMessage(''), 3000);
        } catch (error) {
            console.error('Failed to update profile:', error);
            setMessage('Failed to update profile. Please try again.');
        } finally {
            setSaving(false);
        }
    };

    const getInitials = () => {
        const name = `${formData.first_name} ${formData.last_name}`.trim();
        if (name) {
            const names = name.split(' ');
            if (names.length >= 2) {
                return `${names[0][0]}${names[1][0]}`.toUpperCase();
            }
            return name.substring(0, 2).toUpperCase();
        }
        return formData.email?.substring(0, 2).toUpperCase() || '??';
    };

    if (loading) {
        return (
            <div className="min-h-screen pt-16 flex items-center justify-center">
                <div className="text-xl">Loading...</div>
            </div>
        );
    }

    return (
        <div className="min-h-screen pt-16 px-4 py-8">
            <div className="max-w-4xl mx-auto">
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                >
                    <h1 className="text-4xl font-bold mb-8">
                        <span className="gradient-text">Profile Settings</span>
                    </h1>

                    <Card>
                        <form onSubmit={handleSubmit} className="space-y-6">
                            {/* Profile Picture Section */}
                            <div className="flex flex-col items-center pb-6 border-b border-white/10">
                                <div className="relative">
                                    <div className="w-32 h-32 rounded-full bg-gradient-to-br from-primary-500 to-accent-500 flex items-center justify-center text-4xl font-bold overflow-hidden">
                                        {previewPicture ? (
                                            <img
                                                src={previewPicture}
                                                alt="Profile"
                                                className="w-full h-full object-cover"
                                            />
                                        ) : (
                                            getInitials()
                                        )}
                                    </div>
                                    <label className="absolute bottom-0 right-0 bg-primary-500 hover:bg-primary-600 rounded-full p-2 cursor-pointer transition-colors">
                                        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z" />
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 13a3 3 0 11-6 0 3 3 0 016 0z" />
                                        </svg>
                                        <input
                                            type="file"
                                            accept="image/*"
                                            onChange={handleImageUpload}
                                            className="hidden"
                                        />
                                    </label>
                                </div>
                                <p className="text-sm text-gray-400 mt-3">Click camera icon to upload photo</p>
                            </div>

                            {/* Personal Information */}
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <div>
                                    <label className="block text-sm font-medium mb-2">First Name</label>
                                    <input
                                        type="text"
                                        name="first_name"
                                        value={formData.first_name}
                                        onChange={handleInputChange}
                                        className="w-full glass rounded-lg border border-white/10 px-4 py-2.5 outline-none focus:border-primary-500 transition-colors"
                                        required
                                    />
                                </div>

                                <div>
                                    <label className="block text-sm font-medium mb-2">Last Name</label>
                                    <input
                                        type="text"
                                        name="last_name"
                                        value={formData.last_name}
                                        onChange={handleInputChange}
                                        className="w-full glass rounded-lg border border-white/10 px-4 py-2.5 outline-none focus:border-primary-500 transition-colors"
                                        required
                                    />
                                </div>

                                <div>
                                    <label className="block text-sm font-medium mb-2">Email</label>
                                    <input
                                        type="email"
                                        name="email"
                                        value={formData.email}
                                        disabled
                                        className="w-full glass rounded-lg border border-white/10 px-4 py-2.5 outline-none bg-white/5 cursor-not-allowed"
                                    />
                                    <p className="text-xs text-gray-400 mt-1">Email cannot be changed</p>
                                </div>

                                <div>
                                    <label className="block text-sm font-medium mb-2">Phone Number</label>
                                    <div className="flex items-center glass rounded-lg border border-white/10 focus-within:border-primary-500 transition-colors">
                                        <span className="px-3 text-gray-400 border-r border-white/10">+91</span>
                                        <input
                                            type="tel"
                                            name="mobileNo"
                                            value={formData.mobileNo}
                                            onChange={handleInputChange}
                                            placeholder="98765 43210"
                                            maxLength="10"
                                            pattern="[0-9]{10}"
                                            className="flex-1 bg-transparent px-3 py-2.5 outline-none"
                                        />
                                    </div>
                                </div>

                                <div>
                                    <label className="block text-sm font-medium mb-2">Gender</label>
                                    <select
                                        name="gender"
                                        value={formData.gender}
                                        onChange={handleInputChange}
                                        className="w-full glass rounded-lg border border-white/10 px-4 py-2.5 outline-none focus:border-primary-500 transition-colors"
                                    >
                                        <option value="">Select Gender</option>
                                        <option value="Male">Male</option>
                                        <option value="Female">Female</option>
                                        <option value="Other">Other</option>
                                        <option value="Prefer not to say">Prefer not to say</option>
                                    </select>
                                </div>

                                <div>
                                    <label className="block text-sm font-medium mb-2">Date of Birth</label>
                                    <input
                                        type="date"
                                        name="dateOfBirth"
                                        value={formData.dateOfBirth}
                                        onChange={handleInputChange}
                                        max={new Date().toISOString().split('T')[0]}
                                        className="w-full glass rounded-lg border border-white/10 px-4 py-2.5 outline-none focus:border-primary-500 transition-colors"
                                    />
                                </div>

                                <div>
                                    <label className="block text-sm font-medium mb-2">City</label>
                                    <input
                                        type="text"
                                        name="city"
                                        value={formData.city}
                                        onChange={handleInputChange}
                                        placeholder="Enter your city"
                                        className="w-full glass rounded-lg border border-white/10 px-4 py-2.5 outline-none focus:border-primary-500 transition-colors"
                                    />
                                </div>

                                <div>
                                    <label className="block text-sm font-medium mb-2">State</label>
                                    <select
                                        name="state"
                                        value={formData.state}
                                        onChange={handleInputChange}
                                        className="w-full glass rounded-lg border border-white/10 px-4 py-2.5 outline-none focus:border-primary-500 transition-colors"
                                    >
                                        <option value="">Select State</option>
                                        {indianStates.map((state) => (
                                            <option key={state} value={state}>{state}</option>
                                        ))}
                                    </select>
                                </div>
                            </div>

                            {/* Message Display */}
                            {message && (
                                <div className={`p-3 rounded-lg ${message.includes('success') ? 'bg-green-500/10 border border-green-500/50 text-green-400' : 'bg-red-500/10 border border-red-500/50 text-red-400'}`}>
                                    {message}
                                </div>
                            )}

                            {/* Action Buttons */}
                            <div className="flex gap-4 pt-4">
                                <Button type="submit" disabled={saving} className="flex-1">
                                    {saving ? 'Saving...' : 'Save Changes'}
                                </Button>
                                <button
                                    type="button"
                                    onClick={() => navigate('/')}
                                    className="btn-secondary flex-1"
                                >
                                    Cancel
                                </button>
                            </div>
                        </form>
                    </Card>
                </motion.div>
            </div>
        </div>
    );
}
