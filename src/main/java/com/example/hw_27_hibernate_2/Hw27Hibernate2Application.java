package com.example.hw_27_hibernate_2;

import com.example.hw_27_hibernate_2.dto.AddressDto;
import com.example.hw_27_hibernate_2.dto.ClientInfoDto;
import com.example.hw_27_hibernate_2.dto.ProductDto;
import com.example.hw_27_hibernate_2.services.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;

@SpringBootApplication
@Slf4j
public class Hw27Hibernate2Application {

    @Autowired
    private StoreService storeService;
    private int productIndex;

    public static void main(String[] args) {
        SpringApplication.run(Hw27Hibernate2Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
//        addProducts(9);

//        storeService.findAllProducts();
//
//        AddressDto addressDto = new AddressDto(null, "Country-1", "City-1", "Street-1", "House-1");
//        ClientInfoDto clientDto = new ClientInfoDto(null, "Client-1", "Client-1@gm.com", "11111111", null);
//        storeService.addClient(clientDto, addressDto);
//
//        AddressDto addressDto2 = new AddressDto(null, "Country-2", "City-2", "Street-2", "House-2");
//        ClientInfoDto clientDto2 = new ClientInfoDto(null, "Client-2", "Client-2@gm.com", "22222222", null);
//        storeService.addClient(clientDto2, addressDto2);
//
//        AddressDto addressDto3 = new AddressDto(null, "Country-3", "City-3", "Street-3", "House-3");
//        ClientInfoDto clientDto3 = new ClientInfoDto(null, "Client-3", "Client-3@gm.com", "33333333", null);
//        storeService.addClient(clientDto3, addressDto3);
//
//        AddressDto addressDto3upd = new AddressDto(null, "Country-3-upd", "City-3-upd", "Street-3-upd", "House-3-upd");
//        storeService.changeAddress(3, addressDto3upd);
//
//        Map<Integer, Integer> productMap = Map.of(
//                3, 2,
//                1, 1,
//                7, 1
//        );
//
//        storeService.createOrder(2, productMap);

        storeService.getClientInfoById(3);

        storeService.getClientOrdersById(2);

        storeService.getAllClientInfo(2);

        storeService.findAllClients();
    }

    private void addProducts(int quantity) {
        for (int i = 1; i <= quantity; i++) {
            productIndex++;
            String productName = "Product-" + productIndex;
            ProductDto productDto = new ProductDto(
                    null, productName, "Description of " + productName, parseStringToPrice());
            storeService.addProduct(productDto);
        }
    }

    private double parseStringToPrice() {
        String stringIndex = Integer.toString(productIndex).repeat(2);
        return Double.parseDouble(stringIndex + '.' + stringIndex);
    }
}
