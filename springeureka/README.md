# Service registry 

Eureka used for locating services for the purpose of load balancing and failover of middle-tier servers. 

We call this service, the Eureka Server. Eureka also comes with a Java-based client component,the Eureka Client, which makes interactions with the service much easier. 

The client also has a built-in load balancer that does basic round-robin load balancing. 


All registered services will be shown on http://eurica-host:8080/ 