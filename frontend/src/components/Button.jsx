export default function Button({ children, variant = 'primary', className = '', ...props }) {
    const variants = {
        primary: 'btn-primary',
        secondary: 'btn-secondary',
        ghost: 'px-6 py-3 rounded-lg hover:bg-white/10 transition-all',
    };

    return (
        <button
            className={`${variants[variant]} ${className}`}
            {...props}
        >
            {children}
        </button>
    );
}
