package com.genkey.foodmgt.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.stereotype.Component;

public class fire_wall {
    @Component
    public class Firewall {
        @Bean
        HttpFirewall httpFirewall() {
            StrictHttpFirewall firewall = new StrictHttpFirewall();
            //firewall.setAllowSemicolon(true);
            firewall.setAllowUrlEncodedDoubleSlash(true);
            return firewall;
        }
    }
}
