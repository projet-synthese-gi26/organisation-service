-- ==============================================================================
-- 0. NETTOYAGE (DROP) - À RETIRER UNE FOIS EN PRODUCTION STABLE
-- ==============================================================================

-- Tables de jointure et relations
DROP TABLE IF EXISTS agency_domain CASCADE;
DROP TABLE IF EXISTS organization_domain CASCADE;
DROP TABLE IF EXISTS agency_affiliation CASCADE;
DROP TABLE IF EXISTS organization_actor CASCADE;

-- Tables transverses (enfants)
DROP TABLE IF EXISTS interaction CASCADE;
DROP TABLE IF EXISTS contact CASCADE;
DROP TABLE IF EXISTS address CASCADE;

-- Référentiels et Activités
DROP TABLE IF EXISTS certification CASCADE;
DROP TABLE IF EXISTS proposed_activity CASCADE;
DROP TABLE IF EXISTS business_domain CASCADE;

-- Tiers
DROP TABLE IF EXISTS third_party CASCADE;

-- Acteurs (Héritage)
DROP TABLE IF EXISTS sales_person CASCADE;
DROP TABLE IF EXISTS provider CASCADE;
DROP TABLE IF EXISTS prospect CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS business_actor CASCADE;
DROP TABLE IF EXISTS employee CASCADE;

-- Structure Core (Parents)
DROP TABLE IF EXISTS agency CASCADE;
DROP TABLE IF EXISTS organization CASCADE;

-- Extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. ENTITES DE BASE ET STRUCTURE
CREATE TABLE IF NOT EXISTS organization (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(255) UNIQUE,
    service VARCHAR(255),
    business_actor_id UUID,
    is_individual_business BOOLEAN,
    email VARCHAR(255),
    short_name VARCHAR(255),
    long_name VARCHAR(255),
    description TEXT,
    logo_uri VARCHAR(255),
    logo_id UUID,
    website_url VARCHAR(255),
    social_network TEXT,
    business_registration_number VARCHAR(255),
    tax_number VARCHAR(255),
    capital_share NUMERIC,
    ceo_name VARCHAR(255),
    year_founded INTEGER,
    keywords TEXT[],
    number_of_employees INTEGER,
    legal_form VARCHAR(255),
    is_active BOOLEAN,
    status VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS agency (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(255) UNIQUE,
    organization_id UUID,
    owner_id UUID,
    manager_id UUID,
    name VARCHAR(255),
    location VARCHAR(255),
    description TEXT,
    transferable BOOLEAN,
    is_active BOOLEAN,
    logo_uri VARCHAR(255),
    logo_id UUID,
    short_name VARCHAR(255),
    long_name VARCHAR(255),
    is_individual_business BOOLEAN,
    is_headquarter BOOLEAN,
    country VARCHAR(255),
    city VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    open_time VARCHAR(50),
    close_time VARCHAR(50),
    phone VARCHAR(50),
    email VARCHAR(255),
    whatsapp VARCHAR(50),
    greeting_message TEXT,
    average_revenue NUMERIC,
    capital_share NUMERIC,
    social_network TEXT,
    tax_number VARCHAR(255),
    registration_number VARCHAR(255),
    keywords TEXT[],
    is_public BOOLEAN,
    is_business BOOLEAN,
    total_affiliated_customers INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

-- 2. ACTEURS (Stratégie Table-per-class selon tes entités)

CREATE TABLE IF NOT EXISTS employee (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    auth_user_id UUID,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(50),
    email VARCHAR(255),
    description TEXT,
    role VARCHAR(255),
    gender VARCHAR(50),
    photo_uri VARCHAR(255),
    photo_id UUID,
    nationality VARCHAR(255),
    birth_date DATE,
    profession VARCHAR(255),
    biography TEXT,
    code VARCHAR(255),
    is_manager BOOLEAN,
    department VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS business_actor (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID,
    auth_user_id UUID,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(50),
    email VARCHAR(255),
    description TEXT,
    role VARCHAR(255),
    code VARCHAR(255),
    is_individual BOOLEAN,
    is_available BOOLEAN,
    is_verified BOOLEAN,
    is_active BOOLEAN,
    type VARCHAR(255),
    qualifications TEXT[],
    payment_methods TEXT[],
    is_business BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS customer (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    auth_user_id UUID,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(50),
    email VARCHAR(255),
    code VARCHAR(255),
    is_individual BOOLEAN,
    is_verified BOOLEAN,
    is_active BOOLEAN,
    payment_method VARCHAR(255),
    amount_paid NUMERIC,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS prospect (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(50),
    email VARCHAR(255),
    code VARCHAR(255),
    transaction_id UUID,
    payment_method VARCHAR(255),
    amount_paid NUMERIC,
    interest_level VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS provider (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(50),
    email VARCHAR(255),
    code VARCHAR(255),
    contact_info TEXT,
    address TEXT,
    is_active BOOLEAN,
    product_service_type VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS sales_person (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(50),
    email VARCHAR(255),
    code VARCHAR(255),
    commission_rate DOUBLE PRECISION,
    current_balance DOUBLE PRECISION,
    credit DOUBLE PRECISION,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

-- 3. TIERS ET PARTENAIRES (B2B)

CREATE TABLE IF NOT EXISTS third_party (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(255),
    organization_id UUID,
    type VARCHAR(50),
    legal_form VARCHAR(255),
    unique_identification_number VARCHAR(255),
    trade_registration_number VARCHAR(255),
    name VARCHAR(255),
    acronym VARCHAR(50),
    long_name VARCHAR(255),
    logo_uri VARCHAR(255),
    logo_id UUID,
    accounting_account_numbers TEXT[],
    authorized_payment_methods TEXT[],
    authorized_credit_limit NUMERIC,
    max_discount_rate NUMERIC,
    vat_subject BOOLEAN,
    operations_balance NUMERIC,
    opening_balance NUMERIC,
    pay_term_number INTEGER,
    pay_term_type VARCHAR(50),
    third_party_family VARCHAR(255),
    classification TEXT, -- JSON stocké en String ou type JSONB selon Postgres
    tax_number VARCHAR(255),
    loyalty_points INTEGER,
    loyalty_points_used INTEGER,
    loyalty_points_expired INTEGER,
    enabled BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

-- 4. DOMAINES ET ACTIVITÉS

CREATE TABLE IF NOT EXISTS business_domain (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    code VARCHAR(255),
    service VARCHAR(255),
    parent_id UUID,
    name VARCHAR(255),
    image_uri VARCHAR(255),
    image_id UUID,
    type VARCHAR(255),
    type_label VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS proposed_activity (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    type VARCHAR(255),
    name VARCHAR(255),
    rate NUMERIC,
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS certification (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    type VARCHAR(255),
    name VARCHAR(255),
    description TEXT,
    obtainement_date TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

-- 5. TRANSVERSE (ADRESSES, CONTACTS, INTERACTIONS)

CREATE TABLE IF NOT EXISTS address (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    addressable_id UUID,
    addressable_type VARCHAR(255),
    type VARCHAR(50),
    address_line_1 VARCHAR(255),
    address_line_2 VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    locality VARCHAR(255),
    zip_code VARCHAR(20),
    country_id UUID,
    po_box VARCHAR(50),
    neighbor_hood VARCHAR(255),
    informal_description TEXT,
    is_default BOOLEAN,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS contact (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    contactable_id UUID,
    contactable_type VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    title VARCHAR(50),
    email VARCHAR(255),
    phone_number VARCHAR(50),
    secondary_phone_number VARCHAR(50),
    fax_number VARCHAR(50),
    secondary_email VARCHAR(255),
    is_email_verified BOOLEAN,
    is_phone_number_verified BOOLEAN,
    is_favorite BOOLEAN,
    email_verified_at TIMESTAMP,
    phone_verified_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS interaction (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    interaction_id UUID,
    prospect_id UUID,
    interaction_date TIMESTAMP,
    notes TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

-- 6. TABLES DE JOINTURE

CREATE TABLE IF NOT EXISTS organization_actor (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    actor_id UUID,
    type VARCHAR(50),
    is_active BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS agency_affiliation (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    agency_id UUID,
    actor_id UUID,
    type VARCHAR(50),
    is_active BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS organization_domain (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    domain_id UUID,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS agency_domain (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    organization_id UUID,
    agency_id UUID,
    domain_id UUID,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    version BIGINT
);


-- INDEX DE PERFORMANCE (CRITIQUE)

-- Pour OrganizationService
CREATE INDEX IF NOT EXISTS idx_agency_org_id ON agency(organization_id);

-- Pour EmployeeService / RH
CREATE INDEX IF NOT EXISTS idx_employee_org_id ON employee(organization_id);
CREATE INDEX IF NOT EXISTS idx_employee_auth_user_id ON employee(auth_user_id);
CREATE INDEX IF NOT EXISTS idx_business_actor_org_id ON business_actor(organization_id);
CREATE INDEX IF NOT EXISTS idx_business_actor_auth_user_id ON business_actor(auth_user_id);

-- Pour CRM / Tiers
CREATE INDEX IF NOT EXISTS idx_customer_org_id ON customer(organization_id);
CREATE INDEX IF NOT EXISTS idx_prospect_org_id ON prospect(organization_id);
CREATE INDEX IF NOT EXISTS idx_third_party_org_id ON third_party(organization_id);
CREATE INDEX IF NOT EXISTS idx_interaction_prospect_id ON interaction(prospect_id);

-- Pour les domaines et activités
CREATE INDEX IF NOT EXISTS idx_business_domain_parent_id ON business_domain(parent_id);
CREATE INDEX IF NOT EXISTS idx_proposed_activity_org_id ON proposed_activity(organization_id);
CREATE INDEX IF NOT EXISTS idx_certification_org_id ON certification(organization_id);

-- Pour les tables polymorphiques (Address / Contact)
CREATE INDEX IF NOT EXISTS idx_address_poly ON address(addressable_id, addressable_type);
CREATE INDEX IF NOT EXISTS idx_contact_poly ON contact(contactable_id, contactable_type);

-- Pour les tables de jointure
CREATE INDEX IF NOT EXISTS idx_org_actor_ids ON organization_actor(organization_id, actor_id);
CREATE INDEX IF NOT EXISTS idx_agency_aff_ids ON agency_affiliation(agency_id, actor_id);


-- ==============================================================================
-- 7. CONTRAINTES D'INTÉGRITÉ (FOREIGN KEYS)
-- À exécuter pour garantir la cohérence des données
-- ==============================================================================

-- 1. Liens vers ORGANIZATION
-- Si on supprime une org, on veut souvent empêcher la suppression s'il reste des données (RESTRICT)
-- ou tout supprimer en cascade (CASCADE). Ici, on sécurise par défaut (RESTRICT ou NO ACTION).

ALTER TABLE agency DROP CONSTRAINT IF EXISTS fk_agency_org;
ALTER TABLE agency ADD CONSTRAINT fk_agency_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE employee DROP CONSTRAINT IF EXISTS fk_employee_org;
ALTER TABLE employee ADD CONSTRAINT fk_employee_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE business_actor DROP CONSTRAINT IF EXISTS fk_business_actor_org;
ALTER TABLE business_actor ADD CONSTRAINT fk_business_actor_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE customer DROP CONSTRAINT IF EXISTS fk_customer_org;
ALTER TABLE customer ADD CONSTRAINT fk_customer_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE prospect DROP CONSTRAINT IF EXISTS fk_prospect_org;
ALTER TABLE prospect ADD CONSTRAINT fk_prospect_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE provider DROP CONSTRAINT IF EXISTS fk_provider_org;
ALTER TABLE provider ADD CONSTRAINT fk_provider_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE sales_person DROP CONSTRAINT IF EXISTS fk_sales_person_org;
ALTER TABLE sales_person ADD CONSTRAINT fk_sales_person_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE third_party DROP CONSTRAINT IF EXISTS fk_third_party_org;
ALTER TABLE third_party ADD CONSTRAINT fk_third_party_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE proposed_activity DROP CONSTRAINT IF EXISTS fk_activity_org;
ALTER TABLE proposed_activity ADD CONSTRAINT fk_activity_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE certification DROP CONSTRAINT IF EXISTS fk_certification_org;
ALTER TABLE certification ADD CONSTRAINT fk_certification_org FOREIGN KEY (organization_id) REFERENCES organization(id);

-- 2. CRM & Interactions

ALTER TABLE interaction DROP CONSTRAINT IF EXISTS fk_interaction_prospect;
ALTER TABLE interaction ADD CONSTRAINT fk_interaction_prospect FOREIGN KEY (prospect_id) REFERENCES prospect(id);

-- 3. Domaines & Jointures

ALTER TABLE business_domain DROP CONSTRAINT IF EXISTS fk_domain_parent;
ALTER TABLE business_domain ADD CONSTRAINT fk_domain_parent FOREIGN KEY (parent_id) REFERENCES business_domain(id);

ALTER TABLE organization_domain DROP CONSTRAINT IF EXISTS fk_org_domain_org;
ALTER TABLE organization_domain ADD CONSTRAINT fk_org_domain_org FOREIGN KEY (organization_id) REFERENCES organization(id);

ALTER TABLE organization_domain DROP CONSTRAINT IF EXISTS fk_org_domain_dom;
ALTER TABLE organization_domain ADD CONSTRAINT fk_org_domain_dom FOREIGN KEY (domain_id) REFERENCES business_domain(id);

ALTER TABLE agency_domain DROP CONSTRAINT IF EXISTS fk_agency_domain_ag;
ALTER TABLE agency_domain ADD CONSTRAINT fk_agency_domain_ag FOREIGN KEY (agency_id) REFERENCES agency(id);

ALTER TABLE agency_domain DROP CONSTRAINT IF EXISTS fk_agency_domain_dom;
ALTER TABLE agency_domain ADD CONSTRAINT fk_agency_domain_dom FOREIGN KEY (domain_id) REFERENCES business_domain(id);

-- Note sur les Tables Polymorphiques (Address, Contact, OrganizationActor...) :
-- On NE PEUT PAS mettre de FK sur addressable_id car cela pointe vers plusieurs tables possibles.
-- L'intégrité de ces tables repose sur la logique applicative (Services Java).