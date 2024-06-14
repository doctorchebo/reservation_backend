ALTER TABLE business ADD COLUMN owner_id BIGINT;

ALTER TABLE business
    ADD CONSTRAINT FK_BUSINESS_ON_USER FOREIGN KEY (owner_id) REFERENCES public."user" (id);