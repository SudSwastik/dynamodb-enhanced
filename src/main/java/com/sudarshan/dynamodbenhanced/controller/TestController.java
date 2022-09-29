package com.sudarshan.dynamodbenhanced.controller;

import com.sudarshan.dynamodbenhanced.model.Customer;
import com.sudarshan.dynamodbenhanced.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;
import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primarySortKey;

@RestController
public class TestController {

    @GetMapping("/table")
    public ResponseEntity<Object> createTable() throws InterruptedException {

         final TableSchema<Item> ITEM_SCHEMA =

                TableSchema.builder(Item.class)
                        .newItemSupplier(Item::new)
                        .addAttribute(String.class, a -> a.name("bag")
                                .getter(Item::getBag)
                                .setter(Item::setBag))
                        .addAttribute(String.class, a -> a.name("uniform")
                                .getter(Item::getUniform)
                                .setter(Item::setUniform))
                        .build();;

         final TableSchema<Customer> CUSTOMER_TABLE_SCHEMA =
                TableSchema.builder(Customer.class)
                        .newItemSupplier(Customer::new)
                        .addAttribute(String.class, a -> a.name("account_id")
                                .getter(Customer::getAccountId)
                                .setter(Customer::setAccountId)
                                .tags(primaryPartitionKey()))
                        .addAttribute(Integer.class, a -> a.name("sub_id")
                                .getter(Customer::getSubId)
                                .setter(Customer::setSubId)
                                .tags(primarySortKey()))
                        .addAttribute(String.class, a -> a.name("name")
                                .getter(Customer::getName)
                                .setter(Customer::setName))
                        .addAttribute(
                                EnhancedType.listOf(
                                        EnhancedType.documentOf(
                                                Item.class,
                                                ITEM_SCHEMA)),
                                a -> a.name("items").getter(Customer::getItems)
                                        .setter(Customer::setItems)
                        )
                        .build();
        Region region = Region.AP_SOUTH_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
        // Maps a physical table with the name 'customers_20190205' to the schema
        DynamoDbTable<Customer> customerTable = enhancedClient.table("customers_test", CUSTOMER_TABLE_SCHEMA);
        customerTable.createTable();
//enhancedClient.
//        Customer customer = customerTable.getItem(Key.builder().partitionValue("a123").build());

// UpdateItem
//        Customer updatedCustomer = customerTable.updateItem(customer);

        Thread.sleep(3000);
        Item item = new Item("bag", "uniform");
        List<Item> li = new ArrayList<>();
        li.add(item);
        Customer customer = new Customer("1234", 1 , "sud",li );

        customerTable.putItem(customer);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/item")
    public ResponseEntity<Object> putItem() {
//        Customer customer = new Customer("1234", 1 , "sud");
//
//        customerTable.putItem(customer);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
