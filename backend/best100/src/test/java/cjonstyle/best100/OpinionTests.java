package cjonstyle.best100;

import cjonstyle.best100.domain.dto.OpinionRes;
import cjonstyle.best100.service.OpinionServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpinionTests {
    @Autowired
    OpinionServiceImpl service;

    @Test
    @Transactional
    public void getAllOpinionTest() {
        String itemId="123";
        List<OpinionRes> opinions=service.getAllOpinion(itemId);
        Assert.assertEquals(2,opinions.size());
    }
}
