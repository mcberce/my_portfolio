const express = require('express');
const mongoose = require('mongoose');
const app = express();
const porta = 3000;

app.use(express.json());

// Conectar ao MongoDB
mongoose.connect('mongodb://localhost:27017/teste', { useNewUrlParser: true, useUnifiedTopology: true });

// Definir o modelo de usuário
const Usuario = mongoose.model('Usuario', {
  nome: String,
  email: String
});

// Rota para criar um novo usuário
app.post('/usuarios', async (req, res) => {
  const { nome, email } = req.body;
  const usuario = new Usuario({ nome, email });
  await usuario.save();
  res.status(201).json(usuario);
});

// Rota para obter todos os usuários
app.get('/usuarios', async (req, res) => {
  const usuarios = await Usuario.find();
  res.json(usuarios);
});

app.listen(porta, () => {
  console.log(`Servidor rodando na porta ${porta}`);
});
