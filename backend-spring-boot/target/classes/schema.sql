
drop table if exists Cidade;

create table Cidade(
  id int not null AUTO_INCREMENT,
  nome varchar(100) not null,
  uf varchar(2) not null,
  capital boolean not null,  
  PRIMARY KEY ( ID )
);

drop table if exists Comercio;

CREATE TYPE tipo_comercio AS ENUM (
    'FARMÁCIA',
    'PADARIA',
    'POSTO_GASOLINA',
    'LANCHONETE'
);


create table Comercio(
	id int not null AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL,
    nome_responsavel VARCHAR(255),
    tipo_comercio tipo_comercio NOT NULL,  
    cidade_id INT NOT NULL,
    FOREIGN KEY (cidade_id) REFERENCES cidade(id) ON DELETE CASCADE
	
)
