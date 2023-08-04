ALTER TABLE orphanages
    ALTER COLUMN latitude TYPE NUMERIC(9, 6) using latitude::FLOAT,
    ALTER COLUMN longitude TYPE NUMERIC(9, 6) using longitude::FLOAT;