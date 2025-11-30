# 🚗 ParkWhizz - Smart Parking Reservation System

A modern full-stack parking reservation platform built with React and Spring Boot. Find, book, and manage parking spots across India with real-time availability and secure payments.


## ✨ Features

- 🔍 **Smart Search** - Browse parking locations across major Indian cities
- 📅 **Real-time Availability** - Check spot availability for specific time slots
- 💳 **Secure Bookings** - JWT-based authentication and encrypted transactions
- 👤 **User Profiles** - Manage personal information and view booking history
- 📧 **Email Notifications** - Automated booking confirmations via SendGrid
- 🔐 **Google OAuth** - Quick and secure sign-in with Google
- 📱 **Responsive Design** - Seamless experience across all devices
- 🎨 **Modern UI** - Glassmorphic design with smooth animations using Framer Motion

## 🛠️ Tech Stack

### Frontend
- React 18 + Vite
- Tailwind CSS + Framer Motion
- React Router v6
- Axios + Google OAuth

### Backend
- Spring Boot 3.2.2
- MongoDB (Spring Data MongoDB)
- Spring Security + JWT
- JavaMail + SendGrid
- Firebase Admin SDK

### DevOps & Deployment
- Docker + Docker Compose
- GitHub Actions CI/CD
- Render.com (Free hosting)
- MongoDB Atlas (Free tier)

## 🚀 Quick Start

### Prerequisites
- Node.js 20+
- Java 17+
- Maven 3.8+
- MongoDB (local or Atlas)

### Local Development

#### 1. Clone the Repository
```bash
git clone https://github.com/princee09/ParkWhizz.git
cd ParkWhizz
```

#### 2. Backend Setup
```bash
# Copy example config
cp src/main/resources/application.properties.example src/main/resources/application.properties

# Edit with your credentials:
# - MongoDB URI
# - SendGrid API key
# - Google OAuth credentials
# - JWT secret

# Run backend
./mvnw spring-boot:run
```

Backend starts on `http://localhost:9090`

#### 3. Frontend Setup
```bash
cd frontend
npm install

# Copy env file
cp .env.example .env

# Edit .env:
# VITE_API_BASE_URL=http://localhost:9090
# VITE_GOOGLE_CLIENT_ID=your-google-client-id

# Run frontend
npm run dev
```

Frontend starts on `http://localhost:5173`

## 🌐 Production Deployment

We provide a **100% FREE** deployment solution using:
- **Frontend**: Render Static Site
- **Backend**: Render Web Service
- **Database**: MongoDB Atlas (512MB free)
- **Email**: SendGrid (100 emails/day free)

### Quick Deploy

Follow our comprehensive deployment guide:
- 📖 [Render Deployment Guide](./render-deployment-guide.md)
- 📋 [Environment Variables Reference](./deploy/environment-variables.md)
- ✅ [Deployment Checklist](./deploy/checklist.txt)

### Environment Variables

#### Backend (Render)
```env
SPRING_DATA_MONGODB_URI=mongodb+srv://...
SPRING_MAIL_HOST=smtp.sendgrid.net
SPRING_MAIL_USERNAME=apikey
SPRING_MAIL_PASSWORD=SG.your-api-key
GOOGLE_CLIENT_ID=your-client-id
GOOGLE_CLIENT_SECRET=your-client-secret
JWT_SECRET=your-64-char-secret
CORS_ALLOWED_ORIGINS=https://your-frontend.onrender.com
```

#### Frontend (Render)
```env
VITE_API_BASE_URL=https://your-backend.onrender.com
VITE_GOOGLE_CLIENT_ID=your-google-client-id
```

## 📚 API Documentation

### Authentication
- `POST /api/auth/login` - Email/password login
- `POST /api/auth/register` - User registration
- `POST /api/auth/google` - Google OAuth login

### Parking Locations
- `GET /api/parking/all` - List all parking locations
- `GET /api/parking/{id}` - Get parking details
- `GET /api/parking/city/{city}` - Search by city

### Bookings
- `POST /api/bookings` - Create booking
- `GET /api/bookings` - Get user bookings
- `DELETE /api/bookings/{id}` - Cancel booking

### Spots
- `GET /api/spots/parking/{parkingId}` - Get spots for parking
- `POST /api/spots/check-availability` - Check availability

## 🔐 Security Features

- JWT-based stateless authentication
- Password encryption with BCrypt
- CORS configuration for production
- Google OAuth 2.0 integration
- Input validation and sanitization
- Secure email delivery via SendGrid

## 🧪 Testing

### Backend Tests
```bash
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm run test
```

### E2E Testing
```bash
# After deployment
./scripts/test-deploy.sh https://your-frontend-url.com
```

## � Project Structure

```
ParkWhizz/
├── frontend/                 # React application
│   ├── src/
│   │   ├── components/      # Reusable UI components
│   │   ├── pages/           # Page components
│   │   ├── services/        # API clients
│   │   └── index.css        # Global styles
│   └── package.json
├── src/                     # Spring Boot application
│   └── main/
│       ├── java/com/parkwhizz/
│       │   ├── config/      # Security, CORS, OAuth
│       │   ├── controller/  # REST controllers
│       │   ├── model/       # MongoDB entities
│       │   ├── repository/  # Data access layer
│       │   ├── security/    # JWT, authentication
│       │   └── service/     # Business logic
│       └── resources/
│           ├── application.properties.example
│           └── parkbuzzPushNotification.json.example
├── deploy/                  # Deployment guides
├── .github/workflows/       # CI/CD pipelines
├── docker-compose.yml       # Local development
└── Dockerfile              # Backend container
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Kumar Prince**
- GitHub: [@princee09](https://github.com/princee09)

## 🙏 Acknowledgments

- Icons from [Heroicons](https://heroicons.com/)
- Design inspiration from modern UI/UX patterns
- MongoDB Atlas for database hosting
- Render.com for free application hosting

## 📧 Support

For support, email kp198237@gmail.com or open an issue on GitHub.

---

**Made with ❤️ in India** 🇮🇳