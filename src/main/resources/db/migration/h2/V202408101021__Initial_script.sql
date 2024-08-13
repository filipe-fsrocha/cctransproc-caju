-- ## Create tables ##
CREATE TABLE card (
    id UUID PRIMARY KEY NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    expiration_date VARCHAR(7) NOT NULL,
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

CREATE TABLE transactions (
    id UUID PRIMARY KEY NOT NULL,
    account_id UUID NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE mcc (
    id UUID PRIMARY KEY NOT NULL,
    mcc INTEGER NOT NULL,
    description VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE merchant (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    mcc_id UUID NOT NULL,
    CONSTRAINT fk_mcc FOREIGN KEY (mcc_id) REFERENCES mcc(id)
);

-- ## Initial load ##

-- CARD
INSERT INTO card (id, card_number, expiration_date, ccv, password)
    VALUES ('bb24c923-bc0e-4caa-86a4-c33f464371f9', '5513 1212 6431 3829', '2024-08', 610, 'MTIzNA==');

-- ACCOUNT
INSERT INTO account(id, card_id, account_type, total_amount)
    VALUES('bb24c923-bc0e-4caa-86a4-c33f464371f9', 'bb24c923-bc0e-4caa-86a4-c33f464371f9', 'FOOD', 100),
          ('bb24c923-bc0e-4caa-86a4-c33f464371f8', 'bb24c923-bc0e-4caa-86a4-c33f464371f9', 'MEAL', 100),
          ('bb24c923-bc0e-4caa-86a4-c33f464371f7', 'bb24c923-bc0e-4caa-86a4-c33f464371f9', 'CASH', 200);

-- MCC
INSERT INTO mcc(id, mcc, description, active)
    VALUES ('f5ddc13b-3fc6-40ca-909e-354d33089a29',5411,'MERCEARIAS/SUPERMERCADOS (GROCERY STORES/SUPERM.)', true),
           ('89665adf-ff1f-44cc-afab-5f0d52ed4208',5412, 'LOJAS DE ALIMENTOS', true),
           ('4added58-2da4-4128-90a4-e68a829c0d4c', 5811, 'DISTRIBUIÇÃO E PRODUÇÃO DE ALIMENTOS', true),
           ('c19151fa-560d-4c72-a1f2-288156a160e1', 5812, 'RESTAURANTES', true),
           ('c19151fa-560d-4c72-a1f2-288156a160e2', 4000, 'SERVIÇOS DE TRANSPORTE', true);

-- MERCHANT
INSERT INTO merchant(id, name, created_at, mcc_id)
    VALUES ('bb24c923-bc0e-4caa-86a4-c33f464371f8', 'UBER EATS                  SAO PAULO BR', now(), 'c19151fa-560d-4c72-a1f2-288156a160e1'),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f5', 'PAG*JoseDaSilva         RIO DE JANEI BR', now(), 'f5ddc13b-3fc6-40ca-909e-354d33089a29'),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f6', 'UBER TRIP                  SAO PAULO BR', now(), 'c19151fa-560d-4c72-a1f2-288156a160e2');