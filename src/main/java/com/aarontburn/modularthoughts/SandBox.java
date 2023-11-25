package com.aarontburn.modularthoughts;

import java.time.*;

public class SandBox {

    public static void main(final String[] args) {



        LocalTime midnight = LocalTime.MIDNIGHT;
        long initialDelay = Duration.between(LocalTime.now(), midnight).toMillis();

    }
}
