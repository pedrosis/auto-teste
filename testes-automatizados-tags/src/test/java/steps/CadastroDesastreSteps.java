package steps;

import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.ErrorMessageModel;
import org.junit.Assert;
import services.CadastroDesastreService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroDesastreSteps {
    CadastroDesastreService cadastroDesastreService = new CadastroDesastreService();

    @Dado("que eu tenha os seguintes dados do desastre:")
    public void queEuTenhaOsSeguintesDadosDoDesastre(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            cadastroDesastreService.setFieldsDesastre(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de desastres")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDoDesastre(String endPoint) {
        cadastroDesastreService.createDesastre(endPoint);
    }

    @Então("o status code da resposta deve ser {int}")
    public void oStatusDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroDesastreService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeveRetornarAMensagem(String mensagemEsperada) {
        ErrorMessageModel errorMessageModel = cadastroDesastreService.gson.fromJson(
                cadastroDesastreService.response.getBody().asString(),
                ErrorMessageModel.class
        );
        Assert.assertEquals(mensagemEsperada, errorMessageModel.getStatus());
    }

    @E("que o arquivo de contrato esperado é o {string}")
    public void queOArquivoDeContratoEsperadoÉO(String contract) throws IOException {
        cadastroDesastreService.setContract(contract);
    }

    @Então("a resposta da requisição deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaRequisiçãoDeveEstarEmConformidadeComOContratoSelecionado() throws IOException {
        Set<ValidationMessage> validateResponse = cadastroDesastreService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }


}
