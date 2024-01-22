CREATE TABLE machine_history (
    id int AUTO_INCREMENT primary key NOT NULL,
    number_reps int NOT NULL,
    max_weight int NOT NULL,
    number_sets int NOT NULL,
    workout_date date NOT NULL,
    member_id int NOT NULL,
    machine_id int NOT NULL
)