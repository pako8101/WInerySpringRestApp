package com.kamenov.wineryspringrestapp.models.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;
import java.util.UUID;

public class UUIDSequenceGeneration implements BeforeExecutionGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor,
                           Object owner,
                           Object currentValue,
                           EventType eventType) {
        return UUID.randomUUID();
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT);
    }
}
