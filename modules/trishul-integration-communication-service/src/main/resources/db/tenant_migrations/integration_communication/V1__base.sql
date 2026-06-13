CREATE SEQUENCE integration_communication_config_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE INTEGRATION_COMMUNICATION_CONFIG (
  id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('integration_communication_config_sequence'),
  integration_id BIGINT,
  channel_type VARCHAR(50),
  channel_address VARCHAR(255),
  default_from VARCHAR(255),
  enabled BOOLEAN,
  version INTEGER,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_integration_comm_config_integration FOREIGN KEY (integration_id) REFERENCES INTEGRATION(id) ON DELETE CASCADE
);
