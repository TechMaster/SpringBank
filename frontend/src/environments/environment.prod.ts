export const environment = {
  production: true,
  API_ENDPOINT: 'http://localhost:8082/api',
  SOCKET_ENDPOINT: 'http://localhost:3333',
  keycloak: {
      url: 'http://localhost:8080/auth',
      realm: 'microservice',
      clientId: 'xbank',
      clientSecret: '60bee81a-b06d-4e2f-8d26-7e0f0782bc7d'
  }
};
