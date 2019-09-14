CREATE TABLE IF NOT EXISTS PRODUIT_IMMOBILIER
(
    ID integer NOT NULL,
    TYPE character varying(100) COLLATE pg_catalog."default",
    TELEPHONE character varying(50) COLLATE pg_catalog."default",
    NOMBRE_LOTS smallint,
    PARKING boolean,
    CAVE boolean,
    ASCENCEUR boolean,
    DPE "char",
    CHARGES_COPROP numeric,
    TAXES_FONCIAIRES character varying COLLATE pg_catalog."default",
    ANNEE_CONSTRUCTION date,
    ADRESSE character varying(250) COLLATE pg_catalog."default",
    PRIX numeric,
    CONSTRAINT "PRODUIT_IMMOBILIER_pkey" PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE PRODUIT_IMMOBILIER
    OWNER to postgres;

commit;

INSERT INTO PRODUIT_IMMOBILIER (ID ,  TYPE, TELEPHONE, NOMBRE_LOTS, PARKING, CAVE ,ASCENCEUR , DPE , CHARGES_COPROP , TAXES_FONCIAIRES, ANNEE_CONSTRUCTION ,ADRESSE ,PRIX)
VALUES (1, 'APPARTEMENT NEUF', '065646847',3,TRUE, TRUE,TRUE,'A',4300,'11%','01-01-2020', 'VILLEJUIF', 250000)
ON CONFLICT (ID) DO NOTHING;
