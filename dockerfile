FROM rabbitmq:management

# Abilita i plugin MQTT
RUN rabbitmq-plugins enable rabbitmq_mqtt