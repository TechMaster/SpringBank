export const environment = {
  production: true,
  API_ENDPOINT: 'http://localhost:8082/api',
  SOCKET_ENDPOINT: 'ws://localhost:8082/ws/events',
  keycloak: {
    url: 'http://localhost:8080/auth',
    realm: 'microservice',
    clientId: 'xbank',
    clientUUID: 'xxx',
  },
};
