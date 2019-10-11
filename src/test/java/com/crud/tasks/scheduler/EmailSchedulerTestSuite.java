package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import org.junit.Test;


public class EmailSchedulerTestSuite {


    @Test
    public void shouldGiveSingular(){
        long size = 1;
        String task;

        if (size == 1){
            task = "task";
        } else {
            task = "tasks";
        }

        Mail mail = new Mail(
                "test@test.pl",
                "Subject",
                "Currently in database you got: " + size + " " + task + ".",
                "");

        System.out.println(mail.getMessage());
    }
}
