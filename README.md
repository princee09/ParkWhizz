# 🚗 ParkWhizz - Smart Parking Reservation System

A full-stack parking reservation platform built with React and Spring Boot, featuring real-time availability, secure bookings, and automated email notifications.

[![Frontend CI/CD](https://github.com/YOUR_USERNAME/parkwhizz/actions/workflows/frontend-ci.yml/badge.svg)](https://github.com/YOUR_USERNAME/parkwhizz/actions/workflows/frontend-ci.yml)
[![Backend CI/CD](https://github.com/YOUR_USERNAME/parkwhizz/actions/workflows/backend-ci.yml/badge.svg)](https://github.com/YOUR_USERNAME/parkwhizz/actions/workflows/backend-ci.yml)

## ✨ Features

- 🔍 **Smart Search** - Browse and search parking locations across India
- 📅 **Real-time Availability** - Check spot availability for specific time slots
- 💳 **Secure Bookings** - JWT-based authentication and secure payment flow
- 👤 **User Profiles** - Manage personal information and booking history
- 📧 **Email Notifications** - Automated booking confirmations and cancellations
- 🔐 **Google OAuth** - Quick sign-in with Google account
- 📱 **Responsive Design** - Works seamlessly on desktop and mobile
- 🎨 **Modern UI** - Glassmorphic design with smooth animations

## 🛠️ Tech Stack

### Frontend
- **React 18** - UI library
- **Vite** - Build tool and dev server
- **Tailwind CSS** - Utility-first CSS framework
- **Framer Motion** - Animation library
- **React Router** - Client-side routing
- **Axios** - HTTP client

### Backend
- **Spring Boot 3** - Java framework
- **MongoDB** - NoSQL database
- **Spring Security** - Authentication & authorization
- **JWT** - Stateless authentication
- **Google OAuth 2.0** - Social login
- **JavaMail** - Email notifications
- **Firebase** - Push notifications (optional)

### DevOps
- **Docker** - Containerization
- **GitHub Actions** - CI/CD pipelines
- **Railway** - Backend hosting
- **Vercel** - Frontend hosting
- **MongoDB Atlas** - Database hosting

## 🚀 Quick Start

### Prerequisites

- **Node.js** 20+ and npm
- **Java** 17+
- **Maven** 3.8+
- **MongoDB** (local or Atlas)
- **Git**

### Local Development Setup

#### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/parkwhizz.git
cd parkwhizz
```

#### 2. Backend Setup

```bash
# Copy example config
cp src/main/resources/application.properties.example src/main/resources/application.properties

# Edit application.properties with your values
# - MongoDB URI
# - Email credentials (Gmail or SendGrid)
# - Google OAuth credentials
# - JWT secret

# Run backend
./mvnw spring-boot:run

# Or with Maven
mvn clean spring-boot:run
```

Backend will start on `http://localhost:9090`

#### 3. Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Copy example env file
cp .env.example .env.local

# Edit .env.local
# VITE_API_BASE_URL=http://localhost:9090
# VITE_GOOGLE_CLIENT_ID=your-google-client-id

# Run frontend
npm run dev
```

Frontend will start on `http://localhost:3000`

### Using Docker Compose

```bash
# Create .env file with your credentials
cp .env.example .env

# Edit .env file with your values

# Start all services (MongoDB + Backend + Frontend)
docker-compose up

# Stop all services
docker-compose down
```

## 📦 Production Deployment

### One-Line Deployment Sequence

```
MongoDB Atlas → SendGrid → Google OAuth → Railway Backend → Vercel Frontend → Test → Live
```

### Detailed Deployment Guide

See [deploy/checklist.txt](deploy/checklist.txt) for step-by-step instructions.

### Quick Deploy Commands

#### Deploy Backend to Railway

```bash
# Install Railway CLI
npm install -g @railway/cli

# Login
railway login

# Link project
railway link

# Set environment variables (see deploy/environment-variables.md)
railway variables set SPRING_DATA_MONGODB_URI="your-mongodb-uri"
railway variables set SPRING_MAIL_PASSWORD="your-sendgrid-api-key"
# ... set all other variables

# Deploy
railway up
```

#### Deploy Frontend to Vercel

```bash
# Install Vercel CLI
npm install -g vercel

# Login
vercel login

# Deploy
cd frontend
vercel --prod

# Set environment variables in Vercel dashboard
# VITE_API_BASE_URL=https://your-backend.railway.app
# VITE_GOOGLE_CLIENT_ID=your-google-client-id
```

## 🔧 Configuration

### Environment Variables

See [deploy/environment-variables.md](deploy/environment-variables.md) for complete reference.

#### Backend (Railway/Production)

```bash
SPRING_DATA_MONGODB_URI=mongodb+srv://user:pass@cluster.mongodb.net/Parkwhizz
SPRING_MAIL_HOST=smtp.sendgrid.net
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=apikey
SPRING_MAIL_PASSWORD=SG.your-sendgrid-api-key
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret
JWT_SECRET=your-long-random-secret
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app
```

#### Frontend (Vercel/Production)

```bash
VITE_API_BASE_URL=https://your-backend.railway.app
VITE_GOOGLE_CLIENT_ID=your-google-client-id
```

### MongoDB Atlas Setup

1. Create account at [cloud.mongodb.com](https://cloud.mongodb.com)
2. Create cluster (Free M0 tier)
3. Create database user
4. Whitelist IP: `0.0.0.0/0`
5. Get connection string
6. URL-encode password if it contains special characters

### SendGrid Setup

1. Create account at [sendgrid.com](https://sendgrid.com)
2. Verify sender email
3. Create API Key
4. Use in environment variables:
   - Host: `smtp.sendgrid.net`
   - Port: `587`
   - Username: `apikey`
   - Password: `<your-api-key>`

### Google OAuth Setup

1. Go to [console.cloud.google.com](https://console.cloud.google.com)
2. Create project
3. Enable Google+ API
4. Create OAuth 2.0 credentials
5. Add authorized origins and redirect URIs
6. Copy Client ID and Secret

## 🧪 Testing

### Run Deployment Tests

```bash
# Make script executable
chmod +x scripts/test-deploy.sh

# Test local deployment
./scripts/test-deploy.sh http://localhost:9090

# Test production deployment
./scripts/test-deploy.sh https://your-backend.railway.app https://your-frontend.vercel.app
```

### Manual API Testing

```bash
# Health check
curl https://your-backend.railway.app/actuator/health

# Get all parkings
curl https://your-backend.railway.app/api/parking/all

# Register user
curl -X POST https://your-backend.railway.app/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"first_name":"John","last_name":"Doe","email":"john@example.com","password":"password123","mobileNo":"1234567890"}'

# Login
curl -X POST https://your-backend.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'
```

## 📁 Project Structure

```
parkwhizz/
├── frontend/                  # React frontend
│   ├── src/
│   │   ├── components/       # Reusable UI components
│   │   ├── pages/            # Page components
│   │   ├── services/         # API integration
│   │   └── main.jsx          # Entry point
│   ├── .env.example          # Environment variables template
│   └── package.json
├── src/main/java/            # Spring Boot backend
│   └── com/parkwhizz/
│       ├── controller/       # REST API controllers
│       ├── model/            # Data models
│       ├── repository/       # MongoDB repositories
│       ├── service/          # Business logic
│       ├── security/         # JWT & OAuth
│       └── config/           # Configuration
├── src/main/resources/
│   ├── application-template.properties  # Env-based config
│   └── application.properties.example   # Local dev template
├── .github/workflows/        # CI/CD pipelines
├── deploy/                   # Deployment guides
├── scripts/                  # Utility scripts
├── Dockerfile                # Backend container
├── docker-compose.yml        # Local dev environment
└── README.md
```

## 🔐 Security

- ✅ JWT-based stateless authentication
- ✅ Password hashing with BCrypt
- ✅ CORS protection
- ✅ Environment-based secrets
- ✅ SQL injection prevention (MongoDB)
- ✅ XSS protection
- ✅ HTTPS in production
- ✅ Secure headers
- ✅ Rate limiting (recommended for production)

## 📊 Monitoring & Logs

### Railway Logs

```bash
railway logs
```

### Vercel Logs

```bash
vercel logs
```

### MongoDB Atlas Metrics

Go to Atlas → Clusters → Metrics

## 🔄 Rollback Procedure

If deployment fails:

1. **Railway**: Deployments → Select previous → Redeploy
2. **Vercel**: Deployments → Select previous → Promote to Production
3. Check logs for errors
4. Verify environment variables
5. Test locally with production env vars

## 💰 Cost Breakdown

| Service | Free Tier | Paid Tier |
|---------|-----------|-----------|
| MongoDB Atlas | M0 (512MB) | M10 ($57/mo) |
| SendGrid | 100 emails/day | Essentials ($19.95/mo) |
| Railway | $5 credit | $5/mo (500 hours) |
| Vercel | Unlimited | Pro ($20/mo) |
| **Total** | **~$5/mo** | **~$100/mo** |

## 📈 Scaling Recommendations

When you outgrow free tiers:

1. **Database**: Upgrade to MongoDB Atlas M10
2. **Email**: Upgrade to SendGrid Essentials
3. **Caching**: Add Redis (Railway add-on)
4. **CDN**: Add Cloudflare for static assets
5. **Load Balancer**: Add Railway load balancer
6. **Monitoring**: Add Sentry for error tracking

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📝 License

This project is licensed under the MIT License.

## 🆘 Support

- 📧 Email: support@parkwhizz.com
- 🐛 Issues: [GitHub Issues](https://github.com/YOUR_USERNAME/parkwhizz/issues)
- 📖 Docs: [deploy/checklist.txt](deploy/checklist.txt)

## 🙏 Acknowledgments

- Icons by [Heroicons](https://heroicons.com/)
- UI inspiration from modern parking apps
- Built with ❤️ using React and Spring Boot

---

**Made with ❤️ by Your Name**