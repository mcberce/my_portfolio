// Instale o express antes de rodar o código:
// npm init -y
// npm install express

const express = require('express');
const app = express();
const porta = 3000;

app.use(express.json());

let tarefas = [];

// Rota para obter todas as tarefas
app.get('/tarefas', (req, res) => {
  res.json(tarefas);
});

// Rota para criar uma nova tarefa
app.post('/tarefas', (req, res) => {
  const tarefa = req.body;
  if (!tarefa.nome) {
    return res.status(400).json({ erro: 'Nome da tarefa é obrigatório' });
  }
  tarefa.id = tarefas.length + 1;
  tarefas.push(tarefa);
  res.status(201).json(tarefa);
});

// Rota para excluir uma tarefa
app.delete('/tarefas/:id', (req, res) => {
  const { id } = req.params;
  tarefas = tarefas.filter(tarefa => tarefa.id !== parseInt(id));
  res.status(204).send();
});

app.listen(porta, () => {
  console.log(`Servidor rodando na porta ${porta}`);
});
