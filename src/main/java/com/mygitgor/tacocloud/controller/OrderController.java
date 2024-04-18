package com.mygitgor.tacocloud.controller;

import com.mygitgor.tacocloud.domain.CartItem;
import com.mygitgor.tacocloud.domain.Order;
import com.mygitgor.tacocloud.domain.User;
import com.mygitgor.tacocloud.request.AddCartItemRequest;
import com.mygitgor.tacocloud.request.OrderRequest;
import com.mygitgor.tacocloud.service.OrderService;
import com.mygitgor.tacocloud.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @SneakyThrows
    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request,
                                                  @RequestHeader("Authorization") String jwt){
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(request, user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt){

        User user = userService.findUserByJwtToken(jwt);

        List<Order> order = orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
