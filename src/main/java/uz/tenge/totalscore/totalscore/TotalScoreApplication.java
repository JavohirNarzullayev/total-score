package uz.tenge.totalscore.totalscore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
@Slf4j
@SpringBootApplication
public class TotalScoreApplication {

    public static void main(String[] args) {
        var run = SpringApplication.run(TotalScoreApplication.class, args);
        initApplication(run.getEnvironment());
    }
    private static void initApplication(Environment env) {
        var serverPort = Optional.ofNullable(env.getProperty("server.port")).orElse("8080");
        var hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
                """

                        ----------------------------------------------------------
                        \tApplication '{}' is running! Access URLs:
                        \tLocal: \t\thttp://localhost:{}
                        \tExternal: \thttps://{}:{}
                        \tProfile(s): \t{}
                        ----------------------------------------------------------""",
                env.getProperty("spring.application.name"),
                serverPort,
                hostAddress,
                serverPort,
                env.getActiveProfiles()
        );
    }
}
