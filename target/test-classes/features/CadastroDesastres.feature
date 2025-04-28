# language: pt
@regressivo
Funcionalidade: Cadastro de novo desastre
  Como usuário da API
  Quero cadastrar uma novo desastre
  Para que o registro seja salvo corretamente no sistema

  Cenário: Cadastro bem-sucedido de desastre
    Dado que eu tenha os seguintes dados do desastre:
      | campo          | valor        |
      | natureza       | Teste        |
      | severidade     | Leve         |
      | regiao         | Norte        |
      | status         | Ativo        |
    Quando eu enviar a requisição para o endpoint "/api/desastres" de cadastro de desastres
    Então o status code da resposta deve ser 201

  @padrão
  Cenário: Cadastro de desastre sem sucesso ao passar o campo status nulo
    Dado que eu tenha os seguintes dados do desastre:
      | campo          | valor        |
      | natureza       | Climatica    |
      | severidade     | Leve         |
      | regiao         | Norte        |
      | status         |              |
    Quando eu enviar a requisição para o endpoint "/api/desastres" de cadastro de desastres
    Então o status code da resposta deve ser 400
    E o corpo de resposta de erro da api deve retornar a mensagem "O status do desastre é obrigatório!"

