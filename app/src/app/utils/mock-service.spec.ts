
export class MockService {
    public static mock(serviceName: string, methods: string[]): any {
      return jasmine.createSpyObj(serviceName, methods);
    }
  }