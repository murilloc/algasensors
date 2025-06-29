package com.murilloc.algasensors.device.management.common;

import io.hypersistence.tsid.TSID;

import java.util.Optional;

public class IdGenerator {

    private static final TSID.Factory factory;

    static {
        Optional.ofNullable(System.getenv("TSID_NODE"))
                .ifPresent(tsidNode -> System.setProperty("tsid.tsidNode", tsidNode));

        Optional.ofNullable(System.getenv("TSID_NODE_COUNT"))
                .ifPresent(tsidNodeCount -> System.setProperty("tsid.node.count", tsidNodeCount));


        System.setProperty("tsid.node", "7");
        System.setProperty("tsid.node.count", "32");
        factory = TSID.Factory.builder().build();
    }

    private IdGenerator() {


    }

    public static TSID generateTSID() {

        return factory.generate();
    }
}
