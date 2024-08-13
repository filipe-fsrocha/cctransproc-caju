package br.com.fsrocha.cctransproc.domain.transaction.model.valueobject;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Merchant {

    String name;
    String location;
    String merchantId;

}
