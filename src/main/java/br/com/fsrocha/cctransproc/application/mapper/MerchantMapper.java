package br.com.fsrocha.cctransproc.application.mapper;

import br.com.fsrocha.cctransproc.application.response.DataResponse;
import br.com.fsrocha.cctransproc.application.response.ListInformation;
import br.com.fsrocha.cctransproc.application.response.MerchantResponse;
import br.com.fsrocha.cctransproc.domain.company.entities.MerchantEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MerchantMapper {

    ModelMapper modelMapper;

    public MerchantResponse toResponse(MerchantEntity entity) {
        return modelMapper.map(entity, MerchantResponse.class);
    }

    public DataResponse<MerchantResponse> toDataResponse(Page<MerchantEntity> page) {
        List<MerchantResponse> data = Arrays.asList(modelMapper.map(page.getContent(), MerchantResponse[].class));
        return new DataResponse<>(data, modelMapper.map(page, ListInformation.class));
    }
}
