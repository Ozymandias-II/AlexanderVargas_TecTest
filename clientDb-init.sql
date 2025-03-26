CREATE TABLE IF NOT EXISTS persons (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    age INTEGER NOT NULL,
    identification VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(200) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS clients (
    person_id INTEGER PRIMARY KEY REFERENCES persons(id),
    password VARCHAR(100) NOT NULL,
    status BOOLEAN NOT NULL
);

-- Datos iniciales para pruebas
INSERT INTO persons (id, name, gender, age, identification, address, phone)
VALUES 
(1, 'Jose Lema', 'Masculino', 35, '1234567890', 'Otavalo sn y principal', '098254785'),
(2, 'Marianela Montalvo', 'Femenino', 28, '0987654321', 'Amazonas y NNUU', '097548965'),
(3, 'Juan Osorio', 'Masculino', 40, '1122334455', '13 junio y Equinoccial', '098874587');

INSERT INTO clients (person_id, password, status)
VALUES 
(1, '1234', true),
(2, '5678', true),
(3, '1245', true);