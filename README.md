# Customer Update Spring Batch Job

This Spring Batch job is designed to process customer information updates using Spring Cloud Task and Spring Batch.

## Table of Contents

- [Introduction](#introduction)
- [Configuration Details](#configuration-details)
- [Usage](#usage)
  - [Running Locally](#running-locally)
  - [Using in Spring Data Flow](#using-in-spring-data-flow)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The `CustomerConfiguration` class contains the configuration for the Spring Batch job that updates customer information. It reads user data, processes it, and writes it back to the database.

## Configuration Details

The configuration includes:

- Job setup with a single step (`CustomerProcessing`) that processes users to generate customer records.
- `jsonItemReader()` bean for reading JSON data and converting it to `User` objects.
- `jdbcBillWriter()` bean for writing `Customer` records to the database.
- `customerProcessor()` bean that defines the processing logic in the `CustomerProcessor` class.

## Usage

### Running Locally

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/sipankaj/customer-update.git
   cd customer-update
2. **Build the Application**:

  ```bash
  Copy code
  mvn clean package

3. **Run the Batch Job**:

  ```bash
  Copy code
  java -jar target/customer-update-<version>.jar
  Replace <version> with the actual version number.

### Using in Spring Data Flow
1. **Build and Publish to Local Maven Repository**:

  - Ensure Maven is installed on your system.

  - Navigate to the root directory of the project and run:

  ```bash
  Copy code
  mvn clean install
2. **Add Maven Coordinates to Spring Data Flow Server**:

In your Spring Data Flow server, add the Maven coordinates of the customer-update job to your application registry.

Example Maven Coordinates:

Group ID: com.example
Artifact ID: customer-update
Version: 1.0.0-SNAPSHOT
Create and Launch Task in Spring Data Flow Dashboard:

Open the Spring Data Flow Dashboard.
Create a new task definition that uses the customer-update job.
Launch the task with appropriate parameters.
Contributing
Contributions are welcome! Please follow the guidelines outlined in CONTRIBUTING.md.

License
This project is licensed under the MIT License.

