CREATE TABLE raw_inputs (
    id TEXT NOT NULL PRIMARY KEY,
    type TEXT NOT NULL CHECK (type IN ('TEXT', 'IMAGE')),
    text_content TEXT,
    image_path TEXT,
    created_at TEXT NOT NULL,
    processing_status TEXT NOT NULL CHECK (processing_status IN ('PENDING', 'PROCESSING', 'PROCESSED', 'FAILED')),
    CHECK (
        (type = 'TEXT' AND text_content IS NOT NULL AND image_path IS NULL)
        OR (type = 'IMAGE' AND text_content IS NULL AND image_path IS NOT NULL)
    )
);

CREATE INDEX idx_raw_inputs_created_at ON raw_inputs (created_at);
CREATE INDEX idx_raw_inputs_processing_status ON raw_inputs (processing_status);
