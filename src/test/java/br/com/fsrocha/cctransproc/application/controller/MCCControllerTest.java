package br.com.fsrocha.cctransproc.application.controller;

import br.com.fsrocha.cctransproc.application.response.DataResponse;
import br.com.fsrocha.cctransproc.application.response.ListInformation;
import br.com.fsrocha.cctransproc.application.response.MCCResponse;
import br.com.fsrocha.cctransproc.domain.error.ServiceException;
import br.com.fsrocha.cctransproc.utils.DatabaseTest;
import br.com.fsrocha.cctransproc.utils.ServiceExceptionAssertions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
class MCCControllerTest extends DatabaseTest {

    @Autowired
    MCCController controller;

    @Test
    @DisplayName("Deve retornar uma lista de MCCs de forma páginada")
    void testListAllMCC() {
        // Act
        ResponseEntity<DataResponse<MCCResponse>> data = controller.mcc(0, 10, null);

        // Assert
        var result = data.getBody();

        assertNotNull(result);
        Assertions.assertThat(result.getItems())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringCollectionOrder()
                .isEqualTo(List.of(
                        createMCC("5411", "MERCEARIAS/SUPERMERCADOS (GROCERY STORES/SUPERM.)"),
                        createMCC("5412", "LOJAS DE ALIMENTOS"),
                        createMCC("5811", "DISTRIBUIÇÃO E PRODUÇÃO DE ALIMENTOS"),
                        createMCC("5812", "RESTAURANTES"),
                        createMCC("4000", "SERVIÇOS DE TRANSPORTE")
                ));
        Assertions.assertThat(result.getListInformation())
                .isEqualTo(toListInformation(5, 1));
    }

    @Test
    @DisplayName("Deve retornar a lista de MMC contendo 1 registro e 5 páginas")
    void testListAllMCWithFilterPaginated() {
        // Act
        ResponseEntity<DataResponse<MCCResponse>> data = controller.mcc(0, 1, null);

        // Assert
        var result = data.getBody();

        assertNotNull(result);
        Assertions.assertThat(result.getItems())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringCollectionOrder()
                .isEqualTo(List.of(
                        createMCC("5411", "MERCEARIAS/SUPERMERCADOS (GROCERY STORES/SUPERM.)")
                ));
        Assertions.assertThat(result.getListInformation())
                .isEqualTo(toListInformation(5, 5));
    }

    @Test
    @DisplayName("Deve retornar o resulado com a paginação padrão se o filtro informado for invalido")
    void testListMCCWithInvalidFilterPaginated() {
        // Act
        ResponseEntity<DataResponse<MCCResponse>> data = controller.mcc(-1, -2, null);

        // Assert
        var result = data.getBody();

        assertNotNull(result);
        Assertions.assertThat(result.getItems())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringCollectionOrder()
                .isEqualTo(List.of(
                        createMCC("5411", "MERCEARIAS/SUPERMERCADOS (GROCERY STORES/SUPERM.)"),
                        createMCC("5412", "LOJAS DE ALIMENTOS"),
                        createMCC("5811", "DISTRIBUIÇÃO E PRODUÇÃO DE ALIMENTOS"),
                        createMCC("5812", "RESTAURANTES"),
                        createMCC("4000", "SERVIÇOS DE TRANSPORTE")
                ));
        Assertions.assertThat(result.getListInformation())
                .isEqualTo(toListInformation(5, 1));
    }


    @Test
    @DisplayName("Deve retornar os MCCs que contém parte da pesquisa em sua descrição")
    void testListMCCWithFilterUsingSearch() {
        // Act
        ResponseEntity<DataResponse<MCCResponse>> data = controller.mcc(0, 10, "alimentos");

        // Assert
        var result = data.getBody();

        assertNotNull(result);
        Assertions.assertThat(result.getItems())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringCollectionOrder()
                .isEqualTo(List.of(
                        createMCC("5412", "LOJAS DE ALIMENTOS"),
                        createMCC("5811", "DISTRIBUIÇÃO E PRODUÇÃO DE ALIMENTOS")
                ));
        Assertions.assertThat(result.getListInformation())
                .isEqualTo(toListInformation(2, 1));
    }

    @Test
    @DisplayName("Deve retornar os detalhes do MCC, com base o MCC informado")
    void testReturnMCCDetailsByMCCInformed() {
        // Act
        ResponseEntity<MCCResponse> data = controller.mcc("5412");

        // Assert
        Assertions.assertThat(data.getBody())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createMCC("5412", "LOJAS DE ALIMENTOS"));
    }

    @Test
    @DisplayName("Deve retornar NOT_FOUND para o MCC não encontrado")
    void testReturnNotFound() {
        // Act
        ServiceException exception = ServiceExceptionAssertions.catchThrowableServiceException(
                () -> controller.mcc("5420")
        );

        // Assert
        ServiceExceptionAssertions.assertThat(exception)
                .hasCategory(HttpStatus.NOT_FOUND)
                .hasMessage("MCC 5420 not found.");
    }

    private MCCResponse createMCC(String mcc, String description) {
        var mccResponse = new MCCResponse();
        mccResponse.setMcc(mcc);
        mccResponse.setDescription(description);
        mccResponse.setActive(true);
        return mccResponse;
    }

    private ListInformation toListInformation(int totalElements, int totalPages) {
        var listInformation = new ListInformation();
        listInformation.setTotalElements(totalElements);
        listInformation.setTotalPages(totalPages);
        return listInformation;
    }
}