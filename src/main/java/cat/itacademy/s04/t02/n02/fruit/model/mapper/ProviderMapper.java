package cat.itacademy.s04.t02.n02.fruit.model.mapper;

import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderResponse;
import cat.itacademy.s04.t02.n02.fruit.model.entity.Provider;

import java.util.List;

public final class ProviderMapper {

    private ProviderMapper(){}

    public static ProviderResponse toResponse(Provider provider){

        return new ProviderResponse(
                provider.getId(),
                provider.getName(),
                provider.getCountry()
        );

    }

    public static List<ProviderResponse> toResponseList(List<Provider> providers){
        return providers.stream()
                .map(ProviderMapper::toResponse)
                .toList();
    }
}
