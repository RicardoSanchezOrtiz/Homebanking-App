package com.mindhub.homebanking;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
@SpringBootTest
public class CardUtilsTest {

    @Test
    public void cardNumberTest(){
        Utils utils = new Utils();
        assertThat(utils.cardNumber(), is(not(emptyOrNullString())));
    }
}
