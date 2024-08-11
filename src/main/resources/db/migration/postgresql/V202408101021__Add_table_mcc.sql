CREATE TABLE mcc (
    id UUID PRIMARY KEY NOT NULL,
    mcc INTEGER NOT NULL,
    description VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);

-- Carga inicial
INSERT INTO mcc(id, mcc, description, active)
    VALUES ('f5ddc13b-3fc6-40ca-909e-354d33089a29',5411,'MERCEARIAS/SUPERMERCADOS (GROCERY STORES/SUPERM.)', true),
           ('89665adf-ff1f-44cc-afab-5f0d52ed4208',5412, 'LOJAS DE ALIMENTOS', true),
           ('4added58-2da4-4128-90a4-e68a829c0d4c', 5811, 'DISTRIBUIÇÃO E PRODUÇÃO DE ALIMENTOS', true),
           ('c19151fa-560d-4c72-a1f2-288156a160e1', 5812, 'RESTAURANTES', true),
           ('bb24c923-bc0e-4caa-86a4-c33f464371f9', 5732, 'LOJA DE ELETRÔNICOS', true);
