module.exports = {
    kafka: {
      bootstrapServers: process.env.KAFKA_BOOTSTRAP_SERVERS || 'kafka:9092',
      consumerGroupId: process.env.KAFKA_CONSUMER_GROUP_ID || 'stock',
      autoOffsetReset: process.env.KAFKA_AUTO_OFFSET_RESET || 'earliest',
    },
    redis: {
      host: process.env.SPRING_REDIS_HOST || 'redis-db',
      port: process.env.SPRING_REDIS_PORT || 6379,
    },
  };
  