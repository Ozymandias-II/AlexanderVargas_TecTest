package com.bank_system.client.service;

import com.bank_system.client.dto.ClientRequestDTO;
import com.bank_system.client.dto.ClientResponseDTO;
import com.bank_system.client.model.Client;
import com.bank_system.client.model.Person;
import com.bank_system.client.repository.ClientRepository;
import com.bank_system.client.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ClientResponseDTO create(ClientRequestDTO request) {
        if (personRepository.existsByIdentification(request.getIdentification())) {
            throw new IllegalArgumentException("Ya existe un cliente con identificación " + request.getIdentification());
        }

        Person person = modelMapper.map(request, Person.class);
        person = personRepository.save(person);

        Client client = new Client();
        modelMapper.map(request, client);
        client.setPerson(person);
        client = clientRepository.save(client);
        return modelMapper.map(client, ClientResponseDTO.class);

    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> getAll() {
        return clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO getById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id " + id));
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientRequestDTO request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id " + id));

        Person person = client.getPerson();
        modelMapper.map(request, person);

        modelMapper.map(request, client);

        client = clientRepository.save(client);
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    @Transactional
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id " + id);
        }
        clientRepository.deleteById(id);
    }
}
