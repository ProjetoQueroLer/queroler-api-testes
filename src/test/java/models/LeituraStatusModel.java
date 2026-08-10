package models;

import enums.LeituraStatus;
import lombok.Data;

@Data
public class LeituraStatusModel {

    private Integer livroId;
    private LeituraStatus status;

}
