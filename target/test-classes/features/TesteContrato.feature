# language: pt
@regressivo
Funcionalidade: Validar o contrato ao realizar um cadastro bem-sucedido de desastre
  Como usuário da API
  Quero cadastrar um novo desastre bem-sucedido
  Para que eu consiga validar se o contrato esta conforme o esperado

  Cenário: Validar contrato do cadastro bem-sucedido de desastre
    Dado que eu tenha os seguintes dados do desastre:
      | campo          | valor        |
      | natureza       | Teste        |
      | severidade     | Leve         |
      | regiao         | Norte        |
      | status         | Ativo        |
    Quando eu enviar a requisição para o endpoint "/api/desastres" de cadastro de desastres
    Então o status code da resposta deve ser 201
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de desastre"
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado