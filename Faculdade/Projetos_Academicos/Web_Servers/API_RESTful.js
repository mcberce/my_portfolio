// Importando dependências
const express = require('express');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const dotenv = require('dotenv');
dotenv.config();

// Iniciando o aplicativo Express
const app = express();
const porta = 3000;

// Middleware para permitir o uso de JSON nas requisições
app.use(express.json());

// Conexão com o MongoDB
mongoose.connect('mongodb://localhost:27017/usuarios', {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => {
  console.log('Conectado ao MongoDB');
}).catch((err) => {
  console.error('Erro ao conectar ao MongoDB:', err);
});

// Definindo o modelo de Usuário
const Usuario = mongoose.model('Usuario', new mongoose.Schema({
  nome: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  senha: { type: String, required: true }
}));

// Função para gerar um token JWT
const gerarToken = (id) => {
  return jwt.sign({ id }, process.env.JWT_SECRET, { expiresIn: '1h' });
};

// Rota de registro de usuário
app.post('/register', async (req, res) => {
  const { nome, email, senha } = req.body;
  
  try {
    // Verificar se o e-mail já está cadastrado
    const usuarioExistente = await Usuario.findOne({ email });
    if (usuarioExistente) {
      return res.status(400).json({ erro: 'Usuário já existe' });
    }

    // Criptografar a senha
    const senhaCriptografada = await bcrypt.hash(senha, 10);
    
    // Criar o novo usuário
    const usuario = new Usuario({
      nome,
      email,
      senha: senhaCriptografada
    });

    // Salvar no banco de dados
    await usuario.save();

    // Gerar o token JWT
    const token = gerarToken(usuario._id);
    res.status(201).json({ mensagem: 'Usuário criado com sucesso', token });

  } catch (err) {
    res.status(500).json({ erro: 'Erro ao criar o usuário', detalhes: err.message });
  }
});

// Rota de login de usuário
app.post('/login', async (req, res) => {
  const { email, senha } = req.body;

  try {
    // Verificar se o usuário existe
    const usuario = await Usuario.findOne({ email });
    if (!usuario) {
      return res.status(400).json({ erro: 'Credenciais inválidas' });
    }

    // Verificar se a senha está correta
    const senhaValida = await bcrypt.compare(senha, usuario.senha);
    if (!senhaValida) {
      return res.status(400).json({ erro: 'Credenciais inválidas' });
    }

    // Gerar o token JWT
    const token = gerarToken(usuario._id);
    res.json({ mensagem: 'Login bem-sucedido', token });

  } catch (err) {
    res.status(500).json({ erro: 'Erro ao fazer login', detalhes: err.message });
  }
});

// Middleware para autenticação JWT
const autenticar = (req, res, next) => {
  const token = req.header('Authorization')?.replace('Bearer ', '');
  
  if (!token) {
    return res.status(401).json({ erro: 'Acesso negado' });
  }

  try {
    const usuarioVerificado = jwt.verify(token, process.env.JWT_SECRET);
    req.usuarioId = usuarioVerificado.id;
    next();
  } catch (err) {
    res.status(400).json({ erro: 'Token inválido' });
  }
};

// Rota para obter todos os usuários (apenas para admin, por exemplo)
app.get('/usuarios', autenticar, async (req, res) => {
  try {
    const usuarios = await Usuario.find();
    res.json(usuarios);
  } catch (err) {
    res.status(500).json({ erro: 'Erro ao buscar os usuários', detalhes: err.message });
  }
});

// Rota para obter um usuário específico
app.get('/usuarios/:id', autenticar, async (req, res) => {
  const { id } = req.params;

  try {
    const usuario = await Usuario.findById(id);
    if (!usuario) {
      return res.status(404).json({ erro: 'Usuário não encontrado' });
    }
    res.json(usuario);
  } catch (err) {
    res.status(500).json({ erro: 'Erro ao buscar o usuário', detalhes: err.message });
  }
});

// Rota para atualizar um usuário
app.put('/usuarios/:id', autenticar, async (req, res) => {
  const { id } = req.params;
  const { nome, email, senha } = req.body;

  try {
    const usuario = await Usuario.findById(id);
    if (!usuario) {
      return res.status(404).json({ erro: 'Usuário não encontrado' });
    }

    // Atualizar os dados do usuário
    if (nome) usuario.nome = nome;
    if (email) usuario.email = email;
    if (senha) usuario.senha = await bcrypt.hash(senha, 10);
    
    await usuario.save();
    res.json(usuario);

  } catch (err) {
    res.status(500).json({ erro: 'Erro ao atualizar o usuário', detalhes: err.message });
  }
});

// Rota para excluir um usuário
app.delete('/usuarios/:id', autenticar, async (req, res) => {
  const { id } = req.params;

  try {
    const usuario = await Usuario.findById(id);
    if (!usuario) {
      return res.status(404).json({ erro: 'Usuário não encontrado' });
    }

    await usuario.remove();
    res.status(204).send();

  } catch (err) {
    res.status(500).json({ erro: 'Erro ao excluir o usuário', detalhes: err.message });
  }
});

// Iniciar o servidor
app.listen(porta, () => {
  console.log(`Servidor rodando na porta ${porta}`);
});
