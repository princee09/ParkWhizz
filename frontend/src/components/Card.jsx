import { motion } from 'framer-motion';

export default function Card({ children, className = '', hover = true }) {
    return (
        <motion.div
            whileHover={hover ? { scale: 1.02, y: -5 } : {}}
            className={`glass rounded-xl p-6 ${hover ? 'card-hover' : ''} ${className}`}
        >
            {children}
        </motion.div>
    );
}
