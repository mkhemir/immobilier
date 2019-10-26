DROP TABLE IF EXISTS PRODUIT_IMMOBILIER;

CREATE TABLE IF NOT EXISTS PRODUIT_IMMOBILIER
(
    ID SERIAL NOT NULL ,
    TYPE character varying(100) COLLATE pg_catalog."default",
    TELEPHONE character varying(50) COLLATE pg_catalog."default",
    NOMBRE_LOTS smallint,
    PARKING boolean,
    ASCENCEUR boolean,
    DPE "char",
    CHARGES_COPROP numeric,
    TAXES_FONCIAIRES character varying COLLATE pg_catalog."default",
    ANNEE_CONSTRUCTION date,
    ADRESSE character varying(250) COLLATE pg_catalog."default",
    PRIX numeric,
    SURFACE double precision,
    SURFACE_BALCON double precision,
    SURFACE_TERRASSE double precision,
    SURFACE_VERANDAS double precision,
    SURFACE_SOUS_SOL double precision,
    SURFACE_CAVE double precision,
    SURFACE_LOGIAS double precision,
    AUTRE_SURFACE_ANNEXE double precision,
    CONSTRAINT "PRODUIT_IMMOBILIER_pkey" PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE PRODUIT_IMMOBILIER
    OWNER to postgres;

commit;


