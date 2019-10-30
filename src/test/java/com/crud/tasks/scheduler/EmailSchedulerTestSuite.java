package com.crud.tasks.scheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmailSchedulerTestSuite {

    @Autowired
    EmailScheduler emailScheduler;

    @Test
    public void shouldGiveSingular() {

        emailScheduler.sendInformationEmail();

    }
}
