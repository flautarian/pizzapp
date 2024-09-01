import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { WebSocketService } from '@services/WebSocketService';

@Component({
  selector: 'app-backend',
  templateUrl: './backend.component.html',
  styleUrls: ['./backend.component.css']
})
export class BackendComponent { 

    constructor(
        private http: HttpClient,
        private webSocketService: WebSocketService) { }

    orders: any[] = [];

    ngOnInit(): void {
        this.webSocketService.getUpdates().subscribe(update => {
            // Handle the update, e.g., push it to a data array
            this.orders.push(update);
        });
    }
}
