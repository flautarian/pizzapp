import { Injectable } from '@angular/core';
import { webSocket } from 'rxjs/webSocket';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private socket$ = webSocket('ws://localhost:8090/ws');

  constructor() {}

  getUpdates(): Observable<any> {
    return this.socket$;
  }
}
