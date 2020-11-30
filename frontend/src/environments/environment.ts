// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  API_ENDPOINT: 'http://localhost:8081/api',
  SOCKET_ENDPOINT: 'http://localhost:3333',
  keycloak: {
      url: 'http://localhost:8080/auth',
      realm: 'microservice',
      clientId: 'xbank',
      clientSecret: '60bee81a-b06d-4e2f-8d26-7e0f0782bc7d'
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
