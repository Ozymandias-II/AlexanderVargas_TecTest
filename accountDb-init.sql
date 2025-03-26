CREATE TABLE IF NOT EXISTS accounts (
    account_id SERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(20) NOT NULL,
    initial_balance DECIMAL(15, 2) NOT NULL,
    status BOOLEAN NOT NULL,
    client_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_type VARCHAR(20) NOT NULL,
    value DECIMAL(15, 2) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    account_id BIGINT NOT NULL REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- Datos iniciales para pruebas
INSERT INTO accounts (account_number, account_type, initial_balance, status, client_id)
VALUES 
('478758', 'Ahorro', 2000.00, true, 1),
('225487', 'Corriente', 100.00, true, 2),
('495878', 'Ahorros', 0.00, true, 3),
('496825', 'Ahorros', 540.00, true, 2),
('585545', 'Corriente', 1000.00, true, 1);