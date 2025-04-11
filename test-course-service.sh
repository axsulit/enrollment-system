#!/bin/bash

# Base URL for the course service
BASE_URL="http://localhost:8082/api/courses"

# Function to print a separator
print_separator() {
    echo "----------------------------------------"
}

# Function to print a header
print_header() {
    echo
    print_separator
    echo "$1"
    print_separator
}

# Test 1: Create a new course
print_header "Test 1: Create a new course"
curl -X POST "$BASE_URL" \
     -H "Content-Type: application/json" \
     -d '{
           "courseCode": "CS101",
           "courseName": "Introduction to Computer Science",
           "scheduleDays": ["MONDAY", "WEDNESDAY"],
           "startTime": "09:00:00",
           "endTime": "10:30:00",
           "maxEnrollees": 30,
           "modeOfLearning": "F2F",
           "professorName": "Dr. Smith"
         }'

# Test 2: Create another course
print_header "Test 2: Create another course"
curl -X POST "$BASE_URL" \
     -H "Content-Type: application/json" \
     -d '{
           "courseCode": "CS102",
           "courseName": "Data Structures",
           "scheduleDays": ["TUESDAY", "THURSDAY"],
           "startTime": "13:00:00",
           "endTime": "14:30:00",
           "maxEnrollees": 25,
           "modeOfLearning": "HYB",
           "professorName": "Dr. Johnson"
         }'

# Test 3: Get all courses
print_header "Test 3: Get all courses"
curl -X GET "$BASE_URL"

# Test 4: Get course by ID (replace {id} with actual ID from previous response)
print_header "Test 4: Get course by ID"
curl -X GET "$BASE_URL/1"

# Test 5: Get course by code
print_header "Test 5: Get course by code"
curl -X GET "$BASE_URL/code/CS101"

# Test 6: Update a course
print_header "Test 6: Update a course"
curl -X PUT "$BASE_URL/1" \
     -H "Content-Type: application/json" \
     -d '{
           "courseCode": "CS101",
           "courseName": "Introduction to Computer Science (Updated)",
           "scheduleDays": ["MONDAY", "WEDNESDAY", "FRIDAY"],
           "startTime": "10:00:00",
           "endTime": "11:30:00",
           "maxEnrollees": 35,
           "modeOfLearning": "F2F",
           "professorName": "Dr. Smith"
         }'

# Test 7: Enroll in a course (increment enrollees)
print_header "Test 7: Enroll in a course"
curl -X POST "$BASE_URL/1/enroll"

# Test 8: Drop a course (decrement enrollees)
print_header "Test 8: Drop a course"
curl -X POST "$BASE_URL/1/drop"

# Test 9: Try to create a course with duplicate code
print_header "Test 9: Try to create a course with duplicate code"
curl -X POST "$BASE_URL" \
     -H "Content-Type: application/json" \
     -d '{
           "courseCode": "CS101",
           "courseName": "Duplicate Course",
           "scheduleDays": ["MONDAY", "WEDNESDAY"],
           "startTime": "09:00:00",
           "endTime": "10:30:00",
           "maxEnrollees": 30,
           "modeOfLearning": "F2F",
           "professorName": "Dr. Smith"
         }'

# Test 10: Delete a course
print_header "Test 10: Delete a course"
curl -X DELETE "$BASE_URL/2"

# Test 11: Get all courses after deletion
print_header "Test 11: Get all courses after deletion"
curl -X GET "$BASE_URL" 