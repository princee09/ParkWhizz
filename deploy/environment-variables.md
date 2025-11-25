# ================================================
# ENVIRONMENT VARIABLES REFERENCE
# ================================================

## BACKEND ENVIRONMENT VARIABLES (Railway/Production)

### Required Variables

# MongoDB Connection
SPRING_DATA_MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/Parkwhizz?retryWrites=true&w=majority

# Email Configuration (SendGrid recommended)
SPRING_MAIL_HOST=smtp.sendgrid.net
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=apikey
SPRING_MAIL_PASSWORD=SG.xxxxxxxxxxxxxxxxxxxxxxxxxxxxx

# Google OAuth
GOOGLE_CLIENT_ID=123456789-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=GOCSPX-xxxxxxxxxxxxxxxxxxxxxxxxxxxx

# JWT Security
JWT_SECRET=<generate-with: openssl rand -base64 64>

# CORS Configuration
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app,https://parkwhizz.com

### Optional Variables

# Server Port (Railway auto-assigns, but you can override)
PORT=9090

# JWT Expiration (milliseconds, default: 24 hours)
JWT_EXPIRATION=86400000

# Logging Level
LOG_LEVEL=INFO

# Spring Security Basic Auth (for testing endpoints)
SPRING_SECURITY_USER_NAME=admin
SPRING_SECURITY_USER_PASSWORD=securepassword

# Firebase Config Path
FIREBASE_CONFIG_PATH=parkbuzzPushNotification.json

# Google OAuth Redirect URI
GOOGLE_REDIRECT_URI=https://your-frontend.vercel.app

## FRONTEND ENVIRONMENT VARIABLES (Vercel/Production)

### Required Variables

# Backend API URL
VITE_API_BASE_URL=https://your-backend.railway.app

# Google OAuth Client ID (same as backend)
VITE_GOOGLE_CLIENT_ID=123456789-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.apps.googleusercontent.com

### Optional Variables

# Debug Mode
VITE_DEBUG=false

# Environment Name
VITE_ENV=production

## GITHUB SECRETS (for CI/CD)

### Frontend CI/CD Secrets

# Vercel Deployment
VERCEL_TOKEN=<get-from: vercel.com/account/tokens>
VERCEL_ORG_ID=<get-from: vercel.json or vercel dashboard>
VERCEL_PROJECT_ID=<get-from: vercel.json or vercel dashboard>

# Frontend Environment Variables
VITE_API_BASE_URL=https://your-backend.railway.app
VITE_GOOGLE_CLIENT_ID=<your-google-client-id>

### Backend CI/CD Secrets

# Railway Deployment
RAILWAY_TOKEN=<get-from: railway.app/account/tokens>

# GitHub Container Registry (auto-configured)
GITHUB_TOKEN=<auto-provided-by-github-actions>

## LOCAL DEVELOPMENT (.env files)

### Backend (create .env in root, or use application.properties)
# See application.properties.example

### Frontend (create frontend/.env.local)
VITE_API_BASE_URL=http://localhost:9090
VITE_GOOGLE_CLIENT_ID=<your-google-client-id>
VITE_DEBUG=true
VITE_ENV=development

## SPECIAL CHARACTERS IN PASSWORDS

If your MongoDB password contains special characters, URL-encode them:

Special Character → URL Encoded
@  → %40
:  → %3A
/  → %2F
?  → %3F
#  → %23
[  → %5B
]  → %5D
!  → %21
$  → %24
&  → %26
'  → %27
(  → %28
)  → %29
*  → %2A
+  → %2B
,  → %2C
;  → %3B
=  → %3D

Example:
Password: MyP@ss:123!
Encoded:  MyP%40ss%3A123%21

Full URI: mongodb+srv://user:MyP%40ss%3A123%21@cluster.mongodb.net/Parkwhizz

## HOW TO SET ENVIRONMENT VARIABLES

### Railway
1. Go to your project dashboard
2. Click on your service
3. Go to "Variables" tab
4. Click "New Variable"
5. Add key-value pairs
6. Click "Deploy" to apply changes

### Vercel
1. Go to your project dashboard
2. Go to "Settings" → "Environment Variables"
3. Add key-value pairs
4. Select environments (Production, Preview, Development)
5. Click "Save"
6. Redeploy to apply changes

### GitHub Secrets
1. Go to your repository
2. Settings → Secrets and variables → Actions
3. Click "New repository secret"
4. Add name and value
5. Click "Add secret"

### Local Development
1. Copy .env.example to .env.local (frontend)
2. Copy application.properties.example to application.properties (backend)
3. Fill in your local values
4. Never commit these files to Git!

## SECURITY BEST PRACTICES

1. ✅ Never commit .env files or application.properties with real values
2. ✅ Use strong, random JWT secrets (min 64 characters)
3. ✅ Rotate secrets regularly (every 90 days)
4. ✅ Use different secrets for dev/staging/production
5. ✅ Enable 2FA on all service accounts
6. ✅ Limit CORS origins to your actual domains
7. ✅ Use SendGrid API keys (not SMTP passwords)
8. ✅ Store Firebase credentials as environment variable (JSON string)
9. ✅ Review Railway/Vercel access logs regularly
10. ✅ Use Railway/Vercel's secret scanning features
