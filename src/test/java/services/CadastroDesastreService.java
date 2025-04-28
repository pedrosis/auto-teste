package services;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.DesastreModel;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;
public class CadastroDesastreService {
    final DesastreModel desastreModel = new DesastreModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls() // important
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080";
    public void setFieldsDesastre(String field, String value) {
        switch (field) {
            case "id" -> desastreModel.setId(Integer.parseInt(value));
            case "natureza" -> desastreModel.setNatureza(value);
            case "severidade" -> desastreModel.setSeveridade(value);
            case "regiao" -> desastreModel.setRegiao(value);
            case "status" -> {
                if (value == null || value.isBlank()) {
                    desastreModel.setStatus(null); // Se estiver vazio, seta como null
                } else {
                    desastreModel.setStatus(value);
                }
            }
            default -> throw new IllegalStateException("Unexpected field: " + field);
        }
    }

    public void createDesastre(String endPoint) {
        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(desastreModel);
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    private JSONObject loadJsonFromFile(String filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            JSONTokener tokener = new JSONTokener(inputStream);
            return new JSONObject(tokener);
        }
    }

    public void setContract(String contract) throws IOException {
        switch (contract) {
            case "Cadastro bem-sucedido de desastre" -> jsonSchema = loadJsonFromFile(schemasPath + "cadastro-bem-sucedido-de-desastre.json");
            default -> throw new IllegalStateException("Unexpected contract" + contract);
        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException
    {
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());
        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());
        Set<ValidationMessage> schemaValidationErrors = schema.validate(jsonResponseNode);
        return schemaValidationErrors;
    }

}