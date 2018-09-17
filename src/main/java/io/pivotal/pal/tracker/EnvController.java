package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private Map<String,String> env;

    public EnvController(@Value("${PORT:NOT_SET}") String port,
                         @Value("${MEMORY_LIMIT:NOT_SET}") String memoryLimit,
                         @Value("${CF_INSTANCE_INDEX:NOT_SET}") String cfInstanceIndex,
                         @Value("${CF_INSTANCE_ADDR:NOT_SET}") String cfInstanceAddress){
        env = new HashMap<>();
        env.put("PORT",port);
        env.put("MEMORY_LIMIT",memoryLimit);
        env.put("CF_INSTANCE_INDEX",cfInstanceIndex);
        env.put("CF_INSTANCE_ADDR",cfInstanceAddress);

    }

    @GetMapping("/env")
    public Map<String,String> getEnv(){
        return env;
    }

}
