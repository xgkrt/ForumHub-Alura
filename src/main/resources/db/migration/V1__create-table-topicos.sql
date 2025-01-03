create table topicos(
    id int not null auto_increment,
    titulo varchar(250) not null,
    mensagem varchar(250) not null,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    autor varchar(250) not null,
    curso varchar(250) not null,
    status varchar(250) null,
    ativo tinyint not null default 1,
    primary key(id)
);