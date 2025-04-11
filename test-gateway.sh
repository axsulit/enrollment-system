#!/bin/bash

# Test if the gateway is running
echo "Testing API Gateway..."

# Test 1: Check if the gateway is accessible
echo -e "\nTest 1: Checking gateway accessibility"
curl -I http://localhost:8080/actuator/health

# Test 2: Test route to auth service (should work without token)
echo -e "\nTest 2: Testing auth service route"
curl -I http://localhost:8080/api/auth/health

# Test 3: Test protected route without token (should fail)
echo -e "\nTest 3: Testing protected route without token"
curl -I http://localhost:8080/api/courses/health

# Test 4: Test protected route with invalid token (should fail)
echo -e "\nTest 4: Testing protected route with invalid token"
curl -I -H "Authorization: Bearer invalid-token" http://localhost:8080/api/courses/health 