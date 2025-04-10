package dmit2015.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.Schedules;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timer;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.logging.Logger;

@Singleton                // Instruct the container to create a single instance of this EJB
@Startup                // Create this EJB is created when this app starts
public class AutomaticTimersBean {        // Also known as Calendar-Based Timers

        private Logger _logger = Logger.getLogger(AutomaticTimersBean.class.getName());

        /**
         * Assuming you have define the following entries in your src/main/META-INF/microprofile-config.properties file
             ca.dmit2015.config.sysadmin_email=yourUsername@yourEmailServer

         * This code assumes that this project is configured to use Eclipse Microprofile.
         * You can add the following to pom.xml to enable Eclipse Microprofile

        <dependency>
            <groupId>org.eclipse.microprofile.config</groupId>
            <artifactId>microprofile-config-api</artifactId>
            <version>3.1</version>
        </dependency>

         */
        @Inject
        @ConfigProperty(name="ca.dmit2015.config.sysadmin_email", defaultValue = "webadmin@dmit2015.ca")
        private String mailToAddress;

        @Inject
        private EmailSessionBean mail;

        private void sendEmail(Timer timer) {
                if (!mailToAddress.isBlank()) {
                        String mailSubject = timer.getInfo().toString();
                        String mailText = String.format("You have a %s on %s %s, %s  ",
                                timer.getInfo().toString(),
                                timer.getSchedule().getDayOfWeek(),
                                timer.getSchedule().getMonth(),
                                timer.getSchedule().getYear()
                        );
                        try {
                                // mail.sendTextEmail(mailToAddress, mailSubject, mailText);
                                _logger.info("Successfully sent email to " + mailToAddress);
                        } catch (Exception e) {
                                _logger.fine("Error sending email with exception " + e.getMessage());
                        }
                }
        }

         @Schedules({
                 @Schedule(second = "0", minute ="50", hour = "9", dayOfWeek = "Mon,Wed", month = "Jan-Apr", year = "2025", info ="DMIT2015-A01 In Person Meeting", persistent = false),
                 @Schedule(second = "0", minute ="01", hour = "9", dayOfWeek = "Thu", month = "Jan-Apr", year = "2025", info ="DMIT2015-A01 Online Meeting", persistent = false)
         })
        public void dmit2015SectionA01ClassNotifiation(Timer timer) {
                sendEmail(timer);
        }

        // @Schedule(second = "0", minute ="50", hour = "18", dayOfWeek = "Mon,Wed,Fri", month = "Jan-Apr", year = "2025", info ="DMIT2015-OE01 Online Meeting", persistent = false)
        public void dmit2015SectionOE01ClassNotifiation(Timer timer) {
                sendEmail(timer);
        }

        // @Schedules({
        //         @Schedule(second = "0", minute ="50", hour = "12", dayOfWeek = "Tue, month = "Jan-Apr", year = "2025", info ="DMIT2015-A02 In Person Meeting", persistent = false),
        //         @Schedule(second = "0", minute ="50", hour = "14", dayOfWeek = "Wed", month = "Jan-Apr", year = "2025", info ="DMIT2015-A02 In Person Meeting", persistent = false),
        //         @Schedule(second = "0", minute ="50", hour = "7", dayOfWeek = "Fri", month = "Jan-Apr", year = "2025", info ="DMIT2015-A01 Online Meeting", persistent = false)
        // })
        public void dmit2015SectionA02ClassNotifiation(Timer timer) {
                sendEmail(timer);
        }

}