package com.example.hw_27_hibernate_2.services;

import com.example.hw_27_hibernate_2.dto.*;
import com.example.hw_27_hibernate_2.entities.*;
import com.example.hw_27_hibernate_2.repositories.*;
import com.google.common.collect.Streams;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.example.hw_27_hibernate_2.utilities.Mapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final AddressRepo addressRepo;
    private final ClientRepo clientRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;

    public ProductDto addProduct(ProductDto productDto) {
        Product product = toEntity(productDto, Product.class);
        productRepo.save(product);
        productDto.setId(product.getId());
        log.info("PRODUCT ADDED: {}", productDto);
        return productDto;
    }

    public List<ProductDto> findAllProducts() {
        List<Product> productList = Streams.stream(productRepo.findAll()).toList();
        List<ProductDto> productDtoList = allToDto(productList, ProductDto.class);
        logCollection(productDtoList);
        return productDtoList;
    }

    public ClientInfoDto addClient(ClientInfoDto clientInfoDto, AddressDto addressDto) {
        Client client = toEntity(clientInfoDto, Client.class);
        clientRepo.save(client);

        Address address = toEntity(addressDto, Address.class);
        address.setClient(client);
        addressRepo.save(address);

        addressDto.setId(address.getId());

        clientInfoDto.setId(client.getId());
        clientInfoDto.setAddressDto(addressDto);
        log.info("CLIENT ADDED: {}", clientInfoDto);
        return clientInfoDto;
    }

    public List<ClientDto> findAllClients() {
        List<Client> clientList = Streams.stream(clientRepo.findAll()).toList();
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
        boolean clientExistsById = clientRepo.existsById(clientId);
        if (!clientExistsById) {
            throw new EntityNotFoundException("CLIENT WITH ID=" + clientId + " NOT FOUND");
        }

        Integer addressId = addressRepo.updateAddressForClient(
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

        orderRepo.save(order);

        List<OrderItem> orderItemList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
            Product product = productRepo.findById(entry.getKey()).orElseThrow(EntityNotFoundException::new);
            Integer count = entry.getValue();

            OrderItem orderItem = new OrderItem();
            orderItem.setCount(count);
            orderItem.setProduct(product);
            orderItem.setOrder(order);

            orderItemRepo.save(orderItem);
        }
        order.setOrderItemList(orderItemList);

        OrderDto orderDto = toDto(order, OrderDto.class);
        log.info("ORDER ADDED: {}", orderDto);
        return orderDto;
    }

    private Client findClientById(Integer clientId) {
        return clientRepo.findById(clientId).orElseThrow(EntityNotFoundException::new);
    }

    private void logCollection(Collection<?> list) {
        list.forEach(item -> log.info("{}", item));
    }
}
