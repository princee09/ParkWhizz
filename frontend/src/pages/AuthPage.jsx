import { useState } from 'react';
import { motion } from 'framer-motion';
import { useNavigate, useLocation } from 'react-router-dom';
import Input from '../components/Input';
import Button from '../components/Button';
import Card from '../components/Card';
import { authService } from '../services/api';
import { GoogleLogin } from '@react-oauth/google';

export default function AuthPage() {
    const [isLogin, setIsLogin] = useState(true);
    const [formData, setFormData] = useState({
        email: '',
        password: '',
        name: '',
        phone: ''
    });
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const location = useLocation();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            if (isLogin) {
                await authService.login({
                    email: formData.email,
                    password: formData.password
                });
            } else {
                const nameParts = formData.name.trim().split(' ');
                const firstName = nameParts[0];
                const lastName = nameParts.slice(1).join(' ') || '';

                await authService.register({
                    first_name: firstName,
                    last_name: lastName,
                    email: formData.email,
                    password: formData.password,
                    mobileNo: formData.phone
                });
            }

            // Trigger navbar update
            window.dispatchEvent(new Event('userChanged'));

            const from = location.state?.from?.pathname || '/';
            navigate(from, { replace: true });
        } catch (err) {
            setError(err.response?.data?.message || 'Authentication failed. Please try again.');
            console.error('Auth error:', err);
        }
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    return (
        <div className="min-h-screen pt-16 flex items-center justify-center px-4">
            {/* Background decoration */}
            <div className="absolute inset-0 z-0">
                <div className="absolute top-1/4 left-10 w-96 h-96 bg-primary-500/20 rounded-full blur-3xl animate-float"></div>
                <div className="absolute bottom-1/4 right-10 w-96 h-96 bg-accent-500/20 rounded-full blur-3xl animate-float" style={{ animationDelay: '2s' }}></div>
            </div>

            <motion.div
                initial={{ opacity: 0, scale: 0.9 }}
                animate={{ opacity: 1, scale: 1 }}
                className="relative z-10 w-full max-w-md"
            >
                <Card hover={false}>
                    {/* Header */}
                    <div className="text-center mb-8">
                        <h1 className="text-3xl font-bold mb-2">
                            {isLogin ? 'Welcome Back' : 'Create Account'}
                        </h1>
                        <p className="text-gray-400">
                            {isLogin ? 'Sign in to continue' : 'Join ParkWhizz today'}
                        </p>
                    </div>

                    {/* Form */}
                    <form onSubmit={handleSubmit} className="space-y-4">
                        {!isLogin && (
                            <Input
                                label="Full Name"
                                type="text"
                                name="name"
                                placeholder="John Doe"
                                value={formData.name}
                                onChange={handleChange}
                                required
                            />
                        )}

                        <Input
                            label="Email"
                            type="email"
                            name="email"
                            placeholder="you@example.com"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />

                        <Input
                            label="Password"
                            type="password"
                            name="password"
                            placeholder="••••••••"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />

                        {!isLogin && (
                            <div>
                                <label className="block text-sm font-medium mb-2">
                                    Phone Number
                                </label>
                                <div className="flex items-center glass rounded-lg border border-white/10 focus-within:border-primary-500 transition-colors">
                                    <span className="px-3 text-gray-400 border-r border-white/10">+91</span>
                                    <input
                                        type="tel"
                                        name="phone"
                                        placeholder="98765 43210"
                                        value={formData.phone}
                                        onChange={handleChange}
                                        required
                                        maxLength="10"
                                        pattern="[0-9]{10}"
                                        className="flex-1 bg-transparent px-3 py-2.5 outline-none"
                                    />
                                </div>
                            </div>
                        )}

                        {error && (
                            <div className="p-3 rounded-lg bg-red-500/10 border border-red-500/50 text-red-400 text-sm">
                                {error}
                            </div>
                        )}

                        <Button type="submit" className="w-full">
                            {isLogin ? 'Sign In' : 'Create Account'}
                        </Button>
                    </form>

                    {/* Toggle */}
                    <div className="mt-6 text-center">
                        <p className="text-gray-400">
                            {isLogin ? "Don't have an account?" : 'Already have an account?'}
                            {' '}
                            <button
                                type="button"
                                onClick={() => {
                                    setIsLogin(!isLogin);
                                    setError('');
                                }}
                                className="text-primary-400 hover:text-primary-300 font-semibold"
                            >
                                {isLogin ? 'Sign Up' : 'Sign In'}
                            </button>
                        </p>
                    </div>

                    {/* Google OAuth placeholder */}
                    <div className="mt-6">
                        <div className="relative">
                            <div className="absolute inset-0 flex items-center">
                                <div className="w-full border-t border-white/10"></div>
                            </div>
                            <div className="relative flex justify-center text-sm">
                                <span className="px-2 glass text-gray-400">Or continue with</span>
                            </div>
                        </div>

                        <div className="flex justify-center mt-4">
                            <GoogleLogin
                                onSuccess={async (credentialResponse) => {
                                    try {
                                        await authService.googleLogin(credentialResponse.credential);
                                        window.dispatchEvent(new Event('userChanged'));
                                        const from = location.state?.from?.pathname || '/';
                                        navigate(from, { replace: true });
                                    } catch (err) {
                                        setError('Google login failed. Please try again.');
                                        console.error('Google login error:', err);
                                    }
                                }}
                                onError={() => {
                                    setError('Google login failed.');
                                }}
                                useOneTap
                                theme="filled_black"
                                shape="pill"
                                width="100%"
                            />
                        </div>
                    </div>
                </Card>
            </motion.div>
        </div>
    );
}
