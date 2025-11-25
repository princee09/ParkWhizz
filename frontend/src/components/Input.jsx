export default function Input({ label, error, ...props }) {
    return (
        <div className="space-y-2">
            {label && (
                <label className="block text-sm font-medium text-gray-300">
                    {label}
                </label>
            )}
            <input
                {...props}
                className={`w-full px-4 py-3 rounded-lg glass border ${error ? 'border-red-500' : 'border-white/20'
                    } focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all`}
            />
            {error && (
                <p className="text-sm text-red-400">{error}</p>
            )}
        </div>
    );
}
