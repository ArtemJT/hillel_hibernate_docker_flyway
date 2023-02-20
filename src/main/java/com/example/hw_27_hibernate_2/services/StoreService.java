package com.example.hw_27_hibernate_2.services;

import com.example.hw_27_hibernate_2.dto.*;
import com.example.hw_27_hibernate_2.entities.*;
import com.example.hw_27_hibernate_2.repositories.*;
import com.google.common.collect.Streams;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.example.hw_27_hibernate_2.utilities.Mapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StoreService {

    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public ProductDto addProduct(ProductDto productDto) {
        Product product = toEntity(productDto, Product.class);
        productRepository.save(product);
        productDto.setId(product.getId());
        log.info("PRODUCT ADDED: {}", productDto);
        return productDto;
    }

    public List<ProductDto> findAllProducts() {
        List<Product> productList = Streams.stream(productRepository.findAll()).toList();
        List<ProductDto> productDtoList = allToDto(productList, ProductDto.class);
        logCollection(productDtoList);
        return productDtoList;
    }

    public ClientInfoDto addClient(ClientInfoDto clientInfoDto, AddressDto addressDto) {
        Client client = toEntity(clientInfoDto, Client.class);
        clientRepository.save(client);

        Address address = toEntity(addressDto, Address.class);
        address.setClient(client);
        addressRepository.save(address);

        addressDto.setId(address.getId());

        clientInfoDto.setId(client.getId());
        clientInfoDto.setAddressDto(addressDto);
        log.info("CLIENT ADDED: {}", clientInfoDto);
        return clientInfoDto;
    }

    public List<ClientDto> findAllClients() {
        List<Client> clientList = Streams.stream(clientRepository.findAll()).toList();
        return clientList.stream()
                .map(client -> getAllClientInfo(client.getId()))
                .toList();
    }

    public ClientDto getAllClientInfo(Integer clientId) {
        Client client = findClientById(clientId);
        ClientDto clientDto = toDto(client, ClientDto.class);
        List<Order> orderList = client.getOrdersHistory();
        List<OrderDto> orderDtoList = allToDto(orderList, OrderDto.class);
        clientDto.setOrderDtoList(orderDtoList);

        log.info("{}", clientDto);
        return clientDto;
    }

    public ClientInfoDto getClientInfoById(Integer clientId) throws EntityNotFoundException {
        ClientInfoDto clientInfoDto = toDto(findClientById(clientId), ClientInfoDto.class);
        log.info("{}", clientInfoDto);
        return clientInfoDto;
    }

    public ClientOrdersIdDto getClientOrdersById(Integer clientId) throws EntityNotFoundException {
        Client client = findClientById(clientId);
        List<Integer> ordersId = client.getOrdersHistory().stream().map(Order::getId).toList();
        ClientOrdersIdDto clientOrdersIdDto = toDto(client, ClientOrdersIdDto.class);
        clientOrdersIdDto.setOrderIdList(ordersId);
        log.info("{}", clientOrdersIdDto);
        return clientOrdersIdDto;
    }

    public AddressDto changeAddress(Integer clientId, AddressDto addressDto) {
        boolean clientExistsById = clientRepository.existsById(clientId);
        if (!clientExistsById) {
            throw new EntityNotFoundException("CLIENT WITH ID=" + clientId + " NOT FOUND");
        }

        Integer addressId = addressRepository.updateAddressForClient(
                addressDto.getCountry(),
                addressDto.getCity(),
                addressDto.getStreet(),
                addressDto.getHouse(),
                clientId
        );
        addressDto.setId(addressId);
        log.info("ADDRESS CHANGED TO: {}", addressDto);
        return addressDto;
    }

    public OrderDto createOrder(Integer clientId, Map<Integer, Integer> productMap) {
        Client client = findClientById(clientId);

        Order order = new Order();
        order.setClient(client);

        orderRepository.save(order);

        List<OrderItem> orderItemList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElseThrow(EntityNotFoundException::new);
            Integer count = entry.getValue();

            OrderItem orderItem = new OrderItem();
            orderItem.setCount(count);
            orderItem.setProduct(product);
            orderItem.setOrder(order);

            orderItemRepository.save(orderItem);
        }
        order.setOrderItemList(orderItemList);

        OrderDto orderDto = toDto(order, OrderDto.class);
        log.info("ORDER ADDED: {}", orderDto);
        return orderDto;
    }

    private Client findClientById(Integer clientId) {
        return clientRepository.findById(clientId).orElseThrow(EntityNotFoundException::new);
    }

    private void logCollection(Collection<?> list) {
        list.forEach(item -> log.info("{}", item));
    }
}
