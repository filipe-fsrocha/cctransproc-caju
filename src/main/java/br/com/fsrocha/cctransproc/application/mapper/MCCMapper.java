package br.com.fsrocha.cctransproc.application.mapper;

import br.com.fsrocha.cctransproc.application.response.DataResponse;
import br.com.fsrocha.cctransproc.application.response.ListInformation;
import br.com.fsrocha.cctransproc.application.response.MCCResponse;
import br.com.fsrocha.cctransproc.domain.mcc.entities.MCCEntity;
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
public class MCCMapper {

    ModelMapper modelMapper;

    public MCCResponse toResponse(MCCEntity mcc) {
        return modelMapper.map(mcc, MCCResponse.class);
    }

    public DataResponse<MCCResponse> toResponseData(Page<MCCEntity> page) {
        List<MCCResponse> data = Arrays.asList(modelMapper.map(page.getContent(), MCCResponse[].class));
        return new DataResponse<>(data, modelMapper.map(page, ListInformation.class));
    }

}
