-- Add NOT NULL constraints to audit columns
ALTER TABLE book
ALTER COLUMN created_date SET NOT NULL,
ALTER COLUMN last_modified_date SET NOT NULL,
ALTER COLUMN version SET NOT NULL;

ALTER TABLE category
ALTER COLUMN created_date SET NOT NULL,
ALTER COLUMN last_modified_date SET NOT NULL;

ALTER TABLE review
ALTER COLUMN created_date SET NOT NULL,
ALTER COLUMN last_modified_date SET NOT NULL;

-- Add default values for audit columns
ALTER TABLE book
ALTER COLUMN created_date SET DEFAULT CURRENT_TIMESTAMP,
ALTER COLUMN last_modified_date SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE category
ALTER COLUMN created_date SET DEFAULT CURRENT_TIMESTAMP,
ALTER COLUMN last_modified_date SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE review
ALTER COLUMN created_date SET DEFAULT CURRENT_TIMESTAMP,
ALTER COLUMN last_modified_date SET DEFAULT CURRENT_TIMESTAMP;

-- Add check constraints for data integrity
ALTER TABLE book
ADD CONSTRAINT chk_book_price_positive CHECK (price > 0),
ADD CONSTRAINT chk_book_version_non_negative CHECK (version >= 0);

ALTER TABLE review
ADD CONSTRAINT chk_review_rating_range CHECK (rating BETWEEN 1 AND 5);

-- Add index for better review query performance
CREATE INDEX idx_review_created_date ON review(created_date);
CREATE INDEX idx_book_created_date ON book(created_date);