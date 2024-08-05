### Filter service

#### Redis

##### Configuration

Hands on Reactive Spring with Redis Cache

The concept of reactive programming enables more responsive and scalable programmes by handling asynchronous data
streams. It emphasises on representing data flows as ongoing streams of events so that systems may respond and adjust in
real time to shifting circumstances.

link: https://daasrattale.medium.com/hands-on-practice-with-reactive-spring-with-redis-cache-and-docker-support-4d0bc27f1e54

##### Issue with java 8 Local date

1. resolve with object mapper configuration
   link:  https://github.com/Bryksin/redis-reactive-cache/blob/master/src/main/java/com/vsware/libraries/redisreactivecache/config/RedisReactiveCacheConfig.java

```
    @Value("${spring.redis.date_format:dd-MM-yyyy}")
    public String DEFAULT_DATE_FORMAT;
    
    @Value("${spring.redis.time_format:HH:mm:ss}")
    public String DEFAULT_TIME_FORMAT;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    @Bean
    @Primary
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ObjectMapper objectMapper) {
        var serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
        ......
        .....
    }
```

2. Add additional library to support annotation
   serialize/deserialize java 8 java.time with Jackson JSON mapper
   link: https://stackoverflow.com/questions/27952472/serialize-deserialize-java-8-java-time-with-jackson-json-mapper

gradle:

```
    implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2'
```

Add to entity class:

```
public record User(
    Integer id,
    String username,
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime created)
    implements Serializable {

  public User(Integer id, String username, LocalDateTime created) {
    this.id = id;
    this.username = username;
    this.created = Objects.isNull(created) ? LocalDateTime.now() : created;
  }
}
```