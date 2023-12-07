CREATE TABLE membership_history (
    id int AUTO_INCREMENT primary key NOT NULL,
    date varchar(50) NOT NULL,
    operation varchar(1),
    membership_id int NOT NULL,
    FOREIGN KEY (membership_id)
    REFERENCES membership(id)
)