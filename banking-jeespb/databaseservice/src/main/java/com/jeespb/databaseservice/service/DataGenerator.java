package com.jeespb.databaseservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeespb.databaseservice.model.Account;
import com.jeespb.databaseservice.model.Reward;
import com.jeespb.databaseservice.model.Transaction;
import com.jeespb.databaseservice.model.User;
import com.jeespb.databaseservice.repository.AccountRepository;
import com.jeespb.databaseservice.repository.RewardRepository;
import com.jeespb.databaseservice.repository.TransactionRepository;
import com.jeespb.databaseservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Component
@ConditionalOnProperty(
        value = "data.generator.enabled",
        havingValue = "true")
public class DataGenerator {

    private final Logger logger = LoggerFactory.getLogger(DataGenerator.class);

    @Value("classpath:data/users.json")
    private Resource userJsonResource;

    @Value("classpath:data/accounts.json")
    private Resource accountJsonResource;

    @Value("classpath:data/transactions.json")
    private Resource transactionJsonResource;

    @Value("classpath:data/rewards.json")
    private Resource rewardJsonResource;

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final RewardRepository rewardRepository;

    private final ObjectMapper objectMapper;

    public DataGenerator(UserRepository userRepository,
                         AccountRepository accountRepository,
                         TransactionRepository transactionRepository,
                         RewardRepository rewardRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.rewardRepository = rewardRepository;
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct
    public void init() throws IOException {
        generateData();
    }

    private void generateData() throws IOException {
        logger.info("Generating data...");
        generateUsers();
        generateAccounts();
        generateTransactions();
        generateRewards();
        logger.info("Generated data");
    }

    private void generateRewards() throws IOException {
        logger.info("Generating rewards...");
        InputStream inputStream = rewardJsonResource.getInputStream();
        Reward[] rewards = objectMapper.readValue(inputStream, Reward[].class);
        Arrays.stream(rewards).forEach(rewardRepository::save);
        logger.info("Generated {} rewards", rewards.length);
    }

    private void generateTransactions() throws IOException {
        logger.info("Generating transactions...");
        InputStream inputStream = transactionJsonResource.getInputStream();
        Transaction[] transactions = objectMapper.readValue(inputStream, Transaction[].class);
        Arrays.stream(transactions).forEach(transactionRepository::save);
        logger.info("Generated {} transactions", transactions.length);
    }

    private void generateAccounts() throws IOException {
        logger.info("Generating accounts...");
        InputStream inputStream = accountJsonResource.getInputStream();
        Account[] accounts = objectMapper.readValue(inputStream, Account[].class);
        Arrays.stream(accounts).forEach(accountRepository::save);
        logger.info("Generated {} accounts", accounts.length);
    }

    private void generateUsers() throws IOException {
        logger.info("Generating users...");
        InputStream inputStream = userJsonResource.getInputStream();
        User[] users = objectMapper.readValue(inputStream, User[].class);
        Arrays.stream(users).forEach(userRepository::save);
        logger.info("Generated {} users", users.length);
    }

}
