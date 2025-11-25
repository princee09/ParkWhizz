import apiClient from './apiClient';

export const parkingService = {
    // Get all parking lots
    getAllParkings: async () => {
        const response = await apiClient.get('/parking/all');
        return response.data;
    },

    // Get parking by ID
    getParkingById: async (id) => {
        const response = await apiClient.get(`/parking/${id}`);
        return response.data;
    },

    // Get spots for a parking lot with availability check
    getSpotsByParking: async (parkingId, startTime, endTime) => {
        const response = await apiClient.get(`/spot/${parkingId}`, {
            params: { startTime, endTime }
        });
        return response.data;
    },
};

export const bookingService = {
    // Create a new booking
    createBooking: async (userId, spotId, bookingData) => {
        const response = await apiClient.post(
            `/booking/${userId}/${spotId}/create`,
            bookingData
        );
        return response.data;
    },

    // Get user bookings
    getUserBookings: async (userId) => {
        const response = await apiClient.get(`/booking/${userId}/userBookings`);
        return response.data;
    },

    // Get booking by ID
    getBookingById: async (bookingId) => {
        const response = await apiClient.get(`/booking/${bookingId}`);
        return response.data;
    },

    // Update booking
    updateBooking: async (bookingId, bookingData) => {
        const response = await apiClient.put(
            `/booking/${bookingId}/update`,
            bookingData
        );
        return response.data;
    },
};

export const authService = {
    // Login
    login: async (credentials) => {
        const response = await apiClient.post('/user/login', credentials);
        if (response.data.token) {
            localStorage.setItem('token', response.data.token);

            // Fetch user details after login
            try {
                const userResponse = await apiClient.get(`/user/${credentials.email}`);
                if (userResponse.data && userResponse.data.length > 0) {
                    const userData = userResponse.data[0];
                    localStorage.setItem('user', JSON.stringify({
                        email: userData.email,
                        name: `${userData.first_name || ''} ${userData.last_name || ''}`.trim(),
                        id: userData._id,
                        profilePicture: userData.profilePicture,
                        mobileNo: userData.mobileNo,
                        gender: userData.gender,
                        state: userData.state,
                        dateOfBirth: userData.dateOfBirth
                    }));
                }
            } catch (error) {
                console.error('Failed to fetch user details:', error);
                // Still store basic info
                localStorage.setItem('user', JSON.stringify({
                    email: credentials.email,
                    name: credentials.email.split('@')[0]
                }));
            }
        }
        return response.data;
    },

    // Register
    register: async (userData) => {
        const response = await apiClient.post('/user/register', userData);
        // Auto-login after registration
        if (response.data) {
            return await authService.login({
                email: userData.email,
                password: userData.password
            });
        }
        return response.data;
    },

    // Google Login
    googleLogin: async (token) => {
        const response = await apiClient.post('/user/google-login', { idToken: token });
        if (response.data.token) {
            localStorage.setItem('token', response.data.token);

            // Fetch user details (decoding token or fetching profile)
            // For simplicity, we'll fetch profile using the email from the token if possible,
            // but here we might need to rely on the backend response or fetch /user/me if it existed.
            // Since we don't have the email easily here without decoding, let's try to fetch by email if we can get it,
            // or just rely on the backend to return user info in the future.
            // For now, let's try to decode the token payload to get email for fetching profile.
            try {
                const base64Url = token.split('.')[1];
                const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
                const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
                    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
                }).join(''));
                const { email } = JSON.parse(jsonPayload);

                const userResponse = await apiClient.get(`/user/${email}`);
                if (userResponse.data && userResponse.data.length > 0) {
                    const userData = userResponse.data[0];
                    localStorage.setItem('user', JSON.stringify({
                        email: userData.email,
                        name: `${userData.first_name || ''} ${userData.last_name || ''}`.trim(),
                        id: userData._id,
                        profilePicture: userData.profilePicture,
                        mobileNo: userData.mobileNo,
                        gender: userData.gender,
                        state: userData.state,
                        dateOfBirth: userData.dateOfBirth
                    }));
                }
            } catch (error) {
                console.error('Failed to fetch user details after Google login:', error);
            }
        }
        return response.data;
    },

    // Logout
    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        localStorage.removeItem('userEmail');
    },

    // Get current user
    getCurrentUser: () => {
        const user = localStorage.getItem('user');
        return user ? JSON.parse(user) : null;
    },
};

