package com.apollographql.federation.springexample;

import com.apollographql.federation.graphqljava.Federation;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.servlet.config.DefaultGraphQLSchemaProvider;
import graphql.servlet.config.GraphQLSchemaProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InventorySchemaProvider extends DefaultGraphQLSchemaProvider implements GraphQLSchemaProvider {
    public InventorySchemaProvider(@Value("classpath:schemas/inventory.graphql") Resource sdl) throws IOException {
        super(Federation.transform(sdl.getFile(), InventorySchemaProvider.getRuntimeWiring())
                .build());
    }

    private static RuntimeWiring getRuntimeWiring(){
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("getProduct", new StaticDataFetcher(new Product("upc", 1))))
                .build();
    }
}
