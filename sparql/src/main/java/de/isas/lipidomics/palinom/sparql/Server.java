package de.isas.lipidomics.palinom.sparql;

import org.eclipse.rdf4j.http.server.readonly.QueryResponder;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "org.eclipse.rdf4j", "de.isas.lipidomics.palinom.sparql" })
@Import(QueryResponder.class)
public class Server {
	private static Logger logger = LoggerFactory.getLogger(Server.class);

    @Bean(destroyMethod = "shutDown")
    public Repository getRepository() {
        logger.info("Starting goslin sparql translator");
        
        SailRepository sailRepository = new SailRepository(new GoslingSparqlStore());
        logger.info("Initializing repository");
        sailRepository.init();
        return sailRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
