CREATE TABLE setor (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);


CREATE TABLE usuario (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(40) NOT NULL,
    id_setor INT NOT NULL,

    CONSTRAINT fk_usuario_setor
        FOREIGN KEY (id_setor)
        REFERENCES setor(id)
);


CREATE TABLE equipamento (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    patrimonio INT UNIQUE NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descricao VARCHAR(200),
    id_setor INT NOT NULL,

    CONSTRAINT fk_equipamento_setor
        FOREIGN KEY (id_setor)
        REFERENCES setor(id)
);


CREATE TABLE chamado (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    data_abertura TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ABERTO',
    id_usuario INT NOT NULL,
    id_equipamento INT,

    CONSTRAINT fk_chamado_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id),

    CONSTRAINT fk_chamado_equipamento
        FOREIGN KEY (id_equipamento)
        REFERENCES equipamento(id)
);