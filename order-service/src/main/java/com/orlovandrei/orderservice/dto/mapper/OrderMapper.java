package com.orlovandrei.orderservice.dto.mapper;

import com.orlovandrei.orderservice.dto.order.OrderDto;
import com.orlovandrei.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto dto);
}