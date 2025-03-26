package com.bank_system.client.config;

import com.bank_system.client.dto.ClientRequestDTO;
import com.bank_system.client.dto.ClientResponseDTO;
import com.bank_system.client.model.Client;
import com.bank_system.client.model.Person;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        configureClientMappings(modelMapper);
        configurePersonMappings(modelMapper);

        return modelMapper;
    }

    private void configureClientMappings(ModelMapper modelMapper) {

        modelMapper.createTypeMap(ClientRequestDTO.class, Client.class)
                .addMappings(mapper -> {
                    mapper.skip(Client::setPerson);
                });

        modelMapper.createTypeMap(Client.class, ClientResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getPerson().getName(), ClientResponseDTO::setName);
                    mapper.map(src -> src.getPerson().getGender(), ClientResponseDTO::setGender);
                    mapper.map(src -> src.getPerson().getAge(), ClientResponseDTO::setAge);
                    mapper.map(src -> src.getPerson().getIdentification(), ClientResponseDTO::setIdentification);
                    mapper.map(src -> src.getPerson().getAddress(), ClientResponseDTO::setAddress);
                    mapper.map(src -> src.getPerson().getPhone(), ClientResponseDTO::setPhone);
                });
    }

    private void configurePersonMappings(ModelMapper modelMapper) {

        modelMapper.createTypeMap(ClientRequestDTO.class, Person.class)
                .addMappings(mapper -> {
                    mapper.skip(Person::setId);
                });

        Converter<String, LocalDateTime> toLocalDateTime =
                new AbstractConverter<String, LocalDateTime>() {
                    @Override
                    protected LocalDateTime convert(String source) {
                        return source == null ? null : LocalDateTime.parse(source, DATE_FORMATTER);
                    }
                };

        modelMapper.addConverter(toLocalDateTime);

        // Address formatting example
        modelMapper.createTypeMap(Person.class, ClientResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> ((String) ctx.getSource()).toUpperCase())
                            .map(Person::getAddress, ClientResponseDTO::setAddress);
                });
    }
}
