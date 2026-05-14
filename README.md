# EHR Blockchain Simulation

## Overview

This project implements a blockchain-based Electronic Health Record (EHR) system with secure data storage and access control. The system integrates blockchain technology with AES encryption to protect sensitive patient information.

### Key Features

- **Secure Data Storage**: Patient and visit records are encrypted using AES Symmetric key algorithm
- **Access Control**: Patients can only view their own data; doctors can access patient records with appropriate permissions
- **Blockchain Integrity**: All records are stored on an immutable blockchain with proof-of-work validation
- **Fast Retrieval**: Optimized search using hash-based indexing for quick data access

## System Architecture

### Core Components

- **Doctors**: Create and manage patient records, add visit information
- **Patients**: Store health data encrypted on the blockchain
- **Patient Visits**: Track medical appointments and clinical observations
- **Admin**: Manage doctors in the system

### Data Encryption

Both patient records and visit information are encrypted using AES algorithms where the EHR system maintains the encryption keys.

## Process Flow

### User Management
- The system initializes with at least one predefined doctor
- Admins can add new doctors to the system
- Doctors can register and manage patients

### Patient Registration
When creating a patient record, the following information is collected:
- Name, Age, Height, Weight, Sex
- Initial vital signs (Blood Pressure, Oxygen Level, Glucose Level)

The patient data is converted to a string representation, encrypted, and stored on the blockchain.

### Patient Visits
Doctors can record patient visit information including:
- Patient index reference
- Vital signs (Pulse, Oxygen Level, Blood Pressure, Glucose Level, Temperature)
- Diagnosis and reason for visit
- Prescribed treatment

Visit data follows the same encryption and blockchain storage process as patient records.

### Data Retrieval
- Doctors can access any patient's complete medical history
- Records are indexed using a hash map with keys formatted as:
  - `p_x`: Patient record (where x is the patient index)
  - `v_x`: Visit records (where x is the patient index)

## Blockchain Implementation

### Block Structure

Each block contains:
- Encrypted content data
- Previous block hash
- Current block hash
- Nonce value for proof-of-work

### Mining Process

1. The system calculates an appropriate nonce value
2. Generates a hash with a configurable number of leading zeros (set to 4 in this implementation)
3. Stores the nonce and hash in the block

### Validation

After each block insertion, a validity checker:
- Recalculates hashes for the entire blockchain
- Verifies chain integrity to detect any corruption or unauthorized modifications

