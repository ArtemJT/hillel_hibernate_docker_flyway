package com.example.hw_28_spring_boot_web.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mapper {

    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    public static <E, D> D toDto(final E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public static <E, D> List<D> allToDto(final Collection<E> entityCollection, Class<D> dtoClass) {
        return entityCollection.stream()
                .map(e -> modelMapper.map(e, dtoClass))
                .toList();
    }

    public static <E, D> E toEntity(final D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public static <E, D> List<E> allToEntity(final Collection<D> dtoCollection, Class<E> entityClass) {
        return dtoCollection.stream()
                .map(d -> modelMapper.map(d, entityClass))
                .toList();
    }
}
