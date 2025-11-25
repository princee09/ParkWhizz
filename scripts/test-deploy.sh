#!/bin/bash

# ================================================
# ParkWhizz Deployment Test Script
# ================================================
# This script tests your deployed application
# Usage: ./test-deploy.sh <BACKEND_URL>
# Example: ./test-deploy.sh https://parkwhizz-backend.railway.app

set -e

BACKEND_URL="${1:-http://localhost:9090}"
FRONTEND_URL="${2:-http://localhost:3000}"

echo "================================================"
echo "ParkWhizz Deployment Test"
echo "================================================"
echo "Backend URL: $BACKEND_URL"
echo "Frontend URL: $FRONTEND_URL"
echo ""

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Test counter
TESTS_PASSED=0
TESTS_FAILED=0

# Function to test endpoint
test_endpoint() {
    local name=$1
    local url=$2
    local expected_status=${3:-200}
    
    echo -n "Testing $name... "
    
    response=$(curl -s -o /dev/null -w "%{http_code}" "$url" || echo "000")
    
    if [ "$response" = "$expected_status" ]; then
        echo -e "${GREEN}✓ PASSED${NC} (HTTP $response)"
        ((TESTS_PASSED++))
        return 0
    else
        echo -e "${RED}✗ FAILED${NC} (Expected HTTP $expected_status, got $response)"
        ((TESTS_FAILED++))
        return 1
    fi
}

# Function to test JSON endpoint
test_json_endpoint() {
    local name=$1
    local url=$2
    
    echo -n "Testing $name... "
    
    response=$(curl -s "$url")
    
    if echo "$response" | grep -q "{"; then
        echo -e "${GREEN}✓ PASSED${NC}"
        echo "  Response: $(echo $response | head -c 100)..."
        ((TESTS_PASSED++))
        return 0
    else
        echo -e "${RED}✗ FAILED${NC}"
        echo "  Response: $response"
        ((TESTS_FAILED++))
        return 1
    fi
}

echo "================================================"
echo "1. HEALTH CHECKS"
echo "================================================"

# Backend health check
test_endpoint "Backend Health" "$BACKEND_URL/actuator/health" 200 || \
test_endpoint "Backend Root" "$BACKEND_URL/" 200

# Frontend health check
test_endpoint "Frontend" "$FRONTEND_URL/" 200

echo ""
echo "================================================"
echo "2. API ENDPOINTS"
echo "================================================"

# Test parking locations endpoint
test_json_endpoint "Get All Parkings" "$BACKEND_URL/api/parking/all"

# Test spots endpoint (may need parking ID)
echo -n "Testing Spots API... "
echo -e "${YELLOW}⚠ SKIPPED${NC} (Requires parking ID)"

echo ""
echo "================================================"
echo "3. AUTHENTICATION"
echo "================================================"

# Test login endpoint (should return 401 or 400 without credentials)
echo -n "Testing Login Endpoint... "
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST \
    -H "Content-Type: application/json" \
    -d '{"email":"test@test.com","password":"test"}' \
    "$BACKEND_URL/api/auth/login" || echo "000")

if [ "$response" = "401" ] || [ "$response" = "400" ] || [ "$response" = "403" ]; then
    echo -e "${GREEN}✓ PASSED${NC} (Endpoint accessible, HTTP $response)"
    ((TESTS_PASSED++))
else
    echo -e "${YELLOW}⚠ WARNING${NC} (Unexpected response: HTTP $response)"
fi

echo ""
echo "================================================"
echo "4. DATABASE CONNECTION"
echo "================================================"

echo -n "Testing Database Connection... "
# If parkings endpoint works, database is connected
if curl -s "$BACKEND_URL/api/parking/all" | grep -q "\["; then
    echo -e "${GREEN}✓ PASSED${NC} (MongoDB connected)"
    ((TESTS_PASSED++))
else
    echo -e "${RED}✗ FAILED${NC} (Cannot fetch data from database)"
    ((TESTS_FAILED++))
fi

echo ""
echo "================================================"
echo "5. CORS CONFIGURATION"
echo "================================================"

echo -n "Testing CORS Headers... "
cors_response=$(curl -s -I -H "Origin: $FRONTEND_URL" "$BACKEND_URL/api/parking/all" | grep -i "access-control-allow-origin" || echo "")

if [ -n "$cors_response" ]; then
    echo -e "${GREEN}✓ PASSED${NC}"
    echo "  $cors_response"
    ((TESTS_PASSED++))
else
    echo -e "${YELLOW}⚠ WARNING${NC} (CORS headers not found - may cause frontend issues)"
fi

echo ""
echo "================================================"
echo "TEST SUMMARY"
echo "================================================"
echo -e "Tests Passed: ${GREEN}$TESTS_PASSED${NC}"
echo -e "Tests Failed: ${RED}$TESTS_FAILED${NC}"
echo ""

if [ $TESTS_FAILED -eq 0 ]; then
    echo -e "${GREEN}✓ All critical tests passed!${NC}"
    echo ""
    echo "Next steps:"
    echo "1. Test user registration: POST $BACKEND_URL/api/auth/register"
    echo "2. Test user login: POST $BACKEND_URL/api/auth/login"
    echo "3. Test booking creation with valid JWT token"
    echo "4. Verify email delivery (check spam folder)"
    echo "5. Test Google OAuth login from frontend"
    exit 0
else
    echo -e "${RED}✗ Some tests failed. Please check the logs above.${NC}"
    exit 1
fi
