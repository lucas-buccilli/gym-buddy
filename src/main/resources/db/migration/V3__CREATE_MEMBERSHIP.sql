CREATE TABLE membership (
    id int AUTO_INCREMENT primary key NOT NULL,
    status BIT NOT NULL,
    member_id INT NOT NULL,
    FOREIGN KEY (member_id)
        REFERENCES member(id)
        ON DELETE CASCADE
)