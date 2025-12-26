// Apenas Node.js, sem framework

const http = require('http');
const url = require('url');

const porta = 3000;

const servidor = http.createServer((req, res) => {
  const urlParse = url.parse(req.url, true);
  res.writeHead(200, { 'Content-Type': 'application/json' });

  if (urlParse.pathname === '/saudacao' && req.method === 'GET') {
    res.end(JSON.stringify({ mensagem: 'Olá, bem-vindo à API!' }));
  } else {
    res.end(JSON.stringify({ erro: 'Rota não encontrada' }));
  }
});

servidor.listen(porta, () => {
  console.log(`Servidor rodando na porta ${porta}`);
});
