package model;
import com.google.gson.annotations.Expose;
import lombok.Data;
@Data
public class DesastreModel {
    @Expose(serialize = false)
    private int numeroDesastre;
    @Expose
    private int id;
    @Expose
    private String natureza;
    @Expose
    private String severidade;
    @Expose
    private String regiao;
    @Expose
    private String status;

}

