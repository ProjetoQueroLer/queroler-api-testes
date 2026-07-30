package models;

import enums.LeituraStatus;
import lombok.Data;

@Data
public class LeituraModel {

    private Integer livroId;
    private LeituraStatus status;

}
