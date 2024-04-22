package it.safesiteguard.ms.alarms_ssguard.configuration;

import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MqttConfiguration {


    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Value("${mqtt.auth.username}")
    private String username;

    @Value("${mqtt.auth.password}")
    private String password;

    @Value("${mqtt.topic.machineryAlarms}")
    private String machineriesAlarms_TOPIC;


    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }




    /*@Bean
    public MqttAsyncClient mqttAsyncClient() throws MqttException {
        MqttAsyncClient asyncClient = new MqttAsyncClient(brokerUrl, clientId);

        MqttConnectionOptions mqttConnectionOptions = new MqttConnectionOptions();
        mqttConnectionOptions.setUserName(username);
        mqttConnectionOptions.setPassword(password.getBytes());
        mqttConnectionOptions.setCleanStart(true);

        asyncClient.connect(mqttConnectionOptions);
        return asyncClient;
    }*/

    @Bean
    public Mqttv5PahoMessageDrivenChannelAdapter mqttInputChannel_MachineriesAlarms() {

        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setUserName(username);
        options.setPassword(password.getBytes());
        options.setCleanStart(true);

        Mqttv5PahoMessageDrivenChannelAdapter adapter =
                new Mqttv5PahoMessageDrivenChannelAdapter(options, brokerUrl, clientId, machineriesAlarms_TOPIC);
        adapter.setOutputChannel(mqttInputChannel());

        return adapter;
    }







}
