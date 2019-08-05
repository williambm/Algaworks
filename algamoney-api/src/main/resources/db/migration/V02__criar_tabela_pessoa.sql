CREATE TABLE pessoa (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(64) NOT NULL,
    ativo BOOLEAN NOT NULL,
    logradouro VARCHAR(32),
    numero VARCHAR(16),
    complemento VARCHAR(32),
    bairro VARCHAR(32),
    cep VARCHAR(10),
    cidade VARCHAR(32),
    estado VARCHAR(32)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values (
'William Bragagnollo Montini',
true,
'Rua Rocha',
'666',
null,
'Bela Vista',
'01330-000',
'São Paulo',
'SP'
)
 