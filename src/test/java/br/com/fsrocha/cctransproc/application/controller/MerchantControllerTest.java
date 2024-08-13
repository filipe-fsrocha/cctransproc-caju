package br.com.fsrocha.cctransproc.application.controller;

import br.com.fsrocha.cctransproc.application.response.DataResponse;
import br.com.fsrocha.cctransproc.application.response.MCCResponse;
import br.com.fsrocha.cctransproc.application.response.MerchantResponse;
import br.com.fsrocha.cctransproc.utils.DatabaseTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
class MerchantControllerTest extends DatabaseTest {

    private static final String UBER_EATS = "UBER EATS                  SAO PAULO BR";
    private static final String UBER_TRIP = "UBER TRIP                  SAO PAULO BR";
    private static final String PAG_JOSE_DA_SILVA = "PAG*JoseDaSilva         RIO DE JANEI BR";

    @Autowired
    MerchantController controller;

    @Test
    @DisplayName("Lista todos os merchants")
    void testListMerchants() {
        // Act
        ResponseEntity<DataResponse<MerchantResponse>> data = controller.merchant(0, 10, null);

        // Assert
        var result = data.getBody();

        assertNotNull(result);
        Assertions.assertThat(result.getItems())
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .ignoringCollectionOrder()
                .isEqualTo(List.of(
                        createMerchant(PAG_JOSE_DA_SILVA,
                                createMcc("f5ddc13b-3fc6-40ca-909e-354d33089a29", 5411, "MERCEARIAS/SUPERMERCADOS (GROCERY STORES/SUPERM.)")),
                        createMerchant(UBER_EATS,
                                createMcc("c19151fa-560d-4c72-a1f2-288156a160e1", 5812, "RESTAURANTES")),
                        createMerchant(UBER_TRIP,
                                createMcc("c19151fa-560d-4c72-a1f2-288156a160e2", 4000, "SERVIÇOS DE TRANSPORTE"))
                ));
    }

    @Test
    @DisplayName("Obter o merchant pelo id")
    void testFindMerchantById() {
        // Act
        ResponseEntity<MerchantResponse> data = controller.merchant(UUID.fromString("bb24c923-bc0e-4caa-86a4-c33f464371f8"));

        // Assert
        var result = data.getBody();

        assertNotNull(result);
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(createMerchant(UBER_EATS,
                        createMcc("c19151fa-560d-4c72-a1f2-288156a160e1", 5812, "RESTAURANTES")));
    }


    private MerchantResponse createMerchant(String name, MCCResponse mcc) {
        var merchant = new MerchantResponse();
        merchant.setName(name);
        merchant.setMcc(mcc);
        return merchant;
    }

    private MCCResponse createMcc(String id, int mcc, String description) {
        var mccResponse = new MCCResponse();
        mccResponse.setId(UUID.fromString(id));
        mccResponse.setMcc(mcc);
        mccResponse.setDescription(description);
        mccResponse.setActive(true);
        return mccResponse;
    }
}
