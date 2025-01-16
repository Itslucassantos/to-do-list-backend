CREATE TABLE tb_tasks (
  task_id UUID NOT NULL,
   title VARCHAR(250) NOT NULL,
   description VARCHAR(255) NOT NULL,
   status SMALLINT NOT NULL,
   CONSTRAINT pk_tb_tasks PRIMARY KEY (task_id)
);