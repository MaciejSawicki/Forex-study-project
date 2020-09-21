package com.sawicki.forex;

import com.sawicki.forex.controller.IndexController;
import com.sawicki.forex.controller.ProfileController;
import com.sawicki.forex.controller.TradeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ForexApplicationTests {

    @Autowired
    private IndexController indexController;
    @Autowired
    private TradeController tradeController;
    @Autowired
    private ProfileController profileController;

    @Test
    void contextLoads() {
        assertThat(indexController).isNotNull();
        assertThat(tradeController).isNotNull();
        assertThat(profileController).isNotNull();
    }

}
