ALTER TABLE orphanage_images ALTER COLUMN mime_type TYPE VARCHAR(30) USING mime_type::VARCHAR(30);