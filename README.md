# gym-tracker

Aplicação em Java para registrar treinos (exercício, peso utilizado e data) e acompanhar a evolução de desempenho na academia ao longo do tempo, com geração de gráficos mensais de progresso.

## 💡 Sobre o projeto

O objetivo é permitir que o usuário registre, para cada exercício, o peso utilizado em cada sessão de treino. Com esses dados acumulados ao longo de um mês, o sistema gera um gráfico de evolução de carga por exercício — facilitando visualizar progresso (ou estagnação) na academia.

Este é um projeto de estudo, com foco em Programação Orientada a Objetos (POO) em Java.

## 🚧 Status

Em desenvolvimento — fase inicial de modelagem das classes.

## 🏗️ Estrutura atual

- `Exercicio` — representa um exercício (ex: Supino, Agachamento).
- `RegistroTreino` — representa um registro de desempenho: exercício + peso + data.
- `HistoricoTreino` — armazena os registros e oferece consultas (histórico por exercício, filtro por mês).

## 🛠️ Tecnologias

- Java
- `java.time` (LocalDate) para manipulação de datas
- (planejado) alguma lib de gráficos, como JFreeChart, para a visualização de evolução

## 📌 Roadmap

- [x] Modelagem inicial das classes (`Exercicio`, `RegistroTreino`, `HistoricoTreino`)
- [ ] Entrada de dados via terminal (Scanner)
- [ ] Persistência dos dados (arquivo ou banco de dados)
- [ ] Geração de gráfico de desempenho mensal
- [ ] Interface gráfica (opcional)

## ▶️ Como rodar

```bash
javac *.java
java Main
```

## 📄 Licença

Este projeto é livre para fins de estudo.
