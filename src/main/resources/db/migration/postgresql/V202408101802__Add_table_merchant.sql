CREATE TABLE merchant (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE merchant_mcc (
    merchant_id UUID NOT NULL,
    mcc_id UUID NOT NULL,
    PRIMARY KEY (merchant_id, mcc_id),
    FOREIGN KEY (merchant_id) REFERENCES merchant(id),
    FOREIGN KEY (mcc_id) REFERENCES mcc(id)
);

-- Carga inicial
INSERT INTO merchant(id, name, created_at)
    VALUES ('bb24c923-bc0e-4caa-86a4-c33f464371f9', 'EXTRABOM SUPER         MINAS GERAIS BR', now()),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f8', 'PADARIA PAO/OURO       MINAS GERAIS BR', now()),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f5', 'WEBTECH ELETRONICOS    MINAS GERAIS BR', now());

INSERT INTO merchant_mcc (merchant_id, mcc_id)
    VALUES ('bb24c923-bc0e-4caa-86a4-c33f464371f9', 'f5ddc13b-3fc6-40ca-909e-354d33089a29'),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f9', '89665adf-ff1f-44cc-afab-5f0d52ed4208'),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f9', 'c19151fa-560d-4c72-a1f2-288156a160e1'),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f8', '4added58-2da4-4128-90a4-e68a829c0d4c'),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f8', 'c19151fa-560d-4c72-a1f2-288156a160e1'),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f5', 'bb24c923-bc0e-4caa-86a4-c33f464371f9');