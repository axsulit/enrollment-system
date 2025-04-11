#!/bin/bash

echo "Testing Auth Service..."

# Test 1: Check if auth service is accessible
echo -e "\nTest 1: Checking auth service health"
curl -I http://localhost:8081/actuator/health

# Test 2: Try to login with admin credentials
echo -e "\nTest 2: Testing login with admin credentials"
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Test 3: Try to login with invalid credentials
echo -e "\nTest 3: Testing login with invalid credentials"
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"wrongpassword"}'

# Test signup endpoint
echo "Testing signup endpoint..."
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "teststudent",
    "email": "teststudent@example.com",
    "password": "test123",
    "firstName": "Test",
    "lastName": "Student",
    "roles": ["STUDENT"]
  }'

echo -e "\n\nTesting login endpoint..."
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "teststudent",
    "password": "test123"
  }'

echo -e "\n\nTesting admin login..."
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }' 