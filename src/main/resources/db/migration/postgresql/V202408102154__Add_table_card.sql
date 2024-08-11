CREATE TABLE card (
    id UUID PRIMARY KEY NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    expiration_date DATE NOT NULL,
    ccv INT NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE account (
    id UUID PRIMARY KEY NOT NULL,
    card_id UUID NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (card_id) REFERENCES card(id)
);

-- Carga inicial
INSERT INTO card (id, card_number, expiration_date, ccv, password)
    VALUES ('bb24c923-bc0e-4caa-86a4-c33f464371f9', '5513 1212 6431 3829', '2024-08-30', 610, 'MTIzNA==');

INSERT INTO account(id, card_id, account_type, total_amount)
    VALUES('bb24c923-bc0e-4caa-86a4-c33f464371f9', 'bb24c923-bc0e-4caa-86a4-c33f464371f9', 'FOOD', 500),
          ('bb24c923-bc0e-4caa-86a4-c33f464371f8', 'bb24c923-bc0e-4caa-86a4-c33f464371f9', 'MEAL', 300),
          ('bb24c923-bc0e-4caa-86a4-c33f464371f7', 'bb24c923-bc0e-4caa-86a4-c33f464371f9', 'CASH', 200);
