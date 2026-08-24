USE sunrise_dental_clinic;

INSERT INTO dentists
    (dentist_name, specialization, contact_number)
VALUES
    ('Dr. Nimal Perera', 'General Dentistry', '0712345678'),
    ('Dr. Sarah Fernando', 'Orthodontics', '0723456789'),
    ('Dr. Kavindu Silva', 'Oral Surgery', '0774567890'),
    ('Dr. Amanda Jayasinghe', 'Periodontics', '0755678901');
    
    
INSERT INTO treatments
    (treatment_name, treatment_cost)
VALUES
    ('Dental Cleaning', 5000.00),
    ('Dental Filling', 7500.00),
    ('Tooth Extraction', 10000.00),
    ('Root Canal Treatment', 25000.00),
    ('Dental Crown', 30000.00),
    ('Teeth Whitening', 20000.00);

    
INSERT INTO patients
    (patient_name, address, contact_number)
VALUES
    ('Amal Perera', '45 Galle Road, Colombo 03', '0771234567'),
    ('Kavya Fernando', '18 Duplication Road, Colombo 04', '0719876543'),
    ('Ravindu Silva', '27 High Level Road, Colombo 05', '0764567890'),
    ('Nethmi Jayasinghe', '12 Flower Road, Colombo 07', '0753456789'),
    ('Daniel Thomas', '31 Park Road, Colombo 05', '0726789012');
    
    
INSERT INTO appointments
    (appointment_number, patient_id, dentist_id, treatment_id,
     appointment_date, appointment_time, status)
VALUES
    ('APT-2026-0001', 1, 1, 1, '2026-09-01', '09:00:00', 'SCHEDULED'),

    ('APT-2026-0002', 2, 2, 2, '2026-09-01', '10:00:00', 'SCHEDULED'),

    ('APT-2026-0003', 3, 3, 3, '2026-09-01', '11:00:00', 'SCHEDULED'),

    ('APT-2026-0004', 4, 1, 4, '2026-09-02', '09:30:00', 'SCHEDULED'),

    ('APT-2026-0005', 5, 4, 6, '2026-09-02', '10:30:00', 'SCHEDULED');
    
    
INSERT INTO bills
    (appointment_id, consultation_fee, treatment_cost, total_amount)
VALUES
    (1, 2000.00, 5000.00, 7000.00),
    (2, 2000.00, 7500.00, 9500.00),
    (3, 2000.00, 10000.00, 12000.00);