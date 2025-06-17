package com.example.productoserviceasync;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductorService {

    private final AmqpTemplate customRabbitTemplate;

    public ProductorService(AmqpTemplate customRabbitTemplate) {
        this.customRabbitTemplate = customRabbitTemplate;
    }

    public void enviarListaProductos(List<ProductoDTO> productos) {
        customRabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,
            RabbitMQConfig.ROUTING_KEY,
            productos
        );
        System.out.println("Se ha enviado la lista de productos enviada en formato JSON.");
    }
}