package com.charapadev.secondchef.utils.generators;

import java.util.Random;

public class OtpCodeGenerator {

    public static Integer generate() {
        Random random = new Random();

        return 100000 + random.nextInt(900000);
    }

}
