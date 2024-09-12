package top.flyeric.order.adapter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/{id}")
    public Map<String, Object> getOrder(@PathVariable("id") String orderId) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("orderName", "orderName-" + orderId);
        return result;
    }

}
