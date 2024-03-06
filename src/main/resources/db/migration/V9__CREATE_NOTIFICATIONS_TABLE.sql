CREATE TABLE notifications (
                                    id int AUTO_INCREMENT primary key NOT NULL,
                                    date_sent varchar(50),
                                    notification_type int NOT NULL,
                                    email int NOT NULL,
                                    FOREIGN KEY (email)
                                        REFERENCES member(email)
)