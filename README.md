# Description :
In this project a block chain simulation for an EHR system was used.\
The EHR system contains doctors, patients and patients' visit.\
Both the Patient and the Patient visit information are decrypted using AES Symmetric key algorithm and the EHR system is the one owning the key.\
Patients can only view their information and cannot view any other patients information.\
Only Doctor's can view Patients and Patient visit information.\
## The process flow:
- There is always at least one doctor initially created with the EHR system.
- Admins can add new Doctors to the system.
- Doctors can add new patient to the system.
- A patient should be created with (Name, Age, Height, Weight, sex, initial Blood Pressure, Oxygen Level and GlucoseLevel).
- Once a patient is created we use toString function to generate a string describing the patient after which the string is encrypted and stored in the block chain.
- After a patient is created a doctor is able to add visit info to him/her.
- To create a patient visit the following attributes needs to be provided (the Patient index, patient's Pulse, Oxygen level, Blood pressure, Glucose level, Temperature, Diagnosis, The reason for the visit and prescription provided).
- Like the patient once the visit info is created we use the toString to get the String representing the visit and encrypting it and adding it to the block chain.
- Doctors can always retrieve any patient information as well as all the visits' information for this patient.
- To make the searching process faster the hash of each patient/ patient Visit is stored in a hashmap where the keys are strings and the entry is an array list of string hashes, the keys is a code (p_x) where p stands for patient and x is the patient's index or (v_x) where v stands for visit and c is the patient's index as we get all the visits for this patient.

## How the Blockchain work:
- The block chain is an array list of blocks.
- Each block contains information about the string content, the previous hash, its own hash and a nonce.
- To insert a new block the block first mines for the appropriate nonce that generates a hash with leading zeros equal to our prefix which is set to be 4 in this project.
- After generating the hash a validity checker algorithm runs on the entire block chain recalculating the hash to make sure the block chain was not corrupted (altered).

