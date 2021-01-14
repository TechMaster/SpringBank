// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  API_ENDPOINT: 'http://localhost:8081/api',
  SOCKET_ENDPOINT: 'ws://localhost:8081/ws/events',
  keycloak: {
    url: 'http://localhost:8080/auth',
    realm: 'microservice',
    clientId: 'xbank',
    clientUUID: '1411a7e8-97c1-47c5-b1d8-7e388ae21f40',
  },
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
